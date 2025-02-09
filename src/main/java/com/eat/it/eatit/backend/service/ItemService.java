package com.eat.it.eatit.backend.service;

import com.eat.it.eatit.backend.data.Item;
import com.eat.it.eatit.backend.dto.ItemDTO;
import com.eat.it.eatit.backend.enums.Comparator;
import com.eat.it.eatit.backend.enums.ItemType;
import com.eat.it.eatit.backend.enums.Macros;
import com.eat.it.eatit.backend.mapper.ItemMapper;
import com.eat.it.eatit.backend.repository.ItemRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.eat.it.eatit.backend.utils.UtilsKt.updateField;
import static com.eat.it.eatit.backend.mapper.ItemMapper.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service class for managing items.
 */
@Service
public class ItemService {

    ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    /**
     * Retrieves an ItemDTO by its ID.
     *
     * @param id the ID of the item to be retrieved
     * @return an ItemDTO if found; otherwise, null
     */
    public ItemDTO getItemById(Long id) {
        Item item = itemRepository.findById(id).orElse(null);
        if (item == null) {
            return null;
        }
        return toDTO(item);
    }

    /**
     * Retrieves all items from the repository, converts them to DTOs, and returns them in the response.
     *
     * @return a list of ItemDTO objects representing all items.
     */
    public List<ItemDTO> getAllItems() {
        List<Item> items = itemRepository.findAll();
        List<ItemDTO> itemDTOList = new ArrayList<>();
        for (Item i : items) {
            itemDTOList.add(toDTO(i));
        }
        return itemDTOList;
    }

    /**
     * Retrieves an item by its name.
     *
     * @param name the name of the item to retrieve
     * @return an ItemDTO if found, or null if the item does not exist
     */
    public ItemDTO getItemByName(String name) {
        Optional<Item> item = itemRepository.findByNameIgnoreCase(name);
        return item.map(ItemMapper::toDTO).orElse(null);
    }

    /**
     * Retrieves a list of items where the item names contain the given name, ignoring case.
     *
     * @param name the name to search for within item names
     * @return a list of ItemDTO objects containing the specified name
     */
    public List<ItemDTO> getAllItemsContainingName(String name) {
        return toDTOList(itemRepository.findAllByNameContainsIgnoreCase(name));
    }

    /**
     * Retrieves an item by its barcode.
     *
     * @param barcode the barcode of the item to retrieve
     * @return the ItemDTO of the retrieved item, or null if the item does not exist
     */
    public ItemDTO getItemByBarcode(Long barcode) {
        Item item = itemRepository.findByBarcode(barcode);
        if (item == null) {
            return null;
        }
        return toDTO(item);
    }

    /**
     * Adds a new item to the repository and returns the saved item as a DTO.
     *
     * @param item the ItemDTO object containing item details to be added
     * @return the saved ItemDTO
     */
    @Transactional
    public ItemDTO addNewItem(ItemDTO item) {
        Item newItem = toEntity(item);
        itemRepository.save(newItem);
        return toDTO(newItem);
    }

    /**
     * Updates an existing item record with the provided details in the form of an ItemDTO.
     * If the item with the specified id does not exist, returns null.
     * Otherwise, updates the item with the new values and returns the updated ItemDTO.
     *
     * @param id      the unique identifier of the item to be updated
     * @param itemDTO the data transfer object containing the updated values
     **/
    @Transactional
    public ItemDTO updateItem(Long id, ItemDTO itemDTO) {
        Item item = findItemById(id);
        if (item == null) {
            return null;
        }
        updateField(itemDTO.getName(), item::setName);
        updateField(itemDTO.getBarcode(), item::setBarcode);
        updateField(itemDTO.getCaloriesPer100g(), item::setCaloriesPer100g);
        updateField(itemDTO.getProteins(), item::setProteins);
        updateField(itemDTO.getFats(), item::setFats);
        updateField(itemDTO.getCarbs(), item::setCarbs);
        updateField(itemDTO.getItemType(), item::setItemType);
        Item saved = itemRepository.save(item);
        return toDTO(saved);
    }

    @Transactional
    public boolean updateItemInfo(Long id, String name, Long barcode, ItemType itemType) {
        Item item = findItemById(id);
        if (item == null) {
            return false;
        }
        updateField(name, item::setName);
        updateField(barcode, item::setBarcode);
        updateField(itemType, item::setItemType);
        itemRepository.save(item);
        return true;
    }

    @Transactional
    public boolean updateItemNutrition(Long id, Double calories, Double proteins, Double fats, Double carbs) {
        Item item = findItemById(id);
        if (item == null) {
            return false;
        }
        updateField(calories, item::setCaloriesPer100g);
        updateField(proteins, item::setProteins);
        updateField(fats, item::setFats);
        updateField(carbs, item::setCarbs);
        itemRepository.save(item);
        return true;
    }

    /**
     * Deletes an item by its ID.
     *
     * @param id the ID of the item to be deleted
     * @return a boolean indicating the result of the delete operation;
     * true if the item is deleted successfully, false if the item is not found
     */
    @Transactional
    public boolean deleteItemById(Long id) {
        Item item = findItemById(id);
        if (item == null) {
            return false;
        }
        itemRepository.deleteById(id);
        return true;
    }

    /**
     * Retrieves a list of items filtered by a specified range of macronutrient values.
     *
     * @param minValue The minimum value of the macronutrient range.
     * @param maxValue The maximum value of the macronutrient range.
     * @param macros   The macronutrient
     * @return a list of ItemDTOs that fall within the specified range of macronutrient values
     */
    public List<ItemDTO> getItemsFilteredByMacrosInRange(Double minValue, Double maxValue, Macros macros) {
        return switch (macros) {
            case CALORIES -> toDTOList(itemRepository.findAllByCaloriesPer100gBetween(minValue, maxValue));
            case FATS -> toDTOList(itemRepository.findAllByFatsBetween(minValue, maxValue));
            case PROTEINS -> toDTOList(itemRepository.findAllByProteinsBetween(minValue, maxValue));
            case CARBS -> toDTOList(itemRepository.findAllByCarbsBetween(minValue, maxValue));
        };
    }

    /**
     * Retrieves items filtered by the percentage of specific macronutrients.
     *
     * @param minPercentage the minimum percentage of the specific macronutrient.
     * @param maxPercentage the maximum percentage of the specific macronutrient.
     * @param macros        the type of macronutrient to filter by (FATS, PROTEINS, CARBS).
     * @return a list of ItemDTOs that fall within the specified percentage range for the macronutrient
     */
    public List<ItemDTO> getItemsFilteredByMacrosPercentage(Double minPercentage, Double maxPercentage, Macros macros) {
        if (minPercentage > 100 || maxPercentage > 100 || macros == Macros.CALORIES) {
            return null;
        }

        List<ItemDTO> items = toDTOList(itemRepository.findAllByCaloriesPer100gNotNull());
        return filterItemsByMacrosPercentage(items, minPercentage, maxPercentage, macros);
    }

    /**
     * Retrieves items filtered by the specified macronutrient criteria.
     *
     * @param value      The value to filter the items by.
     * @param macros     The macronutrient to filter the items on (e.g., CALORIES, FATS, PROTEINS, CARBS).
     * @param comparator The comparison method to apply (e.g., GREATER_THAN_OR_EQUAL, LESS_THAN_OR_EQUAL).
     * @return a list of ItemDTOs that match the specified macronutrient criteria
     */
    public List<ItemDTO> getItemsFilteredByMacros(Double value, Macros macros, Comparator comparator) {
        return switch (macros) {
            case CALORIES -> getItemsFilteredByCalories(value, comparator);
            case FATS -> getItemsFilteredByFats(value, comparator);
            case PROTEINS -> getItemsFilteredByProteins(value, comparator);
            case CARBS -> getItemsFilteredByCarbs(value, comparator);
        };
    }

    /**
     * Retrieves a list of items filtered by the given item types.
     *
     * @param types the list of item types to filter by
     * @return a list of ItemDTOs matching the given item types
     */
    public List<ItemDTO> getItemsByTypes(Set<ItemType> types) {
        List<Item> items = itemRepository.findAllByItemTypeIn(types);
        return toDTOList(items);
    }

    /**
     * Finds an item based on its ID.
     *
     * @param id the ID of the item to be found
     * @return the item if found, otherwise null
     */
    public Item findItemById(Long id) {
        return itemRepository.findById(id).orElse(null);
    }

    @Transactional
    public Item createNewItem(ItemDTO itemDTO) {
        Item existingItem = findItemByName(itemDTO.getName());
        if (existingItem != null) {
            return existingItem;
        }
        Item item = toEntity(itemDTO);
        itemRepository.save(item);
        return item;
    }

    public Item findItemByName(String name) {
        return itemRepository.findByNameIgnoreCase(name).orElse(null);
    }

    /**
     * Filters items based on their calorie content according to the specified comparator.
     *
     * @param value      The calorie value to compare against.
     * @param comparator The comparator to use for filtering items (e.g., GREATER_THAN_OR_EQUAL or LESS_THAN_OR_EQUAL).
     * @return A set of ItemDTOs that match the filtering criteria.
     */
    private List<ItemDTO> getItemsFilteredByCalories(Double value, Comparator comparator) {
        return switch (comparator) {
            case GREATER_THAN_OR_EQUAL -> toDTOList(itemRepository.findAllByCaloriesPer100gIsGreaterThanEqual(value));
            case LESS_THAN_OR_EQUAL -> toDTOList(itemRepository.findAllByCaloriesPer100gIsLessThanEqual(value));
            default -> new ArrayList<>(); // Default case returns an empty list
        };
    }

    /**
     * Filters items by their carbohydrate content based on the provided comparator.
     *
     * @param value      The value to compare against the carbohydrate content of the items.
     * @param comparator The comparator to determine the filter condition (greater than or equal to, less than or equal to).
     * @return A list of ItemDTOs that match the specified carbohydrate filter criteria.
     */
    private List<ItemDTO> getItemsFilteredByCarbs(Double value, Comparator comparator) {
        return switch (comparator) {
            case GREATER_THAN_OR_EQUAL -> toDTOList(itemRepository.findAllByCarbsIsGreaterThanEqual(value));
            case LESS_THAN_OR_EQUAL -> toDTOList(itemRepository.findAllByCarbsIsLessThanEqual(value));
            default -> new ArrayList<>(); // Default case returns an empty list
        };
    }

    /**
     * Filters items based on their protein content using the specified comparator.
     *
     * @param value      the protein value to be used for filtering items.
     * @param comparator the comparator to determine whether to filter items with proteins
     *                   greater than or equal to, or less than or equal to the given value.
     * @return a list of ItemDTOs filtered by the specified protein value and comparator.
     */
    private List<ItemDTO> getItemsFilteredByProteins(Double value, Comparator comparator) {
        return switch (comparator) {
            case GREATER_THAN_OR_EQUAL -> toDTOList(itemRepository.findAllByProteinsIsGreaterThanEqual(value));
            case LESS_THAN_OR_EQUAL -> toDTOList(itemRepository.findAllByProteinsIsLessThanEqual(value));
            default -> new ArrayList<>(); // Default case returns an empty list
        };
    }

    /**
     * Retrieves a list of items filtered based on their fat content per 100 grams.
     *
     * @param value      The fat content value to filter the items by.
     * @param comparator The comparison operation to apply (e.g., GREATER_THAN_OR_EQUAL, LESS_THAN_OR_EQUAL).
     * @return A list of items that match the specified fat content criteria, converted into ItemDTO objects.
     */
    private List<ItemDTO> getItemsFilteredByFats(Double value, Comparator comparator) {
        return switch (comparator) {
            case GREATER_THAN_OR_EQUAL -> toDTOList(itemRepository.findAllByFatsIsGreaterThanEqual(value));
            case LESS_THAN_OR_EQUAL -> toDTOList(itemRepository.findAllByFatsIsLessThanEqual(value));
            default -> new ArrayList<>(); // Default case returns an empty list
        };
    }


    /**
     * Filters a list of items based on the percentage of a specific macronutrient (FATS, PROTEINS, or CARBS) within a given range.
     *
     * @param items         The list of ItemDTO objects to filter.
     * @param minPercentage The minimum percentage of the specified macronutrient.
     * @param maxPercentage The maximum percentage of the specified macronutrient.
     * @param macros        The macronutriment
     */
    private List<ItemDTO> filterItemsByMacrosPercentage(List<ItemDTO> items, Double minPercentage, Double maxPercentage, Macros macros) {
        return items.stream()
                .map(item -> new ItemWithMacrosPercentage(item, getMacrosPercentage(item, macros)))
                .filter(item -> item.macrosPercentage >= minPercentage && item.macrosPercentage <= maxPercentage)
                .map(ItemWithMacrosPercentage::getItem)
                .collect(Collectors.toList());
    }

    private Double getMacrosPercentage(ItemDTO item, Macros macros) {
        double perc;
        switch (macros) {
            case FATS -> perc = ((item.getFats() * 9) / item.getCaloriesPer100g()) * 100;
            case PROTEINS -> perc = ((item.getProteins() * 4) / item.getCaloriesPer100g()) * 100;
            case CARBS -> perc = ((item.getCarbs() * 4) / item.getCaloriesPer100g()) * 100;
            default -> throw new IllegalStateException("Unexpected value: " + macros);
        }
        return perc;
    }

    @Data
    private static class ItemWithMacrosPercentage {
        ItemDTO item;
        Double macrosPercentage;

        public ItemWithMacrosPercentage(ItemDTO item, Double macrosPercentage) {
            this.item = item;
            this.macrosPercentage = macrosPercentage;
        }
    }

}

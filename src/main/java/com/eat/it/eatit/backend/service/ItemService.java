package com.eat.it.eatit.backend.service;

import com.eat.it.eatit.backend.data.Item;
import com.eat.it.eatit.backend.dto.ItemDTO;
import com.eat.it.eatit.backend.enums.Comparator;
import com.eat.it.eatit.backend.enums.Macros;
import com.eat.it.eatit.backend.mapper.ItemMapper;
import com.eat.it.eatit.backend.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static com.eat.it.eatit.backend.utils.UtilsKt.updateField;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
     * Retrieves an item by its unique identifier.
     *
     * @param id the unique identifier of the item to be retrieved
     * @return a ResponseEntity containing the ItemDTO if found, or a ResponseEntity with not found status if the item does not exist
     */
    public ResponseEntity<ItemDTO> getItemById(Long id) {
        Item item = itemRepository.findById(id).orElse(null);
        if (item == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ItemMapper.toDTO(item));
    }

    /**
     * Retrieves all items from the repository and converts them to DTOs.
     *
     * @return A ResponseEntity containing a list of ItemDTOs.
     */
    public ResponseEntity<List<ItemDTO>> getAllItems() {
        List<Item> items = itemRepository.findAll();
        List<ItemDTO> itemDTOList = new ArrayList<>();
        for (Item i : items) {
            itemDTOList.add(ItemMapper.toDTO(i));
        }
        return ResponseEntity.ok(itemDTOList);
    }

    /**
     * Fetches an item by its name from the repository and converts it to a DTO.
     *
     * @param name The name of the item to fetch.
     * @return A ResponseEntity containing the ItemDTO if found, or a 404 Not Found status if not found.
     */
    public ResponseEntity<ItemDTO> getItemByName(String name) {
        Item item = itemRepository.findByNameIgnoreCase(name);
        if (item == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ItemMapper.toDTO(item));
    }

    /**
     * Retrieves a set of ItemDTO objects whose names contain the specified string, ignoring case.
     *
     * @param name the string to search for within item names
     * @return a set of ItemDTOs that match the search criteria
     */
    public Set<ItemDTO> getAllItemsContainingName(String name) {
        return ItemMapper.toDTOSet(itemRepository.findAllByNameContainsIgnoreCase(name));
    }

    /**
     * Retrieves an item by its barcode.
     *
     * @param barcode The barcode of the item to retrieve.
     * @return A ResponseEntity containing the item data transfer object (DTO) if found, or a not found status if the item does not exist.
     */
    public ResponseEntity<ItemDTO> getItemByBarcode(Long barcode) {
        Item item = itemRepository.findByBarcode(barcode);
        if (item == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ItemMapper.toDTO(item));
    }

    /**
     * Adds a new item to the repository.
     *
     * @param item The ItemDTO object containing the details of the item to be added.
     * @return ResponseEntity containing the added ItemDTO object.
     */
    public ResponseEntity<ItemDTO> addNewItem(ItemDTO item) {
        Item newItem = ItemMapper.toEntity(item);
        Item savedItem = itemRepository.save(newItem);
        return ResponseEntity.ok(ItemMapper.toDTO(savedItem));
    }

    /**
     * Updates an existing item with the given details.
     *
     * @param id      the ID of the item to be updated
     * @param itemDTO the data transfer object containing new details for the item
     * @return a ResponseEntity containing the updated ItemDTO if the update is successful,
     * or a ResponseEntity with a not found status if the item does not exist
     */
    public ResponseEntity<ItemDTO> updateItem(Long id, ItemDTO itemDTO) {
        Item item = findItem(id);
        if (item == null) {
            return ResponseEntity.notFound().build();
        }
        updateField(itemDTO.getName(), item::setName);
        updateField(itemDTO.getBarcode(), item::setBarcode);
        updateField(itemDTO.getCaloriesPer100g(), item::setCaloriesPer100g);
        updateField(itemDTO.getProteins(), item::setProteins);
        updateField(itemDTO.getFatPer100G(), item::setFatPer100G);
        updateField(itemDTO.getCarbsPer100G(), item::setCarbsPer100G);
        updateField(itemDTO.getItemType(), item::setItemType);
        itemRepository.save(item);
        return ResponseEntity.ok(ItemMapper.toDTO(item));
    }

    /**
     * Deletes an item by its unique identifier.
     *
     * @param id the unique identifier of the item to be deleted
     * @return a ResponseEntity with an appropriate HTTP status code:
     * OK if the item was successfully deleted, or NOT FOUND if the item does not exist
     */
    public ResponseEntity<Void> deleteItemById(Long id) {
        Item item = findItem(id);
        if (item == null) {
            return ResponseEntity.notFound().build();
        }
        itemRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Retrieves a set of ItemDTO objects filtered by a specified macronutrient (e.g., calories, fats, proteins, carbs)
     * within a range based on the minimum and maximum values.
     *
     * @param minValue The minimum threshold value used for filtering the items.
     * @param maxValue The maximum threshold value used for filtering the items.
     * @param macros   The macronutrient by which to filter the items (calories, fats, proteins, carbs).
     * @return A ResponseEntity containing a set of filtered ItemDTO objects.
     */
    public ResponseEntity<Set<ItemDTO>> getItemsFilteredByMacrosInRange(Double minValue, Double maxValue, Macros macros) {
        return switch (macros) {
            case CALORIES ->
                    ResponseEntity.ok(ItemMapper.toDTOSet(itemRepository.findAllByCaloriesPer100gBetween(minValue, maxValue)));
            case FATS ->
                    ResponseEntity.ok(ItemMapper.toDTOSet(itemRepository.findAllByFatPer100GBetween(minValue, maxValue)));
            case PROTEINS ->
                    ResponseEntity.ok(ItemMapper.toDTOSet(itemRepository.findAllByProteinsBetween(minValue, maxValue)));
            case CARBS ->
                    ResponseEntity.ok(ItemMapper.toDTOSet(itemRepository.findAllByCarbsPer100GBetween(minValue, maxValue)));
        };
    }


    /**
     * Retrieves a set of ItemDTO objects filtered by a specified macronutrient (e.g., fats, proteins, carbs)
     * based on a percentage range specified by minimum and maximum values.
     *
     * @param minValue The minimum percentage value used for filtering the items.
     * @param maxValue The maximum percentage value used for filtering the items.
     * @param macros   The macronutrient by which to filter the items (fats, proteins, carbs).
     * @return A ResponseEntity containing a set of filtered ItemDTO objects.
     */
    public ResponseEntity<Set<ItemDTO>> getItemsFilteredByMacrosPercentage(Double minValue, Double maxValue, Macros macros) {

        if (minValue > 100 || maxValue > 100 || macros == Macros.CALORIES) {
            return ResponseEntity.badRequest().build();
        }

        double macrosCaloriesMin = 0.0;
        double macrosCaloriesMax = 0.0;
        switch (macros) {
            case FATS -> {
                macrosCaloriesMin = minValue * 9.0;
                macrosCaloriesMax = maxValue * 9.0;
            }
            case CARBS, PROTEINS -> {
                macrosCaloriesMin = minValue * 4.0;
                macrosCaloriesMax = maxValue * 4.0;
            }
        }

        return switch (macros) {
            case FATS -> getItemsFilteredByMacrosInRange(macrosCaloriesMin, macrosCaloriesMax, Macros.FATS);
            case PROTEINS -> getItemsFilteredByMacrosInRange(macrosCaloriesMin, macrosCaloriesMax, Macros.PROTEINS);
            case CARBS -> getItemsFilteredByMacrosInRange(macrosCaloriesMin, macrosCaloriesMax, Macros.CARBS);
            default -> ResponseEntity.noContent().build();
        };
    }


    /**
     * Retrieves a set of ItemDTO objects filtered by a specified macronutrient (e.g., calories, fats, proteins, carbs)
     * based on a comparison operator and threshold value.
     *
     * @param value      The threshold value used for filtering the items.
     * @param macros     The macronutrient by which to filter the items (calories, fats, proteins, carbs).
     * @param comparator The comparison operator used to apply the threshold (e.g., GREATER_THAN_OR_EQUAL, LESS_THAN_OR_EQUAL).
     * @return A ResponseEntity containing a set of filtered ItemDTO objects.
     */
    public ResponseEntity<Set<ItemDTO>> getItemsFilteredByMacros(Double value, Macros macros, Comparator comparator) {
        return switch (macros) {
            case CALORIES -> ResponseEntity.ok(getItemsFilteredByCalories(value, comparator));
            case FATS -> ResponseEntity.ok(getItemsFilteredByFats(value, comparator));
            case PROTEINS -> ResponseEntity.ok(getItemsFilteredByProteins(value, comparator));
            case CARBS -> ResponseEntity.ok(getItemsFilteredByCarbs(value, comparator));
        };
    }

    /**
     * Retrieves a set of ItemDTO objects filtered by calorie content.
     *
     * @param value      the calorie value to compare against
     * @param comparator the comparison operator to use (e.g. GREATER_THAN_OR_EQUAL, LESS_THAN_OR_EQUAL)
     * @return a set of ItemDTO objects that match the specified calorie filter criteria
     */
    private Set<ItemDTO> getItemsFilteredByCalories(Double value, Comparator comparator) {
        return switch (comparator) {
            case GREATER_THAN_OR_EQUAL ->
                    ItemMapper.toDTOSet(itemRepository.findAllByCaloriesPer100gIsGreaterThanEqual(value));
            case LESS_THAN_OR_EQUAL ->
                    ItemMapper.toDTOSet(itemRepository.findAllByCaloriesPer100gIsLessThanEqual(value));
            default -> new HashSet<>(); // Default case returns an empty set
        };
    }

    /**
     * Filters items based on their carbohydrate content and returns them as a set of ItemDTOs.
     *
     * @param value      the carbohydrate value to compare against.
     * @param comparator the comparison operator used to filter the items (e.g., GREATER_THAN_OR_EQUAL, LESS_THAN_OR_EQUAL).
     * @return a set of ItemDTOs that match the specified carbohydrate filter criteria.
     */
    private Set<ItemDTO> getItemsFilteredByCarbs(Double value, Comparator comparator) {
        return switch (comparator) {
            case GREATER_THAN_OR_EQUAL ->
                    ItemMapper.toDTOSet(itemRepository.findAllByCarbsPer100GIsGreaterThanEqual(value));
            case LESS_THAN_OR_EQUAL -> ItemMapper.toDTOSet(itemRepository.findAllByCarbsPer100GIsLessThanEqual(value));
            default -> new HashSet<>(); // Default case returns an empty set
        };
    }

    /**
     * Filters items based on their protein content using a specified comparison operator and value.
     *
     * @param value      the reference value of proteins to filter items by
     * @param comparator the comparison operator to use for filtering (e.g., GREATER_THAN_OR_EQUAL, LESS_THAN_OR_EQUAL)
     * @return a set of ItemDTO objects that match the filtering criteria
     */
    private Set<ItemDTO> getItemsFilteredByProteins(Double value, Comparator comparator) {
        return switch (comparator) {
            case GREATER_THAN_OR_EQUAL ->
                    ItemMapper.toDTOSet(itemRepository.findAllByProteinsIsGreaterThanEqual(value));
            case LESS_THAN_OR_EQUAL -> ItemMapper.toDTOSet(itemRepository.findAllByProteinsIsLessThanEqual(value));
            default -> new HashSet<>(); // Default case returns an empty set
        };
    }

    /**
     * Retrieves a set of ItemDTOs filtered by fat content based on the specified value and comparison operator.
     *
     * @param value      the fat content value to compare against
     * @param comparator the comparison operator to use (GREATER_THAN_OR_EQUAL, LESS_THAN_OR_EQUAL)
     * @return a set of ItemDTOs that match the specified fat content criteria
     */
    private Set<ItemDTO> getItemsFilteredByFats(Double value, Comparator comparator) {
        return switch (comparator) {
            case GREATER_THAN_OR_EQUAL ->
                    ItemMapper.toDTOSet(itemRepository.findAllByFatPer100GIsGreaterThanEqual(value));
            case LESS_THAN_OR_EQUAL -> ItemMapper.toDTOSet(itemRepository.findAllByFatPer100GIsLessThanEqual(value));
            default -> new HashSet<>(); // Default case returns an empty set
        };
    }

    /**
     * Finds an item by its unique identifier.
     *
     * @param id the unique identifier of the item to be retrieved
     * @return the item if found, otherwise null
     */
    private Item findItem(Long id) {
        return itemRepository.findById(id).orElse(null);
    }


    // TODO:
    //  znajdowanie po wartościach odżywczych jako procencie kalorii,
    //  znajdowanie po typie, znajdowanie wielu typów (mozna chyba jednym jak sie zrobi dobrze w repo metode)
}

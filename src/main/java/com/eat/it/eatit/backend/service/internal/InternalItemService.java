package com.eat.it.eatit.backend.service.internal;

import com.eat.it.eatit.backend.data.Item;
import com.eat.it.eatit.backend.dto.ItemDTO;
import com.eat.it.eatit.backend.enums.ItemType;
import com.eat.it.eatit.backend.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.eat.it.eatit.backend.mapper.ItemMapper.*;
import static com.eat.it.eatit.backend.utils.UtilsKt.updateField;

/**
 * Service class for managing items.
 */
@Service
public class InternalItemService {

    ItemRepository itemRepository;

    @Autowired
    public InternalItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Transactional
    public ItemDTO addNewItem(ItemDTO item) {
        Item newItem = toEntity(item);
        itemRepository.save(newItem);
        return toDTO(newItem);
    }

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
     * Finds an item based on its ID.
     *
     * @param id the ID of the item to be found
     * @return the item if found, otherwise null
     */
    public Item findItemById(Long id) {
        return itemRepository.findById(id).orElse(null);
    }

}

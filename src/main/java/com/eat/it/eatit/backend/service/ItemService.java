package com.eat.it.eatit.backend.service;

import com.eat.it.eatit.backend.data.Item;
import com.eat.it.eatit.backend.dto.ItemDTO;
import com.eat.it.eatit.backend.mapper.ItemMapper;
import com.eat.it.eatit.backend.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    // TODO: delete,
    //  update wybranych pól,
    //  znajdowanie po wartościach odżywczych (może być procentowy udział względem kalorii, może być ogólnie np >10g białka/100g),
    //  znajdowanie po typie, znajdowanie wielu typów (mozna chyba jednym jak sie zrobi dobrze w repo metode)
}

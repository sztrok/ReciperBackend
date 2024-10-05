package com.eat.it.eatit.backend.components;

import com.eat.it.eatit.backend.data.Item;
import com.eat.it.eatit.backend.dto.ItemDTO;
import com.eat.it.eatit.backend.mappers.ItemMapper;
import com.eat.it.eatit.backend.repositories.ItemRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class ItemHandler {

    ItemRepository itemRepository;
    EntityManager entityManager;
    @Autowired
    public ItemHandler(ItemRepository itemRepository, EntityManager entityManager) {
        this.itemRepository = itemRepository;
        this.entityManager = entityManager;
    }

    public ItemDTO getItemById(Long id) {
        Item item = itemRepository.findById(id).orElse(null);
        return ItemMapper.toDTO(item);
    }

    public Set<ItemDTO> getAllItems() {
        List<Item> items = itemRepository.findAll();
        Set<ItemDTO> itemDTOSet = new HashSet<>();
        for(Item i : items) {
            itemDTOSet.add(ItemMapper.toDTO(i));
        }
        return itemDTOSet;
    }

    public ItemDTO getItemByName(String name) {
        Item item = itemRepository.findByName(name);
        return ItemMapper.toDTO(item);
    }

    public Set<ItemDTO> getAllItemsContainingName(String name) {
        return ItemMapper.toDTOSet(itemRepository.findAllByNameContains(name));
    }

    public ItemDTO getItemByBarcode(Long barcode) {
        Item item = itemRepository.findByBarcode(barcode);
        return ItemMapper.toDTO(item);
    }


    public ResponseEntity<ItemDTO> addNewItem(ItemDTO item) {
        itemRepository.flush();
        Long newItemId = itemRepository.getMaxId() + 1;
        System.out.println(newItemId);
        Item newItem = ItemMapper.toEntity(item);

        newItem.setId(newItemId);
        System.out.println(newItem.getId());
        itemRepository.save(newItem);
        return new ResponseEntity<>(ItemMapper.toDTO(itemRepository.findById(newItemId).orElse(null)), HttpStatus.OK);
    }


}

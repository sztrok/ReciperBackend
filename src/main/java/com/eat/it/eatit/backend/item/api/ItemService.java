package com.eat.it.eatit.backend.item.api;

import com.eat.it.eatit.backend.item.data.Item;
import com.eat.it.eatit.backend.item.data.ItemDTO;
import com.eat.it.eatit.backend.item.data.ItemMapper;
import com.eat.it.eatit.backend.item.data.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ItemService {

    ItemRepository itemRepository;
    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public ResponseEntity<ItemDTO> getItemById(Long id) {
        Item item = itemRepository.findById(id).orElse(null);
        if(item == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ItemMapper.toDTO(item));
    }

    public ResponseEntity<List<ItemDTO>> getAllItems() {
        List<Item> items = itemRepository.findAll();
        List<ItemDTO> itemDTOList = new ArrayList<>();
        for(Item i : items) {
            itemDTOList.add(ItemMapper.toDTO(i));
        }
        return ResponseEntity.ok(itemDTOList);
    }

    public ResponseEntity<ItemDTO> getItemByName(String name) {
        Item item = itemRepository.findByName(name);
        if(item == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ItemMapper.toDTO(item));
    }

    public Set<ItemDTO> getAllItemsContainingName(String name) {
        return ItemMapper.toDTOSet(itemRepository.findAllByNameContains(name));
    }

    public ResponseEntity<ItemDTO> getItemByBarcode(Long barcode) {
        Item item = itemRepository.findByBarcode(barcode);
        if(item == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ItemMapper.toDTO(item));
    }


    public ResponseEntity<ItemDTO> addNewItem(ItemDTO item) {
        Item newItem = ItemMapper.toEntity(item);
        Item savedItem = itemRepository.save(newItem);
        return ResponseEntity.ok(ItemMapper.toDTO(savedItem));
    }


}

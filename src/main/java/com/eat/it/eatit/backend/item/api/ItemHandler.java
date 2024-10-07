package com.eat.it.eatit.backend.item.api;

import com.eat.it.eatit.backend.item.data.Item;
import com.eat.it.eatit.backend.item.data.ItemDTO;
import com.eat.it.eatit.backend.item.data.ItemMapper;
import com.eat.it.eatit.backend.item.data.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ItemHandler {

    ItemRepository itemRepository;
    @Autowired
    public ItemHandler(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
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
        Item newItem = ItemMapper.toEntity(item);
        itemRepository.save(newItem);
        return new ResponseEntity<>(ItemMapper.toDTO(itemRepository.findById(newItem.getId()).orElse(null)), HttpStatus.OK);
    }


}

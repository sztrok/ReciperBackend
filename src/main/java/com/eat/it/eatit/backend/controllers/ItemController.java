package com.eat.it.eatit.backend.controllers;

import com.eat.it.eatit.backend.data.Item;
import com.eat.it.eatit.backend.dto.ItemDTO;
import com.eat.it.eatit.backend.mappers.ItemMapper;
import com.eat.it.eatit.backend.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/item")
public class ItemController {

    ItemRepository itemRepository;

    @Autowired
    public ItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }


    @GetMapping("/test")
    public String test() {
        return "Success";
    }

    @GetMapping("/get_item/id/{id}")
    public ItemDTO getItemById(@PathVariable Long id) {
        Item item = itemRepository.findById(id).orElse(null);
        return ItemMapper.toDTO(item);
    }

    @GetMapping("/get_all")
    public Set<ItemDTO> getAllItems() {
        List<Item> items = itemRepository.findAll();
        Set<ItemDTO> itemDTOSet = new HashSet<>();
        for(Item i : items) {
            itemDTOSet.add(ItemMapper.toDTO(i));
        }
        return itemDTOSet;
    }

    @GetMapping("/get_item/name/{name}")
    public ItemDTO getItemByName(@PathVariable String name) {
        Item item = itemRepository.findByName(name);
        return ItemMapper.toDTO(item);
    }

    @GetMapping("/get_all/name/{name}")
    public Set<ItemDTO> getAllItemsContainingName(@PathVariable String name) {
        return ItemMapper.toDTOSet(itemRepository.findAllByNameContains(name));
    }

    @GetMapping("/get_item/barcode/{barcode}")
    public ItemDTO getItemByBarcode(@PathVariable Long barcode) {
        Item item = itemRepository.findByBarcode(barcode);
        return ItemMapper.toDTO(item);
    }

}

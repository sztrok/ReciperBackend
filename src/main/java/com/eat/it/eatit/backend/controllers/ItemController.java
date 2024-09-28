package com.eat.it.eatit.backend.controllers;

import com.eat.it.eatit.backend.components.ItemHandler;
import com.eat.it.eatit.backend.dto.ItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/item")
public class ItemController {

    ItemHandler itemHandler;

    @Autowired
    public ItemController(ItemHandler itemHandler) {
        this.itemHandler = itemHandler;
    }


    @GetMapping("/test")
    public String test() {
        return "Success";
    }

    @GetMapping("/get_item/id/{id}")
    public ItemDTO getItemById(@PathVariable Long id) {
        return itemHandler.getItemById(id);
    }

    @GetMapping("/get_all")
    public Set<ItemDTO> getAllItems() {
        return itemHandler.getAllItems();
    }

    @GetMapping("/get_item/name/{name}")
    public ItemDTO getItemByName(@PathVariable String name) {
        return itemHandler.getItemByName(name);
    }

    @GetMapping("/get_all/name/{name}")
    public Set<ItemDTO> getAllItemsContainingName(@PathVariable String name) {
        return itemHandler.getAllItemsContainingName(name);
    }

    @GetMapping("/get_item/barcode/{barcode}")
    public ItemDTO getItemByBarcode(@PathVariable Long barcode) {
        return itemHandler.getItemByBarcode(barcode);
    }

}

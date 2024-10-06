package com.eat.it.eatit.backend.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(value = "/add_item", consumes = "application/json")
    public ResponseEntity<ItemDTO> addNewItem(@RequestBody ItemDTO item) {
        return itemHandler.addNewItem(item);
    }

}

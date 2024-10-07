package com.eat.it.eatit.backend.item.api;

import com.eat.it.eatit.backend.item.data.ItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/item")
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

    @GetMapping("/get/id/{id}")
    public ItemDTO getItemById(@PathVariable Long id) {
        return itemHandler.getItemById(id);
    }

    @GetMapping("/get_all")
    public Set<ItemDTO> getAllItems() {
        return itemHandler.getAllItems();
    }

    @GetMapping("/get/name/{name}")
    public ItemDTO getItemByName(@PathVariable String name) {
        return itemHandler.getItemByName(name);
    }

    @GetMapping("/get_all/name/{name}")
    public Set<ItemDTO> getAllItemsContainingName(@PathVariable String name) {
        return itemHandler.getAllItemsContainingName(name);
    }

    @GetMapping("/get/barcode/{barcode}")
    public ItemDTO getItemByBarcode(@PathVariable Long barcode) {
        return itemHandler.getItemByBarcode(barcode);
    }

    @PostMapping(value = "/add", consumes = "application/json")
    public ResponseEntity<ItemDTO> addNewItem(@RequestBody ItemDTO item) {
        return itemHandler.addNewItem(item);
    }

}

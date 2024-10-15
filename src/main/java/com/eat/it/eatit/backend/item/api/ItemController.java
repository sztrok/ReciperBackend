package com.eat.it.eatit.backend.item.api;

import com.eat.it.eatit.backend.item.data.ItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/item")
public class ItemController {

    ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }


    @GetMapping("/test")
    public String test() {
        return "Success";
    }

    @GetMapping("/get/id/{id}")
    public ResponseEntity<ItemDTO> getItemById(@PathVariable Long id) {
        return itemService.getItemById(id);
    }

    @GetMapping("/get_all")
    public ResponseEntity<List<ItemDTO>> getAllItems() {
        return itemService.getAllItems();
    }

    @GetMapping("/get/name/{name}")
    public ResponseEntity<ItemDTO> getItemByName(@PathVariable String name) {
        return itemService.getItemByName(name);
    }

    @GetMapping("/get_all/name/{name}")
    public Set<ItemDTO> getAllItemsContainingName(@PathVariable String name) {
        return itemService.getAllItemsContainingName(name);
    }

    @GetMapping("/get/barcode/{barcode}")
    public ResponseEntity<ItemDTO> getItemByBarcode(@PathVariable Long barcode) {
        return itemService.getItemByBarcode(barcode);
    }

    @PostMapping(value = "/add", consumes = "application/json")
    public ResponseEntity<ItemDTO> addNewItem(@RequestBody ItemDTO item) {
        return itemService.addNewItem(item);
    }

}

package com.eat.it.eatit.backend.controller;

import com.eat.it.eatit.backend.enums.Comparator;
import com.eat.it.eatit.backend.enums.Macros;
import com.eat.it.eatit.backend.service.ItemService;
import com.eat.it.eatit.backend.dto.ItemDTO;
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

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteItemById(@PathVariable Long id) {
        return itemService.deleteItemById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ItemDTO> updateItem(@PathVariable Long id, @RequestBody ItemDTO itemDTO) {
        return itemService.updateItem(id, itemDTO);
    }

    @GetMapping("/get/macro")
    public ResponseEntity<Set<ItemDTO>> getItemsFilteredByMacros(
            @RequestParam Double value,
            @RequestParam Macros macros,
            @RequestParam Comparator comparator) {
        return itemService.getItemsFilteredByMacros(value, macros, comparator);
    }

    @GetMapping("get/macro/range")
    public ResponseEntity<Set<ItemDTO>> getItemsFilteredByMacrosInRange(
            @RequestParam Double minValue,
            @RequestParam Double maxValue,
            @RequestParam Macros macros) {
        return itemService.getItemsFilteredByMacrosInRange(minValue, maxValue, macros);
    }

    @GetMapping("get/macro/percentage")
    public ResponseEntity<Set<ItemDTO>> getItemsFilteredByMacrosPercentage(
            @RequestParam Double minValue,
            @RequestParam Double maxValue,
            @RequestParam Macros macros) {
        return itemService.getItemsFilteredByMacrosPercentage(minValue, maxValue, macros);
    }


}

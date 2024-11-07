package com.eat.it.eatit.backend.controller;

import com.eat.it.eatit.backend.enums.Comparator;
import com.eat.it.eatit.backend.enums.ItemType;
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
        ItemDTO item = itemService.getItemById(id);
        if (item == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(item);
    }

    @GetMapping("/get_all")
    public ResponseEntity<List<ItemDTO>> getAllItems() {
        List<ItemDTO> items = itemService.getAllItems();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/get/name/{name}")
    public ResponseEntity<ItemDTO> getItemByName(@PathVariable String name) {
        ItemDTO item = itemService.getItemByName(name);
        if (item == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(item);
    }

    @GetMapping("/get_all/name/{name}")
    public Set<ItemDTO> getAllItemsContainingName(@PathVariable String name) {
        return itemService.getAllItemsContainingName(name);
    }

    @GetMapping("/get/barcode/{barcode}")
    public ResponseEntity<ItemDTO> getItemByBarcode(@PathVariable Long barcode) {
        ItemDTO item = itemService.getItemByBarcode(barcode);
        if (item == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(item);
    }

    @PostMapping(value = "/add", consumes = "application/json")
    public ResponseEntity<ItemDTO> addNewItem(@RequestBody ItemDTO item) {
        ItemDTO addedItem = itemService.addNewItem(item);
        return ResponseEntity.ok(addedItem);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteItemById(@PathVariable Long id) {
        boolean res = itemService.deleteItemById(id);
        if (res) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ItemDTO> updateItem(@PathVariable Long id, @RequestBody ItemDTO itemDTO) {
        ItemDTO updatedItem = itemService.updateItem(id, itemDTO);
        if (updatedItem == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(updatedItem);
    }

    @GetMapping("/get/macro")
    public ResponseEntity<Set<ItemDTO>> getItemsFilteredByMacros(
            @RequestParam Double value,
            @RequestParam Macros macros,
            @RequestParam Comparator comparator) {
        Set<ItemDTO> items = itemService.getItemsFilteredByMacros(value, macros, comparator);
        return ResponseEntity.ok(items);
    }

    @GetMapping("/get/macro/range")
    public ResponseEntity<Set<ItemDTO>> getItemsFilteredByMacrosInRange(
            @RequestParam Double minValue,
            @RequestParam Double maxValue,
            @RequestParam Macros macros) {
        Set<ItemDTO> items = itemService.getItemsFilteredByMacrosInRange(minValue, maxValue, macros);
        return ResponseEntity.ok(items);
    }

    @GetMapping("/get/macro/percentage")
    public ResponseEntity<Set<ItemDTO>> getItemsFilteredByMacrosPercentage(
            @RequestParam Double minPercentage,
            @RequestParam Double maxPercentage,
            @RequestParam Macros macros) {
        Set<ItemDTO> items = itemService.getItemsFilteredByMacrosPercentage(minPercentage, maxPercentage, macros);
        return ResponseEntity.ok(items);
    }

    @GetMapping("/get/types")
    public ResponseEntity<Set<ItemDTO>> getItemsByTypes(@RequestBody Set<ItemType> types) {
        Set<ItemDTO> items = itemService.getItemsByTypes(types);
        return ResponseEntity.ok(items);
    }

}

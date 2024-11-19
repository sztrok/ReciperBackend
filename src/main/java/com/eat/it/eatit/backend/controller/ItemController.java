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

    @GetMapping("/{id}")
    public ResponseEntity<ItemDTO> getItemById(@PathVariable Long id) {
        ItemDTO item = itemService.getItemById(id);
        if (item == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(item);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ItemDTO>> getAllItems() {
        List<ItemDTO> items = itemService.getAllItems();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/name")
    public ResponseEntity<ItemDTO> getItemByName(@RequestParam String name) {
        ItemDTO item = itemService.getItemByName(name);
        if (item == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(item);
    }

    @GetMapping("/all/name")
    public Set<ItemDTO> getAllItemsContainingName(@RequestParam String name) {
        return itemService.getAllItemsContainingName(name);
    }

    @GetMapping("/barcode")
    public ResponseEntity<ItemDTO> getItemByBarcode(@RequestParam Long barcode) {
        ItemDTO item = itemService.getItemByBarcode(barcode);
        if (item == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(item);
    }

    @PostMapping(value = "/new", consumes = "application/json")
    public ResponseEntity<ItemDTO> addNewItem(@RequestBody ItemDTO item) {
        ItemDTO addedItem = itemService.addNewItem(item);
        return ResponseEntity.ok(addedItem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItemById(@PathVariable Long id) {
        boolean res = itemService.deleteItemById(id);
        if (res) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemDTO> updateItem(@PathVariable Long id, @RequestBody ItemDTO itemDTO) {
        ItemDTO updatedItem = itemService.updateItem(id, itemDTO);
        if (updatedItem == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(updatedItem);
    }

    @PatchMapping("/info")
    public ResponseEntity<ItemDTO> updateItemInfo(
            @RequestParam Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long barcode,
            @RequestParam(required = false) ItemType itemType
    ) {
        boolean updatedItem = itemService.updateItemInfo(id, name, barcode, itemType);
        if (!updatedItem) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/nutrition")
    public ResponseEntity<ItemDTO> updateItemNutrition(
            @RequestParam Long id,
            @RequestParam(required = false) Double calories,
            @RequestParam(required = false) Double proteins,
            @RequestParam(required = false) Double fats,
            @RequestParam(required = false) Double carbs
    ) {
        boolean updatedItem = itemService.updateItemNutrition(id, calories, proteins, fats, carbs);
        if (!updatedItem) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/macro")
    public ResponseEntity<Set<ItemDTO>> getItemsFilteredByMacros(
            @RequestParam Double value,
            @RequestParam Macros macros,
            @RequestParam Comparator comparator
    ) {
        Set<ItemDTO> items = itemService.getItemsFilteredByMacros(value, macros, comparator);
        return ResponseEntity.ok(items);
    }

    @GetMapping("/macro_range")
    public ResponseEntity<Set<ItemDTO>> getItemsFilteredByMacrosInRange(
            @RequestParam Double minValue,
            @RequestParam Double maxValue,
            @RequestParam Macros macros
    ) {
        Set<ItemDTO> items = itemService.getItemsFilteredByMacrosInRange(minValue, maxValue, macros);
        return ResponseEntity.ok(items);
    }

    @GetMapping("/macro_percentage")
    public ResponseEntity<Set<ItemDTO>> getItemsFilteredByMacrosPercentage(
            @RequestParam Double minPercentage,
            @RequestParam Double maxPercentage,
            @RequestParam Macros macros
    ) {
        Set<ItemDTO> items = itemService.getItemsFilteredByMacrosPercentage(minPercentage, maxPercentage, macros);
        return ResponseEntity.ok(items);
    }

    @GetMapping("/types")
    public ResponseEntity<Set<ItemDTO>> getItemsByTypes(@RequestBody Set<ItemType> types) {
        Set<ItemDTO> items = itemService.getItemsByTypes(types);
        return ResponseEntity.ok(items);
    }

}

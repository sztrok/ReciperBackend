package com.eat.it.eatit.backend.controller.support;

import com.eat.it.eatit.backend.dto.ItemDTO;
import com.eat.it.eatit.backend.enums.Comparator;
import com.eat.it.eatit.backend.enums.ItemType;
import com.eat.it.eatit.backend.enums.Macros;
import com.eat.it.eatit.backend.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/support/item")
public class SupportItemController {

    ItemService itemService;

    @Autowired
    public SupportItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    // ALL
    @GetMapping("/{id}")
    @Operation(summary = "Retrieve an item by its ID")
    @ApiResponse(responseCode = "200", description = "Item retrieved successfully")
    @ApiResponse(responseCode = "400", description = "Invalid Item ID")
    public ResponseEntity<ItemDTO> getItemById(@PathVariable Long id) {
        ItemDTO item = itemService.getItemById(id);
        return item != null
                ? ResponseEntity.ok(item)
                : ResponseEntity.badRequest().build();
    }

    // ALL
    @GetMapping("/all")
    @Operation(summary = "Retrieve all items")
    @ApiResponse(responseCode = "200", description = "All items retrieved successfully")
    public ResponseEntity<List<ItemDTO>> getAllItems() {
        List<ItemDTO> items = itemService.getAllItems();
        return ResponseEntity.ok(items);
    }

    // ALL
    @GetMapping("/name")
    @Operation(summary = "Retrieve an item by its name")
    @ApiResponse(responseCode = "200", description = "Item with the given name retrieved successfully")
    @ApiResponse(responseCode = "400", description = "Invalid Item name")
    public ResponseEntity<ItemDTO> getItemByName(@RequestParam String name) {
        ItemDTO item = itemService.getItemByName(name);
        return item != null
                ? ResponseEntity.ok(item)
                : ResponseEntity.badRequest().build();
    }

    // ALL
    @GetMapping("/all/name")
    @Operation(summary = "Retrieve all items containing a specific name")
    @ApiResponse(responseCode = "200", description = "Items containing the given name retrieved successfully")
    public List<ItemDTO> getAllItemsContainingName(@RequestParam String name) {
        return itemService.getAllItemsContainingName(name);
    }

    // ALL
    @GetMapping("/barcode")
    @Operation(summary = "Retrieve an item by its barcode")
    @ApiResponse(responseCode = "200", description = "Item with the given barcode retrieved successfully")
    @ApiResponse(responseCode = "400", description = "Invalid Barcode")
    public ResponseEntity<ItemDTO> getItemByBarcode(@RequestParam Long barcode) {
        ItemDTO item = itemService.getItemByBarcode(barcode);
        return item != null
                ? ResponseEntity.ok(item)
                : ResponseEntity.badRequest().build();
    }

    // ALL
    @PostMapping(consumes = "application/json")
    @Operation(summary = "Add a new item")
    @ApiResponse(responseCode = "200", description = "New item added successfully")
    public ResponseEntity<ItemDTO> addNewItem(@RequestBody ItemDTO item) {
        ItemDTO addedItem = itemService.addNewItem(item);
        return ResponseEntity.ok(addedItem);
    }

    //TODO: poprawić, bo jest jako klucz obcy w fridge i recipe, więc z tamtąd też by tzeba usunąć
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an item by its ID")
    @ApiResponse(responseCode = "200", description = "Item deleted successfully")
    @ApiResponse(responseCode = "400", description = "Invalid Item ID")
    public ResponseEntity<Void> deleteItemById(@PathVariable Long id) {
        return itemService.deleteItemById(id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an item by its ID")
    @ApiResponse(responseCode = "200", description = "Item updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid Item ID or data")
    public ResponseEntity<ItemDTO> updateItem(@PathVariable Long id, @RequestBody ItemDTO itemDTO) {
        ItemDTO updatedItem = itemService.updateItem(id, itemDTO);
        return updatedItem != null
                ? ResponseEntity.ok(updatedItem)
                : ResponseEntity.badRequest().build();
    }

    @PatchMapping("/{id}/info")
    @Operation(summary = "Update an item's general information")
    @ApiResponse(responseCode = "200", description = "Item information updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid update data")
    public ResponseEntity<ItemDTO> updateItemInfo(
            @PathVariable Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long barcode,
            @RequestParam(required = false) ItemType itemType
    ) {
        return itemService.updateItemInfo(id, name, barcode, itemType)
                ? ResponseEntity.ok().build()
                : ResponseEntity.badRequest().build();
    }

    @PatchMapping("/{id}/nutrition")
    @Operation(summary = "Update an item's nutrition details")
    @ApiResponse(responseCode = "200", description = "Item nutrition details updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid nutrition data")
    public ResponseEntity<ItemDTO> updateItemNutrition(
            @PathVariable Long id,
            @RequestParam(required = false) Double calories,
            @RequestParam(required = false) Double proteins,
            @RequestParam(required = false) Double fats,
            @RequestParam(required = false) Double carbs
    ) {
        return itemService.updateItemNutrition(id, calories, proteins, fats, carbs)
                ? ResponseEntity.ok().build()
                : ResponseEntity.badRequest().build();
    }

    @GetMapping("/macro")
    @Operation(summary = "Filter items by macro values")
    @ApiResponse(responseCode = "200", description = "Items filtered by macros retrieved successfully")
    public ResponseEntity<List<ItemDTO>> getItemsFilteredByMacros(
            @RequestParam Double value,
            @RequestParam Macros macros,
            @RequestParam Comparator comparator
    ) {
        List<ItemDTO> items = itemService.getItemsFilteredByMacros(value, macros, comparator);
        return ResponseEntity.ok(items);
    }

    @GetMapping("/macro_range")
    @Operation(summary = "Filter items by macro values within a range")
    @ApiResponse(responseCode = "200", description = "Items filtered by macros within range retrieved successfully")
    public ResponseEntity<List<ItemDTO>> getItemsFilteredByMacrosInRange(
            @RequestParam Double minValue,
            @RequestParam Double maxValue,
            @RequestParam Macros macros
    ) {
        List<ItemDTO> items = itemService.getItemsFilteredByMacrosInRange(minValue, maxValue, macros);
        return ResponseEntity.ok(items);
    }

    @GetMapping("/macro_percentage")
    @Operation(summary = "Filter items by macro percentage values")
    @ApiResponse(responseCode = "200", description = "Items filtered by macros percentage retrieved successfully")
    public ResponseEntity<List<ItemDTO>> getItemsFilteredByMacrosPercentage(
            @RequestParam Double minPercentage,
            @RequestParam Double maxPercentage,
            @RequestParam Macros macros
    ) {
        List<ItemDTO> items = itemService.getItemsFilteredByMacrosPercentage(minPercentage, maxPercentage, macros);
        return ResponseEntity.ok(items);
    }

    @GetMapping("/types")
    @Operation(summary = "Filter items by their types")
    @ApiResponse(responseCode = "200", description = "Items filtered by types retrieved successfully")
    public ResponseEntity<List<ItemDTO>> getItemsByTypes(@RequestBody Set<ItemType> types) {
        List<ItemDTO> items = itemService.getItemsByTypes(types);
        return ResponseEntity.ok(items);
    }

}

package com.eat.it.eatit.backend.controller.internal;

import com.eat.it.eatit.backend.dto.ItemDTO;
import com.eat.it.eatit.backend.enums.ItemType;
import com.eat.it.eatit.backend.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/internal/item")
@PreAuthorize("hasAuthority('ROLE_SUPPORT')")
public class InternalItemController {

    ItemService itemService;

    @Autowired
    public InternalItemController(ItemService itemService) {
        this.itemService = itemService;
    }

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

}

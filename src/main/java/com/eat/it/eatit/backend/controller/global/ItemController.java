package com.eat.it.eatit.backend.controller.global;

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
@RequestMapping("/api/v1/item")
public class ItemController {

    ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

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

    @GetMapping("/all")
    @Operation(summary = "Retrieve all items")
    @ApiResponse(responseCode = "200", description = "All items retrieved successfully")
    public ResponseEntity<List<ItemDTO>> getAllItems() {
        List<ItemDTO> items = itemService.getAllItems();
        return ResponseEntity.ok(items);
    }

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

    @GetMapping("/all/name")
    @Operation(summary = "Retrieve all items containing a specific name")
    @ApiResponse(responseCode = "200", description = "Items containing the given name retrieved successfully")
    public List<ItemDTO> getAllItemsContainingName(@RequestParam String name) {
        return itemService.getAllItemsContainingName(name);
    }

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

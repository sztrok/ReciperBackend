package com.eat.it.eatit.backend.controller;

import com.eat.it.eatit.backend.enums.Operations;
import com.eat.it.eatit.backend.service.FridgeService;
import com.eat.it.eatit.backend.dto.FridgeDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * NIE MOŻNA ZROBIC DELETE FRIDGE BO JEST ŚCISLE ZWIĄZANA Z ACCOUNT
 */
@RestController
@RequestMapping("/api/v1/fridge")
public class FridgeController {

    FridgeService fridgeService;

    @Autowired
    public FridgeController(FridgeService fridgeService) {
        this.fridgeService = fridgeService;
    }

    // ALL ale user tylko swoją może zobaczyć, wiec moze to jakos rozdzielic?
    @GetMapping("/{id}")
    @Operation(summary = "Retrieve fridge by ID.")
    @ApiResponse(responseCode = "200", description = "Fridge found successfully.")
    @ApiResponse(responseCode = "400", description = "Invalid fridge ID.")
    public ResponseEntity<FridgeDTO> getFridgeById(@PathVariable Long id) {
        FridgeDTO fridge = fridgeService.getFridgeById(id);
        return fridge != null
                ? ResponseEntity.ok(fridge)
                : ResponseEntity.badRequest().build();
    }
    // ADMIN / SUPPORT
    @GetMapping("/all")
    @Operation(summary = "Retrieve all fridges.")
    @ApiResponse(responseCode = "200", description = "All fridges retrieved successfully.")
    public ResponseEntity<List<FridgeDTO>> getAllFridges() {
        List<FridgeDTO> fridges = fridgeService.getAllFridges();
        return ResponseEntity.ok(fridges);
    }

    // ALL ale user tylko do swojej lodowki
    @PostMapping("/item")
    @Operation(summary = "Add item to fridge.")
    @ApiResponse(responseCode = "200", description = "Item added to fridge successfully.")
    @ApiResponse(responseCode = "400", description = "Invalid fridge or item data.")
    public ResponseEntity<FridgeDTO> addItemToFridge(
            @RequestParam Long itemId,
            @RequestParam Long fridgeId,
            @RequestParam Double amount
    ) {
        FridgeDTO fridgeDTO = fridgeService.addItemToFridge(itemId, fridgeId, amount);
        return fridgeDTO != null
                ? ResponseEntity.ok(fridgeDTO)
                : ResponseEntity.badRequest().build();
    }

    @PatchMapping("/item/amount")
    @Operation(summary = "Adjust the amount of an item in the fridge.")
    @ApiResponse(responseCode = "200", description = "Item amount updated successfully.")
    @ApiResponse(responseCode = "400", description = "Invalid operation or data.")
    public ResponseEntity<FridgeDTO> adjustItemAmount(
            @RequestParam Long itemId,
            @RequestParam Long fridgeId,
            @RequestParam Double amount,
            @RequestParam Operations operation
    ) {
        FridgeDTO fridgeDTO;
        if (operation == Operations.ADD) {
            fridgeDTO = fridgeService.increaseItemAmount(itemId, fridgeId, amount);
        } else {
            fridgeDTO = fridgeService.reduceItemAmount(itemId, fridgeId, amount);
        }
        return fridgeDTO != null
                ? ResponseEntity.ok(fridgeDTO)
                : ResponseEntity.badRequest().build();
    }
}

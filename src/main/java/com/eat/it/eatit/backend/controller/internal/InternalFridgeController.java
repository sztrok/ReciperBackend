package com.eat.it.eatit.backend.controller.internal;

import com.eat.it.eatit.backend.dto.FridgeDTO;
import com.eat.it.eatit.backend.enums.Operations;
import com.eat.it.eatit.backend.service.internal.InternalFridgeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * NIE MOŻNA ZROBIC DELETE FRIDGE BO JEST ŚCISLE ZWIĄZANA Z ACCOUNT
 */
@Deprecated
@RestController
@RequestMapping("/api/v1/internal/fridge")
@PreAuthorize("hasAuthority('ROLE_SUPPORT')")
public class InternalFridgeController {

    InternalFridgeService fridgeService;

    @Autowired
    public InternalFridgeController(InternalFridgeService fridgeService) {
        this.fridgeService = fridgeService;
    }

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

    @GetMapping("/account")
    @Operation(summary = "Retrieve fridge by ID.")
    @ApiResponse(responseCode = "200", description = "Fridge found successfully.")
    @ApiResponse(responseCode = "400", description = "Invalid fridge ID.")
    public ResponseEntity<FridgeDTO> getFridgeById(@RequestParam String username) {
        FridgeDTO fridge = fridgeService.getFridgeByAccountName(username);
        return fridge != null
                ? ResponseEntity.ok(fridge)
                : ResponseEntity.badRequest().build();
    }

    @GetMapping("/all")
    @Operation(summary = "Retrieve all fridges.")
    @ApiResponse(responseCode = "200", description = "All fridges retrieved successfully.")
    public ResponseEntity<List<FridgeDTO>> getAllFridges() {
        List<FridgeDTO> fridges = fridgeService.getAllFridges();
        return ResponseEntity.ok(fridges);
    }

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

    @DeleteMapping("/item")
    @Operation(summary = "Remove item from fridge.")
    @ApiResponse(responseCode = "200", description = "Item removed successfully.")
    @ApiResponse(responseCode = "400", description = "Invalid operation or data.")
    public ResponseEntity<FridgeDTO> deleteItemFromFridge(
            @RequestParam Long itemId,
            @RequestParam Long fridgeId
    ) {
        FridgeDTO fridgeDTO = fridgeService.deleteItemFromFridge(itemId, fridgeId);
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
        FridgeDTO fridgeDTO = fridgeService.changeItemAmountInFridge(itemId, fridgeId, amount, operation);
        return fridgeDTO != null
                ? ResponseEntity.ok(fridgeDTO)
                : ResponseEntity.badRequest().build();
    }


}

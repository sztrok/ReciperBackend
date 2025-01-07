package com.eat.it.eatit.backend.controller.user;

import com.eat.it.eatit.backend.dto.FridgeDTO;
import com.eat.it.eatit.backend.enums.Operations;
import com.eat.it.eatit.backend.service.FridgeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@PreAuthorize("hasAuthority('ROLE_USER')")
@RequestMapping("/api/v1/user/fridge")
public class UserFridgeController {

    FridgeService fridgeService;

    @Autowired
    public UserFridgeController(FridgeService fridgeService) {
        this.fridgeService = fridgeService;
    }

    @GetMapping()
    @Operation(summary = "Retrieve fridge by ID.")
    @ApiResponse(responseCode = "200", description = "Fridge found successfully.")
    @ApiResponse(responseCode = "400", description = "Invalid fridge ID.")
    public ResponseEntity<FridgeDTO> getFridgeById(Authentication authentication) {
        Long fridgeId = fridgeService.getFridgeByAccountName(authentication.getName()).getId();
        FridgeDTO fridge = fridgeService.getFridgeById(fridgeId);
        return fridge != null
                ? ResponseEntity.ok(fridge)
                : ResponseEntity.badRequest().build();
    }

    @PostMapping("/item")
    @Operation(summary = "Add item to fridge.")
    @ApiResponse(responseCode = "200", description = "Item added to fridge successfully.")
    @ApiResponse(responseCode = "400", description = "Invalid fridge or item data.")
    public ResponseEntity<FridgeDTO> addItemToFridge(
            Authentication authentication,
            @RequestParam Long itemId,
            @RequestParam Double amount
    ) {
        Long fridgeId = fridgeService.getFridgeByAccountName(authentication.getName()).getId();
        FridgeDTO fridgeDTO = fridgeService.addItemToFridge(itemId, fridgeId, amount);
        return fridgeDTO != null
                ? ResponseEntity.ok(fridgeDTO)
                : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/item")
    @Operation(summary = "Delete item from fridge.")
    @ApiResponse(responseCode = "200", description = "Item deleted from fridge successfully.")
    @ApiResponse(responseCode = "400", description = "Invalid item data.")
    public ResponseEntity<FridgeDTO> deleteItemFromFridge(
            Authentication authentication,
            @RequestParam Long itemId
    ) {
        Long fridgeId = fridgeService.getFridgeByAccountName(authentication.getName()).getId();
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
            Authentication authentication,
            @RequestParam Long itemId,
            @RequestParam Double amount,
            @RequestParam Operations operation
    ) {
        Long fridgeId = fridgeService.getFridgeByAccountName(authentication.getName()).getId();
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

package com.eat.it.eatit.backend.controller.user;

import com.eat.it.eatit.backend.dto.FridgeDTO;
import com.eat.it.eatit.backend.enums.Operations;
import com.eat.it.eatit.backend.service.user.UserFridgeService;
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

    UserFridgeService fridgeService;

    @Autowired
    public UserFridgeController(UserFridgeService fridgeService) {
        this.fridgeService = fridgeService;
    }

    @GetMapping()
    @Operation(summary = "Retrieve fridge for this account.")
    @ApiResponse(responseCode = "200", description = "Fridge found successfully.")
    @ApiResponse(responseCode = "400", description = "Invalid fridge ID.")
    public ResponseEntity<FridgeDTO> getAccountFridge(Authentication authentication) {
        FridgeDTO fridge = fridgeService.getAccountFridge(authentication);
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
        FridgeDTO fridgeDTO = fridgeService.addItemToFridge(authentication, itemId, amount);
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
        FridgeDTO fridgeDTO = fridgeService.deleteItemFromFridge(authentication, itemId, fridgeId);
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
        FridgeDTO fridgeDTO = fridgeService.changeItemAmountInFridge(authentication, itemId, amount, operation);
        return fridgeDTO != null
                ? ResponseEntity.ok(fridgeDTO)
                : ResponseEntity.badRequest().build();
    }
}

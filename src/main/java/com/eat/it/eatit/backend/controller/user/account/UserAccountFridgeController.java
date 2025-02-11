package com.eat.it.eatit.backend.controller.user.account;

import com.eat.it.eatit.backend.dto.simple.FridgeSimpleDTO;
import com.eat.it.eatit.backend.enums.Operations;
import com.eat.it.eatit.backend.service.user.account.UserAccountFridgeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@PreAuthorize("hasAuthority('ROLE_USER')")
@RequestMapping("/pi/v1/user/account/fridge")
public class UserAccountFridgeController {

    UserAccountFridgeService fridgeService;

    @Autowired
    public UserAccountFridgeController(UserAccountFridgeService fridgeService) {
        this.fridgeService = fridgeService;
    }

    @GetMapping()
    @Operation(summary = "Retrieve fridge for this account.")
    @ApiResponse(responseCode = "200", description = "Fridge found successfully.")
    @ApiResponse(responseCode = "400", description = "Invalid fridge ID.")
    public ResponseEntity<FridgeSimpleDTO> getAccountFridge(Authentication authentication) {
        FridgeSimpleDTO fridge = fridgeService.getFridge(authentication);
        return fridge != null
                ? ResponseEntity.ok(fridge)
                : ResponseEntity.badRequest().build();
    }

    @PostMapping("/item")
    @Operation(summary = "Add item to fridge.")
    @ApiResponse(responseCode = "200", description = "Item added to fridge successfully.")
    @ApiResponse(responseCode = "400", description = "Invalid fridge or item data.")
    public ResponseEntity<FridgeSimpleDTO> addItemToFridge(
            Authentication authentication,
            @RequestParam Long itemId,
            @RequestParam Double amount
    ) {
        FridgeSimpleDTO fridgeDTO = fridgeService.addItemToFridge(authentication, itemId, amount);
        return fridgeDTO != null
                ? ResponseEntity.ok(fridgeDTO)
                : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/item")
    @Operation(summary = "Delete item from fridge.")
    @ApiResponse(responseCode = "200", description = "Item deleted from fridge successfully.")
    @ApiResponse(responseCode = "400", description = "Invalid item data.")
    public ResponseEntity<FridgeSimpleDTO> deleteItemFromFridge(
            Authentication authentication,
            @RequestParam Long itemId
    ) {
        FridgeSimpleDTO fridgeDTO = fridgeService.deleteItemFromFridge(authentication, itemId);
        return fridgeDTO != null
                ? ResponseEntity.ok(fridgeDTO)
                : ResponseEntity.badRequest().build();
    }

    @PatchMapping("/item/amount")
    @Operation(summary = "Adjust the amount of an item in the fridge.")
    @ApiResponse(responseCode = "200", description = "Item amount updated successfully.")
    @ApiResponse(responseCode = "400", description = "Invalid operation or data.")
    public ResponseEntity<FridgeSimpleDTO> adjustItemAmount(
            Authentication authentication,
            @RequestParam Long itemId,
            @RequestParam Double amount,
            @RequestParam Operations operation
    ) {
        FridgeSimpleDTO fridgeDTO = fridgeService.changeItemAmountInFridge(authentication, itemId, amount, operation);
        return fridgeDTO != null
                ? ResponseEntity.ok(fridgeDTO)
                : ResponseEntity.badRequest().build();
    }
}

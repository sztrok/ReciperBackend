package com.eat.it.eatit.backend.controller;

import com.eat.it.eatit.backend.service.FridgeService;
import com.eat.it.eatit.backend.dto.FridgeDTO;
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

    @GetMapping("/{id}")
    public ResponseEntity<FridgeDTO> getFridgeById(@PathVariable Long id) {
        FridgeDTO fridge = fridgeService.getFridgeById(id);
        if (fridge == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(fridge);
    }

    @GetMapping("/all")
    public ResponseEntity<List<FridgeDTO>> getAllFridges() {
        List<FridgeDTO> fridges = fridgeService.getAllFridges();
        return ResponseEntity.ok(fridges);
    }

    @PostMapping("/new")
    public ResponseEntity<FridgeDTO> addItemToFridge(
            @RequestParam Long itemId,
            @RequestParam Long fridgeId,
            @RequestParam Double amount
    ) {
        FridgeDTO fridgeDTO = fridgeService.addItemToFridge(itemId, fridgeId, amount);
        if (fridgeDTO == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(fridgeDTO);
    }

    @PatchMapping("/reduce_item_amount")
    public ResponseEntity<FridgeDTO> reduceItemAmount(
            @RequestParam Long itemId,
            @RequestParam Long fridgeId,
            @RequestParam Double amount
    ) {

        FridgeDTO fridgeDTO = fridgeService.reduceItemAmount(itemId, fridgeId, amount);
        if (fridgeDTO == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(fridgeDTO);
    }

    @PatchMapping("/increase_item_amount")
    public ResponseEntity<FridgeDTO> increaseItemAmount(
            @RequestParam Long itemId,
            @RequestParam Long fridgeId,
            @RequestParam Double amount
    ) {

        FridgeDTO fridgeDTO = fridgeService.increaseItemAmount(itemId, fridgeId, amount);
        if (fridgeDTO == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(fridgeDTO);
    }

}

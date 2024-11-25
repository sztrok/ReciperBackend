package com.eat.it.eatit.backend.controller;

import com.eat.it.eatit.backend.enums.Operation;
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
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(fridge);
    }

    @GetMapping("/all")
    public ResponseEntity<List<FridgeDTO>> getAllFridges() {
        List<FridgeDTO> fridges = fridgeService.getAllFridges();
        return ResponseEntity.ok(fridges);
    }

    @PostMapping("/item")
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

    @PatchMapping("/item/amount")
    public ResponseEntity<FridgeDTO> adjustItemAmount(
            @RequestParam Long itemId,
            @RequestParam Long fridgeId,
            @RequestParam Double amount,
            @RequestParam Operation operation
    ) {
        FridgeDTO fridgeDTO;
        if (operation == Operation.ADD) {
           fridgeDTO = fridgeService.increaseItemAmount(itemId, fridgeId, amount);
        }
        else {
            fridgeDTO = fridgeService.reduceItemAmount(itemId, fridgeId, amount);
        }
        if (fridgeDTO == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(fridgeDTO);
    }
}

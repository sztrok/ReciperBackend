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

    @GetMapping("/test")
    public String test() {
        return "Success";
    }

    @GetMapping("/get/id/{id}")
    public ResponseEntity<FridgeDTO> getFridgeById(@PathVariable Long id) {
        FridgeDTO fridge = fridgeService.getFridgeById(id);
        if(fridge == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(fridge);
    }

    @GetMapping("/get_all")
    public ResponseEntity<List<FridgeDTO>> getAllFridges() {
        List<FridgeDTO> fridges = fridgeService.getAllFridges();
        return ResponseEntity.ok(fridges);
    }

}

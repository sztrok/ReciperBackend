package com.eat.it.eatit.backend.fridge.api;

import com.eat.it.eatit.backend.fridge.data.FridgeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/fridge")
public class FridgeController {

    FridgeHandler fridgeHandler;

    @Autowired
    public FridgeController(FridgeHandler fridgeHandler) {
        this.fridgeHandler = fridgeHandler;
    }

    @GetMapping("/test")
    public String test() {
        return "Success";
    }

    @GetMapping("/get/id/{id}")
    public FridgeDTO getFridgeById(@PathVariable Long id) {
        return fridgeHandler.getFridgeById(id);
    }

    @GetMapping("/get_all")
    public List<FridgeDTO> getAllFridges() {
        return fridgeHandler.getAllFridges();
    }

    @PostMapping(value = "/add", consumes = "application/json")
    public ResponseEntity<FridgeDTO> addNewFridge(@RequestBody FridgeDTO fridgeDTO) {
        return fridgeHandler.addNewFridge(fridgeDTO);
    }
}

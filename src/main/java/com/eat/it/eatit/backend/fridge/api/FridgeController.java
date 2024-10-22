package com.eat.it.eatit.backend.fridge.api;

import com.eat.it.eatit.backend.fridge.data.FridgeDTO;
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
        return fridgeService.getFridgeById(id);
    }

    @GetMapping("/get_all")
    public ResponseEntity<List<FridgeDTO>> getAllFridges() {
        return fridgeService.getAllFridges();
    }

    // CZY TO MA SENS? CZY TO ZROBIC TAM GDZIE SIE TWORZY UZYTKOWNIKA?
//    @PostMapping(value = "/add/{ownerId}", consumes = "application/json")
//    public ResponseEntity<FridgeDTO> addNewFridge(@RequestBody FridgeDTO fridgeDTO, @PathVariable Long ownerId) {
//        return fridgeService.addNewFridge(fridgeDTO, ownerId);
//    }

}

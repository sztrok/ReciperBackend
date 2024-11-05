package com.eat.it.eatit.backend.controller;

import com.eat.it.eatit.backend.service.CookwareService;
import com.eat.it.eatit.backend.dto.CookwareDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cookware")
public class CookwareController {

    CookwareService cookwareService;

    @Autowired
    public CookwareController(CookwareService cookwareService) {
        this.cookwareService = cookwareService;
    }

    @GetMapping("/get/id/{id}")
    public ResponseEntity<CookwareDTO> getCookwareById(@PathVariable Long id) {
        return cookwareService.getCookwareById(id);
    }

    @GetMapping("/get_all")
    public ResponseEntity<List<CookwareDTO>> getAllCookwares() {
        return cookwareService.getAllCookwares();
    }

    @PostMapping(value = "/add", consumes = "application/json")
    public ResponseEntity<CookwareDTO> addNewCookware(@RequestBody CookwareDTO cookwareDTO) {
        return cookwareService.addNewCookware(cookwareDTO);
    }

    @PutMapping(value = "/update/{id}", consumes = "application/json")
    public ResponseEntity<CookwareDTO> updateCookware(@PathVariable Long id, @RequestBody CookwareDTO cookwareDTO) {
        return cookwareService.updateCookware(id, cookwareDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCookware(@PathVariable Long id) {
        return cookwareService.deleteCookware(id);
    }
}

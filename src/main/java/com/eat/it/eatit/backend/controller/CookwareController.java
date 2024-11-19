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

    @GetMapping("/{id}")
    public ResponseEntity<CookwareDTO> getCookwareById(@PathVariable Long id) {
        return cookwareService.getCookwareById(id);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CookwareDTO>> getAllCookwares() {
        return cookwareService.getAllCookwares();
    }

    @PostMapping(value = "/new", consumes = "application/json")
    public ResponseEntity<CookwareDTO> addNewCookware(@RequestBody CookwareDTO cookwareDTO) {
        return cookwareService.addNewCookware(cookwareDTO);
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<CookwareDTO> updateCookware(@PathVariable Long id, @RequestBody CookwareDTO cookwareDTO) {
        return cookwareService.updateCookware(id, cookwareDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCookware(@PathVariable Long id) {
        return cookwareService.deleteCookware(id);
    }
}

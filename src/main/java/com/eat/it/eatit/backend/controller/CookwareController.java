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
        CookwareDTO cookware = cookwareService.getCookwareById(id);
        return cookware != null
                ? ResponseEntity.ok(cookware)
                : ResponseEntity.badRequest().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<CookwareDTO>> getAllCookwares() {
        List<CookwareDTO> cookwares = cookwareService.getAllCookwares();
        return ResponseEntity.ok(cookwares);
    }

    @PostMapping(value = "/new", consumes = "application/json")
    public ResponseEntity<CookwareDTO> addNewCookware(@RequestBody CookwareDTO cookwareDTO) {
        CookwareDTO cookware = cookwareService.addNewCookware(cookwareDTO);
        return ResponseEntity.ok(cookware);
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<CookwareDTO> updateCookware(@PathVariable Long id, @RequestParam String name) {
        CookwareDTO cookware = cookwareService.updateCookware(id, name);
        return cookware != null
                ? ResponseEntity.ok(cookware)
                : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCookware(@PathVariable Long id) {
        return cookwareService.deleteCookware(id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.badRequest().build();
    }
}

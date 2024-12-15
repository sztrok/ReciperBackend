package com.eat.it.eatit.backend.controller;

import com.eat.it.eatit.backend.service.CookwareService;
import com.eat.it.eatit.backend.dto.CookwareDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(summary = "Retrieve cookware by ID", description = "Fetches a cookware item by its unique ID.")
    @ApiResponse(responseCode = "200", description = "Cookware retrieved successfully")
    @ApiResponse(responseCode = "400", description = "Invalid ID or cookware not found")
    public ResponseEntity<CookwareDTO> getCookwareById(@PathVariable Long id) {
        CookwareDTO cookware = cookwareService.getCookwareById(id);
        return cookware != null
                ? ResponseEntity.ok(cookware)
                : ResponseEntity.badRequest().build();
    }

    @GetMapping("/all")
    @Operation(summary = "Retrieve all cookwares", description = "Fetches all cookware items in the inventory.")
    @ApiResponse(responseCode = "200", description = "Cookwares retrieved successfully")
    public ResponseEntity<List<CookwareDTO>> getAllCookwares() {
        List<CookwareDTO> cookwares = cookwareService.getAllCookwares();
        return ResponseEntity.ok(cookwares);
    }

    @PostMapping(value = "/new", consumes = "application/json")
    @Operation(summary = "Add new cookware", description = "Adds a new cookware item to the inventory.")
    @ApiResponse(responseCode = "200", description = "Cookware added successfully")
    public ResponseEntity<CookwareDTO> addNewCookware(@RequestBody CookwareDTO cookwareDTO) {
        CookwareDTO cookware = cookwareService.addNewCookware(cookwareDTO);
        return ResponseEntity.ok(cookware);
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    @Operation(summary = "Update cookware", description = "Updates the details of an existing cookware item by ID.")
    @ApiResponse(responseCode = "200", description = "Cookware updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid ID or cookware not found")
    public ResponseEntity<CookwareDTO> updateCookware(@PathVariable Long id, @RequestParam String name) {
        CookwareDTO cookware = cookwareService.updateCookware(id, name);
        return cookware != null
                ? ResponseEntity.ok(cookware)
                : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete cookware", description = "Deletes the cookware item identified by the given ID.")
    @ApiResponse(responseCode = "200", description = "Cookware deleted successfully")
    @ApiResponse(responseCode = "400", description = "Invalid ID or cookware not found")
    public ResponseEntity<Void> deleteCookware(@PathVariable Long id) {
        return cookwareService.deleteCookware(id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.badRequest().build();
    }
}

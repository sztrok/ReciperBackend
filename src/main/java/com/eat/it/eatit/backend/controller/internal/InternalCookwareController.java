package com.eat.it.eatit.backend.controller.internal;

import com.eat.it.eatit.backend.dto.CookwareDTO;
import com.eat.it.eatit.backend.service.internal.InternalCookwareService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/internal/cookware")
@PreAuthorize("hasAuthority('ROLE_SUPPORT')")
public class InternalCookwareController {

    InternalCookwareService cookwareService;

    @Autowired
    public InternalCookwareController(InternalCookwareService cookwareService) {
        this.cookwareService = cookwareService;
    }

    @PostMapping(value = "/new", consumes = "application/json")
    @Operation(summary = "Add new cookware", description = "Adds a new cookware to database.")
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

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Update cookware", description = "Updates the details of an existing cookware item by ID.")
    @ApiResponse(responseCode = "200", description = "Cookware updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid ID or cookware not found")
    public ResponseEntity<CookwareDTO> deleteCookware(@PathVariable Long id) {
        return cookwareService.deleteCookware(id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.badRequest().build();
    }

}

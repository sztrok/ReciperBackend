package com.eat.it.eatit.backend.controller.global;

import com.eat.it.eatit.backend.dto.CookwareDTO;
import com.eat.it.eatit.backend.service.CookwareService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/global/cookware")
public class GlobalCookwareController {

    CookwareService cookwareService;

    @Autowired
    public GlobalCookwareController(CookwareService cookwareService) {
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
}

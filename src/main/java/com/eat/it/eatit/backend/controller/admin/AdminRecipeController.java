package com.eat.it.eatit.backend.controller.admin;

import com.eat.it.eatit.backend.dto.recipe.RecipeDTO;
import com.eat.it.eatit.backend.service.admin.AdminRecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@RequestMapping("/api/v1/admin/recipe")
public class AdminRecipeController {

    private final AdminRecipeService recipeService;

    @Autowired
    public AdminRecipeController(AdminRecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<RecipeDTO>> getAllRecipes(@RequestParam(required = false) Long limit) {
        return ResponseEntity.ok(recipeService.getAllRecipes(Optional.ofNullable(limit)));
    }
}

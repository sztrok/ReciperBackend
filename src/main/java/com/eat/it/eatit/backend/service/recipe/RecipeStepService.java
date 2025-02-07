package com.eat.it.eatit.backend.service.recipe;

import com.eat.it.eatit.backend.repository.recipe.RecipeStepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecipeStepService {

    private final RecipeStepRepository repository;

    @Autowired
    public RecipeStepService(RecipeStepRepository repository) {
        this.repository = repository;
    }
}

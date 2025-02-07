package com.eat.it.eatit.backend.service.recipe;

import com.eat.it.eatit.backend.repository.recipe.RecipeComponentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecipeComponentService {

    private final RecipeComponentRepository repository;

    @Autowired
    public RecipeComponentService(RecipeComponentRepository repository) {
        this.repository = repository;
    }
}

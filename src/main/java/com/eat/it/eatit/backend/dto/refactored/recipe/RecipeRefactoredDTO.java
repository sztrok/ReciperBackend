package com.eat.it.eatit.backend.dto.refactored.recipe;

import com.eat.it.eatit.backend.enums.RecipeDifficulty;
import com.eat.it.eatit.backend.enums.Visibility;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeRefactoredDTO {
    private Long id;
    private String name;
    private String description;
    private List<String> simpleSteps = new ArrayList<>();
    private List<RecipeStepDTO> detailedSteps = new ArrayList<>();
    private List<String> tips = new ArrayList<>();
    private String imageUrl = "";
    private List<String> tags = new ArrayList<>();
    private List<RecipeComponentDTO> recipeComponents = new ArrayList<>();
    private Visibility visibility = Visibility.PUBLIC;
    private RecipeDifficulty difficulty = RecipeDifficulty.EASY;

    public void setSimpleSteps(List<String> simpleSteps) {
        this.simpleSteps = new ArrayList<>(simpleSteps);
    }

    public void setDetailedSteps(List<RecipeStepDTO> detailedSteps) {
        this.detailedSteps = new ArrayList<>(detailedSteps);
    }

    public void setTips(List<String> tips) {
        this.tips = new ArrayList<>(tips);
    }

    public void setTags(List<String> tags) {
        this.tags = new ArrayList<>(tags);
    }

    public void setRecipeComponents(List<RecipeComponentDTO> recipeComponents) {
        this.recipeComponents = new ArrayList<>(recipeComponents);
    }
}

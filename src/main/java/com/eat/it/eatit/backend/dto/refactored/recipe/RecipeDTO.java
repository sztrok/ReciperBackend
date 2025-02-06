package com.eat.it.eatit.backend.dto.refactored.recipe;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeDTO {
    private Long id;
    private String description;
    private List<String> simpleSteps = new ArrayList<>();
    private List<RecipeStepDTO> detailedSteps = new ArrayList<>();
    private List<String> tips = new ArrayList<>();
    private String imageUrl = "";
    private List<String> tags = new ArrayList<>();
    private List<RecipePartDTO> recipeParts = new ArrayList<>();

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

    public void setRecipeParts(List<RecipePartDTO> recipeParts) {
        this.recipeParts = new ArrayList<>(recipeParts);
    }
}

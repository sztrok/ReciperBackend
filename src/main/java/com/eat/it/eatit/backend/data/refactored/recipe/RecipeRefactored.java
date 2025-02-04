package com.eat.it.eatit.backend.data.refactored.recipe;

import com.eat.it.eatit.backend.utils.ListToStringConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeRefactored {

    @Id
    private Long id;
    private String description;
    @Convert(converter = ListToStringConverter.class)
    private List<String> simpleSteps = new ArrayList<>();
    private List<RecipeStep> detailedSteps = new ArrayList<>();
    @Convert(converter = ListToStringConverter.class)
    private List<String> tips = new ArrayList<>();
    private String imageUrl = "";
    @Convert(converter = ListToStringConverter.class)
    private List<String> tags = new ArrayList<>(); //TODO: zamienic na enum?
    private List<RecipePart> recipeParts = new ArrayList<>();


}

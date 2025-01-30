package com.eat.it.eatit.backend.dto.simple;

import com.eat.it.eatit.backend.dto.CookwareDTO;
import com.eat.it.eatit.backend.enums.RecipeDifficulty;
import com.eat.it.eatit.backend.enums.Visibility;
import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class SimpleRecipeDTO {

    @Nullable
    private Long id;
    private String name;
    private String description;
    private List<String> simpleSteps;
    private List<String> complexSteps;
    private List<ItemWithAmountDTO> items;
    private List<CookwareDTO> cookware;
    private String visibility = Visibility.PUBLIC.toString();
    private String difficulty = RecipeDifficulty.EASY.toString();
}

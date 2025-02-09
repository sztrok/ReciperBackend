package com.eat.it.eatit.backend.dto.refactored.recipe.fastapi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeFastApiRequest {
    private String raw_text;
}

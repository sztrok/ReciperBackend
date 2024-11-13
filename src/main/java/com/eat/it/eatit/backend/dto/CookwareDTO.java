package com.eat.it.eatit.backend.dto;

import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CookwareDTO {

    @Nullable
    private Long id;
    private String name;

    public CookwareDTO(@Nullable Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public CookwareDTO(String name) {
        this.name = name;
    }
}

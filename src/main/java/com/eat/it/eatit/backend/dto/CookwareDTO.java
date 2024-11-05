package com.eat.it.eatit.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CookwareDTO {

    private String name;

    public CookwareDTO(String name) {
        this.name = name;
    }

}

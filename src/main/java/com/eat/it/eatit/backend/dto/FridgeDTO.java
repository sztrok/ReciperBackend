package com.eat.it.eatit.backend.dto;

import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class FridgeDTO {

    @Nullable
    private Long id;
    private Long ownerId;
    private List<ItemInFridgeDTO> items;

    public FridgeDTO(@Nullable Long id, Long ownerId, List<ItemInFridgeDTO> items) {
        this.id = id;
        this.ownerId = ownerId;
        this.items = items;
    }

}

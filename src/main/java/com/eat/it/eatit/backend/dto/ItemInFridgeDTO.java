package com.eat.it.eatit.backend.dto;

import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemInFridgeDTO {

    @Nullable
    private Long id;
    private Long fridgeId;
    private ItemDTO item;
    private Double amount;

    public ItemInFridgeDTO(@Nullable Long id, Long fridgeId, ItemDTO item, Double amount) {
        this.id = id;
        this.fridgeId = fridgeId;
        this.item = item;
        this.amount = amount;
    }

    public ItemInFridgeDTO(Long fridgeId, ItemDTO item, Double amount) {
        this.fridgeId = fridgeId;
        this.item = item;
        this.amount = amount;
    }
}

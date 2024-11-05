package com.eat.it.eatit.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemInFridgeDTO {

    private Long fridgeId;
    private ItemDTO item;
    private Double amount;

    public ItemInFridgeDTO(Long fridgeId, ItemDTO item, Double amount) {
        this.fridgeId = fridgeId;
        this.item = item;
        this.amount = amount;
    }
}

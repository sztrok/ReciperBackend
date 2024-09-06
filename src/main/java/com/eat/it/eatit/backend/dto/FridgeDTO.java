package com.eat.it.eatit.backend.dto;

import lombok.Data;

import java.util.List;

@Data
public class FridgeDTO {
    private Long ownerId;
    private List<ItemDTO> items;

    public FridgeDTO(Long ownerId, List<ItemDTO> items) {
        this.ownerId = ownerId;
        this.items = items;
    }
}

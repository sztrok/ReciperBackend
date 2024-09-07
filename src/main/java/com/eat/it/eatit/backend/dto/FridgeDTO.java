package com.eat.it.eatit.backend.dto;

import lombok.Data;

import java.util.Set;

@Data
public class FridgeDTO {
    private Long ownerId;
    private Set<ItemDTO> items;

    public FridgeDTO(Long ownerId, Set<ItemDTO> items) {
        this.ownerId = ownerId;
        this.items = items;
    }
}

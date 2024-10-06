package com.eat.it.eatit.backend.fridge;

import com.eat.it.eatit.backend.item.ItemDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class FridgeDTO {

    private Long ownerId;
    private Set<ItemDTO> items;

    public FridgeDTO(Long ownerId, Set<ItemDTO> items) {
        this.ownerId = ownerId;
        this.items = items;
    }
}

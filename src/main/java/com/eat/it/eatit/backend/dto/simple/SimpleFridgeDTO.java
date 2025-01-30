package com.eat.it.eatit.backend.dto.simple;

import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class SimpleFridgeDTO {

    @Nullable
    private Long id;
    private Long ownerId;
    private List<ItemWithAmountDTO> items;

    public SimpleFridgeDTO(@Nullable Long id, Long ownerId, List<ItemWithAmountDTO> items) {
        this.id = id;
        this.ownerId = ownerId;
        this.items = items;
    }
}

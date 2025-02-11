package com.eat.it.eatit.backend.dto.simple;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FridgeSimpleDTO {

    @Nullable
    private Long id;
    private Long ownerId;
    private List<ItemWithAmountDTO> items;
}

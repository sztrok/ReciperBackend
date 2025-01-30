package com.eat.it.eatit.backend.dto.simple;

import lombok.Data;

@Data
public class ItemWithAmountDTO {

    private String name;
    private String itemType;
    private Double amount;
    private String unitOfMeasure;

}

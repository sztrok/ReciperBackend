package com.eat.it.eatit.backend.mapper.simple;

import com.eat.it.eatit.backend.data.ItemInFridge;
import com.eat.it.eatit.backend.dto.simple.ItemWithAmountDTO;


public class ItemWithAmountMapper {

    private ItemWithAmountMapper() {
    }

    public static ItemWithAmountDTO toDTO(ItemInFridge itemInFridge) {
        ItemWithAmountDTO item = new ItemWithAmountDTO();
        item.setName(itemInFridge.getItem().getName());
        item.setItemType(itemInFridge.getItem().getItemType().getDescription());
        item.setAmount(itemInFridge.getQuantity());
        item.setUnit(itemInFridge.getUnit().getUnit());
        return item;
    }
}

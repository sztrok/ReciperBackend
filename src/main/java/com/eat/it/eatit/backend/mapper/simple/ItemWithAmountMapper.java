package com.eat.it.eatit.backend.mapper.simple;

import com.eat.it.eatit.backend.data.ItemInFridge;
import com.eat.it.eatit.backend.data.ItemInRecipe;
import com.eat.it.eatit.backend.dto.simple.ItemWithAmountDTO;

public class ItemWithAmountMapper {

    private ItemWithAmountMapper() {
    }

    public static ItemWithAmountDTO toDTO(ItemInRecipe itemInRecipe) {
        ItemWithAmountDTO item = new ItemWithAmountDTO();
        item.setName(itemInRecipe.getItem().getName());
        item.setItemType(itemInRecipe.getItem().getItemType().getDescription());
        item.setAmount(itemInRecipe.getAmount());
        item.setUnitOfMeasure(itemInRecipe.getUnit().getUnit());
        return item;
    }

    public static ItemWithAmountDTO toDTO(ItemInFridge itemInFridge) {
        ItemWithAmountDTO item = new ItemWithAmountDTO();
        item.setName(itemInFridge.getItem().getName());
        item.setItemType(itemInFridge.getItem().getItemType().getDescription());
        item.setAmount(itemInFridge.getAmount());
        item.setUnitOfMeasure(itemInFridge.getUnit().getUnit());
        return item;
    }


}

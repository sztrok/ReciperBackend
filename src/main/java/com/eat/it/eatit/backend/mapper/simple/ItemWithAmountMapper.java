package com.eat.it.eatit.backend.mapper.simple;

import com.eat.it.eatit.backend.data.ItemInFridge;
import com.eat.it.eatit.backend.data.ItemInRecipe;
import com.eat.it.eatit.backend.dto.simple.ItemWithAmountDTO;

import java.util.List;

public class ItemWithAmountMapper {

    private ItemWithAmountMapper() {
    }

    public static ItemWithAmountDTO toDTO(ItemInRecipe itemInRecipe) {
        ItemWithAmountDTO item = new ItemWithAmountDTO();
        item.setName(itemInRecipe.getItem().getName());
        item.setItemType(itemInRecipe.getItem().getItemType().getDescription());
        item.setAmount(itemInRecipe.getAmount());
        item.setUnit(itemInRecipe.getUnit().getUnit());
        return item;
    }

    public static ItemWithAmountDTO toDTO(ItemInFridge itemInFridge) {
        ItemWithAmountDTO item = new ItemWithAmountDTO();
        item.setName(itemInFridge.getItem().getName());
        item.setItemType(itemInFridge.getItem().getItemType().getDescription());
        item.setAmount(itemInFridge.getQuantity());
        item.setUnit(itemInFridge.getUnit().getUnit());
        return item;
    }

    public static List<ItemWithAmountDTO> toDTOFromFridge(List<ItemInFridge> itemInFridgeList) {
        return itemInFridgeList.stream().map(ItemWithAmountMapper::toDTO).toList();
    }

    public static List<ItemWithAmountDTO> toDTOFromRecipe(List<ItemInRecipe> itemInRecipeList) {
        return itemInRecipeList.stream().map(ItemWithAmountMapper::toDTO).toList();
    }


}

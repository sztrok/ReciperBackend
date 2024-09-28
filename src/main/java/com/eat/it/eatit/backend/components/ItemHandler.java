package com.eat.it.eatit.backend.components;

import com.eat.it.eatit.backend.data.Item;
import com.eat.it.eatit.backend.dto.ItemDTO;
import com.eat.it.eatit.backend.mappers.ItemMapper;
import com.eat.it.eatit.backend.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class ItemHandler {

    ItemRepository itemRepository;
    @Autowired
    public ItemHandler(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public ItemDTO getItemById(Long id) {
        Item item = itemRepository.findById(id).orElse(null);
        return ItemMapper.toDTO(item);
    }

    public Set<ItemDTO> getAllItems() {
        List<Item> items = itemRepository.findAll();
        Set<ItemDTO> itemDTOSet = new HashSet<>();
        for(Item i : items) {
            itemDTOSet.add(ItemMapper.toDTO(i));
        }
        return itemDTOSet;
    }


}

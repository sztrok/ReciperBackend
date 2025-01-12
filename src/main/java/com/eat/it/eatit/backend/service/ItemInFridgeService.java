package com.eat.it.eatit.backend.service;

import com.eat.it.eatit.backend.data.ItemInFridge;
import com.eat.it.eatit.backend.repository.ItemInFridgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemInFridgeService {

    ItemInFridgeRepository itemInFridgeRepository;

    @Autowired
    public ItemInFridgeService(ItemInFridgeRepository itemInFridgeRepository) {
        this.itemInFridgeRepository = itemInFridgeRepository;
    }

    protected ItemInFridge getItemInFridgeById(Long id) {
        return itemInFridgeRepository.findById(id).orElse(null);
    }

    public void removeItemFromFridge(ItemInFridge item) {
        itemInFridgeRepository.delete(item);
    }

    public void saveItemInFridge(ItemInFridge item) {
        itemInFridgeRepository.save(item);
    }
}

package com.eat.it.eatit.backend.service;

import com.eat.it.eatit.backend.data.Fridge;
import com.eat.it.eatit.backend.data.Item;
import com.eat.it.eatit.backend.data.ItemInFridge;
import com.eat.it.eatit.backend.dto.FridgeDTO;
import com.eat.it.eatit.backend.enums.Operation;
import com.eat.it.eatit.backend.mapper.FridgeMapper;
import com.eat.it.eatit.backend.repository.FridgeRepository;
import com.eat.it.eatit.backend.repository.ItemInFridgeRepository;
import com.eat.it.eatit.backend.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Service class for handling fridge-related operations.
 */
@Service
public class FridgeService {

    FridgeRepository fridgeRepository;
    ItemRepository itemRepository;
    ItemInFridgeRepository itemInFridgeRepository;

    @Autowired
    public FridgeService(FridgeRepository fridgeRepository, ItemRepository itemRepository, ItemInFridgeRepository itemInFridgeRepository) {
        this.fridgeRepository = fridgeRepository;
        this.itemRepository = itemRepository;
        this.itemInFridgeRepository = itemInFridgeRepository;
    }

    /**
     * Retrieves a fridge by its unique identifier.
     *
     * @param id the unique identifier of the fridge
     * @return FridgeDTO containing the details of the fridge if found, otherwise null
     */
    public FridgeDTO getFridgeById(Long id) {
        Fridge fridge = fridgeRepository.findById(id).orElse(null);
        if (fridge == null) {
            return null;
        }
        return FridgeMapper.toDTO(fridge);
    }

    /**
     * Retrieves all fridges from the repository and maps them to DTOs.
     *
     * @return a list of FridgeDTO objects.
     */
    public List<FridgeDTO> getAllFridges() {
        List<Fridge> fridgeList = fridgeRepository.findAll();
        List<FridgeDTO> fridgeDTOList = new ArrayList<>();
        for (Fridge fridge : fridgeList) {
            fridgeDTOList.add(FridgeMapper.toDTO(fridge));
        }
        return fridgeDTOList;
    }

    @Transactional
    public FridgeDTO addItemToFridge(Long itemId, Long fridgeId, Double amount) {
        Item item = findItemById(itemId);
        Fridge fridge = findFridgeById(fridgeId);

        if (item == null || fridge == null) {
            return null;
        }

        Set<ItemInFridge> itemsInFridge = fridge.getItems();
        if (isItemAlreadyInFridge(itemsInFridge, itemId)) {
            changeItemAmountInFridge(itemId, fridgeId, amount, Operation.ADD);
        } else {
            addNewItemToFridge(fridge, item, amount);
        }

        return FridgeMapper.toDTO(fridge);
    }

    @Transactional
    public FridgeDTO reduceItemAmount(Long itemId, Long fridgeId, Double amount) {
        return changeItemAmountInFridge(itemId, fridgeId, amount, Operation.SUBSTRACT);
    }

    @Transactional
    public FridgeDTO increaseItemAmount(Long itemId, Long fridgeId, Double amount) {
        return changeItemAmountInFridge(itemId, fridgeId, amount, Operation.ADD);
    }

    private FridgeDTO changeItemAmountInFridge(Long itemId, Long fridgeId, Double amount, Operation operation) {
        Fridge fridge = fridgeRepository.findById(fridgeId).orElse(null);

        if (fridge == null) {
            return null;
        }

        Set<ItemInFridge> itemsInFridge = fridge.getItems();
        ItemInFridge itemInFridge = getItemInFridgeOrNull(itemsInFridge, itemId);

        if (itemInFridge == null) {
            return null;
        }

        double currentAmount = itemInFridge.getAmount();
        double newAmount = 0.0;

        switch (operation) {
            case SUBSTRACT -> newAmount = currentAmount - amount;
            case ADD -> newAmount = currentAmount + amount;
        }

        if (newAmount <= 0) {
            itemsInFridge.remove(itemInFridge);
            itemInFridgeRepository.delete(itemInFridge);
        } else {
            itemInFridge.setAmount(newAmount);
            itemInFridgeRepository.save(itemInFridge);
        }

        fridge.setItems(itemsInFridge);
        fridgeRepository.save(fridge);
        return FridgeMapper.toDTO(fridge);
    }

    private Item findItemById(Long itemId) {
        return itemRepository.findById(itemId).orElse(null);
    }

    private Fridge findFridgeById(Long fridgeId) {
        return fridgeRepository.findById(fridgeId).orElse(null);
    }

    private void addNewItemToFridge(Fridge fridge, Item item, Double amount) {
        ItemInFridge newItemInFridge = new ItemInFridge(fridge.getId(), item, amount);
        fridge.getItems().add(newItemInFridge);
        fridgeRepository.save(fridge);
    }

    private ItemInFridge getItemInFridgeOrNull(Set<ItemInFridge> itemsInFridge, Long itemId) {
        return itemsInFridge.stream()
                .filter(i -> i.getItem().getId().equals(itemId))
                .findFirst()
                .orElse(null);
    }

    private boolean isItemAlreadyInFridge(Set<ItemInFridge> itemsInFridge, Long itemId) {
        return itemsInFridge.stream()
                .map(ItemInFridge::getItem)
                .anyMatch(item -> item.getId().equals(itemId));

    }

}

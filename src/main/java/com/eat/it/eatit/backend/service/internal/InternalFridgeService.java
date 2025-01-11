package com.eat.it.eatit.backend.service.internal;

import com.eat.it.eatit.backend.data.Fridge;
import com.eat.it.eatit.backend.data.Item;
import com.eat.it.eatit.backend.data.ItemInFridge;
import com.eat.it.eatit.backend.dto.FridgeDTO;
import com.eat.it.eatit.backend.enums.Operations;
import com.eat.it.eatit.backend.repository.FridgeRepository;
import com.eat.it.eatit.backend.service.AccountAuthAndAccessService;
import com.eat.it.eatit.backend.service.ItemInFridgeService;
import com.eat.it.eatit.backend.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.eat.it.eatit.backend.mapper.FridgeMapper.toDTO;

/**
 * Service class for handling fridge-related operations.
 */
@Service
public class InternalFridgeService {

    private final FridgeRepository fridgeRepository;
    private final ItemService itemService;
    private final ItemInFridgeService itemInFridgeService;
    private final AccountAuthAndAccessService accountService;

    @Autowired
    public InternalFridgeService(
            FridgeRepository fridgeRepository,
            ItemService itemService,
            ItemInFridgeService itemInFridgeService,
            AccountAuthAndAccessService accountService
    ) {
        this.fridgeRepository = fridgeRepository;
        this.itemService = itemService;
        this.itemInFridgeService = itemInFridgeService;
        this.accountService = accountService;
    }

    public Fridge createFridge(Long accountId) {
        Fridge fridge = new Fridge();
        fridge.setOwnerId(accountId);
        return fridgeRepository.save(fridge);
    }

    public FridgeDTO getFridgeById(Long id) {
        Fridge fridge = fridgeRepository.findById(id).orElse(null);
        if (fridge == null) {
            return null;
        }
        return toDTO(fridge);
    }

    public List<FridgeDTO> getAllFridges() {
        List<Fridge> fridgeList = fridgeRepository.findAll();
        List<FridgeDTO> fridgeDTOList = new ArrayList<>();
        for (Fridge fridge : fridgeList) {
            fridgeDTOList.add(toDTO(fridge));
        }
        return fridgeDTOList;
    }

    public FridgeDTO getFridgeByAccountName(String accountName) {
        return accountService.getAccountByName(accountName).getFridge();
    }

    @Transactional
    public FridgeDTO addItemToFridge(Long itemId, Long fridgeId, Double amount) {
        Item item = itemService.findItemById(itemId);
        Fridge fridge = findFridgeById(fridgeId);

        if (item == null || fridge == null) {
            return null;
        }

        List<ItemInFridge> itemsInFridge = fridge.getItems();
        if (isItemAlreadyInFridge(itemsInFridge, itemId)) {
            changeItemAmountInFridge(itemId, fridgeId, amount, Operations.ADD);
        } else {
            addNewItemToFridge(fridge, item, amount);
        }

        return toDTO(fridge);
    }

    @Transactional
    public FridgeDTO deleteItemFromFridge(Long fridgeId, Long itemId) {
        Fridge fridge = findFridgeById(fridgeId);
        if (fridge == null) {
            return null;
        }
        fridge.getItems().removeIf(item -> item.getId().equals(itemId));
        return toDTO(fridge);
    }

    @Transactional
    public FridgeDTO changeItemAmountInFridge(Long itemId, Long fridgeId, Double amount, Operations operation) {
        Fridge fridge = findFridgeById(fridgeId);
        if (fridge == null) {
            return null;
        }
        List<ItemInFridge> itemsInFridge = fridge.getItems();
        ItemInFridge itemInFridge = getItemInFridgeOrNull(itemsInFridge, itemId);
        if (itemInFridge == null) {
            if (operation == Operations.ADD) {
                addNewItemToFridge(fridge, itemService.findItemById(itemId), amount);
                return toDTO(fridge);
            }
            return null;
        }

        double currentAmount = itemInFridge.getAmount();
        double newAmount;
        if (operation == Operations.ADD) {
            newAmount = currentAmount + amount;
        } else {
            newAmount = currentAmount - amount;
        }

        if (newAmount <= 0) {
            itemsInFridge.remove(itemInFridge);
            itemInFridgeService.removeItemFromFridge(itemInFridge);
        } else {
            itemInFridge.setAmount(newAmount);
            itemInFridgeService.saveItemInFridge(itemInFridge);
        }
        return toDTO(fridge);
    }

    private Fridge findFridgeById(Long fridgeId) {
        return fridgeRepository.findById(fridgeId).orElse(null);
    }

    private void addNewItemToFridge(Fridge fridge, Item item, Double amount) {
        ItemInFridge newItemInFridge = new ItemInFridge(fridge.getId(), item, amount);
        fridge.addItem(newItemInFridge);
        fridgeRepository.save(fridge);
    }

    private ItemInFridge getItemInFridgeOrNull(List<ItemInFridge> itemsInFridge, Long itemId) {
        return itemsInFridge.stream()
                .filter(i -> i.getItem().getId().equals(itemId))
                .findFirst()
                .orElse(null);
    }

    private boolean isItemAlreadyInFridge(List<ItemInFridge> itemsInFridge, Long itemId) {
        return itemsInFridge.stream()
                .map(ItemInFridge::getItem)
                .anyMatch(item -> item.getId().equals(itemId));
    }

}

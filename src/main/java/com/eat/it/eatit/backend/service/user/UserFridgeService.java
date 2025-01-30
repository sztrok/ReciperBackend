package com.eat.it.eatit.backend.service.user;

import com.eat.it.eatit.backend.data.Fridge;
import com.eat.it.eatit.backend.data.Item;
import com.eat.it.eatit.backend.data.ItemInFridge;
import com.eat.it.eatit.backend.dto.FridgeDTO;
import com.eat.it.eatit.backend.dto.simple.FridgeSimpleDTO;
import com.eat.it.eatit.backend.enums.Operations;
import com.eat.it.eatit.backend.repository.FridgeRepository;
import com.eat.it.eatit.backend.service.AccountAuthAndAccessService;
import com.eat.it.eatit.backend.service.ItemInFridgeService;
import com.eat.it.eatit.backend.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.eat.it.eatit.backend.mapper.FridgeMapper.toSimpleDTO;

@Service
public class UserFridgeService {

    private final FridgeRepository fridgeRepository;
    private final ItemService itemService;
    private final ItemInFridgeService itemInFridgeService;
    private final AccountAuthAndAccessService accountService;

    @Autowired
    public UserFridgeService(
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

    public FridgeSimpleDTO getAccountFridge(Authentication authentication) {
        Fridge fridge = getFridgeByUsername(authentication.getName());
        if (fridge == null) {
            return null;
        }
        return toSimpleDTO(fridge);
    }

    public FridgeDTO getFridgeByAccountName(String accountName) {
        return accountService.getAccountByName(accountName).getFridge();
    }

    @Transactional
    public FridgeSimpleDTO addItemToFridge(Authentication authentication, Long itemId, Double amount) {
        Item item = itemService.findItemById(itemId);
        Fridge fridge = getFridgeByUsername(authentication.getName());

        if (item == null || fridge == null) {
            return null;
        }

        List<ItemInFridge> itemsInFridge = fridge.getItems();
        if (isItemAlreadyInFridge(itemsInFridge, itemId)) {
            changeItemAmountInFridge(fridge, itemId, amount, Operations.ADD);
        } else {
            addNewItemToFridge(fridge, item, amount);
        }

        return toSimpleDTO(fridge);
    }

    @Transactional
    public FridgeSimpleDTO deleteItemFromFridge(Authentication authentication, Long itemId, Long fridgeId) {
        Fridge fridge = getFridgeByUsername(authentication.getName());
        if (fridge == null) {
            return null;
        }
        fridge.getItems().removeIf(item -> item.getId().equals(itemId));
        return toSimpleDTO(fridge);
    }

    @Transactional
    public FridgeSimpleDTO changeItemAmountInFridge(Authentication authentication, Long itemId, Double amount, Operations operation) {
        Fridge fridge = getFridgeByUsername(authentication.getName());
        if (fridge == null) {
            return null;
        }
        return changeItemAmountInFridge(fridge, itemId, amount, operation);
    }

    private FridgeSimpleDTO changeItemAmountInFridge(Fridge fridge, Long itemId, Double amount, Operations operation) {
        List<ItemInFridge> itemsInFridge = fridge.getItems();
        ItemInFridge itemInFridge = getItemInFridgeOrNull(itemsInFridge, itemId);
        if (itemInFridge == null) {
            if (operation == Operations.ADD) {
                addNewItemToFridge(fridge, itemService.findItemById(itemId), amount);
                return toSimpleDTO(fridge);
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
        return toSimpleDTO(fridge);
    }

    private Fridge getFridgeByUsername(String username) {
        Long accountId = accountService.getAccountByName(username).getId();
        return findFridgeByAccountId(accountId);
    }

    private Fridge findFridgeByAccountId(Long accountId) {
        return fridgeRepository.getFridgeByOwnerId(accountId);
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

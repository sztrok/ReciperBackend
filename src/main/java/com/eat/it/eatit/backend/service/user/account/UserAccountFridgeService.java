package com.eat.it.eatit.backend.service.user.account;

import com.eat.it.eatit.backend.data.Account;
import com.eat.it.eatit.backend.data.Fridge;
import com.eat.it.eatit.backend.data.Item;
import com.eat.it.eatit.backend.data.ItemInFridge;
import com.eat.it.eatit.backend.dto.simple.FridgeSimpleDTO;
import com.eat.it.eatit.backend.enums.Operations;
import com.eat.it.eatit.backend.mapper.FridgeMapper;
import com.eat.it.eatit.backend.repository.AccountRepository;
import com.eat.it.eatit.backend.repository.FridgeRepository;
import com.eat.it.eatit.backend.repository.ItemInFridgeRepository;
import com.eat.it.eatit.backend.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.eat.it.eatit.backend.mapper.FridgeMapper.toSimpleDTO;

@Service
public class UserAccountFridgeService {

    private final AccountRepository accountRepository;
    private final FridgeRepository fridgeRepository;
    private final ItemRepository itemRepository;
    private final ItemInFridgeRepository itemInFridgeRepository;

    @Autowired
    public UserAccountFridgeService(
            AccountRepository accountRepository,
            FridgeRepository fridgeRepository,
            ItemRepository itemRepository,
            ItemInFridgeRepository itemInFridgeRepository
    ) {
        this.accountRepository = accountRepository;
        this.fridgeRepository = fridgeRepository;
        this.itemRepository = itemRepository;
        this.itemInFridgeRepository = itemInFridgeRepository;
    }

    public FridgeSimpleDTO getFridge(Authentication authentication) {
        Account account = getAccountEntityByName(authentication.getName());
        if (account == null) {
            return null;
        }
        return FridgeMapper.toSimpleDTO(account.getFridge());
    }

    @Transactional
    public FridgeSimpleDTO addItemToFridge(Authentication authentication, Long itemId, Double amount) {
        Account account = getAccountEntityByName(authentication.getName());
        if (account == null) {
            return null;
        }
        Fridge fridge = account.getFridge();

        Optional<Item> item = itemRepository.findById(itemId);
        if (item.isEmpty()|| fridge == null) {
            return null;
        }

        List<ItemInFridge> itemsInFridge = fridge.getItems();
        if (isItemAlreadyInFridge(itemsInFridge, itemId)) {
            changeItemAmountInFridge(fridge, itemId, amount, Operations.ADD);
        } else {
            addNewItemToFridge(fridge, item.get(), amount);
        }

        return toSimpleDTO(fridge);
    }

    @Transactional
    public FridgeSimpleDTO deleteItemsFromFridge(Authentication authentication, List<Long> items) {
        Account account = getAccountEntityByName(authentication.getName());
        if (account == null) {
            return null;
        }
        Fridge fridge = account.getFridge();
        if (fridge == null) {
            return null;
        }
        fridge.getItems().removeIf(item -> items.contains(item.getId()));
        return toSimpleDTO(fridge);
    }

    @Transactional
    public FridgeSimpleDTO deleteAllItemsFromFridge(Authentication authentication) {
        Account account = getAccountEntityByName(authentication.getName());
        if (account == null) {
            return null;
        }
        Fridge fridge = account.getFridge();
        if (fridge == null) {
            return null;
        }
        fridge.getItems().clear();
        return toSimpleDTO(fridge);
    }

    @Transactional
    public FridgeSimpleDTO changeItemAmountInFridge(Authentication authentication, Long itemId, Double amount, Operations operation) {
        Account account = getAccountEntityByName(authentication.getName());
        if (account == null) {
            return null;
        }
        Fridge fridge = account.getFridge();
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
                Optional<Item> item = itemRepository.findById(itemId);
                if (item.isEmpty()) {
                    return null;
                }
                addNewItemToFridge(fridge, item.get(), amount);
                return toSimpleDTO(fridge);
            }
            return null;
        }

        double currentAmount = itemInFridge.getQuantity();
        double newAmount;
        if (operation == Operations.ADD) {
            newAmount = currentAmount + amount;
        } else {
            newAmount = currentAmount - amount;
        }

        if (newAmount <= 0) {
            itemsInFridge.remove(itemInFridge);
            itemInFridgeRepository.delete(itemInFridge);
        } else {
            itemInFridge.setQuantity(newAmount);
            itemInFridgeRepository.save(itemInFridge);
        }
        return toSimpleDTO(fridge);
    }

    private void addNewItemToFridge(Fridge fridge, Item item, Double amount) {
        ItemInFridge newItemInFridge = new ItemInFridge();
        newItemInFridge.setItem(item);
        newItemInFridge.setQuantity(amount);
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
    private Account getAccountEntityByName(String username) {
        return accountRepository.findByUsername(username);
    }
}

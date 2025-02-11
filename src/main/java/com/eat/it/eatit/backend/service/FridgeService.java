package com.eat.it.eatit.backend.service;

import com.eat.it.eatit.backend.data.Fridge;
import com.eat.it.eatit.backend.data.Item;
import com.eat.it.eatit.backend.data.ItemInFridge;
import com.eat.it.eatit.backend.dto.FridgeDTO;
import com.eat.it.eatit.backend.enums.Operations;
import com.eat.it.eatit.backend.repository.FridgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.eat.it.eatit.backend.mapper.FridgeMapper.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class for handling fridge-related operations.
 */
@Service
public class FridgeService {

    private final FridgeRepository fridgeRepository;
    private final ItemService itemService;
    private final ItemInFridgeService itemInFridgeService;
    private final AccountAuthAndAccessService accountService;

    @Autowired
    public FridgeService(
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
        return toDTO(fridge);
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
            fridgeDTOList.add(toDTO(fridge));
        }
        return fridgeDTOList;
    }

    public FridgeDTO getFridgeByAccountName(String accountName) {
        return accountService.getAccountByName(accountName).getFridge();
    }

    /**
     * Adds an item to the fridge. If the item already exists in the fridge, the amount is increased.
     * Otherwise, a new item entry is created in the fridge.
     *
     * @param itemId   the unique identifier of the item to add.
     * @param fridgeId the unique identifier of the fridge where the item will be added.
     * @param amount   the amount of the item to add to the fridge.
     * @return a FridgeDTO containing the updated details of the fridge, or null if the item or fridge is not found.
     */
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
    public FridgeDTO deleteItemFromFridge(Long itemId, Long fridgeId) {
        Fridge fridge = findFridgeById(fridgeId);
        if (fridge == null) {
            return null;
        }
        fridge.getItems().removeIf(item -> item.getId().equals(itemId));
        return toDTO(fridge);
    }

    /**
     * Reduces the amount of a specified item in a specified fridge.
     *
     * @param itemId   the unique identifier of the item to reduce
     * @param fridgeId the unique identifier of the fridge where the item is stored
     * @param amount   the amount by which to reduce the item's quantity
     * @return a FridgeDTO containing the updated details of the fridge, or null if the fridge or item is not found
     */
    @Transactional
    public FridgeDTO reduceItemAmount(Long itemId, Long fridgeId, Double amount) {
        return changeItemAmountInFridge(itemId, fridgeId, amount, Operations.SUBSTRACT);
    }

    /**
     * Increases the amount of a specific item in a fridge.
     *
     * @param itemId   the unique identifier of the item to increase the amount of
     * @param fridgeId the unique identifier of the fridge where the item amount will be increased
     * @param amount   the amount by which the item should be increased
     * @return a FridgeDTO containing the updated details of the fridge, or null if the fridge or item is not found
     */
    @Transactional
    public FridgeDTO increaseItemAmount(Long itemId, Long fridgeId, Double amount) {
        return changeItemAmountInFridge(itemId, fridgeId, amount, Operations.ADD);
    }

    /**
     * Changes the amount of a specified item in the fridge.
     * The operation can either add to or subtract from the current amount.
     *
     * @param itemId    the unique identifier of the item
     * @param fridgeId  the unique identifier of the fridge
     * @param amount    the amount to add or subtract
     * @param operation the operation to perform, either ADD or SUBTRACT
     * @return a FridgeDTO containing the updated details of the fridge if the operation is successful, otherwise null
     */
    private FridgeDTO changeItemAmountInFridge(Long itemId, Long fridgeId, Double amount, Operations operation) {
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

        double currentAmount = itemInFridge.getQuantity();
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
            itemInFridge.setQuantity(newAmount);
            itemInFridgeService.saveItemInFridge(itemInFridge);
        }
        return toDTO(fridge);
    }

    /**
     * Retrieves a fridge by its unique identifier.
     *
     * @param fridgeId the unique identifier of the fridge
     * @return the Fridge object if found, otherwise null
     */
    private Fridge findFridgeById(Long fridgeId) {
        return fridgeRepository.findById(fridgeId).orElse(null);
    }

    /**
     * Adds a new item to the fridge with the specified amount and saves the updated fridge.
     *
     * @param fridge the fridge to which the new item will be added
     * @param item   the item to be added to the fridge
     * @param amount the amount of the item to be added to the fridge
     */
    private void addNewItemToFridge(Fridge fridge, Item item, Double amount) {
        ItemInFridge newItemInFridge = new ItemInFridge();
        newItemInFridge.setItem(item);
        newItemInFridge.setQuantity(amount);
        fridge.addItem(newItemInFridge);
        fridgeRepository.save(fridge);
    }

    /**
     * Retrieves an item from the given set of items in the fridge that matches the specified item ID,
     * or returns null if no such item is found.
     *
     * @param itemsInFridge the set of items currently in the fridge
     * @param itemId        the unique identifier of the item to find
     * @return the item in the fridge with the specified ID, or null if no such item is found
     */
    private ItemInFridge getItemInFridgeOrNull(List<ItemInFridge> itemsInFridge, Long itemId) {
        return itemsInFridge.stream()
                .filter(i -> i.getItem().getId().equals(itemId))
                .findFirst()
                .orElse(null);
    }

    /**
     * Checks if an item with the given ID is already present in the fridge.
     *
     * @param itemsInFridge a set of ItemInFridge objects representing the items currently in the fridge
     * @param itemId        the unique identifier of the item to check
     * @return true if the item is already in the fridge, false otherwise
     */
    private boolean isItemAlreadyInFridge(List<ItemInFridge> itemsInFridge, Long itemId) {
        return itemsInFridge.stream()
                .map(ItemInFridge::getItem)
                .anyMatch(item -> item.getId().equals(itemId));
    }

}

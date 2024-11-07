package com.eat.it.eatit.backend.service;

import com.eat.it.eatit.backend.data.Fridge;
import com.eat.it.eatit.backend.data.Item;
import com.eat.it.eatit.backend.data.ItemInFridge;
import com.eat.it.eatit.backend.dto.FridgeDTO;
import com.eat.it.eatit.backend.mapper.FridgeMapper;
import com.eat.it.eatit.backend.repository.FridgeRepository;
import com.eat.it.eatit.backend.repository.ItemInFridgeRepository;
import com.eat.it.eatit.backend.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public FridgeDTO addItemToFridge(Long itemId, Long fridgeId, Double amount) {
        Item item = itemRepository.findById(itemId).orElse(null);
        Fridge fridge = fridgeRepository.findById(fridgeId).orElse(null);

        if(item == null || fridge == null ){
            return null;
        }

        ItemInFridge itemInFridge = new ItemInFridge(fridge.getId(), item, amount);

        Set<ItemInFridge> itemInFridges = fridge.getItems();


    }

    //TODO: dodawanie itemu do fridge,
    // update itemow w fridge,
    // zmiana amount itemu we fridge,
    // jak sie dodaje taki sam item to powinien sie zrobic update amount,
}

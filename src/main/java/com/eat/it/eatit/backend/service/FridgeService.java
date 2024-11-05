package com.eat.it.eatit.backend.service;

import com.eat.it.eatit.backend.data.Fridge;
import com.eat.it.eatit.backend.dto.FridgeDTO;
import com.eat.it.eatit.backend.mapper.FridgeMapper;
import com.eat.it.eatit.backend.repository.FridgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class for handling fridge-related operations.
 */
@Service
public class FridgeService {

    FridgeRepository fridgeRepository;

    @Autowired
    public FridgeService(FridgeRepository fridgeRepository) {
        this.fridgeRepository = fridgeRepository;
    }

    /**
     * Retrieves a fridge by its unique identifier.
     *
     * @param id the unique identifier of the fridge
     * @return ResponseEntity containing the FridgeDTO if found, otherwise a ResponseEntity with a 404 not found status
     */
    public ResponseEntity<FridgeDTO> getFridgeById(Long id) {
        Fridge fridge = fridgeRepository.findById(id).orElse(null);
        if(fridge == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(FridgeMapper.toDTO(fridge));
    }

    /**
     * Retrieves all fridges from the repository and maps them to DTOs.
     *
     * @return a ResponseEntity containing a list of FridgeDTO objects.
     */
    public ResponseEntity<List<FridgeDTO>> getAllFridges() {
        List<Fridge> fridgeList = fridgeRepository.findAll();
        List<FridgeDTO> fridgeDTOList = new ArrayList<>();
        for (Fridge fridge : fridgeList) {
            fridgeDTOList.add(FridgeMapper.toDTO(fridge));
        }
        return ResponseEntity.ok(fridgeDTOList);
    }

    //TODO: dodawanie itemu do fridge,
    // update itemow w fridge,
    // zmiana amount itemu we fridge,
    // jak sie dodaje taki sam item to powinien sie zrobic update amount,
}

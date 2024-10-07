package com.eat.it.eatit.backend.fridge.api;

import com.eat.it.eatit.backend.fridge.data.Fridge;
import com.eat.it.eatit.backend.fridge.data.FridgeDTO;
import com.eat.it.eatit.backend.fridge.data.FridgeMapper;
import com.eat.it.eatit.backend.fridge.data.FridgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FridgeHandler {

    FridgeRepository fridgeRepository;

    @Autowired
    public FridgeHandler(FridgeRepository fridgeRepository) {
        this.fridgeRepository = fridgeRepository;
    }

    public ResponseEntity<FridgeDTO> getFridgeById(Long id) {
        Fridge fridge = fridgeRepository.findById(id).orElse(null);
        if(fridge == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(FridgeMapper.toDTO(fridge));
    }

    public ResponseEntity<List<FridgeDTO>> getAllFridges() {
        List<Fridge> fridgeList = fridgeRepository.findAll();
        List<FridgeDTO> fridgeDTOList = new ArrayList<>();
        for (Fridge fridge : fridgeList) {
            fridgeDTOList.add(FridgeMapper.toDTO(fridge));
        }
        return ResponseEntity.ok(fridgeDTOList);
    }

    public ResponseEntity<FridgeDTO> addNewFridge(FridgeDTO fridgeDTO) {
        Fridge newFridge = FridgeMapper.toEntity(fridgeDTO);
        Fridge savedFridge = fridgeRepository.save(newFridge);
        return ResponseEntity.ok(FridgeMapper.toDTO(savedFridge));
    }
}

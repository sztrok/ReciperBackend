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

@Service
public class FridgeService {

    FridgeRepository fridgeRepository;

    @Autowired
    public FridgeService(FridgeRepository fridgeRepository) {
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

}

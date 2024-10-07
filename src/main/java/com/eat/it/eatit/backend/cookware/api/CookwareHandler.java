package com.eat.it.eatit.backend.cookware.api;

import com.eat.it.eatit.backend.cookware.data.Cookware;
import com.eat.it.eatit.backend.cookware.data.CookwareDTO;
import com.eat.it.eatit.backend.cookware.data.CookwareMapper;
import com.eat.it.eatit.backend.cookware.data.CookwareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CookwareHandler {

    CookwareRepository cookwareRepository;

    @Autowired
    public CookwareHandler(CookwareRepository cookwareRepository) {
        this.cookwareRepository = cookwareRepository;
    }

    public CookwareDTO getCookwareById(Long id) {
        Cookware cookware = cookwareRepository.findById(id).orElse(null);
        return CookwareMapper.toDTO(cookware);
    }

    public List<CookwareDTO> getAllCookwares() {
        List<Cookware> cookwares = cookwareRepository.findAll();
        List<CookwareDTO> cookwareDTOList = new ArrayList<>();
        for(Cookware cookware : cookwares) {
            cookwareDTOList.add(CookwareMapper.toDTO(cookware));
        }
        return cookwareDTOList;
    }

    public ResponseEntity<CookwareDTO> addNewCookware(CookwareDTO cookwareDTO) {
        Cookware cookware = CookwareMapper.toEntity(cookwareDTO);
        Cookware savedCookware = cookwareRepository.save(cookware);
        return ResponseEntity.ok(CookwareMapper.toDTO(savedCookware));
    }
}

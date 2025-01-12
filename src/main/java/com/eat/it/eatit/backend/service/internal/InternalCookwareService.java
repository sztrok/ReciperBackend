package com.eat.it.eatit.backend.service.internal;

import com.eat.it.eatit.backend.data.Cookware;
import com.eat.it.eatit.backend.dto.CookwareDTO;
import com.eat.it.eatit.backend.repository.CookwareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static com.eat.it.eatit.backend.mapper.CookwareMapper.toDTO;
import static com.eat.it.eatit.backend.mapper.CookwareMapper.toEntity;

/**
 * Service class for managing cookware inventory.
 * Provides methods for CRUD operations on cookware items.
 */
@Service
public class InternalCookwareService {

    CookwareRepository cookwareRepository;

    @Autowired
    public InternalCookwareService(CookwareRepository cookwareRepository) {
        this.cookwareRepository = cookwareRepository;
    }

    @Transactional
    public CookwareDTO addNewCookware(CookwareDTO cookwareDTO) {
        Cookware cookware = toEntity(cookwareDTO);
        Cookware savedCookware = cookwareRepository.save(cookware);
        return toDTO(savedCookware);
    }

    @Transactional
    public CookwareDTO updateCookware(Long id, String name) {
        Cookware existingCookware = cookwareRepository.findById(id).orElse(null);
        if (existingCookware == null) {
            return null;
        }
        existingCookware.setName(name);
        Cookware saved = cookwareRepository.save(existingCookware);
        return toDTO(saved);
    }

    @Transactional
    public boolean deleteCookware(Long id) {
        Cookware existingCookware = cookwareRepository.findById(id).orElse(null);
        if (existingCookware == null) {
            return false;
        }
        cookwareRepository.delete(existingCookware);
        return true;
    }

}

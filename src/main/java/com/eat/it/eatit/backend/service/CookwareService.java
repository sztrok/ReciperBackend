package com.eat.it.eatit.backend.service;

import com.eat.it.eatit.backend.data.Cookware;
import com.eat.it.eatit.backend.dto.CookwareDTO;
import com.eat.it.eatit.backend.mapper.CookwareMapper;
import com.eat.it.eatit.backend.repository.CookwareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class for managing cookware inventory.
 * Provides methods for CRUD operations on cookware items.
 */
@Service
public class CookwareService {

    CookwareRepository cookwareRepository;

    @Autowired
    public CookwareService(CookwareRepository cookwareRepository) {
        this.cookwareRepository = cookwareRepository;
    }

    /**
     * Retrieves a cookware item by its unique identifier.
     *
     * @param id the unique identifier of the cookware item
     * @return ResponseEntity containing the cookware item if found, or a not found status if the item does not exist
     */
    public ResponseEntity<CookwareDTO> getCookwareById(Long id) {
        Cookware cookware = cookwareRepository.findById(id).orElse(null);
        if (cookware == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(CookwareMapper.toDTO(cookware));
    }

    /**
     * Retrieves all cookware items from the repository and converts them into a list of CookwareDTO.
     *
     * @return ResponseEntity containing a list of CookwareDTO.
     */
    public ResponseEntity<List<CookwareDTO>> getAllCookwares() {
        List<Cookware> cookwares = cookwareRepository.findAll();
        List<CookwareDTO> cookwareDTOList = new ArrayList<>();
        for(Cookware cookware : cookwares) {
            cookwareDTOList.add(CookwareMapper.toDTO(cookware));
        }
        return ResponseEntity.ok(cookwareDTOList);
    }

    /**
     * Adds a new cookware item to the inventory.
     *
     * @param cookwareDTO The data transfer object containing cookware details to be added.
     * @return The response entity containing the saved cookware details.
     */
    public ResponseEntity<CookwareDTO> addNewCookware(CookwareDTO cookwareDTO) {
        Cookware cookware = CookwareMapper.toEntity(cookwareDTO);
        Cookware savedCookware = cookwareRepository.save(cookware);
        return ResponseEntity.ok(CookwareMapper.toDTO(savedCookware));
    }

    /**
     * Updates an existing cookware item identified by the provided ID with the details
     * from the provided CookwareDTO object.
     *
     * @param id The ID of the cookware item to be updated.
     * @param cookwareDTO The CookwareDTO object containing the new details for the cookware item.
     * @return A ResponseEntity containing the updated CookwareDTO object if the update is successful,
     *         or a 404 Not Found response if the cookware item with the provided ID does not exist.
     */
    public ResponseEntity<CookwareDTO> updateCookware(Long id, CookwareDTO cookwareDTO) {
        Cookware existingCookware = cookwareRepository.findById(id).orElse(null);
        if (existingCookware == null) {
            return ResponseEntity.notFound().build();
        }

        existingCookware.setName(cookwareDTO.getName());
        Cookware updatedCookware = cookwareRepository.save(existingCookware);
        return ResponseEntity.ok(CookwareMapper.toDTO(updatedCookware));
    }

    /**
     * Deletes a cookware item identified by its ID.
     *
     * @param id The ID of the cookware item to be deleted.
     * @return A ResponseEntity<Void> indicating the outcome of the delete operation.
     */
    public ResponseEntity<Void> deleteCookware(Long id) {
        Cookware existingCookware = cookwareRepository.findById(id).orElse(null);
        if (existingCookware == null) {
            return ResponseEntity.notFound().build();
        }

        cookwareRepository.delete(existingCookware);
        return ResponseEntity.noContent().build();
    }
}

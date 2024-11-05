package com.eat.it.eatit.backend.repository;

import com.eat.it.eatit.backend.data.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Item findByNameIgnoreCase(String name);

    Item findByBarcode(Long barcode);

    Set<Item> findAllByNameContainsIgnoreCase(String name);

    Set<Item> findAllByProteinsIsGreaterThanEqual(Double amount);

    Set<Item> findAllByProteinsIsLessThanEqual(Double amount);

    Set<Item> findAllByCaloriesPer100gIsGreaterThanEqual(Double amount);

    Set<Item> findAllByCaloriesPer100gIsLessThanEqual(Double amount);

    Set<Item> findAllByFatPer100GIsGreaterThanEqual(Double amount);

    Set<Item> findAllByFatPer100GIsLessThanEqual(Double amount);

    Set<Item> findAllByCarbsPer100GIsGreaterThanEqual(Double amount);

    Set<Item> findAllByCarbsPer100GIsLessThanEqual(Double amount);

    Set<Item> findAllByProteinsBetween(Double min, Double max);

    Set<Item> findAllByCaloriesPer100gBetween(Double min, Double max);

    Set<Item> findAllByFatPer100GBetween(Double min, Double max);

    Set<Item> findAllByCarbsPer100GBetween(Double min, Double max);

}

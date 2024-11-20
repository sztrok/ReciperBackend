package com.eat.it.eatit.backend.repository;

import com.eat.it.eatit.backend.data.Item;
import com.eat.it.eatit.backend.enums.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Item findByNameIgnoreCase(String name);

    Item findByBarcode(Long barcode);

    List<Item> findAllByCaloriesPer100gNotNull();

    List<Item> findAllByItemTypeIn(Set<ItemType> types);

    List<Item> findAllByNameContainsIgnoreCase(String name);

    List<Item> findAllByProteinsIsGreaterThanEqual(Double amount);

    List<Item> findAllByProteinsIsLessThanEqual(Double amount);

    List<Item> findAllByCaloriesPer100gIsGreaterThanEqual(Double amount);

    List<Item> findAllByCaloriesPer100gIsLessThanEqual(Double amount);

    List<Item> findAllByFatPer100GIsGreaterThanEqual(Double amount);

    List<Item> findAllByFatPer100GIsLessThanEqual(Double amount);

    List<Item> findAllByCarbsPer100GIsGreaterThanEqual(Double amount);

    List<Item> findAllByCarbsPer100GIsLessThanEqual(Double amount);

    List<Item> findAllByProteinsBetween(Double min, Double max);

    List<Item> findAllByCaloriesPer100gBetween(Double min, Double max);

    List<Item> findAllByFatPer100GBetween(Double min, Double max);

    List<Item> findAllByCarbsPer100GBetween(Double min, Double max);

}

package com.eat.it.eatit.backend.item.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Item findByNameIgnoreCase(String name);
    Item findByBarcode(Long barcode);
    Set<Item> findAllByNameContainsIgnoreCase(String name);

    @Query("SELECT coalesce(MAX(id), 0) FROM Item")
    Long getMaxId();
}

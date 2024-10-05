package com.eat.it.eatit.backend.repositories;

import com.eat.it.eatit.backend.data.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Item findByName(String name);
    Item findByBarcode(Long barcode);
    Set<Item> findAllByNameContains(String name);

    @Query("SELECT coalesce(MAX(id), 0) FROM Item")
    Long getMaxId();
}

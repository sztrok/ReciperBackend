package com.eat.it.eatit.backend.repository;

import com.eat.it.eatit.backend.data.ItemInFridge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemInFridgeRepository extends JpaRepository<ItemInFridge, Long> {
}

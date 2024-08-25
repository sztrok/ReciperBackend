package com.eat.it.eatit.backend.repositories;

import com.eat.it.eatit.backend.data.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}

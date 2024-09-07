package com.eat.it.eatit.backend.repositories;

import com.eat.it.eatit.backend.data.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}

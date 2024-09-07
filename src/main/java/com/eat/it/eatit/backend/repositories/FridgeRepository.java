package com.eat.it.eatit.backend.repositories;

import com.eat.it.eatit.backend.data.Fridge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FridgeRepository extends JpaRepository<Fridge, Long> {
}

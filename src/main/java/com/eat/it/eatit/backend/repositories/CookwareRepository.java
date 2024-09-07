package com.eat.it.eatit.backend.repositories;

import com.eat.it.eatit.backend.data.Cookware;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CookwareRepository extends JpaRepository<Cookware, Long> {

}

package com.eat.it.eatit.backend.cookware;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CookwareRepository extends JpaRepository<Cookware, Long> {

}

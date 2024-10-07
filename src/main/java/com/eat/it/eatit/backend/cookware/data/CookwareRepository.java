package com.eat.it.eatit.backend.cookware.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CookwareRepository extends JpaRepository<Cookware, Long> {

}

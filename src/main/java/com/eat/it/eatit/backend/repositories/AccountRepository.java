package com.eat.it.eatit.backend.repositories;

import com.eat.it.eatit.backend.data.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

}

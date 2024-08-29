package com.eat.it.eatit.backend.repositories;

import com.eat.it.eatit.backend.data.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByMail(String mail);
    List<Account> findAllByUsernameContaining(String username);
    List<Account> findAllByPremiumIsTrue();
    List<Account> findAllByPremiumIsFalse();
}

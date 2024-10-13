package com.eat.it.eatit.backend.account.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByMail(String mail);
    Set<Account> findAllByUsernameContaining(String username);
    Set<Account> findAllByPremiumIsTrue();
    Set<Account> findAllByPremiumIsFalse();
    Account findByUsername(String username);

}

package com.eat.it.eatit.backend.repository;

import com.eat.it.eatit.backend.data.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByMail(String mail);
    List<Account> findAllByUsernameContaining(String username);
    List<Account> findAllByPremiumIsTrue();
    List<Account> findAllByPremiumIsFalse();
    Account findByUsername(String username);
    boolean existsByMail(String mail);

}

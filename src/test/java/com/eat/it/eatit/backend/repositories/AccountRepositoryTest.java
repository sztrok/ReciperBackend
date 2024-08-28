package com.eat.it.eatit.backend.repositories;

import com.eat.it.eatit.backend.data.Account;
import com.eat.it.eatit.backend.data.Fridge;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AccountRepositoryTest {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TestEntityManager testEntityManager;

    private Account account;

    @BeforeEach
    public void setUp() {
        account = new Account();
        account.setUsername("kamil");
        account.setMail("kamil@mail.com");
    }

    @AfterEach
    public void tearDown() {
        accountRepository.delete(account);
    }

    @Test
    void testInsertion() {
        Account insertedAccount = accountRepository.save(account);
        assertThat(testEntityManager.find(Account.class, insertedAccount.getId())).isEqualTo(account);
    }

    @Test
    void testUpdate() {
        testEntityManager.persist(account);
        String newName = "krzysztof";
        account.setUsername(newName);
        accountRepository.save(account);
        assertThat(testEntityManager.find(Account.class, account.getId()).getUsername()).isEqualTo(newName);
    }

    @Test
    void testFindById() {
        testEntityManager.persist(account);
        Optional<Account> retrievedAccount = accountRepository.findById(account.getId());
        assertThat(retrievedAccount).contains(account);
    }

    @Test
    void testFindByMail() {
        testEntityManager.persist(account);
        Optional<Account> retrievedMail = Optional.ofNullable(accountRepository.findByMail(account.getMail()));
        System.out.println(retrievedMail);
        assertThat(retrievedMail).contains(account);
    }

    @Test
    void testFindAllByUsernameContaining() {
        Account secondAccount = new Account("aaaakamilwada", "dasda@mail.com", false);
        accountRepository.save(account);
        accountRepository.save(secondAccount);
        String partOfUsername = "kamil";
        Optional<List<Account>> accountList = Optional.ofNullable(accountRepository.findAllByUsernameContaining(partOfUsername));
        System.out.println(accountList);
        assertThat(accountList).contains(List.of(account, secondAccount));
    }

    @Test
    void testfindAllByPremiumIsTrue() {
        Account secondAccount = new Account("aaaakamilwada", "dasda@mail.com", false);
        testEntityManager.persist(account);
        testEntityManager.persist(secondAccount);
        account.setPremium(true);
        Optional<List<Account>> allPremiumAccounts = Optional.ofNullable(accountRepository.findAllByPremiumIsTrue());
        assertThat(allPremiumAccounts).contains(List.of(account));
    }

    @Test
    void testfindAllByPremiumIsFalse() {
        Account secondAccount = new Account("aaaakamilwada", "dasda@mail.com", false);
        testEntityManager.persist(account);
        testEntityManager.persist(secondAccount);
        account.setPremium(true);
        Optional<List<Account>> allPremiumAccounts = Optional.ofNullable(accountRepository.findAllByPremiumIsFalse());
        assertThat(allPremiumAccounts).contains(List.of(secondAccount));
    }

    @Test
    void testSettingFridge() {
        testEntityManager.persist(account);
        Fridge fridge = new Fridge();
        account.setFridge(fridge);
        assertThat(testEntityManager.find(Account.class, account.getId()).getFridge()).isEqualTo(fridge);
    }

}

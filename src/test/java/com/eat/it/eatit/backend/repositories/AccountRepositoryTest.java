package com.eat.it.eatit.backend.repositories;

import com.eat.it.eatit.backend.data.Account;
import com.eat.it.eatit.backend.data.Fridge;
import com.eat.it.eatit.backend.data.Recipe;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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
        accountRepository.save(account);
        String newName = "krzysztof";
        account.setUsername(newName);
        assertThat(testEntityManager.find(Account.class, account.getId()).getUsername()).isEqualTo(newName);
    }

    @Test
    void testFindById() {
        accountRepository.save(account);
        Optional<Account> retrievedAccount = accountRepository.findById(account.getId());
        assertThat(retrievedAccount).contains(account);
    }

    @Test
    void testFindByMail() {
        accountRepository.save(account);
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
    void testFindAllByPremiumIsTrue() {
        Account secondAccount = new Account("aaaakamilwada", "dasda@mail.com", false);
        accountRepository.save(account);
        accountRepository.save(secondAccount);
        account.setPremium(true);
        Optional<List<Account>> allPremiumAccounts = Optional.ofNullable(accountRepository.findAllByPremiumIsTrue());
        assertThat(allPremiumAccounts).contains(List.of(account));
    }

    @Test
    void testFindAllByPremiumIsFalse() {
        Account secondAccount = new Account("aaaakamilwada", "dasda@mail.com", false);
        accountRepository.save(account);
        accountRepository.save(secondAccount);
        account.setPremium(true);
        Optional<List<Account>> allPremiumAccounts = Optional.ofNullable(accountRepository.findAllByPremiumIsFalse());
        assertThat(allPremiumAccounts).contains(List.of(secondAccount));
    }

    @Test
    void testAttachingFridge() {
        accountRepository.save(account);
        Fridge fridge = new Fridge();
        account.setFridge(fridge);
        System.out.println(testEntityManager.find(Account.class, account.getId()).getFridge());
        assertThat(testEntityManager.find(Account.class, account.getId()).getFridge()).isEqualTo(fridge);
    }

    @Test
    void testAttachingRecipes() {
        accountRepository.save(account);
        Set<Recipe> recipes = Set.of(
                new Recipe("test", account.getId(), "recipe for test purposes"),
                new Recipe("test2", account.getId(), "recipe for test purposes")
        );
        account.setRecipes(recipes);
        System.out.println(testEntityManager.find(Account.class, account.getId()).getRecipes());
        assertThat(testEntityManager.find(Account.class, account.getId()).getRecipes()).isEqualTo(recipes);
    }

}

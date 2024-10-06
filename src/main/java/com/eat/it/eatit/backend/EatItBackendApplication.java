package com.eat.it.eatit.backend;

import com.eat.it.eatit.backend.account.Account;
import com.eat.it.eatit.backend.fridge.Fridge;
import com.eat.it.eatit.backend.account.AccountRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class EatItBackendApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(EatItBackendApplication.class, args);
        AccountRepository repo = context.getBean(AccountRepository.class);

        Account account = new Account();
        account.setUsername("kamil");
        account.setMail("kamil@mail.com");
        account.setFridge(new Fridge());
        repo.save(account);
        System.out.println(account);
    }

}

package com.eat.it.eatit.backend;

import com.eat.it.eatit.backend.account.data.Account;
import com.eat.it.eatit.backend.fridge.data.Fridge;
import com.eat.it.eatit.backend.account.data.AccountRepository;
import com.eat.it.eatit.backend.fridge.data.FridgeRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class EatItBackendApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(EatItBackendApplication.class, args);
        AccountRepository repo = context.getBean(AccountRepository.class);
        FridgeRepository fridgeRepository = context.getBean(FridgeRepository.class);

        Account account = new Account();
        Fridge fridge = new Fridge();
        account.setUsername("kamil");
        account.setMail("kamil@mail.com");
        account.setFridge(fridge);
        Account savedAcc = repo.save(account);
        fridge.setOwnerId(savedAcc.getId());
        fridgeRepository.save(fridge);
        System.out.println(account);
    }

}

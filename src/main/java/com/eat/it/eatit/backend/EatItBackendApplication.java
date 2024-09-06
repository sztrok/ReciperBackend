package com.eat.it.eatit.backend;

import com.eat.it.eatit.backend.data.Account;
import com.eat.it.eatit.backend.data.Fridge;
import com.eat.it.eatit.backend.dto.AccountDTO;
import com.eat.it.eatit.backend.dto.FridgeDTO;
import com.eat.it.eatit.backend.repositories.AccountRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class EatItBackendApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(EatItBackendApplication.class, args);
        AccountRepository repo = context.getBean(AccountRepository.class);
//        AccountDTO account = context.getBean(AccountDTO.class);
//        account.setUsername("kamil");
//        account.setUsername("kamil@mail.com");
//        account.setFridge(context.getBean(FridgeDTO.class));
//        System.out.println(account);
//        Account account1 = new Account();
//        account1.setFridge(account.getFridge());
//        account1.setMail(account.getMail());
//
//        repo.save(account);

        Account account = new Account();
        account.setUsername("kamil");
        account.setMail("kamil@mail.com");
        account.setFridge(new Fridge());
        System.out.println(account);
        repo.save(account);
    }

}

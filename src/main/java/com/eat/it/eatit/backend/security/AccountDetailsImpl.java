package com.eat.it.eatit.backend.security;

import com.eat.it.eatit.backend.account.data.Account;
import com.eat.it.eatit.backend.account.data.AccountRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class AccountDetailsImpl implements UserDetails {

    private final Account account;

    public AccountDetailsImpl(Account account) {
        this.account = account;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<AccountRole> accountRoles = account.getAccountRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for(AccountRole accountRole : accountRoles) {
            authorities.add(new SimpleGrantedAuthority(accountRole.name()));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return account.getPassword();
    }

    @Override
    public String getUsername() {
        return account.getUsername();
    }
}

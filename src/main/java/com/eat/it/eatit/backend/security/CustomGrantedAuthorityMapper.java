package com.eat.it.eatit.backend.security;

import com.eat.it.eatit.backend.security.service.RoleHierarchyService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Component
public class CustomGrantedAuthorityMapper {
    private final RoleHierarchyService roleHierarchyService;

    public CustomGrantedAuthorityMapper(RoleHierarchyService roleHierarchyService) {
        this.roleHierarchyService = roleHierarchyService;
    }

    /**
     * Mapuje podane uprawnienia (GrantedAuthority) na rozszerzone uprawnienia
     * na podstawie hierarchii ról zdefiniowanej w RoleHierarchyService.
     *
     * @param authorities Zbiór uprawnień użytkownika.
     * @return Zbiór uprawnień rozszerzony o uprawnienia dziedziczone.
     */
    public Collection<GrantedAuthority> mapAuthorities(Collection<? extends GrantedAuthority> authorities) {

        // Dodaj oryginalne uprawnienia
        Set<GrantedAuthority> mappedAuthorities = new HashSet<>(authorities);

        // Dodaj uprawnienia dziedziczone na podstawie hierarchii
        for (GrantedAuthority authority : authorities) {
            Collection<GrantedAuthority> inheritedAuthorities =
                    roleHierarchyService.getAuthorities(Set.of(authority));
            mappedAuthorities.addAll(inheritedAuthorities);
        }

        return mappedAuthorities;
    }
}

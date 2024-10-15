package com.eat.it.eatit.backend.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class RoleHierarchyService {

    private static final Map<String, Set<String>> ROLE_HIERARCHY_MAP = Map.of(
            "ROLE_ADMIN", Set.of("ROLE_SUPPORT", "ROLE_USER"),
            "ROLE_SUPPORT", Set.of("ROLE_USER")
    );

    public Collection<GrantedAuthority> getAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<GrantedAuthority> allAuthorities = new HashSet<>(authorities);
        for(GrantedAuthority authority : authorities) {
            String roleName = authority.getAuthority();
            Set<String> inheritedRoles = ROLE_HIERARCHY_MAP.getOrDefault(roleName, Set.of());

            for(String inheritedRole : inheritedRoles) {
                allAuthorities.add(new SimpleGrantedAuthority(inheritedRole));
            }
        }
        return allAuthorities;
    }
}

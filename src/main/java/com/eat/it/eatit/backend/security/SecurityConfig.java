package com.eat.it.eatit.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AccountDetailsServiceImpl accountDetailsService;
    private final CustomGrantedAuthorityMapper authorityMapper;

    private static final String USER_AUTHORITY = "ROLE_USER";
    private static final String SUPPORT_AUTHORITY = "ROLE_SUPPORT";
    private static final String ADMIN_AUTHORITY = "ROLE_ADMIN";
    private static final String GLOBAL_API_PATH = "/api/v1/global";
    private static final String USER_API_PATH = "/api/v1/user";
    private static final String INTERNAL_API_PATH = "/api/v1/internal";
    private static final String ADMIN_API_PATH = "/api/v1/admin";

    @Autowired
    public SecurityConfig(AccountDetailsServiceImpl accountDetailsService, CustomGrantedAuthorityMapper authorityMapper) {
        this.accountDetailsService = accountDetailsService;
        this.authorityMapper = authorityMapper;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {



        httpSecurity.formLogin(Customizer.withDefaults());
 
        // GLOBAL
        httpSecurity.authorizeHttpRequests(
                auth -> auth
                        // GENERAL
                        .requestMatchers(HttpMethod.POST, GLOBAL_API_PATH + "/general/register").permitAll()
                        .requestMatchers(HttpMethod.GET, GLOBAL_API_PATH + "/general/profile").authenticated()
                        // COOKWARE
                        .requestMatchers(HttpMethod.GET, GLOBAL_API_PATH + "/cookware").permitAll()
                        // ITEM
                        .requestMatchers(HttpMethod.GET, GLOBAL_API_PATH + "/item").permitAll()
                        // RECIPE
                        .requestMatchers(HttpMethod.GET, GLOBAL_API_PATH + "/recipe").permitAll()
        );

        // USER
        httpSecurity.authorizeHttpRequests(
                auth -> auth
                        .requestMatchers(HttpMethod.GET, USER_API_PATH + "/**").hasAuthority(USER_AUTHORITY)
                        .requestMatchers(HttpMethod.POST, USER_API_PATH + "/**").hasAuthority(USER_AUTHORITY)
                        .requestMatchers(HttpMethod.PUT, USER_API_PATH + "/**").hasAuthority(USER_AUTHORITY)
                        .requestMatchers(HttpMethod.DELETE, USER_API_PATH + "/**").hasAuthority(USER_AUTHORITY)
                        .requestMatchers(HttpMethod.PATCH, USER_API_PATH + "/**").hasAuthority(USER_AUTHORITY)
        );
        
        // INTERNAL (SUPPORT + ADMIN)
        httpSecurity.authorizeHttpRequests(
                auth -> auth
                        .requestMatchers(HttpMethod.GET, INTERNAL_API_PATH + "/**").hasAnyAuthority(SUPPORT_AUTHORITY, ADMIN_AUTHORITY)
                        .requestMatchers(HttpMethod.POST, INTERNAL_API_PATH + "/**").hasAnyAuthority(SUPPORT_AUTHORITY, ADMIN_AUTHORITY)
                        .requestMatchers(HttpMethod.PUT, INTERNAL_API_PATH + "/**").hasAnyAuthority(SUPPORT_AUTHORITY, ADMIN_AUTHORITY)
                        .requestMatchers(HttpMethod.PATCH, INTERNAL_API_PATH + "/**").hasAnyAuthority(SUPPORT_AUTHORITY, ADMIN_AUTHORITY)
        );

        // ADMIN
        httpSecurity.authorizeHttpRequests(
                auth -> auth
                        .requestMatchers(HttpMethod.GET,"/**").hasAuthority(ADMIN_AUTHORITY)
                        .requestMatchers(HttpMethod.POST,"/**").hasAuthority(ADMIN_AUTHORITY)
                        .requestMatchers(HttpMethod.PUT,"/**").hasAuthority(ADMIN_AUTHORITY)
                        .requestMatchers(HttpMethod.PATCH,"/**").hasAuthority(ADMIN_AUTHORITY)
                        .requestMatchers(HttpMethod.DELETE,"/**").hasAuthority(ADMIN_AUTHORITY)
                        )
                .csrf(AbstractHttpConfigurer::disable)
                .userDetailsService(accountDetailsService);
        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(accountDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        provider.setAuthoritiesMapper(authorityMapper::mapAuthorities);
        return provider;
    }

}

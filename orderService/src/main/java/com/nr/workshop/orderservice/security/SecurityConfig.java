package com.nr.workshop.orderservice.security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @SuppressWarnings("removal")
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf().disable().authorizeHttpRequests((authorize) -> {
            authorize
//                    .requestMatchers("/products").authenticated()
//                    .requestMatchers("/products/**").authenticated()
//                    .requestMatchers("/*").permitAll()
//                    .requestMatchers("/static/**").permitAll()
                    .anyRequest().authenticated()
            ;
        }).httpBasic(Customizer.withDefaults());
        http.cors(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {

        List<UserDetails> inMemoryUserList = new ArrayList<>();

        UserDetails admin = User.builder().username("admin").password(passwordEncoder().encode("abc123")).roles("ADMIN")
                .build();
        UserDetails testuser = User.builder().username("testuser").password(passwordEncoder().encode("abc123")).roles("USER")
                .build();
        UserDetails nruser = User.builder().username("nruser").password(passwordEncoder().encode("abc123")).roles("USER")
                .build();
        UserDetails demouser = User.builder().username("demouser").password(passwordEncoder().encode("abc123")).roles("USER")
                .build();

        inMemoryUserList.add(admin);
        inMemoryUserList.add(testuser);
        inMemoryUserList.add(nruser);
        inMemoryUserList.add(demouser);

        return new InMemoryUserDetailsManager(inMemoryUserList);
    }
}
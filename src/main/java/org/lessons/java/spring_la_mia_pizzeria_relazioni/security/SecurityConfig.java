package org.lessons.java.spring_la_mia_pizzeria_relazioni.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(requests -> requests
                .requestMatchers("/pizzas/create", "/pizzas/update/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.POST, "/pizzas/**").hasAuthority("ADMIN")
                .requestMatchers("/ingredients/**").hasAuthority("ADMIN")
                .anyRequest().permitAll())
                .formLogin(Customizer.withDefaults())
                .cors(cors -> cors.disable())
                .csrf(csrf -> csrf.disable());
        return http.build();
    }
}

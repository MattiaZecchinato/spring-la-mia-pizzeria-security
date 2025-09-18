package org.lessons.java.spring_la_mia_pizzeria_relazioni.security;

import org.lessons.java.spring_la_mia_pizzeria_relazioni.service.DataBaseUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    DataBaseUserDetailsService dataBaseUserDetailsService() {
        return new DataBaseUserDetailsService();
    }

    @Bean
    @SuppressWarnings("deprecation")
    DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthProvider = new DaoAuthenticationProvider();

        daoAuthProvider.setUserDetailsService(dataBaseUserDetailsService());

        daoAuthProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthProvider;
    }
}

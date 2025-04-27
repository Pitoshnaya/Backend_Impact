package Pitoshnaya.Impact.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    //шифровка кода
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/api/register").permitAll() // Разрешаем доступ к /api/register без аутентификации
                                .anyRequest().authenticated() // Все остальные запросы требуют аутентификацию
                )
                .httpBasic(Customizer.withDefaults()) // Для базовой аутентификации
                .formLogin(Customizer.withDefaults()) // Если хотите использовать форму входа
                .csrf(csrf -> csrf.disable()); // Отключаем CSRF
        return http.build();
    }
}
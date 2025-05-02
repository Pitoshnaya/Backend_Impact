package Pitoshnaya.Impact.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import Pitoshnaya.Impact.dto.AuthRequest;
import Pitoshnaya.Impact.dto.AuthResponse;
import Pitoshnaya.Impact.dto.RegisterRequest;
import java.util.Objects;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testUserRegistrationAndLogin() {
        String username = "user_" + UUID.randomUUID();
        String password = "StrongP@ssword123";

        ResponseEntity<Void> registerResponse = restTemplate.postForEntity(
            "/api/register",
            new RegisterRequest(username, password),
            Void.class
        );

        assertEquals(HttpStatus.OK, registerResponse.getStatusCode(), "Регистрация не прошла");

        ResponseEntity<AuthResponse> loginResponse = restTemplate.postForEntity(
            "/api/login",
            new AuthRequest(username, password),
            AuthResponse.class
        );

        assertEquals(HttpStatus.OK, loginResponse.getStatusCode(), "Авторизация не прошла");
        assertNotNull(loginResponse.getBody(), "Ответ на авторизацию пустой");
        assertNotNull(loginResponse.getBody().token(), "Токен отсутствует");
    }


    @Test
    void testOnlyLatestTokenIsValid() {
        String username = "session_user_" + UUID.randomUUID();
        String password = "Secure123!";

        // 1. Регистрация
        ResponseEntity<Void> registerResponse = restTemplate.postForEntity(
            "/api/register",
            new RegisterRequest(username, password),
            Void.class
        );
        Assertions.assertEquals(HttpStatus.OK, registerResponse.getStatusCode());

        // 2. Первая авторизация — token1
        ResponseEntity<AuthResponse> loginResponse1 = restTemplate.postForEntity(
            "/api/login",
            new AuthRequest(username, password),
            AuthResponse.class
        );
        String token1 = Objects.requireNonNull(loginResponse1.getBody()).token();
        Assertions.assertNotNull(token1);

        // 3. Вторая авторизация — token2
        ResponseEntity<AuthResponse> loginResponse2 = restTemplate.postForEntity(
            "/api/login",
            new AuthRequest(username, password),
            AuthResponse.class
        );
        String token2 = Objects.requireNonNull(loginResponse2.getBody()).token();
        Assertions.assertNotNull(token2);
        Assertions.assertNotEquals(token1, token2, "Ожидались разные токены");

        // 4. Запрос с token1 (должен быть невалиден)
        HttpHeaders headers1 = new HttpHeaders();
        headers1.setBearerAuth(token1);
        HttpEntity<Void> request1 = new HttpEntity<>(headers1);
        ResponseEntity<String> oldTokenResponse = restTemplate.exchange(
            "/hello", HttpMethod.GET, request1, String.class
        );
        Assertions.assertEquals(HttpStatus.FORBIDDEN, oldTokenResponse.getStatusCode(),
            "Первый токен должен быть невалидным"
        );

        // 5. Запрос с token2 (должен пройти)
        HttpHeaders headers2 = new HttpHeaders();
        headers2.setBearerAuth(token2);
        HttpEntity<Void> request2 = new HttpEntity<>(headers2);
        ResponseEntity<String> newTokenResponse = restTemplate.exchange(
            "/hello", HttpMethod.GET, request2, String.class
        );
        Assertions.assertEquals(HttpStatus.OK, newTokenResponse.getStatusCode(),
            "Второй токен должен быть валидным"
        );
    }
}

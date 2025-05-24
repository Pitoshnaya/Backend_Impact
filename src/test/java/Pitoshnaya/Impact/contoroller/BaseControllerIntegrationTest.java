package Pitoshnaya.Impact.contoroller;

import Pitoshnaya.Impact.dto.AuthResponse;
import Pitoshnaya.Impact.dto.RegisterRequest;
import java.util.Objects;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class BaseControllerIntegrationTest {

    protected String token;

    @Autowired
    protected TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @BeforeAll
    void beforeAll() {
        String username = "user_";
        String password = "StrongP@ssword123";

        restTemplate.postForEntity(
            "/api/register",
            new RegisterRequest(username, password),
            Void.class
        );

        ResponseEntity<AuthResponse> loginResponse = restTemplate.postForEntity(
            "/api/login",
            new RegisterRequest(username, password),
            AuthResponse.class
        );

        token = "Bearer " + Objects.requireNonNull(loginResponse.getBody()).token();
    }

    private <T, R> ResponseEntity<T> sendRequest(String url, R requestBody, HttpMethod method, Class<T> responseType) {
        url = url.startsWith("/") ? url : "/" + url;
        String fullUrl = "http://localhost:" + port + url;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<R> entity = new HttpEntity<>(requestBody, headers);

        return restTemplate.exchange(
            fullUrl,
            method,
            entity,
            responseType
        );
    }

    protected ResponseEntity<?> sendGet(String url, Class<?> responseType) {
        return sendRequest(url, null, HttpMethod.GET, responseType);
    }

    protected <T, R> ResponseEntity<T> sendPut(String url, R requestBody, Class<T> responseType) {
        return sendRequest(url, requestBody, HttpMethod.PUT, responseType);
    }
}


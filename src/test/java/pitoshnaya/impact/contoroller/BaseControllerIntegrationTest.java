package pitoshnaya.impact.contoroller;

import java.util.Map;
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
import org.springframework.web.util.UriComponentsBuilder;
import pitoshnaya.impact.dto.AuthResponse;
import pitoshnaya.impact.dto.RegisterRequest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class BaseControllerIntegrationTest {

  protected String token;

  @Autowired protected TestRestTemplate restTemplate;

  @LocalServerPort private int port;

  @BeforeAll
  void beforeAll() {
    String username = "user_";
    String password = "StrongP@ssword123";

    restTemplate.postForEntity(
        "/api/register", new RegisterRequest(username, password), Void.class);

    ResponseEntity<AuthResponse> loginResponse =
        restTemplate.postForEntity(
            "/api/login", new RegisterRequest(username, password), AuthResponse.class);

    token = "Bearer " + Objects.requireNonNull(loginResponse.getBody()).token();
  }

  private <T, R> ResponseEntity<T> sendRequest(
      String url, R requestBody, HttpMethod method, Class<T> responseType, Map<String, ?> params) {
    url = url.startsWith("/") ? url : "/" + url;
    String fullUrl = "http://localhost:" + port + url;

    UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(fullUrl);
    if (params != null) {
      params.forEach(builder::queryParam);
    }

    fullUrl = builder.build().toUriString();

    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", token);
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<R> entity = new HttpEntity<>(requestBody, headers);

    return restTemplate.exchange(fullUrl, method, entity, responseType);
  }

  protected <T> ResponseEntity<T> sendGet(
      String url, Map<String, ?> params, Class<T> responseType) {
    return sendRequest(url, null, HttpMethod.GET, responseType, params);
  }

  protected <T, R> ResponseEntity<T> sendPut(String url, R requestBody, Class<T> responseType) {
    return sendRequest(url, requestBody, HttpMethod.PUT, responseType, null);
  }
}

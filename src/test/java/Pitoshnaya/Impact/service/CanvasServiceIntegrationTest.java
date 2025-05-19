package Pitoshnaya.Impact.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import Pitoshnaya.Impact.config.GridSize;
import Pitoshnaya.Impact.dao.CellsDao;
import Pitoshnaya.Impact.dto.AuthResponse;
import Pitoshnaya.Impact.dto.DrawRequest;
import Pitoshnaya.Impact.dto.RegisterRequest;
import Pitoshnaya.Impact.entity.Cell;
import Pitoshnaya.Impact.validate.ValidationErrorResponse;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CanvasServiceIntegrationTest {

    private String token;

    private static final Random random = new Random();

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CellsDao cellsDao;

    @BeforeAll
    void beforeAll() {
        String username = "user_" + UUID.randomUUID();
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

        System.out.println(token);
    }

    @ParameterizedTest
    @MethodSource("validCoordinatesProvider")
    void testDrawIsValidDrawRequest(Map<Integer, Integer> coords) {
        String color = getRandomHexColor();
        int x = coords.keySet().iterator().next();
        int y = coords.values().iterator().next();
        DrawRequest drawRequest = new DrawRequest(x, y, color);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity<DrawRequest> entity = new HttpEntity<>(drawRequest, headers);

        ResponseEntity<Void> response = restTemplate.exchange(
            "/api/canvas",
            HttpMethod.PUT,
            entity,
            Void.class
        );

        Cell cell = cellsDao.getCellByCoordinates(x, y);

        assertNotNull(cell);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(color, cell.getColor());
    }

    @ParameterizedTest
    @MethodSource("invalidCoordinatesProvider")
    void testDrawIsInvalidDrawRequest(int x, int y, String color) {
        DrawRequest drawRequest = new DrawRequest(x, y, color);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity<DrawRequest> entity = new HttpEntity<>(drawRequest, headers);

        ResponseEntity<ValidationErrorResponse> response = restTemplate.exchange(
            "/api/canvas",
            HttpMethod.PUT,
            entity,
            ValidationErrorResponse.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody().violations());
        assertFalse(response.getBody().violations().isEmpty());
    }

    private static Stream<Arguments> validCoordinatesProvider() {
        int width = GridSize.WIDTH;
        int height = GridSize.HEIGHT;

        return Stream.of(
            Arguments.of(Map.of(1, 1)),
            Arguments.of(Map.of(width, 1)),
            Arguments.of(Map.of(1, height)),
            Arguments.of(Map.of(width, height)),
            Arguments.of(Map.of(width / 2, height / 2))
        );
    }

    private static Stream<Arguments> invalidCoordinatesProvider() {
        int width = GridSize.WIDTH;
        int height = GridSize.HEIGHT;

        return Stream.of(
            Arguments.of(-1, 10, "#ffffff"),
            Arguments.of(10, -1, "#123abc"),
            Arguments.of(width + 1, 10, "#abcdef"),
            Arguments.of(10, height + 1, "#abcdef"),
            Arguments.of(10, 10, "not-a-color"),
            Arguments.of(10, 10, "#12345"),
            Arguments.of(10, 10, "#1234567"),
            Arguments.of(10, 10, "#ZZZZZZ")
        );
    }

    private static String getRandomHexColor() {
        StringBuilder sb = new StringBuilder("#");
        for (int i = 0; i < 6; i++) {
            int digit = random.nextInt(16);
            sb.append(Integer.toHexString(digit));
        }
        return sb.toString();
    }
}

package pitoshnaya.impact.contoroller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import pitoshnaya.impact.dto.GridHistoryDto;
import pitoshnaya.impact.validate.ValidationErrorResponse;

public class HistoryControllerIntegrationTest extends BaseControllerIntegrationTest {
  private final String url = "/api/history";

  @ParameterizedTest
  @Sql("/fixtures/write_history.sql")
  @MethodSource("validRangeProvider")
  void testGetHistoryByValidRange(String start, String end, int expectedSize) {
    var response =
        sendGet(
            url,
            Map.of(
                "start", start,
                "end", end),
            GridHistoryDto.class);

    var history = response.getBody();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(history);
    assertThat(history.base()).hasSize(625);
    assertThat(history.history()).hasSize(expectedSize);
  }

  @ParameterizedTest
  @MethodSource("invalidRangeProvider")
  void testGetHistoryByInvalidParameters(
      String start, String end, ValidationErrorResponse expected) {
    var response =
        sendGet(
            url,
            Map.of(
                "start", start,
                "end", end),
            ValidationErrorResponse.class);

    var body = response.getBody();

    assertNotNull(body);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertThat(body.violations()).containsExactlyInAnyOrderElementsOf(expected.violations());
  }

  private Stream<Arguments> validRangeProvider() {
    return Stream.of(
        Arguments.of("2025-05-24T00:00:00", "2025-05-30T23:59:59", 10),
        Arguments.of("2025-05-30T00:00:00", "2025-05-30T23:59:59", 3),
        Arguments.of("2025-05-28T08:22:00", "2025-05-28T08:23:30", 3),
        Arguments.of("2025-05-25T00:00:00", "2025-05-27T23:59:59", 2),
        Arguments.of("2025-05-24T00:00:00", "2025-05-24T23:59:59", 1),
        Arguments.of("2025-05-20T00:00:00", "2025-05-23T23:59:59", 0),
        Arguments.of("2025-06-01T00:00:00", "2025-06-02T00:00:00", 0));
  }

  public Stream<Arguments> invalidRangeProvider() {
    String defaultMessage = "Дата и время должны быть в формате yyyy-MM-dd'T'HH:mm:ss";
    return Stream.of(
        Arguments.of(
            "2025-05-24T00:00:01",
            "2025-05-24T00:00:00",
            new ValidationErrorResponse(
                List.of(Map.of("start", "Начальная дата должна быть меньше конечной")))),
        Arguments.of(
            "2025-05-24T00:00:0",
            "2025-05-30T23:59:59",
            new ValidationErrorResponse(List.of(Map.of("start", defaultMessage)))),
        Arguments.of(
            "2025-05-24T00:00:01",
            "2025-05-30T23:59:5",
            new ValidationErrorResponse(List.of(Map.of("end", defaultMessage)))),
        Arguments.of(
            "2025-05-24T00:00:0",
            "2025-05-30T23:59:5",
            new ValidationErrorResponse(
                List.of(Map.of("start", defaultMessage), Map.of("end", defaultMessage)))),
        Arguments.of(
            "2025-02-29T00:00:01",
            "2025-05-30T23:59:59",
            new ValidationErrorResponse(List.of(Map.of("start", defaultMessage)))));
  }
}

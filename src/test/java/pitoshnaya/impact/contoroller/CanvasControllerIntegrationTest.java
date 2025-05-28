package pitoshnaya.impact.contoroller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import pitoshnaya.impact.dao.CellDao;
import pitoshnaya.impact.dto.DrawRequest;
import pitoshnaya.impact.entity.Cell;
import pitoshnaya.impact.validate.ValidationErrorResponse;

public class CanvasControllerIntegrationTest extends BaseControllerIntegrationTest {

  private static final int width = 25;
  private static final int height = 25;

  private final String url = "api/canvas";

  @Autowired private CellDao cellDao;

  @ParameterizedTest
  @MethodSource("validRequestProvider")
  void testDrawIsValidDrawRequest(DrawRequest request) {
    var response = sendPut(url, request, Void.class);

    Cell cell = cellDao.getCellByCoordinates(request.x(), request.y());

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(request.color(), cell.getColor());
  }

  @ParameterizedTest
  @MethodSource("invalidRequestProvider")
  void testDrawIsInvalidDrawRequest(DrawRequest request, ValidationErrorResponse expected) {
    var response = sendPut(url, request, ValidationErrorResponse.class);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertThat(response.getBody().violations())
        .containsExactlyInAnyOrderElementsOf(expected.violations());
  }

  private static Stream<Arguments> validRequestProvider() {
    return Stream.of(
        Arguments.of(new DrawRequest(1, 1, "#ffffff")),
        Arguments.of(new DrawRequest(width, 1, "#000000")),
        Arguments.of(new DrawRequest(1, height, "#123abc")),
        Arguments.of(new DrawRequest(width, height, "#abc123")),
        Arguments.of(new DrawRequest(width / 2, height / 2, "#00ff00")));
  }

  public Stream<Arguments> invalidRequestProvider() {
    String maxXValidationError = "Значение не должно превышать " + width;
    String maxYValidationError = "Значение не должно превышать " + height;
    String minValidationError = "Значение не должно быть меньше 1";
    String invalidColorErrorMessage = "Цвет должен иметь формат #rrggbb";

    var invalidXTooBig = Map.of("draw.drawRequest.x", maxXValidationError);
    var invalidXTooSmall = Map.of("draw.drawRequest.x", minValidationError);
    var invalidYTooBig = Map.of("draw.drawRequest.y", maxYValidationError);
    var invalidYTooSmall = Map.of("draw.drawRequest.y", minValidationError);
    var invalidColor = Map.of("draw.drawRequest.color", invalidColorErrorMessage);

    return Stream.of(
        Arguments.of(
            new DrawRequest(width + 1, 1, "#123123"),
            new ValidationErrorResponse(List.of(invalidXTooBig))),
        Arguments.of(
            new DrawRequest(0, 1, "#123123"),
            new ValidationErrorResponse(List.of(invalidXTooSmall))),
        Arguments.of(
            new DrawRequest(1, width + 1, "#123123"),
            new ValidationErrorResponse(List.of(invalidYTooBig))),
        Arguments.of(
            new DrawRequest(1, 0, "#123123"),
            new ValidationErrorResponse(List.of(invalidYTooSmall))),
        Arguments.of(
            new DrawRequest(1, 1, "red"), new ValidationErrorResponse(List.of(invalidColor))),
        Arguments.of(
            new DrawRequest(0, 0, "red"),
            new ValidationErrorResponse(
                List.of(invalidXTooSmall, invalidYTooSmall, invalidColor))));
  }
}

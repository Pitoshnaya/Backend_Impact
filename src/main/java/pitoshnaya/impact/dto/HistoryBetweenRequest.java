package pitoshnaya.impact.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import pitoshnaya.impact.annotation.IsoDateTime;
import pitoshnaya.impact.annotation.ValidDateRange;

@ValidDateRange
public record HistoryBetweenRequest(
    @NotNull @IsoDateTime String start, @NotNull @IsoDateTime String end) {

  public LocalDateTime getStartDate() {
    try {
      return LocalDateTime.parse(start);
    } catch (Exception ignored) {
    }

    return null;
  }

  public LocalDateTime getEndDate() {
    try {
      return LocalDateTime.parse(end);
    } catch (Exception ignored) {
    }

    return null;
  }
}

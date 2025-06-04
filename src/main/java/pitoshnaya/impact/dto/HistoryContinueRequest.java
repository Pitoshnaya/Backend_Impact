package pitoshnaya.impact.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import pitoshnaya.impact.annotation.IsoDateTime;
import pitoshnaya.impact.annotation.ValidContinueRange;

@ValidContinueRange
public record HistoryContinueRequest(@NotNull Long startId, @NotNull @IsoDateTime String endDate) {
  public LocalDateTime getEndDate() {
    try {
      return LocalDateTime.parse(endDate);
    } catch (Exception ignored) {
    }

    return null;
  }
}

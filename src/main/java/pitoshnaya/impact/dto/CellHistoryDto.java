package pitoshnaya.impact.dto;

import java.time.LocalDateTime;

public record CellHistoryDto(long id, int x, int y, String color, LocalDateTime time) {}

package pitoshnaya.impact.dto;

import java.util.List;

public record GridHistoryDto(List<CellAtTime> base, List<CellHistoryDto> history) {}

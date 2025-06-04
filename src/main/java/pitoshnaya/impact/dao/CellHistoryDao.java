package pitoshnaya.impact.dao;

import java.time.LocalDateTime;
import java.util.List;
import pitoshnaya.impact.dto.CellAtTime;
import pitoshnaya.impact.entity.CellHistory;

public interface CellHistoryDao {
  void saveCellHistory(CellHistory cellHistory);

  CellHistory getCellHistoryById(long id);

  List<CellHistory> getCellHistoriesBetween(LocalDateTime start, LocalDateTime end);

  List<CellHistory> getCellHistoriesBetween(long start, LocalDateTime end);

  List<CellAtTime> getCellsBeforeToTimestamp(LocalDateTime timestamp);
}

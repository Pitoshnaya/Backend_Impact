package pitoshnaya.impact.service;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import pitoshnaya.impact.dao.CellHistoryDao;
import pitoshnaya.impact.dto.CellHistoryDto;
import pitoshnaya.impact.dto.GridHistoryDto;
import pitoshnaya.impact.entity.Cell;
import pitoshnaya.impact.entity.CellHistory;

@Service
public class GridHistoryService {
  private final CellHistoryDao cellHistoryDao;

  public GridHistoryService(CellHistoryDao cellHistoryDao) {
    this.cellHistoryDao = cellHistoryDao;
  }

  public GridHistoryDto getHistoryBetween(
      @NotNull(message = "start не должен быть null") LocalDateTime start,
      @NotNull(message = "end не должен быть null") LocalDateTime end) {
    var base = cellHistoryDao.getCellsBeforeToTimestamp(start);
    var history = cellHistoryDao.getCellHistoriesBetween(start, end);

    var historyDto =
        history.stream()
            .map(
                cellHistory ->
                    new CellHistoryDto(
                        cellHistory.getId(),
                        cellHistory.getCell().getX(),
                        cellHistory.getCell().getY(),
                        cellHistory.getColor(),
                        cellHistory.getRedrawingTime()))
            .toList();

    return new GridHistoryDto(base, historyDto);
  }

  public List<CellHistoryDto> getContinueHistory(
      @NotNull(message = "end не должен быть null") Long startId,
      @NotNull(message = "end не должен быть null") LocalDateTime end) {
    var history = cellHistoryDao.getCellHistoriesBetween(startId, end);

    return history.stream()
        .map(
            cellHistory ->
                new CellHistoryDto(
                    cellHistory.getId(),
                    cellHistory.getCell().getX(),
                    cellHistory.getCell().getY(),
                    cellHistory.getColor(),
                    cellHistory.getRedrawingTime()))
        .toList();
  }

  public void writeToHistory(Cell cell) {
    var cellHistory = new CellHistory();
    cellHistory.setX(cell.getX());
    cellHistory.setY(cell.getY());
    cellHistory.setCell(cell);
    cellHistory.setColor(cell.getColor());
    cellHistory.setRedrawingTime(LocalDateTime.now());

    cellHistoryDao.saveCellHistory(cellHistory);
  }
}

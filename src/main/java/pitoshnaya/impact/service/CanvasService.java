package pitoshnaya.impact.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import pitoshnaya.impact.dao.CellDao;
import pitoshnaya.impact.dto.DrawRequest;
import pitoshnaya.impact.entity.Cell;

@Service
@Validated
public class CanvasService {
  private final CellDao cellDao;
  private final GridHistoryService gridHistoryService;

  public CanvasService(CellDao cellDao, GridHistoryService gridHistoryService) {
    this.cellDao = cellDao;
    this.gridHistoryService = gridHistoryService;
  }

  @Transactional
  public void draw(@Valid DrawRequest drawRequest) {
    Cell cell = cellDao.getCellByCoordinates(drawRequest.x(), drawRequest.y());
    cell.setColor(drawRequest.color());

    gridHistoryService.writeToHistory(cell);
  }
}

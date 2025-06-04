package pitoshnaya.impact.controller;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pitoshnaya.impact.dto.CellHistoryDto;
import pitoshnaya.impact.dto.HistoryBetweenRequest;
import pitoshnaya.impact.dto.HistoryContinueRequest;
import pitoshnaya.impact.service.GridHistoryService;

@RestController
@RequestMapping("api/history")
@Validated
public class GridHistoryController {
  private final GridHistoryService gridHistoryService;

  public GridHistoryController(GridHistoryService gridHistoryService) {
    this.gridHistoryService = gridHistoryService;
  }

  @GetMapping(params = {"start", "end"})
  public ResponseEntity<?> get(@Valid HistoryBetweenRequest historyBetweenRequest) {
    var history =
        gridHistoryService.getHistoryBetween(
            historyBetweenRequest.getStartDate(), historyBetweenRequest.getEndDate());

    return ResponseEntity.ok(history);
  }

  @GetMapping(params = {"startId", "endDate"})
  public ResponseEntity<List<CellHistoryDto>> continueHistory(
      @Valid HistoryContinueRequest historyContinueRequest) {
    var history =
        gridHistoryService.getContinueHistory(
            historyContinueRequest.startId(), historyContinueRequest.getEndDate());

    return ResponseEntity.ok(history);
  }
}

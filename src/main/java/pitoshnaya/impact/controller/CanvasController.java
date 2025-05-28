package pitoshnaya.impact.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pitoshnaya.impact.dto.DrawRequest;
import pitoshnaya.impact.service.CanvasService;

@RestController
@RequestMapping("api/canvas")
public class CanvasController {

  private final CanvasService canvasService;

  public CanvasController(CanvasService canvasService) {
    this.canvasService = canvasService;
  }

  @PutMapping
  public ResponseEntity<?> draw(@RequestBody DrawRequest drawRequest) {
    canvasService.draw(drawRequest);
    return ResponseEntity.ok().build();
  }
}

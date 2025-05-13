package Pitoshnaya.Impact.controller;

import Pitoshnaya.Impact.dto.DrawRequest;
import Pitoshnaya.Impact.service.CanvasService;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/canvas")
public class CanvasController {
    private final CanvasService canvasService;

    public CanvasController(CanvasService canvasService) {
        this.canvasService = canvasService;
    }

    @PutMapping
    public ResponseEntity<?> draw(@RequestBody DrawRequest drawRequest) {
        try {
            canvasService.draw(drawRequest);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
}

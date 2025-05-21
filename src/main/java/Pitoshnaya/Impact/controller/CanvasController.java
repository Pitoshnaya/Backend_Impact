package Pitoshnaya.Impact.controller;


import Pitoshnaya.Impact.dto.DrawRequest;
import Pitoshnaya.Impact.entity.Cell;
import Pitoshnaya.Impact.entity.Pixel;
import Pitoshnaya.Impact.service.CanvasService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/getcanvas")
    public List<Cell> getCanvas(){
        return canvasService.getCanvas();
    }

}

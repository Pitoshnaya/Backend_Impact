package Pitoshnaya.Impact.service;

import Pitoshnaya.Impact.entity.Cell;
import Pitoshnaya.Impact.entity.Pixel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.parameters.P;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CanvasTest {

    @Autowired
    private CanvasService canvasService;

    @Test
    public void testGetCanvas(){
        List<Cell> canvas = canvasService.getCanvas();

        assertEquals(625, canvas.size());

        Cell cell = canvas.get(0);
        assertEquals(1, cell.getX());
        assertEquals(1, cell.getY());
    }

    @Test
    public void testUpdateCell(){
        Cell cellToUpdate = new Cell(1, 1, "#ccc");

        canvasService.updateCell(cellToUpdate);
    }
}

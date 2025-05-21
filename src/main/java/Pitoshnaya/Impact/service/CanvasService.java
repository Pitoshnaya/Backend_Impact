package Pitoshnaya.Impact.service;


import Pitoshnaya.Impact.dao.CellsDao;
import Pitoshnaya.Impact.dto.DrawRequest;
import Pitoshnaya.Impact.entity.Cell;
import Pitoshnaya.Impact.entity.Pixel;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

@Service
@Validated
public class CanvasService {
    private final CellsDao cellsDao;

    public CanvasService(CellsDao cellsDao) {this.cellsDao = cellsDao;}

    @Transactional
    public void draw(@Valid DrawRequest drawRequest) {
        Cell cell = cellsDao.getCellByCoordinates(drawRequest.x(), drawRequest.y());
    }
    public List<Cell> getCanvas(){
        return cellsDao.getAll();
    }

    @Transactional
    public void updateCell(Cell cell){
        cellsDao.updateCell(cell);
    }
}

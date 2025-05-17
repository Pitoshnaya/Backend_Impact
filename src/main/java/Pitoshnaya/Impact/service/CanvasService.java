package Pitoshnaya.Impact.service;

import Pitoshnaya.Impact.dao.CellsDao;
import Pitoshnaya.Impact.dto.DrawRequest;
import Pitoshnaya.Impact.entity.Cell;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class CanvasService {
    private final CellsDao cellsDao;

    public CanvasService(CellsDao cellsDao) {this.cellsDao = cellsDao;}

    @Transactional
    public void draw(@Valid DrawRequest drawRequest) {
        Cell cell = cellsDao.getCellByCoordinates(drawRequest.x(), drawRequest.y());
        cell.setColor(drawRequest.color());
    }
}

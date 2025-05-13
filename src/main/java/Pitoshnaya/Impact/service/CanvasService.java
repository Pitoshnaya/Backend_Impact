package Pitoshnaya.Impact.service;

import Pitoshnaya.Impact.dao.CellsDao;
import Pitoshnaya.Impact.dto.DrawRequest;
import Pitoshnaya.Impact.entity.Cell;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CanvasService {
    private final int width = 25;
    private final int height = 25;

    private final CellsDao cellsDao;

    public CanvasService(CellsDao cellsDao) {this.cellsDao = cellsDao;}

    @Transactional
    public void draw(DrawRequest drawRequest) {
        if (drawRequest.x() > width || drawRequest.x() < 0) {
            throw new IllegalArgumentException("Координата X должна лежать в диапазоне от 0 до " + width);
        }
        if (drawRequest.y() > height || drawRequest.y() < 0) {
            throw new IllegalArgumentException("Координата Y должна лежать в диапазоне от 0 до " + height);
        }
        if (!drawRequest.color().matches("^#([A-Fa-f0-9]{6})$")) {
            throw new IllegalArgumentException("Неизвестный цвет");
        }

        Cell cell = cellsDao.getCellByCoordinates(drawRequest.x(), drawRequest.y());
        cell.setColor(drawRequest.color());
        cellsDao.updateCell(cell);
    }
}

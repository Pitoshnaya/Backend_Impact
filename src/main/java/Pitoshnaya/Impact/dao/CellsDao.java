package Pitoshnaya.Impact.dao;

import Pitoshnaya.Impact.entity.Cell;

public interface CellsDao {
    void saveCell(Cell cell);
    void updateCell(Cell cell);
    Cell getCellByCoordinates(int x, int y);
    long count();
}

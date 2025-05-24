package Pitoshnaya.Impact.dao;

import Pitoshnaya.Impact.entity.Cell;

public interface CellDao {

    void saveCell(Cell cell);

    Cell getCellByCoordinates(int x, int y);

    long count();
}

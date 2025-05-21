package Pitoshnaya.Impact.dao;

import Pitoshnaya.Impact.entity.Cell;

import java.util.List;

public interface CellsDao {

    void saveCell(Cell cell);

    Cell getCellByCoordinates(int x, int y);

    long count();

    List<Cell> getAll();

    void updateCell(Cell cell);
}

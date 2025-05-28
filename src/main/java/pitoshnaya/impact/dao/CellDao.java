package pitoshnaya.impact.dao;

import pitoshnaya.impact.entity.Cell;

public interface CellDao {

  void saveCell(Cell cell);

  Cell getCellByCoordinates(int x, int y);

  long count();
}

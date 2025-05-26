package pitoshnaya.impact.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pitoshnaya.impact.dao.CellDao;
import pitoshnaya.impact.entity.Cell;

@Component
public class DatabaseCellInitializer implements CommandLineRunner {

  private final CellDao cellDao;

  public DatabaseCellInitializer(CellDao cellDao) {
    this.cellDao = cellDao;
  }

  @Override
  @Transactional
  public void run(String... args) {
    if (cellDao.count() > 0) {
      return;
    }

    for (int i = 1; i <= GridSize.WIDTH; i++) {
      for (int j = 1; j <= GridSize.HEIGHT; j++) {
        cellDao.saveCell(new Cell(i, j, "#ffffff"));
      }
    }
  }
}

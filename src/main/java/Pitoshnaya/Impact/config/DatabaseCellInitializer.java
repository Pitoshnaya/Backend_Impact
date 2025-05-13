package Pitoshnaya.Impact.config;

import Pitoshnaya.Impact.dao.CellsDao;
import Pitoshnaya.Impact.entity.Cell;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DatabaseCellInitializer implements CommandLineRunner {

    private final CellsDao cellsDao;

    public DatabaseCellInitializer(CellsDao cellsDao) {
        this.cellsDao = cellsDao;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (cellsDao.count() > 0) {
            return;
        }

        for (int i = 1; i <= 25; i++) {
            for (int j = 1; j <= 25; j++) {
                cellsDao.saveCell(new Cell(i, j, "#ffffff"));
            }
        }
    }
}

package pitoshnaya.impact.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import pitoshnaya.impact.dao.CellHistoryDao;
import pitoshnaya.impact.dto.CellAtTime;
import pitoshnaya.impact.entity.CellHistory;

@Repository
public class CellHistoryDaoDatabase implements CellHistoryDao {

  @PersistenceContext private EntityManager em;

  @Override
  public void saveCellHistory(CellHistory cellHistory) {
    em.persist(cellHistory);
  }

  @Override
  public CellHistory getCellHistoryById(long id) {
    return em.find(CellHistory.class, id);
  }

  @Override
  public List<CellHistory> getCellHistoriesBetween(LocalDateTime start, LocalDateTime end) {
    String query = "SELECT ch FROM CellHistory ch WHERE ch.redrawingTime BETWEEN :start AND :end";
    return em.createQuery(query, CellHistory.class)
        .setParameter("start", start)
        .setParameter("end", end)
        .getResultList();
  }

  @Override
  public List<CellHistory> getCellHistoriesBetween(long start, LocalDateTime end) {
    String query =
        """
            SELECT ch FROM CellHistory ch
            WHERE ch.id > :startId AND ch.redrawingTime <= :endTime
            ORDER BY ch.id
        """;

    return em.createQuery(query, CellHistory.class)
        .setParameter("startId", start)
        .setParameter("endTime", end)
        .getResultList();
  }

  @Override
  public List<CellAtTime> getCellsBeforeToTimestamp(LocalDateTime timestamp) {
    String sql =
        """
        SELECT
              c.id AS cell_id,
              c.x AS x,
              c.y AS y,
              COALESCE(ch.color, '#ffffff') AS color
          FROM cell c
          LEFT JOIN (
              SELECT DISTINCT ON (cell_id)
                     cell_id,
                     color,
                     redrawing_time
              FROM cell_history
              WHERE redrawing_time <= :ts
              ORDER BY cell_id, redrawing_time DESC
          ) ch ON c.id = ch.cell_id

          """;

    @SuppressWarnings("unchecked")
    List<Object[]> rows =
        em.createNativeQuery(sql)
            .setParameter("ts", Timestamp.valueOf(timestamp.plusSeconds(1)))
            .getResultList();

    List<CellAtTime> result = new ArrayList<>();
    for (Object[] row : rows) {
      int x = ((Number) row[1]).intValue();
      int y = ((Number) row[2]).intValue();
      String color = (String) row[3];
      result.add(new CellAtTime(x, y, color));
    }

    return result;
  }
}

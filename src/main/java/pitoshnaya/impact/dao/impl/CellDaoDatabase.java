package pitoshnaya.impact.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import pitoshnaya.impact.dao.CellDao;
import pitoshnaya.impact.entity.Cell;

@Repository
public class CellDaoDatabase implements CellDao {

  @PersistenceContext private EntityManager em;

  @Override
  public void saveCell(Cell cell) {
    em.persist(cell);
  }

  @Override
  public Cell getCellByCoordinates(int x, int y) {
    return em.createQuery("SELECT c FROM Cell c WHERE c.x = :x AND c.y = :y", Cell.class)
        .setParameter("x", x)
        .setParameter("y", y)
        .getSingleResult();
  }

  @Override
  public long count() {
    String jpql = "SELECT COUNT(c) FROM Cell c";

    return em.createQuery(jpql, Long.class).getSingleResult();
  }
}

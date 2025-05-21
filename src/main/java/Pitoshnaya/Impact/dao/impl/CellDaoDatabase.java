package Pitoshnaya.Impact.dao.impl;

import Pitoshnaya.Impact.dao.CellsDao;
import Pitoshnaya.Impact.entity.Cell;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Transient;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class CellDaoDatabase implements CellsDao {

    @PersistenceContext
    private EntityManager em;

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

    @Override
    public List<Cell> getAll() {
        return em.createQuery("SELECT c FROM Cell c", Cell.class).getResultList();
    }

    @Transactional
    public void updateCell(Cell cell) {
        em.merge(cell);
    }
}

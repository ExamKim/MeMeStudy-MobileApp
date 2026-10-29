package iuh.fit.backend.repository;

import iuh.fit.backend.config.JPAUtil;
import iuh.fit.backend.model.Round;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class RoundRepository {

    public List<Round> findActiveExamRounds() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                "SELECT DISTINCT r FROM Round r " +
                "JOIN FETCH r.exam e " +
                "LEFT JOIN FETCH r.roundQuestions rq " +
                "WHERE e.isActive = true " +
                "ORDER BY r.roundNumber ASC",
                Round.class
            ).getResultList();
        } finally {
            em.close();
        }
    }

    public Optional<Round> findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Round round = em.find(Round.class, id);
            return Optional.ofNullable(round);
        } finally {
            em.close();
        }
    }
}

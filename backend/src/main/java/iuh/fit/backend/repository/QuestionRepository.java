package iuh.fit.backend.repository;

import iuh.fit.backend.config.JPAUtil;
import iuh.fit.backend.model.RoundQuestion;
import jakarta.persistence.EntityManager;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class QuestionRepository {

    public List<RoundQuestion> findRoundQuestions(Long roundId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                "SELECT rq FROM RoundQuestion rq " +
                "JOIN FETCH rq.question q " +
                "WHERE rq.round.id = :roundId " +
                "ORDER BY rq.orderIndex ASC",
                RoundQuestion.class
            ).setParameter("roundId", roundId)
             .getResultList();
        } finally {
            em.close();
        }
    }

    public Set<Long> findQuestionIdsByRoundId(Long roundId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            List<Long> ids = em.createQuery(
                "SELECT rq.question.id FROM RoundQuestion rq WHERE rq.round.id = :roundId",
                Long.class
            ).setParameter("roundId", roundId)
             .getResultList();
            return new HashSet<>(ids);
        } finally {
            em.close();
        }
    }
}


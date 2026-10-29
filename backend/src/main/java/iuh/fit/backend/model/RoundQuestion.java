package iuh.fit.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "round_questions", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"round_id", "question_id"})
})
public class RoundQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "round_id", nullable = false)
    private Round round;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(name = "order_index", nullable = false)
    private Integer orderIndex;

    public RoundQuestion() {}

    public RoundQuestion(Round round, Question question, Integer orderIndex) {
        this.round = round;
        this.question = question;
        this.orderIndex = orderIndex;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Round getRound() { return round; }
    public void setRound(Round round) { this.round = round; }

    public Question getQuestion() { return question; }
    public void setQuestion(Question question) { this.question = question; }

    public Integer getOrderIndex() { return orderIndex; }
    public void setOrderIndex(Integer orderIndex) { this.orderIndex = orderIndex; }
}


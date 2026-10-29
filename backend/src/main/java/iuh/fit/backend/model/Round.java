package iuh.fit.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rounds")
public class Round {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "exam_id", nullable = false)
    private Exam exam;

    @Column(name = "round_number", nullable = false)
    private Integer roundNumber;

    @Column(name = "title", nullable = false, length = 150)
    private String title;

    @Column(name = "time_limit_seconds", nullable = false)
    private Integer timeLimitSeconds = 600;

    @Column(name = "max_score", nullable = false)
    private Integer maxScore = 100;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @OneToMany(mappedBy = "round", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("orderIndex ASC")
    private List<RoundQuestion> roundQuestions = new ArrayList<>();

    public Round() {}

    public Round(Exam exam, Integer roundNumber, String title, Integer timeLimitSeconds, Integer maxScore) {
        this.exam = exam;
        this.roundNumber = roundNumber;
        this.title = title;
        this.timeLimitSeconds = timeLimitSeconds != null ? timeLimitSeconds : 600;
        this.maxScore = maxScore != null ? maxScore : 100;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onPreUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Exam getExam() { return exam; }
    public void setExam(Exam exam) { this.exam = exam; }

    public Integer getRoundNumber() { return roundNumber; }
    public void setRoundNumber(Integer roundNumber) { this.roundNumber = roundNumber; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Integer getTimeLimitSeconds() { return timeLimitSeconds; }
    public void setTimeLimitSeconds(Integer timeLimitSeconds) { this.timeLimitSeconds = timeLimitSeconds; }

    public Integer getMaxScore() { return maxScore; }
    public void setMaxScore(Integer maxScore) { this.maxScore = maxScore; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public List<RoundQuestion> getRoundQuestions() { return roundQuestions; }
    public void setRoundQuestions(List<RoundQuestion> roundQuestions) { this.roundQuestions = roundQuestions; }
}

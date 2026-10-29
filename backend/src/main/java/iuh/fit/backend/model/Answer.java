package iuh.fit.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "answers")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "result_id", nullable = false)
    private Result result;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(name = "selected_answer", length = 500)
    private String selectedAnswer;

    @Column(name = "is_correct", nullable = false)
    private Boolean isCorrect = false;

    public Answer() {}

    public Answer(Result result, Question question, String selectedAnswer, Boolean isCorrect) {
        this.result = result;
        this.question = question;
        this.selectedAnswer = selectedAnswer;
        this.isCorrect = isCorrect != null ? isCorrect : false;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Result getResult() { return result; }
    public void setResult(Result result) { this.result = result; }

    public Question getQuestion() { return question; }
    public void setQuestion(Question question) { this.question = question; }

    public String getSelectedAnswer() { return selectedAnswer; }
    public void setSelectedAnswer(String selectedAnswer) { this.selectedAnswer = selectedAnswer; }

    public Boolean getIsCorrect() { return isCorrect; }
    public void setIsCorrect(Boolean isCorrect) { this.isCorrect = isCorrect; }
}


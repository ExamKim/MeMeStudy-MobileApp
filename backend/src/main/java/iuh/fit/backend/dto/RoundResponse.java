package iuh.fit.backend.dto;

public class RoundResponse {
    private Long id;
    private Integer roundNumber;
    private String title;
    private Integer timeLimitSeconds;
    private Integer maxScore;
    private Integer questionCount;

    public RoundResponse() {}

    public RoundResponse(Long id, Integer roundNumber, String title, Integer timeLimitSeconds, Integer maxScore, Integer questionCount) {
        this.id = id;
        this.roundNumber = roundNumber;
        this.title = title;
        this.timeLimitSeconds = timeLimitSeconds;
        this.maxScore = maxScore;
        this.questionCount = questionCount;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getRoundNumber() { return roundNumber; }
    public void setRoundNumber(Integer roundNumber) { this.roundNumber = roundNumber; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Integer getTimeLimitSeconds() { return timeLimitSeconds; }
    public void setTimeLimitSeconds(Integer timeLimitSeconds) { this.timeLimitSeconds = timeLimitSeconds; }

    public Integer getMaxScore() { return maxScore; }
    public void setMaxScore(Integer maxScore) { this.maxScore = maxScore; }

    public Integer getQuestionCount() { return questionCount; }
    public void setQuestionCount(Integer questionCount) { this.questionCount = questionCount; }
}


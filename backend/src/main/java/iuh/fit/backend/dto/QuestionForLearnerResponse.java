package iuh.fit.backend.dto;

import java.util.List;

public class QuestionForLearnerResponse {
    private Long id;
    private String content;
    private String type;
    private List<String> options;
    private String audioUrl;
    private Integer orderIndex;

    public QuestionForLearnerResponse() {}

    public QuestionForLearnerResponse(Long id, String content, String type, List<String> options, String audioUrl, Integer orderIndex) {
        this.id = id;
        this.content = content;
        this.type = type;
        this.options = options;
        this.audioUrl = audioUrl;
        this.orderIndex = orderIndex;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public List<String> getOptions() { return options; }
    public void setOptions(List<String> options) { this.options = options; }

    public String getAudioUrl() { return audioUrl; }
    public void setAudioUrl(String audioUrl) { this.audioUrl = audioUrl; }

    public Integer getOrderIndex() { return orderIndex; }
    public void setOrderIndex(Integer orderIndex) { this.orderIndex = orderIndex; }
}


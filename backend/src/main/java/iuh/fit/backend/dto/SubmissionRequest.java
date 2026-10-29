package iuh.fit.backend.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class SubmissionRequest {

    @NotEmpty(message = "Danh sách answers không được để trống")
    @Valid
    private List<AnswerItemRequest> answers;

    public SubmissionRequest() {}

    public SubmissionRequest(List<AnswerItemRequest> answers) {
        this.answers = answers;
    }

    public List<AnswerItemRequest> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerItemRequest> answers) {
        this.answers = answers;
    }

    public static class AnswerItemRequest {
        @NotNull(message = "questionId không được để trống")
        private Long questionId;

        @NotBlank(message = "selectedAnswer không được để trống")
        private String selectedAnswer;

        public AnswerItemRequest() {}

        public AnswerItemRequest(Long questionId, String selectedAnswer) {
            this.questionId = questionId;
            this.selectedAnswer = selectedAnswer;
        }

        public Long getQuestionId() {
            return questionId;
        }

        public void setQuestionId(Long questionId) {
            this.questionId = questionId;
        }

        public String getSelectedAnswer() {
            return selectedAnswer;
        }

        public void setSelectedAnswer(String selectedAnswer) {
            this.selectedAnswer = selectedAnswer;
        }
    }
}


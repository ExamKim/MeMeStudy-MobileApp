package iuh.fit.backend.service;

import iuh.fit.backend.dto.QuestionForLearnerResponse;
import iuh.fit.backend.dto.RoundResponse;
import iuh.fit.backend.dto.SubmissionRequest;
import iuh.fit.backend.dto.SubmissionResponse;
import iuh.fit.backend.exception.BadRequestException;
import iuh.fit.backend.exception.ResourceNotFoundException;
import iuh.fit.backend.model.Question;
import iuh.fit.backend.model.Round;
import iuh.fit.backend.model.RoundQuestion;
import iuh.fit.backend.repository.QuestionRepository;
import iuh.fit.backend.repository.RoundRepository;

import java.util.*;
import java.util.stream.Collectors;

public class RoundService {
    private final RoundRepository roundRepository = new RoundRepository();
    private final QuestionRepository questionRepository = new QuestionRepository();

    public List<RoundResponse> getRounds() {
        // TODO: bật xác thực khi filter của Khánh sẵn sàng
        List<Round> rounds = roundRepository.findActiveExamRounds();
        return rounds.stream().map(round -> {
            int qCount = round.getRoundQuestions() != null ? round.getRoundQuestions().size() : 0;
            return new RoundResponse(
                    round.getId(),
                    round.getRoundNumber(),
                    round.getTitle(),
                    round.getTimeLimitSeconds(),
                    round.getMaxScore(),
                    qCount
            );
        }).collect(Collectors.toList());
    }

    public List<QuestionForLearnerResponse> getQuestionsForRound(Long roundId) {
        // TODO: bật xác thực khi filter của Khánh sẵn sàng
        Round round = roundRepository.findById(roundId)
                .orElseThrow(() -> new ResourceNotFoundException("Vòng thi không tồn tại với ID: " + roundId));

        List<RoundQuestion> roundQuestions = questionRepository.findRoundQuestions(roundId);

        return roundQuestions.stream().map(rq -> {
            Question q = rq.getQuestion();
            // TUYỆT ĐỐI KHÔNG trả correctAnswer
            return new QuestionForLearnerResponse(
                    q.getId(),
                    q.getContent(),
                    q.getType(),
                    new ArrayList<>(q.getOptions()),
                    q.getAudioUrl(),
                    rq.getOrderIndex()
            );
        }).collect(Collectors.toList());
    }

    public SubmissionResponse submitAnswers(Long roundId, SubmissionRequest request) {
        // TODO: bật xác thực khi filter của Khánh sẵn sàng
        Round round = roundRepository.findById(roundId)
                .orElseThrow(() -> new ResourceNotFoundException("Vòng thi không tồn tại với ID: " + roundId));

        if (request == null || request.getAnswers() == null || request.getAnswers().isEmpty()) {
            throw new BadRequestException("Danh sách câu trả lời không được để trống");
        }

        List<SubmissionRequest.AnswerItemRequest> answers = request.getAnswers();

        // 1. Kiểm tra không trùng questionId
        Set<Long> seenQuestionIds = new HashSet<>();
        for (SubmissionRequest.AnswerItemRequest answer : answers) {
            if (answer.getQuestionId() == null) {
                throw new BadRequestException("questionId không được null");
            }
            if (!seenQuestionIds.add(answer.getQuestionId())) {
                throw new BadRequestException("Phát hiện questionId bị trùng lặp: " + answer.getQuestionId());
            }
        }

        // 2. Kiểm tra mọi questionId phải thuộc vòng thi này
        Set<Long> validRoundQuestionIds = questionRepository.findQuestionIdsByRoundId(roundId);
        for (Long qId : seenQuestionIds) {
            if (!validRoundQuestionIds.contains(qId)) {
                throw new BadRequestException("Câu hỏi ID " + qId + " không thuộc vòng thi có ID: " + roundId);
            }
        }

        // TODO(Tuần 2): chấm điểm phía server và lưu results + answers trong transaction
        return new SubmissionResponse("Đã nhận bài", answers.size());
    }
}


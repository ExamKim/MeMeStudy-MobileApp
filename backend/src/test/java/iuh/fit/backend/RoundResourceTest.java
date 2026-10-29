package iuh.fit.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import iuh.fit.backend.config.JPAUtil;
import iuh.fit.backend.dto.QuestionForLearnerResponse;
import iuh.fit.backend.dto.RoundResponse;
import iuh.fit.backend.dto.SubmissionRequest;
import iuh.fit.backend.dto.SubmissionResponse;
import iuh.fit.backend.exception.BadRequestException;
import iuh.fit.backend.exception.ResourceNotFoundException;
import iuh.fit.backend.service.RoundService;
import iuh.fit.backend.util.DatabaseSeeder;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RoundResourceTest {

    private static RoundService roundService;
    private static ObjectMapper objectMapper;

    @BeforeAll
    public static void setUp() {
        DatabaseSeeder.seedIfEmpty();
        roundService = new RoundService();
        objectMapper = new ObjectMapper();
    }

    @AfterAll
    public static void tearDown() {
        JPAUtil.close();
    }

    @Test
    @Order(1)
    @DisplayName("GET /api/rounds returns 3 rounds ordered by roundNumber")
    public void testGetRounds() {
        List<RoundResponse> rounds = roundService.getRounds();
        assertNotNull(rounds);
        assertEquals(3, rounds.size());

        RoundResponse r1 = rounds.get(0);
        assertEquals(1, r1.getRoundNumber());
        assertEquals(600, r1.getTimeLimitSeconds());
        assertEquals(100, r1.getMaxScore());
        assertEquals(10, r1.getQuestionCount());

        RoundResponse r2 = rounds.get(1);
        assertEquals(2, r2.getRoundNumber());
        assertEquals(900, r2.getTimeLimitSeconds());

        RoundResponse r3 = rounds.get(2);
        assertEquals(3, r3.getRoundNumber());
        assertEquals(1200, r3.getTimeLimitSeconds());
    }

    @Test
    @Order(2)
    @DisplayName("GET /api/rounds/{roundId}/questions returns 10 questions and NO correctAnswer")
    public void testGetQuestionsForRound() throws Exception {
        List<QuestionForLearnerResponse> questions = roundService.getQuestionsForRound(1L);
        assertNotNull(questions);
        assertEquals(10, questions.size());

        // Serialize to JSON and assert that "correctAnswer" is ABSOLUTELY NOT PRESENT
        String json = objectMapper.writeValueAsString(questions);
        assertFalse(json.contains("correctAnswer"), "Response must NOT contain correctAnswer!");
        assertFalse(json.contains("correct_answer"), "Response must NOT contain correct_answer!");

        QuestionForLearnerResponse q1 = questions.get(0);
        assertEquals(1, q1.getOrderIndex());
        assertEquals(4, q1.getOptions().size());
        assertNotNull(q1.getContent());

        // Check listening question (e.g. Q9, Q10)
        QuestionForLearnerResponse q9 = questions.get(8);
        assertEquals("LISTENING", q9.getType());
        assertNotNull(q9.getAudioUrl());
    }

    @Test
    @Order(3)
    @DisplayName("GET /api/rounds/999/questions throws ResourceNotFoundException (404)")
    public void testGetQuestionsRoundNotFound() {
        assertThrows(ResourceNotFoundException.class, () -> {
            roundService.getQuestionsForRound(999L);
        });
    }

    @Test
    @Order(4)
    @DisplayName("POST /api/rounds/{id}/submissions succeeds with valid input")
    public void testSubmitAnswersSuccess() {
        List<SubmissionRequest.AnswerItemRequest> answers = new ArrayList<>();
        for (long qId = 1; qId <= 10; qId++) {
            answers.add(new SubmissionRequest.AnswerItemRequest(qId, "A"));
        }
        SubmissionRequest request = new SubmissionRequest(answers);

        SubmissionResponse response = roundService.submitAnswers(1L, request);
        assertNotNull(response);
        assertEquals("Đã nhận bài", response.getMessage());
        assertEquals(10, response.getReceived());
    }

    @Test
    @Order(5)
    @DisplayName("POST /api/rounds/{id}/submissions fails on duplicate questionId (400)")
    public void testSubmitAnswersDuplicateQuestionId() {
        List<SubmissionRequest.AnswerItemRequest> answers = Arrays.asList(
                new SubmissionRequest.AnswerItemRequest(1L, "A"),
                new SubmissionRequest.AnswerItemRequest(1L, "B")
        );
        SubmissionRequest request = new SubmissionRequest(answers);

        BadRequestException ex = assertThrows(BadRequestException.class, () -> {
            roundService.submitAnswers(1L, request);
        });
        assertTrue(ex.getMessage().contains("trùng lặp"));
    }

    @Test
    @Order(6)
    @DisplayName("POST /api/rounds/{id}/submissions fails when question does not belong to round (400)")
    public void testSubmitAnswersQuestionNotInRound() {
        // Question 15 belongs to Round 2, submitting to Round 1 must fail
        List<SubmissionRequest.AnswerItemRequest> answers = Arrays.asList(
                new SubmissionRequest.AnswerItemRequest(1L, "A"),
                new SubmissionRequest.AnswerItemRequest(15L, "B")
        );
        SubmissionRequest request = new SubmissionRequest(answers);

        BadRequestException ex = assertThrows(BadRequestException.class, () -> {
            roundService.submitAnswers(1L, request);
        });
        assertTrue(ex.getMessage().contains("không thuộc"));
    }

    @Test
    @Order(7)
    @DisplayName("POST /api/rounds/{id}/submissions fails on empty answers (400)")
    public void testSubmitAnswersEmptyList() {
        SubmissionRequest request = new SubmissionRequest(new ArrayList<>());
        assertThrows(BadRequestException.class, () -> {
            roundService.submitAnswers(1L, request);
        });
    }
}


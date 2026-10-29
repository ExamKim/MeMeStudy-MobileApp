package iuh.fit.backend.util;

import iuh.fit.backend.config.JPAUtil;
import iuh.fit.backend.model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class DatabaseSeeder {
    private static final Logger LOGGER = Logger.getLogger(DatabaseSeeder.class.getName());

    public static synchronized void seedIfEmpty() {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            Long examCount = em.createQuery("SELECT COUNT(e) FROM Exam e", Long.class).getSingleResult();
            if (examCount > 0) {
                LOGGER.info("Database already contains data (" + examCount + " exams). Skipping seeder.");
                return;
            }

            LOGGER.info("Database is empty. Seeding initial exam, 3 rounds, and 30 questions...");
            tx.begin();

            // 1. Exam
            Exam exam = new Exam("Kỳ thi Mô phỏng IOE Toàn quốc 2026", "Bài thi mô phỏng định dạng IOE chuẩn 3 vòng thi tiếng Anh", true);
            em.persist(exam);

            // 2. Rounds
            Round r1 = new Round(exam, 1, "Vòng 1 - Khởi động (Warm Up)", 600, 100);
            Round r2 = new Round(exam, 2, "Vòng 2 - Tăng tốc (Speed Up)", 900, 100);
            Round r3 = new Round(exam, 3, "Vòng 3 - Về đích (Finish Line)", 1200, 100);
            em.persist(r1);
            em.persist(r2);
            em.persist(r3);

            // 3. Questions
            // Round 1
            seedQuestion(em, r1, 1, "What is the synonym of \"happy\"?", "MULTIPLE_CHOICE", Arrays.asList("Sad", "Joyful", "Angry", "Tired"), "Joyful", "Vocabulary", "EASY", null);
            seedQuestion(em, r1, 2, "She _____ to school every morning by bicycle.", "MULTIPLE_CHOICE", Arrays.asList("go", "goes", "going", "went"), "goes", "Grammar", "EASY", null);
            seedQuestion(em, r1, 3, "Which of the following is an irregular verb?", "MULTIPLE_CHOICE", Arrays.asList("Play", "Walk", "Write", "Talk"), "Write", "Grammar", "EASY", null);
            seedQuestion(em, r1, 4, "Choose the word that has different pronunciation: cat, hat, bat, hate", "MULTIPLE_CHOICE", Arrays.asList("cat", "hat", "bat", "hate"), "hate", "Phonetics", "EASY", null);
            seedQuestion(em, r1, 5, "If it rains tomorrow, we _____ the picnic.", "MULTIPLE_CHOICE", Arrays.asList("cancel", "cancelled", "will cancel", "would cancel"), "will cancel", "Grammar", "MEDIUM", null);
            seedQuestion(em, r1, 6, "The opposite of \"ancient\" is: ", "MULTIPLE_CHOICE", Arrays.asList("Old", "Modern", "Historical", "Traditional"), "Modern", "Vocabulary", "EASY", null);
            seedQuestion(em, r1, 7, "How many days _____ there in February of a leap year?", "MULTIPLE_CHOICE", Arrays.asList("is", "are", "was", "were"), "are", "Grammar", "EASY", null);
            seedQuestion(em, r1, 8, "He is interested _____ learning foreign languages.", "MULTIPLE_CHOICE", Arrays.asList("on", "at", "in", "with"), "in", "Grammar", "EASY", null);
            seedQuestion(em, r1, 9, "Listen and select what the speaker plans to buy this weekend.", "LISTENING", Arrays.asList("A new laptop", "A new bicycle", "A pair of shoes", "A winter jacket"), "A new bicycle", "Listening", "MEDIUM", "https://mock-audio.memestudy.io/audio/r1_q9.mp3");
            seedQuestion(em, r1, 10, "Listen to the conversation: What time does the train depart?", "LISTENING", Arrays.asList("07:15 AM", "08:00 AM", "08:30 AM", "09:00 AM"), "08:30 AM", "Listening", "MEDIUM", "https://mock-audio.memestudy.io/audio/r1_q10.mp3");

            // Round 2
            seedQuestion(em, r2, 1, "Neither Peter nor his friends _____ present at the meeting yesterday.", "MULTIPLE_CHOICE", Arrays.asList("was", "were", "is", "are"), "were", "Grammar", "MEDIUM", null);
            seedQuestion(em, r2, 2, "The book _____ I bought last week was very captivating.", "MULTIPLE_CHOICE", Arrays.asList("who", "which", "whose", "where"), "which", "Grammar", "MEDIUM", null);
            seedQuestion(em, r2, 3, "Could you please _____ down the volume? It is too loud.", "MULTIPLE_CHOICE", Arrays.asList("turn", "shut", "put", "keep"), "turn", "Phrasal Verbs", "MEDIUM", null);
            seedQuestion(em, r2, 4, "Despite _____ hard, he failed the entrance exam.", "MULTIPLE_CHOICE", Arrays.asList("study", "studied", "studying", "to study"), "studying", "Grammar", "MEDIUM", null);
            seedQuestion(em, r2, 5, "What does the idiom \"break a leg\" mean?", "MULTIPLE_CHOICE", Arrays.asList("Be careful", "Good luck", "Give up", "Run away"), "Good luck", "Idioms", "MEDIUM", null);
            seedQuestion(em, r2, 6, "Mount Everest is the _____ mountain in the world.", "MULTIPLE_CHOICE", Arrays.asList("higher", "highest", "most high", "high"), "highest", "Grammar", "EASY", null);
            seedQuestion(em, r2, 7, "I look forward to _____ from you soon.", "MULTIPLE_CHOICE", Arrays.asList("hear", "heard", "hearing", "to hear"), "hearing", "Grammar", "MEDIUM", null);
            seedQuestion(em, r2, 8, "The teacher asked the students to hand _____ their assignments.", "MULTIPLE_CHOICE", Arrays.asList("in", "out", "on", "off"), "in", "Phrasal Verbs", "MEDIUM", null);
            seedQuestion(em, r2, 9, "Listen to the announcement: What gate should passengers proceed to?", "LISTENING", Arrays.asList("Gate 12A", "Gate 14B", "Gate 18C", "Gate 20"), "Gate 14B", "Listening", "MEDIUM", "https://mock-audio.memestudy.io/audio/r2_q19.mp3");
            seedQuestion(em, r2, 10, "Listen to the weather forecast: What will the weather be like tomorrow?", "LISTENING", Arrays.asList("Sunny and warm", "Snowy", "Rainy and windy", "Foggy and calm"), "Rainy and windy", "Listening", "HARD", "https://mock-audio.memestudy.io/audio/r2_q20.mp3");

            // Round 3
            seedQuestion(em, r3, 1, "Had they known about the traffic jam, they _____ earlier.", "MULTIPLE_CHOICE", Arrays.asList("would leave", "will leave", "would have left", "had left"), "would have left", "Grammar", "HARD", null);
            seedQuestion(em, r3, 2, "The company is seeking candidates with exceptional interpersonal _____.", "MULTIPLE_CHOICE", Arrays.asList("skills", "talents", "habits", "manners"), "skills", "Vocabulary", "HARD", null);
            seedQuestion(em, r3, 3, "Not only _____ late, but he also forgot the key documents.", "MULTIPLE_CHOICE", Arrays.asList("he was", "was he", "he arrived", "did he"), "was he", "Inversion", "HARD", null);
            seedQuestion(em, r3, 4, "The contract was deemed null and _____ due to lack of signatures.", "MULTIPLE_CHOICE", Arrays.asList("empty", "void", "blank", "bare"), "void", "Collocations", "HARD", null);
            seedQuestion(em, r3, 5, "The scientist made a major _____ in renewable energy research.", "MULTIPLE_CHOICE", Arrays.asList("breakup", "breakdown", "breakthrough", "breakout"), "breakthrough", "Vocabulary", "HARD", null);
            seedQuestion(em, r3, 6, "By the time we arrive at the airport, the flight _____.", "MULTIPLE_CHOICE", Arrays.asList("took off", "has taken off", "will take off", "will have taken off"), "will have taken off", "Tenses", "HARD", null);
            seedQuestion(em, r3, 7, "She took the job solely _____ the attractive remuneration package.", "MULTIPLE_CHOICE", Arrays.asList("because", "because of", "in spite of", "even though"), "because of", "Grammar", "HARD", null);
            seedQuestion(em, r3, 8, "Hardly _____ the house when the storm began.", "MULTIPLE_CHOICE", Arrays.asList("she left", "did she leave", "had she left", "she had left"), "had she left", "Inversion", "HARD", null);
            seedQuestion(em, r3, 9, "Listen to the lecture: What is the main cause of deforestation discussed?", "LISTENING", Arrays.asList("Urbanization", "Agricultural expansion", "Mining operations", "Natural forest fires"), "Agricultural expansion", "Listening", "HARD", "https://mock-audio.memestudy.io/audio/r3_q29.mp3");
            seedQuestion(em, r3, 10, "Listen to the interview: What advice does the expert offer young entrepreneurs?", "LISTENING", Arrays.asList("Avoid all risks", "Stay persistent and adaptable", "Seek quick profits", "Work completely alone"), "Stay persistent and adaptable", "Listening", "HARD", "https://mock-audio.memestudy.io/audio/r3_q30.mp3");

            tx.commit();
            LOGGER.info("Seeding completed successfully!");
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            LOGGER.severe("Error while seeding data: " + e.getMessage());
            throw e;
        } finally {
            em.close();
        }
    }

    private static void seedQuestion(EntityManager em, Round round, int orderIndex, String content, String type, List<String> options, String correctAnswer, String topic, String difficulty, String audioUrl) {
        Question q = new Question(content, type, options, correctAnswer, topic, difficulty, audioUrl);
        em.persist(q);
        RoundQuestion rq = new RoundQuestion(round, q, orderIndex);
        em.persist(rq);
    }

    public static void main(String[] args) {
        seedIfEmpty();
        System.out.println("Seeding process finished.");
        JPAUtil.close();
    }
}


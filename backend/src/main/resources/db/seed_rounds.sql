-- ==========================================================
-- SEED DATA: 1 Exam, 3 Rounds, 30 Questions (10 questions/round)
-- ==========================================================

-- 1. Insert Exam
INSERT INTO exams (id, title, description, is_active, created_at, updated_at)
VALUES (1, 'Kỳ thi Mô phỏng IOE Toàn quốc 2026', 'Bài thi mô phỏng định dạng IOE chuẩn 3 vòng thi tiếng Anh', 1, NOW(), NOW())
ON DUPLICATE KEY UPDATE title=VALUES(title);

-- 2. Insert 3 Rounds
INSERT INTO rounds (id, exam_id, round_number, title, time_limit_seconds, max_score, created_at, updated_at) VALUES
(1, 1, 1, 'Vòng 1 - Khởi động (Warm Up)', 600, 100, NOW(), NOW()),
(2, 1, 2, 'Vòng 2 - Tăng tốc (Speed Up)', 900, 100, NOW(), NOW()),
(3, 1, 3, 'Vòng 3 - Về đích (Finish Line)', 1200, 100, NOW(), NOW())
ON DUPLICATE KEY UPDATE title=VALUES(title);

-- 3. Insert Questions (1 to 30)
-- ROUND 1 (Questions 1 - 10)
INSERT INTO questions (id, content, type, correct_answer, topic, difficulty, audio_url, created_at, updated_at) VALUES
(1, 'What is the synonym of "happy"?', 'MULTIPLE_CHOICE', 'Joyful', 'Vocabulary', 'EASY', NULL, NOW(), NOW()),
(2, 'She _____ to school every morning by bicycle.', 'MULTIPLE_CHOICE', 'goes', 'Grammar', 'EASY', NULL, NOW(), NOW()),
(3, 'Which of the following is an irregular verb?', 'MULTIPLE_CHOICE', 'Write', 'Grammar', 'EASY', NULL, NOW(), NOW()),
(4, 'Choose the word that has different pronunciation: cat, hat, bat, hate', 'MULTIPLE_CHOICE', 'hate', 'Phonetics', 'EASY', NULL, NOW(), NOW()),
(5, 'If it rains tomorrow, we _____ the picnic.', 'MULTIPLE_CHOICE', 'will cancel', 'Grammar', 'MEDIUM', NULL, NOW(), NOW()),
(6, 'The opposite of "ancient" is: ', 'MULTIPLE_CHOICE', 'Modern', 'Vocabulary', 'EASY', NULL, NOW(), NOW()),
(7, 'How many days _____ there in February of a leap year?', 'MULTIPLE_CHOICE', 'are', 'Grammar', 'EASY', NULL, NOW(), NOW()),
(8, 'He is interested _____ learning foreign languages.', 'MULTIPLE_CHOICE', 'in', 'Grammar', 'EASY', NULL, NOW(), NOW()),
(9, 'Listen and select what the speaker plans to buy this weekend.', 'LISTENING', 'A new bicycle', 'Listening', 'MEDIUM', 'https://mock-audio.memestudy.io/audio/r1_q9.mp3', NOW(), NOW()),
(10, 'Listen to the conversation: What time does the train depart?', 'LISTENING', '08:30 AM', 'Listening', 'MEDIUM', 'https://mock-audio.memestudy.io/audio/r1_q10.mp3', NOW(), NOW()),

-- ROUND 2 (Questions 11 - 20)
(11, 'Neither Peter nor his friends _____ present at the meeting yesterday.', 'MULTIPLE_CHOICE', 'were', 'Grammar', 'MEDIUM', NULL, NOW(), NOW()),
(12, 'The book _____ I bought last week was very captivating.', 'MULTIPLE_CHOICE', 'which', 'Grammar', 'MEDIUM', NULL, NOW(), NOW()),
(13, 'Could you please _____ down the volume? It is too loud.', 'MULTIPLE_CHOICE', 'turn', 'Phrasal Verbs', 'MEDIUM', NULL, NOW(), NOW()),
(14, 'Despite _____ hard, he failed the entrance exam.', 'MULTIPLE_CHOICE', 'studying', 'Grammar', 'MEDIUM', NULL, NOW(), NOW()),
(15, 'What does the idiom "break a leg" mean?', 'MULTIPLE_CHOICE', 'Good luck', 'Idioms', 'MEDIUM', NULL, NOW(), NOW()),
(16, 'Mount Everest is the _____ mountain in the world.', 'MULTIPLE_CHOICE', 'highest', 'Grammar', 'EASY', NULL, NOW(), NOW()),
(17, 'I look forward to _____ from you soon.', 'MULTIPLE_CHOICE', 'hearing', 'Grammar', 'MEDIUM', NULL, NOW(), NOW()),
(18, 'The teacher asked the students to hand _____ their assignments.', 'MULTIPLE_CHOICE', 'in', 'Phrasal Verbs', 'MEDIUM', NULL, NOW(), NOW()),
(19, 'Listen to the announcement: What gate should passengers proceed to?', 'LISTENING', 'Gate 14B', 'Listening', 'MEDIUM', 'https://mock-audio.memestudy.io/audio/r2_q19.mp3', NOW(), NOW()),
(20, 'Listen to the weather forecast: What will the weather be like tomorrow?', 'LISTENING', 'Rainy and windy', 'Listening', 'HARD', 'https://mock-audio.memestudy.io/audio/r2_q20.mp3', NOW(), NOW()),

-- ROUND 3 (Questions 21 - 30)
(21, 'Had they known about the traffic jam, they _____ earlier.', 'MULTIPLE_CHOICE', 'would have left', 'Grammar', 'HARD', NULL, NOW(), NOW()),
(22, 'The company is seeking candidates with exceptional interpersonal _____.', 'MULTIPLE_CHOICE', 'skills', 'Vocabulary', 'HARD', NULL, NOW(), NOW()),
(23, 'Not only _____ late, but he also forgot the key documents.', 'MULTIPLE_CHOICE', 'was he', 'Inversion', 'HARD', NULL, NOW(), NOW()),
(24, 'The contract was deemed null and _____ due to lack of signatures.', 'MULTIPLE_CHOICE', 'void', 'Collocations', 'HARD', NULL, NOW(), NOW()),
(25, 'The scientist made a major _____ in renewable energy research.', 'MULTIPLE_CHOICE', 'breakthrough', 'Vocabulary', 'HARD', NULL, NOW(), NOW()),
(26, 'By the time we arrive at the airport, the flight _____.', 'MULTIPLE_CHOICE', 'will have taken off', 'Tenses', 'HARD', NULL, NOW(), NOW()),
(27, 'She took the job solely _____ the attractive remuneration package.', 'MULTIPLE_CHOICE', 'because of', 'Grammar', 'HARD', NULL, NOW(), NOW()),
(28, 'Hardly _____ the house when the storm began.', 'MULTIPLE_CHOICE', 'had she left', 'Inversion', 'HARD', NULL, NOW(), NOW()),
(29, 'Listen to the lecture: What is the main cause of deforestation discussed?', 'LISTENING', 'Agricultural expansion', 'Listening', 'HARD', 'https://mock-audio.memestudy.io/audio/r3_q29.mp3', NOW(), NOW()),
(30, 'Listen to the interview: What advice does the expert offer young entrepreneurs?', 'LISTENING', 'Stay persistent and adaptable', 'Listening', 'HARD', 'https://mock-audio.memestudy.io/audio/r3_q30.mp3', NOW(), NOW())
ON DUPLICATE KEY UPDATE content=VALUES(content);

-- 4. Insert Question Options
INSERT INTO question_options (question_id, option_order, option_text) VALUES
-- Q1
(1, 0, 'Sad'), (1, 1, 'Joyful'), (1, 2, 'Angry'), (1, 3, 'Tired'),
-- Q2
(2, 0, 'go'), (2, 1, 'goes'), (2, 2, 'going'), (2, 3, 'went'),
-- Q3
(3, 0, 'Play'), (3, 1, 'Walk'), (3, 2, 'Write'), (3, 3, 'Talk'),
-- Q4
(4, 0, 'cat'), (4, 1, 'hat'), (4, 2, 'bat'), (4, 3, 'hate'),
-- Q5
(5, 0, 'cancel'), (5, 1, 'cancelled'), (5, 2, 'will cancel'), (5, 3, 'would cancel'),
-- Q6
(6, 0, 'Old'), (6, 1, 'Modern'), (6, 2, 'Historical'), (6, 3, 'Traditional'),
-- Q7
(7, 0, 'is'), (7, 1, 'are'), (7, 2, 'was'), (7, 3, 'were'),
-- Q8
(8, 0, 'on'), (8, 1, 'at'), (8, 2, 'in'), (8, 3, 'with'),
-- Q9
(9, 0, 'A new laptop'), (9, 1, 'A new bicycle'), (9, 2, 'A pair of shoes'), (9, 3, 'A winter jacket'),
-- Q10
(10, 0, '07:15 AM'), (10, 1, '08:00 AM'), (10, 2, '08:30 AM'), (10, 3, '09:00 AM'),

-- Q11
(11, 0, 'was'), (11, 1, 'were'), (11, 2, 'is'), (11, 3, 'are'),
-- Q12
(12, 0, 'who'), (12, 1, 'which'), (12, 2, 'whose'), (12, 3, 'where'),
-- Q13
(13, 0, 'turn'), (13, 1, 'shut'), (13, 2, 'put'), (13, 3, 'keep'),
-- Q14
(14, 0, 'study'), (14, 1, 'studied'), (14, 2, 'studying'), (14, 3, 'to study'),
-- Q15
(15, 0, 'Be careful'), (15, 1, 'Good luck'), (15, 2, 'Give up'), (15, 3, 'Run away'),
-- Q16
(16, 0, 'higher'), (16, 1, 'highest'), (16, 2, 'most high'), (16, 3, 'high'),
-- Q17
(17, 0, 'hear'), (17, 1, 'heard'), (17, 2, 'hearing'), (17, 3, 'to hear'),
-- Q18
(18, 0, 'in'), (18, 1, 'out'), (18, 2, 'on'), (18, 3, 'off'),
-- Q19
(19, 0, 'Gate 12A'), (19, 1, 'Gate 14B'), (19, 2, 'Gate 18C'), (19, 3, 'Gate 20'),
-- Q20
(20, 0, 'Sunny and warm'), (20, 1, 'Snowy'), (20, 2, 'Rainy and windy'), (20, 3, 'Foggy and calm'),

-- Q21
(21, 0, 'would leave'), (21, 1, 'will leave'), (21, 2, 'would have left'), (21, 3, 'had left'),
-- Q22
(22, 0, 'skills'), (22, 1, 'talents'), (22, 2, 'habits'), (22, 3, 'manners'),
-- Q23
(23, 0, 'he was'), (23, 1, 'was he'), (23, 2, 'he arrived'), (23, 3, 'did he'),
-- Q24
(24, 0, 'empty'), (24, 1, 'void'), (24, 2, 'blank'), (24, 3, 'bare'),
-- Q25
(25, 0, 'breakup'), (25, 1, 'breakdown'), (25, 2, 'breakthrough'), (25, 3, 'breakout'),
-- Q26
(26, 0, 'took off'), (26, 1, 'has taken off'), (26, 2, 'will take off'), (26, 3, 'will have taken off'),
-- Q27
(27, 0, 'because'), (27, 1, 'because of'), (27, 2, 'in spite of'), (27, 3, 'even though'),
-- Q28
(28, 0, 'she left'), (28, 1, 'did she leave'), (28, 2, 'had she left'), (28, 3, 'she had left'),
-- Q29
(29, 0, 'Urbanization'), (29, 1, 'Agricultural expansion'), (29, 2, 'Mining operations'), (29, 3, 'Natural forest fires'),
-- Q30
(30, 0, 'Avoid all risks'), (30, 1, 'Stay persistent and adaptable'), (30, 2, 'Seek quick profits'), (30, 3, 'Work completely alone')
ON DUPLICATE KEY UPDATE option_text=VALUES(option_text);

-- 5. Link Questions to Rounds (RoundQuestion)
INSERT INTO round_questions (round_id, question_id, order_index) VALUES
-- Round 1: Q1 - Q10
(1, 1, 1), (1, 2, 2), (1, 3, 3), (1, 4, 4), (1, 5, 5),
(1, 6, 6), (1, 7, 7), (1, 8, 8), (1, 9, 9), (1, 10, 10),

-- Round 2: Q11 - Q20
(2, 11, 1), (2, 12, 2), (2, 13, 3), (2, 14, 4), (2, 15, 5),
(2, 16, 6), (2, 17, 7), (2, 18, 8), (2, 19, 9), (2, 20, 10),

-- Round 3: Q21 - Q30
(3, 21, 1), (3, 22, 2), (3, 23, 3), (3, 24, 4), (3, 25, 5),
(3, 26, 6), (3, 27, 7), (3, 28, 8), (3, 29, 9), (3, 30, 10)
ON DUPLICATE KEY UPDATE order_index=VALUES(order_index);


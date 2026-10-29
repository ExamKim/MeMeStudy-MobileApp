# MeMeStudy - Database Documentation (MariaDB 11 / JPA)

Tài liệu thiết kế cơ sở dữ liệu cho dự án **MeMeStudy** (Hệ thống thi mô phỏng IOE).

---

## 1. Sơ đồ các bảng dữ liệu

### 1.1. `exams` (Kỳ thi)
| Cột | Kiểu dữ liệu | Ràng buộc | Mô tả |
|-----|-------------|-----------|-------|
| `id` | BIGINT | PK, AUTO_INCREMENT | Mã kỳ thi |
| `title` | VARCHAR(200) | NOT NULL | Tên kỳ thi (VD: Kỳ thi IOE Toàn quốc 2026) |
| `description` | TEXT | NULL | Mô tả chi tiết kỳ thi |
| `is_active` | BOOLEAN | NOT NULL, DEFAULT TRUE | Trạng thái kích hoạt (chỉ lấy round của exam đang active) |
| `created_at` | DATETIME | DEFAULT CURRENT_TIMESTAMP | Thời điểm tạo |
| `updated_at` | DATETIME | DEFAULT CURRENT_TIMESTAMP | Thời điểm cập nhật |

### 1.2. `rounds` (Vòng thi)
Mỗi kỳ thi gồm 3 vòng thi chuẩn (Vòng 1: Khởi động, Vòng 2: Tăng tốc, Vòng 3: Về đích).
| Cột | Kiểu dữ liệu | Ràng buộc | Mô tả |
|-----|-------------|-----------|-------|
| `id` | BIGINT | PK, AUTO_INCREMENT | Mã vòng thi |
| `exam_id` | BIGINT | FK -> exams(id), NOT NULL | Thuộc kỳ thi nào |
| `round_number` | INT | NOT NULL (1 - 3) | Thứ tự vòng (1, 2, 3) |
| `title` | VARCHAR(150) | NOT NULL | Tên vòng (VD: Vòng 1 - Khởi động) |
| `time_limit_seconds` | INT | NOT NULL, DEFAULT 600 | Thời gian làm bài tính bằng giây (10-20 phút) |
| `max_score` | INT | NOT NULL, DEFAULT 100 | Điểm tối đa (100 điểm/vòng, tổng 3 vòng = 300) |
| `created_at` | DATETIME | DEFAULT CURRENT_TIMESTAMP | Thời điểm tạo |
| `updated_at` | DATETIME | DEFAULT CURRENT_TIMESTAMP | Thời điểm cập nhật |

### 1.3. `questions` (Ngân hàng câu hỏi)
| Cột | Kiểu dữ liệu | Ràng buộc | Mô tả |
|-----|-------------|-----------|-------|
| `id` | BIGINT | PK, AUTO_INCREMENT | Mã câu hỏi |
| `content` | TEXT | NOT NULL | Nội dung câu hỏi |
| `type` | VARCHAR(50) | NOT NULL | Loại câu (`MULTIPLE_CHOICE`, `LISTENING`) |
| `correct_answer` | VARCHAR(500) | NOT NULL | Đáp án đúng (**Bảo mật trên Backend, không trả về Mobile**) |
| `topic` | VARCHAR(100) | NULL | Chủ đề (Grammar, Vocabulary, Phonetics, Listening...) |
| `difficulty` | VARCHAR(50) | DEFAULT 'MEDIUM' | Độ khó (EASY, MEDIUM, HARD) |
| `audio_url` | VARCHAR(500) | NULL | Đường dẫn file âm thanh (dành cho câu hỏi LISTENING) |
| `created_at` | DATETIME | DEFAULT CURRENT_TIMESTAMP | Thời điểm tạo |
| `updated_at` | DATETIME | DEFAULT CURRENT_TIMESTAMP | Thời điểm cập nhật |

### 1.4. `question_options` (Các lựa chọn của câu hỏi)
Bảng phụ lưu 4 lựa chọn (A, B, C, D) theo thứ tự:
| Cột | Kiểu dữ liệu | Ràng buộc | Mô tả |
|-----|-------------|-----------|-------|
| `question_id` | BIGINT | FK -> questions(id), NOT NULL | Mã câu hỏi |
| `option_order` | INT | NOT NULL | Thứ tự lựa chọn (0, 1, 2, 3) |
| `option_text` | VARCHAR(500) | NOT NULL | Nội dung của lựa chọn |

### 1.5. `round_questions` (Liên kết câu hỏi vào vòng thi)
Mỗi vòng thi gồm 10 câu hỏi theo thứ tự cố định.
| Cột | Kiểu dữ liệu | Ràng buộc | Mô tả |
|-----|-------------|-----------|-------|
| `id` | BIGINT | PK, AUTO_INCREMENT | Khóa chính |
| `round_id` | BIGINT | FK -> rounds(id), NOT NULL | Mã vòng thi |
| `question_id` | BIGINT | FK -> questions(id), NOT NULL | Mã câu hỏi |
| `order_index` | INT | NOT NULL (1 - 10) | Vị trí xuất hiện của câu hỏi trong vòng thi |

> **Ràng buộc duy nhất:** `UNIQUE KEY uk_round_question (round_id, question_id)`

### 1.6. `results` (Kết quả thi của thí sinh)
| Cột | Kiểu dữ liệu | Ràng buộc | Mô tả |
|-----|-------------|-----------|-------|
| `id` | BIGINT | PK, AUTO_INCREMENT | Mã kết quả |
| `user_id` | BIGINT | FK -> users(id), NULLABLE | Người làm bài |
| `round_id` | BIGINT | FK -> rounds(id), NOT NULL | Thuộc vòng thi nào |
| `score` | INT | NOT NULL, DEFAULT 0 | Tổng điểm đạt được (tối đa 100) |
| `submitted_at` | DATETIME | DEFAULT CURRENT_TIMESTAMP | Thời gian nộp bài |

> **Chỉ mục (Indexes):**
> - `idx_results_user_id (user_id)`
> - `idx_results_round_id (round_id)`

### 1.7. `answers` (Chi tiết câu trả lời của thí sinh)
| Cột | Kiểu dữ liệu | Ràng buộc | Mô tả |
|-----|-------------|-----------|-------|
| `id` | BIGINT | PK, AUTO_INCREMENT | Khóa chính |
| `result_id` | BIGINT | FK -> results(id), NOT NULL | Mã kết quả thi |
| `question_id` | BIGINT | FK -> questions(id), NOT NULL | Mã câu hỏi |
| `selected_answer` | VARCHAR(500) | NULL | Đáp án thí sinh đã chọn |
| `is_correct` | BOOLEAN | NOT NULL, DEFAULT FALSE | Kết quả đúng hay sai |

---

## 2. Ghi chú thống nhất với Khánh (Người 1)
1. **Entity `User`:** Hiện tại Người 2 tạo bản tối thiểu (`users` gồm `id, username, email, password, role, created_at`) để mapping foreign key trong `results`. Khi Người 1 hoàn thiện module Auth/User, chỉ cần dùng entity `User` của Người 1.
2. **Quyền riêng tư đáp án:** Cột `correct_answer` trong `questions` được bảo vệ nghiêm ngặt ở tầng backend, DTO `QuestionForLearnerResponse` trả về cho mobile loại trừ hoàn toàn trường này.
3. **Seed Data:** File `backend/src/main/resources/db/seed_rounds.sql` đã chuẩn bị sẵn 1 exam, 3 rounds, 30 câu hỏi (10 câu/vòng) sẵn sàng khởi tạo dữ liệu mẫu.


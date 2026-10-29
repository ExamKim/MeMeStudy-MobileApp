# MeMeStudy API Documentation (Tuần 1 - JAX-RS / Jersey)

Base URL: `http://<SERVER_HOST>:8080/api`

---

## 1. Health Check

### `GET /api/health`
Kiểm tra trạng thái hoạt động của Backend và kết nối Database.

- **Authentication:** Không yêu cầu
- **Response `200 OK`:**
```json
{
  "status": "UP",
  "database": "CONNECTED",
  "timestamp": "2026-10-29T17:15:00"
}
```

---

## 2. Vòng thi (Rounds)

### `GET /api/rounds`
Lấy danh sách các vòng thi thuộc kỳ thi đang được kích hoạt (`is_active = true`), sắp xếp theo `roundNumber` tăng dần.

- **Authentication:** Yêu cầu đăng nhập (Role: `USER` hoặc `ADMIN`). *(Giai đoạn Tuần 1: Chờ filter của Khánh sẵn sàng)*
- **Response `200 OK`:**
```json
[
  {
    "id": 1,
    "roundNumber": 1,
    "title": "Vòng 1 - Khởi động (Warm Up)",
    "timeLimitSeconds": 600,
    "maxScore": 100,
    "questionCount": 10
  },
  {
    "id": 2,
    "roundNumber": 2,
    "title": "Vòng 2 - Tăng tốc (Speed Up)",
    "timeLimitSeconds": 900,
    "maxScore": 100,
    "questionCount": 10
  },
  {
    "id": 3,
    "roundNumber": 3,
    "title": "Vòng 3 - Về đích (Finish Line)",
    "timeLimitSeconds": 1200,
    "maxScore": 100,
    "questionCount": 10
  }
]
```

---

## 3. Lấy câu hỏi cho người học

### `GET /api/rounds/{roundId}/questions`
Lấy danh sách 10 câu hỏi thuộc vòng thi theo thứ tự `orderIndex`.

> ⚠️ **BẢO MẬT:** API này **TUYỆT ĐỐI KHÔNG** trả về trường `correctAnswer` / `correct_answer` để tránh lộ đề và gian lận.

- **Authentication:** Yêu cầu đăng nhập.
- **Path Parameters:**
  - `roundId`: ID của vòng thi (Long)
- **Response `200 OK`:**
```json
[
  {
    "id": 1,
    "content": "What is the synonym of \"happy\"?",
    "type": "MULTIPLE_CHOICE",
    "options": [
      "Sad",
      "Joyful",
      "Angry",
      "Tired"
    ],
    "audioUrl": null,
    "orderIndex": 1
  },
  {
    "id": 9,
    "content": "Listen and select what the speaker plans to buy this weekend.",
    "type": "LISTENING",
    "options": [
      "A new laptop",
      "A new bicycle",
      "A pair of shoes",
      "A winter jacket"
    ],
    "audioUrl": "https://mock-audio.memestudy.io/audio/r1_q9.mp3",
    "orderIndex": 9
  }
]
```

- **Response `404 Not Found`:** Khi không tìm thấy vòng thi.
```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Vòng thi không tồn tại với ID: 999",
  "timestamp": "2026-10-29T17:15:00"
}
```

---

## 4. Nộp bài làm (Submissions)

### `POST /api/rounds/{roundId}/submissions`
Nhận bài thi của thí sinh và kiểm tra hợp lệ của danh sách câu hỏi.

> 📝 **Lưu ý Tuần 1:** API validate dữ liệu đầu vào (không rỗng, không trùng `questionId`, mọi `questionId` thuộc vòng này) và ghi nhận bài. Logic chấm điểm và lưu kết quả transaction sẽ được hoàn thiện ở **Tuần 2**.

- **Authentication:** Yêu cầu đăng nhập.
- **Path Parameters:**
  - `roundId`: ID của vòng thi (Long)
- **Request Body:**
```json
{
  "answers": [
    {
      "questionId": 1,
      "selectedAnswer": "Joyful"
    },
    {
      "questionId": 2,
      "selectedAnswer": "goes"
    }
  ]
}
```

- **Response `200 OK` (Thành công):**
```json
{
  "message": "Đã nhận bài",
  "received": 2
}
```

- **Response `400 Bad Request`:**
  - Khi danh sách answers rỗng:
    ```json
    {
      "status": 400,
      "error": "Bad Request",
      "message": "Danh sách câu trả lời không được để trống",
      "timestamp": "2026-10-29T17:15:00"
    }
    ```
  - Khi phát hiện trùng `questionId`:
    ```json
    {
      "status": 400,
      "error": "Bad Request",
      "message": "Phát hiện questionId bị trùng lặp: 1",
      "timestamp": "2026-10-29T17:15:00"
    }
    ```
  - Khi câu hỏi không thuộc vòng thi:
    ```json
    {
      "status": 400,
      "error": "Bad Request",
      "message": "Câu hỏi ID 15 không thuộc vòng thi có ID: 1",
      "timestamp": "2026-10-29T17:15:00"
    }
    ```


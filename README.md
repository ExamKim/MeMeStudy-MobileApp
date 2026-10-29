# MeMeStudy - Nền tảng Học Tiếng Anh & Mô phỏng Thi IOE

Dự án mô phỏng kỳ thi Olympic Tiếng Anh trên Internet (IOE) với cấu trúc 3 vòng thi chuẩn (Vòng 1: Khởi động, Vòng 2: Tăng tốc, Vòng 3: Về đích), tổng 30 câu hỏi và tối đa 300 điểm.

---

## 👥 Phân công nhiệm vụ

- **Khánh (Người 1):** Web Admin (`screens/admin`), Đăng ký / Đăng nhập / JWT / Phân quyền, CRUD ngân hàng câu hỏi, kết nối DB. (Làm việc trên nhánh `main`).
- **Thi (Người 2):** Ứng dụng Mobile người học (`screens/user`), API luồng thi (`rounds`, `questions`, `submissions`), cài đặt & kiểm tra môi trường. (Làm việc trên nhánh `thi`).

---

## 🛠️ Kiểm tra môi trường hệ thống

| Công cụ | Phiên bản yêu cầu | Phiên bản hệ thống | Trạng thái |
|---|---|---|---|
| **Java** | JDK 17+ | Java 21 LTS (`21.0.11`) | ✅ Đạt |
| **Maven** | 3.8+ / 3.9+ | Apache Maven 3.8.5 qua `.\mvnw.cmd` | ✅ Đạt |
| **Node.js** | 20+ / 22+ (LTS) | `v24.13.0` | ✅ Đạt |
| **npm** | 10+ / 11+ | `11.6.2` | ✅ Đạt |
| **Git** | 2.x | `2.46.0.windows.1` | ✅ Đạt |
| **Docker** | Docker Compose v2 | MariaDB 11 & Adminer qua `docker-compose.yml` | ✅ Đã cấu hình |

---

## 🚀 Hướng dẫn chạy dự án (Từng bước)

### Bước 1: Khởi chạy Database (MariaDB 11)
Tại thư mục gốc dự án:
```bash
docker compose up -d
```
- **MariaDB:** cổng `3306` (Database: `memestudy`, User: `memeuser`, Password: `memepassword`)
- **Adminer Web UI:** truy cập `http://localhost:8080` để quản lý DB trực quan.
- **Dữ liệu mẫu (Seed Data):** File `backend/src/main/resources/db/seed_rounds.sql` tự động được nạp khi container khởi tạo lần đầu, hoặc chạy tự động bởi `DatabaseSeeder`.

> 💡 *Ghi chú cho Dev:* Backend tích hợp cơ chế tự động fallback sang cơ sở dữ liệu in-memory H2 nếu MariaDB chưa khởi chạy, giúp việc phát triển và chạy test không bao giờ bị gián đoạn.

---

### Bước 2: Khởi chạy Backend (Java Jakarta EE / Jersey JAX-RS)
Di chuyển vào thư mục `backend/`:
```bash
cd backend

# Chạy kiểm thử tự động (Unit & Integration tests)
.\mvnw.cmd test

# Chạy trực tiếp máy chủ Backend ở cổng 8080:
.\mvnw.cmd compile exec:java

# Hoặc đóng gói file WAR triển khai lên Apache Tomcat 10.1:
.\mvnw.cmd clean package
# File xuất ra: backend/target/backend-1.0-SNAPSHOT.war
```

- **Kiểm tra Health Check:** `GET http://localhost:8080/api/health`
- **Danh sách 3 vòng thi:** `GET http://localhost:8080/api/rounds`
- **10 câu hỏi vòng 1 (không lộ đáp án):** `GET http://localhost:8080/api/rounds/1/questions`

---

### Bước 3: Khởi chạy Frontend (Expo React Native)
Di chuyển vào thư mục `frontend/`:
```bash
cd frontend

# Cài đặt thư viện (nếu chưa cài)
npm install

# Khởi chạy ứng dụng Expo
npx expo start
```

- Nhấn `a` để mở trên Android Emulator.
- Nhấn `w` để mở trên trình duyệt Web.
- Quét mã QR bằng ứng dụng **Expo Go** trên điện thoại thật (lưu ý điện thoại và máy tính cùng mạng Wi-Fi và cập nhật `EXPO_PUBLIC_API_URL` bằng IP LAN máy tính).

---

## ⚙️ Biến môi trường

### Backend (`backend`)
| Tên biến | Mặc định | Mô tả |
|---|---|---|
| `PORT` | `8080` | Cổng HTTP của Backend |
| `DB_URL` | `jdbc:mariadb://localhost:3306/memestudy` | JDBC URL kết nối MariaDB |
| `DB_USER` | `memeuser` | Tên người dùng database |
| `DB_PASSWORD` | `memepassword` | Mật khẩu database |

### Frontend (`frontend/.env`)
| Tên biến | Mặc định | Mô tả |
|---|---|---|
| `EXPO_PUBLIC_API_URL` | `http://<IP_LAN>:8080/api` | Địa chỉ Backend API (Dùng IP LAN khi chạy Expo Go) |
| `EXPO_PUBLIC_USE_MOCK` | `false` | Đặt `true` để dùng dữ liệu mẫu offline độc lập |

---

## 📂 Cấu trúc Repository

```text
MeMeStudy/
├── backend/                              # Java Jakarta EE 10 + Jersey + Maven
│   ├── src/main/java/iuh/fit/backend/
│   │   ├── config/                       # JPAUtil, cấu hình kết nối DB
│   │   ├── dto/                          # DTO: RoundResponse, QuestionForLearnerResponse (không có correctAnswer), SubmissionRequest/Response
│   │   ├── exception/                    # GlobalExceptionMapper, BadRequest, NotFound
│   │   ├── filter/                       # CorsFilter, AuthFilter (chờ Người 1)
│   │   ├── model/                        # JPA Entities: Exam, Round, Question, RoundQuestion, Result, Answer, User
│   │   ├── repository/                   # RoundRepository, QuestionRepository
│   │   ├── resource/                     # JAX-RS: RoundResource, HealthResource
│   │   ├── service/                      # RoundService (nghiệp vụ & validation)
│   │   ├── util/                         # DatabaseSeeder
│   │   ├── HelloApplication.java         # JAX-RS ResourceConfig (/api)
│   │   └── Main.java                     # Standalone Grizzly Server Runner
│   ├── src/main/resources/
│   │   ├── META-INF/persistence.xml      # Cấu hình Hibernate JPA
│   │   └── db/seed_rounds.sql            # Script SQL 1 exam, 3 rounds, 30 questions
│   ├── src/test/java/                    # Bộ kiểm thử tự động RoundResourceTest
│   └── pom.xml                           # Maven cấu hình dependencies
├── frontend/                             # Expo + React Native + TypeScript + NativeWind v4
│   ├── src/
│   │   ├── components/                   # RoundCard, QuestionCard, OptionButton, LoadingView, ErrorView
│   │   ├── context/                      # AuthContext (cờ isAuthenticated & dev bypass)
│   │   ├── navigation/                   # RootNavigator, UserNavigator, AuthNavigator
│   │   ├── screens/
│   │   │   ├── auth/                     # LoginScreen, RegisterScreen (placeholder)
│   │   │   ├── user/                     # HomeScreen, RoundListScreen, ExamScreen, ResultScreen
│   │   │   └── admin/                    # Khu vực Web Admin (Khánh phụ trách)
│   │   ├── services/                     # api.ts (Axios + JWT), roundService.ts
│   │   ├── types/                        # TypeScript Interfaces
│   │   └── utils/                        # mockData.ts (3 rounds x 10 questions)
│   ├── App.tsx                           # Điểm nhập ứng dụng (NativeWind + Context + Nav)
│   ├── tailwind.config.js                # Cấu hình Tailwind CSS
│   ├── global.css                        # Tailwind directives
│   ├── metro.config.js                   # withNativeWind
│   ├── babel.config.js                   # nativewind/babel & reanimated
│   └── package.json
├── docs/
│   ├── API.md                            # Tài liệu đặc tả 4 API endpoints
│   └── DATABASE.md                       # Tài liệu thiết kế CSDL JPA / MariaDB
├── docker-compose.yml                    # MariaDB 11 + Adminer
└── README.md
```

---

## 📖 Tài liệu liên quan
- [Tài liệu API (docs/API.md)](file:///docs/API.md)
- [Tài liệu Cơ sở dữ liệu (docs/DATABASE.md)](file:///docs/DATABASE.md)

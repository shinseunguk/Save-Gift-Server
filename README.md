# Save-Gift-Server

기프티콘 저장 및 관리 서비스의 백엔드 서버입니다.

## 기술 스택

- **Framework**: Spring MVC 4.3.30
- **ORM**: MyBatis 3.5.7
- **Database**: MySQL 8.0
- **Security**: Spring Security 4.1.0
- **Build**: Maven
- **Push**: FCM (Firebase Cloud Messaging)
- **Container**: Docker + Tomcat 9
- **Java**: JDK 1.8

## 프로젝트 구조

계층형 아키텍처(Layered Architecture) 패턴을 적용하여 구성되어 있습니다.

```
src/main/java/com/savegift
├── controller/     # 프레젠테이션 계층 - HTTP 요청/응답 처리
│   ├── UserController.java
│   ├── GiftController.java
│   ├── NotificationController.java
│   ├── EmailController.java
│   └── SmsController.java
│
├── service/        # 비즈니스 계층 - 비즈니스 로직 처리
│   ├── UserService.java
│   ├── GiftService.java
│   ├── NotificationService.java
│   ├── MailService.java
│   └── SmsService.java
│
├── repository/     # 영속성 계층 - 데이터 접근 처리
│   ├── UserRepository.java
│   ├── GiftRepository.java
│   └── NotificationRepository.java
│
├── domain/         # 도메인 모델 - 엔티티 클래스
│   ├── User.java
│   ├── Friend.java
│   ├── Gift.java
│   ├── GiftUserDevice.java
│   ├── Notification.java
│   └── UserDevice.java
│
├── dto/            # 데이터 전송 객체
│   ├── EmailDto.java
│   └── SmsDto.java
│
├── scheduler/      # 스케줄러
│   └── GiftExpirationScheduler.java
│
└── util/           # 유틸리티
    └── SHA256Util.java
```

### 계층 설명

| 계층 | 역할 |
|------|------|
| Controller | 클라이언트 요청을 받아 Service 계층으로 전달하고 응답 반환 |
| Service | 비즈니스 로직을 처리하고 트랜잭션 관리 |
| Repository | MyBatis를 통한 데이터베이스 CRUD 작업 수행 |
| Domain | 데이터베이스 테이블과 매핑되는 엔티티 클래스 |
| DTO | 계층 간 데이터 전송을 위한 객체 |

## 주요 기능

### 회원 관리
- 회원가입 / 로그인
- 소셜 로그인 (카카오, 애플)
- 아이디 찾기
- 비밀번호 변경
- 회원 탈퇴

### 친구 관리
- 이메일 / 전화번호로 친구 검색
- 친구 요청 / 수락 / 거절
- 친구 삭제

### 기프티콘 관리
- 기프티콘 등록 (이미지 업로드)
- 기프티콘 목록 조회 (미사용/사용/전체)
- 기프티콘 상세 조회
- 기프티콘 수정 / 삭제
- 기프티콘 사용 처리
- 친구에게 기프티콘 선물

### 알림
- FCM 푸시 알림
- 유효기간 만료 알림 (30일/7일/1일 전)
- 친구 요청 알림
- 선물 수신 알림

## API 엔드포인트

### 회원

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/register` | 회원가입 |
| POST | `/login` | 로그인 |
| POST | `/social/login` | 소셜 로그인 |
| GET | `/duplicationid` | 아이디 중복 확인 |
| POST | `/userinfo` | 회원 정보 조회 |
| POST | `/userinfo/name` | 이름 변경 |
| POST | `/useinfo/password` | 비밀번호 변경 |
| POST | `/secession` | 회원 탈퇴 |
| POST | `/find/id` | 아이디 찾기 |

### 친구

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/findemail` | 이메일로 친구 검색 |
| POST | `/findphone` | 전화번호로 친구 검색 |
| POST | `/waitFriend` | 친구 요청 |
| POST | `/addFriend` | 친구 수락 |
| POST | `/deleteFriendWait` | 친구 요청 취소/거절 |
| POST | `/delete/friend` | 친구 삭제 |
| POST | `/getFriend` | 친구 목록 조회 |
| POST | `/getRequestFriend` | 친구 요청 목록 조회 |

### 기프티콘

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/register/image` | 기프티콘 등록 |
| POST | `/gift/save` | 기프티콘 목록 조회 |
| POST | `/gift/detail` | 기프티콘 상세 조회 |
| POST | `/gift/revise` | 기프티콘 수정 |
| POST | `/gift/delete` | 기프티콘 삭제 |
| POST | `/gift/useyn` | 사용 여부 변경 |
| POST | `/gift/present` | 기프티콘 선물 |
| POST | `/present/tab` | 선물함 조회 |

### 알림

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/device/insert` | 디바이스 등록 |
| POST | `/notisetting` | 알림 설정 변경 |
| GET | `/status` | 알림 상태 조회 |
| GET | `/version` | 앱 버전 조회 |

## 실행 방법

### 요구사항
- JDK 1.8+
- Maven 3.x
- Docker & Docker Compose
- MySQL 8.0 (Docker 사용 시 자동 설정)

### 환경 설정

`src/main/resources/datasource.properties` 파일에서 설정:

```properties
# DB 설정
jdbc.driverClassName=com.mysql.cj.jdbc.Driver
jdbc.url=jdbc:mysql://db:3306/save_gift?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC
jdbc.username=root
jdbc.password=root12345

# 메일 설정
mail.host=smtp.gmail.com
mail.port=587
mail.username=your-email@gmail.com
mail.password=your-app-password

# FCM 설정
fcm.keyValue=YOUR_FCM_KEY_HERE

# SMS API 설정 (CoolSMS)
sms.api.key=YOUR_SMS_API_KEY
sms.api.secret=YOUR_SMS_API_SECRET
```

### Docker로 실행 (권장)

```bash
# 빌드 및 실행
docker-compose up --build

# 백그라운드 실행
docker-compose up -d --build

# 로그 확인
docker-compose logs -f

# 앱 로그만 확인
docker-compose logs -f app

# 컨테이너 상태 확인
docker-compose ps

# 중지
docker-compose down

# 볼륨 포함 완전 삭제
docker-compose down -v
```

실행 후 `http://localhost:8080`으로 접속

### 로컬 Tomcat으로 실행

```bash
# 빌드
mvn clean package

# target/ios-1.0.0-BUILD-SNAPSHOT.war 파일을 Tomcat webapps에 배포
```

> 로컬 실행 시 `datasource.properties`의 DB URL을 `localhost`로 변경 필요

## 주요 의존성

| 라이브러리 | 버전 | 용도 |
|-----------|------|------|
| Spring MVC | 4.3.30 | 웹 프레임워크 |
| Spring Security | 4.1.0 | 보안 |
| MyBatis | 3.5.7 | ORM |
| MySQL Connector | 8.0.25 | DB 드라이버 |
| Jackson | 2.9.6 | JSON 처리 |
| Lombok | 1.18.30 | 보일러플레이트 코드 제거 |
| Google API Client | 1.32.1 | FCM 연동 |
| JavaMail | 1.4.7 | 이메일 발송 |
| CoolSMS SDK | 2.2 | SMS 발송 |

## 라이선스

Private Project

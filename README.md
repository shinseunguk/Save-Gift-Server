# Save-Gift-Server

기프티콘 저장 및 관리 서비스의 백엔드 서버입니다.

## 기술 스택

- **Framework**: Spring MVC 3.1.1
- **ORM**: MyBatis 3.5.7
- **Database**: MySQL 8.0.25
- **Security**: Spring Security 4.1.0
- **Build**: Maven
- **Push**: FCM (Firebase Cloud Messaging)

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
- JDK 1.6+
- Maven
- MySQL

### 설정
1. `src/main/resources/datasource.properties` 파일에 DB 접속 정보 설정
2. `src/main/webapp/WEB-INF/spring/datasource.properties` 파일에 DB 접속 정보 설정

### 빌드 및 실행
```bash
# 빌드
mvn clean package

# 실행 (Tomcat 등 WAS에 배포)
```

## 라이선스

Private Project

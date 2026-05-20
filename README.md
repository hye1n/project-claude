# 자유 게시판

Spring Boot 3.2 + jQuery + Bootstrap 5 기반의 게시판 애플리케이션입니다.

---

## 기술 스택

| 구분 | 기술 |
|------|------|
| Backend | Spring Boot 3.2.3, Java 17 |
| ORM | Spring Data JPA (Hibernate) |
| DB | Supabase (PostgreSQL) |
| Frontend | HTML5, jQuery 3.7.1, Bootstrap 5.3 |

---

## 프로젝트 구조

```
src/main/java/com/example/board/
├── BoardApplication.java
├── controller/
│   └── PostController.java       # REST API 컨트롤러
├── dto/
│   ├── PostRequestDto.java       # 요청 DTO (등록/수정)
│   └── PostResponseDto.java      # 응답 DTO
├── entity/
│   └── Post.java                 # JPA 엔티티
├── exception/
│   ├── PostNotFoundException.java
│   └── GlobalExceptionHandler.java
├── repository/
│   └── PostRepository.java
└── service/
    └── PostService.java

src/main/resources/
├── application.yml               # DB 설정
└── static/
    ├── index.html                # 목록 페이지
    ├── view.html                 # 상세 보기 페이지
    └── write.html                # 글쓰기/수정 페이지
```

---

## 실행 방법

### 1. Supabase 설정

1. [https://supabase.com](https://supabase.com) 에서 프로젝트 생성
2. **Settings → Database → Connection string → JDBC** 확인
3. `src/main/resources/application.yml` 수정:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://db.YOUR_PROJECT_REF.supabase.co:5432/postgres?sslmode=require
    username: postgres
    password: YOUR_SUPABASE_PASSWORD
```

> `ddl-auto: update` 설정으로 첫 실행 시 **posts 테이블이 자동 생성**됩니다.

### 2. 빌드 및 실행

```bash
# Maven 빌드
mvn clean package

# 실행
mvn spring-boot:run
```

### 3. 접속

```
http://localhost:8080
```

---

## REST API

| Method | URL | 설명 |
|--------|-----|------|
| GET | `/api/posts` | 전체 목록 (최신순) |
| GET | `/api/posts?keyword=검색어` | 제목/내용 검색 |
| GET | `/api/posts/{id}` | 상세 조회 + 조회수 증가 |
| GET | `/api/posts/{id}/edit` | 수정용 조회 (조회수 증가 없음) |
| POST | `/api/posts` | 게시글 등록 |
| PUT | `/api/posts/{id}` | 게시글 수정 |
| DELETE | `/api/posts/{id}` | 게시글 삭제 |

### 요청 예시 (POST/PUT)

```json
{
  "title": "제목입니다",
  "author": "홍길동",
  "content": "내용입니다."
}
```

---

## 주요 기능

- ✅ 게시글 목록 조회 (최신순 정렬)
- ✅ 게시글 상세 보기 (조회수 자동 증가)
- ✅ 게시글 작성
- ✅ 게시글 수정 (조회수 증가 없음)
- ✅ 게시글 삭제 (확인 모달)
- ✅ 제목/내용 검색
- ✅ 클라이언트 페이지네이션 (10개씩)
- ✅ 24시간 이내 게시글 NEW 배지
- ✅ 서버/클라이언트 양쪽 유효성 검사
- ✅ XSS 방지 (jQuery `.text()` 사용)

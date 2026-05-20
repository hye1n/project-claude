# CLAUDE.md — project-claude

자유 게시판 MVP. Spring Boot REST API + jQuery SPA.
코드를 수정하기 전 이 파일을 읽고 규칙을 확인할 것.

---

## 기술 스택과 아키텍처

### 구성 요소 (4개 고정)
- **Vercel** — jQuery SPA 정적 호스팅
- **Spring Boot 3.x** — REST API 서버 (base path `/api`, 버전 prefix 금지)
- **Supabase** — PostgreSQL 단일 DB + Auth 토큰 검증
- **Google OAuth** — 로그인 전용, Spring Boot에서 토큰 수신·검증

> 상세는 `docs/architecture.md` 참조

### 금지 항목 (MVP 범위 밖)
메시지 큐·캐시·Read Replica·전문검색·WebSocket·마이크로서비스 도입 금지.
테이블 추가 금지 — `tasks` 단일 테이블만 사용.

---

## DB

- 테이블: `tasks` 하나뿐 (ENUM·인덱스·트리거·RLS·Comment 금지)
- 필수 컬럼: `title VARCHAR(200) NOT NULL`, `author VARCHAR(50) NOT NULL`
- 자동 컬럼: `view_count INTEGER DEFAULT 0`, `created_at / updated_at TIMESTAMPTZ DEFAULT now()`

> 상세는 `docs/db.md` 참조

---

## API

- base path: `/api` — 버전 prefix(`/v1` 등) 금지
- **읽기는 공개**: `GET /api/tasks`, `GET /api/tasks/{id}` — 인증 불필요
- **쓰기는 인증**: `POST / PUT / DELETE /api/tasks/**` — Bearer JWT 필수
- auth 엔드포인트: `POST /api/auth/login`(토큰 발급), `DELETE /api/auth/session`(로그아웃)
- 총 엔드포인트: 7개 (auth 2 + CRUD 5), 이 이상 추가 시 요건 검토 필요

> 상세는 `docs/api.md` 참조

---

## 핵심 동작 규칙

| 규칙 | 내용 |
|------|------|
| 입력 길이 위반 | title 0자·201자↑ 또는 author 0자·51자↑ → HTTP **400** 반환 |
| 존재하지 않는 ID | 조회·수정·삭제 모두 → HTTP **404** 반환 |
| 조회수 증가 | `GET /api/tasks/{id}` 호출마다 `view_count + 1` (중복 제거 없음) |
| 수정 시 조회수 | `GET /api/tasks/{id}/edit` 및 `PUT` 호출 시 `view_count` 변경 없음 |
| 검색 범위 | `?keyword=` 는 `title` + `content` 동시 LIKE 검색 |
| 삭제 방식 | 즉시 영구 삭제 — Soft Delete 없음 |
| 페이지네이션 | 클라이언트 10건씩 처리; 게시글 500건 초과 시 서버사이드 전환 검토 |
| XSS 방지 | 모든 사용자 입력 HTML 이스케이프 필수; 스크립트 실행 0건 기준 |
| NEW 배지 | `created_at` 기준 24시간 이내 task에만 표시 |

> 상세는 `docs/requirements.md` · `docs/user-stories.md` 참조

---

## 도메인 용어

| 용어 | 정의 |
|------|------|
| **task** | `tasks` 테이블 단일 행. UI·사용자에게는 "게시글"로 표시 |
| **view_count** | 상세 페이지(`GET /api/tasks/{id}`) 진입 횟수 누적값. 수정 조회 시 불변 |
| **NEW 배지** | `created_at` 기준 24시간 이내 task에 표시하는 시각적 신규 표시자 |
| **keyword** | `?keyword=` 쿼리 파라미터. `title`·`content` 동시 LIKE 검색에 사용 |
| **Bearer JWT** | Google OAuth 로그인 후 `/api/auth/login`이 발급하는 인증 토큰 |
| **수정용 조회** | `GET /api/tasks/{id}/edit` — view_count를 올리지 않는 별도 조회 경로 |

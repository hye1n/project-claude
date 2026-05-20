# API 엔드포인트 — 자유 게시판 MVP

| METHOD | PATH | 설명 | 인증 |
|--------|------|------|------|
| `POST` | `/api/auth/login` | Google OAuth 코드로 세션 토큰 발급 | 불필요 |
| `DELETE` | `/api/auth/session` | 세션 토큰 삭제(로그아웃) | Bearer JWT |
| `GET` | `/api/tasks` | 전체 게시글 최신순 반환, `?keyword=` 로 제목·내용 검색 | 불필요 |
| `GET` | `/api/tasks/{id}` | 게시글 단건 조회, 조회수 +1 | 불필요 |
| `POST` | `/api/tasks` | 게시글 등록 (제목 1–200자, 작성자 1–50자 필수) | Bearer JWT |
| `PUT` | `/api/tasks/{id}` | 게시글 수정, 조회수 변경 없음 | Bearer JWT |
| `DELETE` | `/api/tasks/{id}` | 게시글 즉시 영구 삭제 | Bearer JWT |

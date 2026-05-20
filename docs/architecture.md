# 아키텍처 — 자유 게시판 MVP

```
┌───────────┐  HTTPS   ┌──────────────────┐  JDBC    ┌──────────────────────┐
│  Vercel   │◄────────►│  Spring Boot 3.x  │─────────►│  Supabase            │
│  jQuery   │          │  REST API         │          │  Postgres · Auth     │
└───────────┘          └────────┬──────────┘          └──────────────────────┘
                                └── OAuth 2.0 token ──► Google OAuth
```

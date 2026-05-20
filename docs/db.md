# DB 설계 — tasks

```sql
CREATE TABLE tasks (
    id         BIGSERIAL     PRIMARY KEY,
    title      VARCHAR(200)  NOT NULL,
    content    TEXT,
    author     VARCHAR(50)   NOT NULL,
    view_count INTEGER       NOT NULL DEFAULT 0,
    created_at TIMESTAMPTZ   NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ   NOT NULL DEFAULT now()
);
```

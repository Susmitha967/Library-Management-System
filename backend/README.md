## Spring Boot backend (for Swing client)

### Run
From `backend/`:

```bash
mvn spring-boot:run
```

It uses the same env vars as the Swing app:
- `LIB_DB_URL` (default `jdbc:postgresql://localhost:5432/library`)
- `LIB_DB_USER` (default `postgres`)
- `LIB_DB_PASSWORD` (default `postgres`)

### Endpoints (form/query params, not JSON)
- `POST /api/auth/login?userid=...&password=...` → `OK` or `401 INVALID`
- `POST /api/students?id=...&name=...&course=...&branch=...&semester=...` → `OK`
- `POST /api/books?id=...&name=...&publisher=...&price=...&year=...` → `OK`
- `GET /api/books?id=...` → key/value lines or `404 NOT_FOUND`
- `GET /api/books/by-student?studentId=...` → key/value lines or `404 NOT_FOUND`
- `POST /api/books/issue?id=...&studentId=...&issue=...&due=...` → `OK`
- `POST /api/books/return?id=...` → `OK`


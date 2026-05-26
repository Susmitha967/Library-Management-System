# Library Management System (Swing + Spring Boot + PostgreSQL)

Desktop **Java Swing** app → **Spring Boot REST API** → **PostgreSQL**.

## Quick start

**Full steps for any OS (Windows / macOS / Linux):** see **[RUN.md](RUN.md)**

Summary:

1. Install **Java 17+** and **PostgreSQL**
2. Create database `library` and tables (SQL in [RUN.md](RUN.md) or [README_POSTGRES.md](README_POSTGRES.md))
3. Start **backend** from `backend/` (port **9090**)
4. Start **Swing UI** from project root (NetBeans F6, or command line in [RUN.md](RUN.md))
5. Log in (default: `admin` / `admin`)

Set your own database password with environment variables:

- `LIB_DB_URL` — default `jdbc:postgresql://localhost:5432/library`
- `LIB_DB_USER` — default `postgres`
- `LIB_DB_PASSWORD` — default `postgres` (change to your PostgreSQL password)

Swing app API URL:

- `LIB_API_URL` — default in NetBeans: `http://localhost:9090`

## Project structure

| Path | Role |
|------|------|
| `src/` | Swing desktop client |
| `backend/` | Spring Boot REST API |
| `lib/` | Optional JARs (e.g. AbsoluteLayout for command-line builds) |
| `RUN.md` | **How to run (everyone)** |

## API

See [backend/README.md](backend/README.md).

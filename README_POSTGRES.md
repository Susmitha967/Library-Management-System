## PostgreSQL setup (for this project)

### 1) Add the PostgreSQL JDBC driver
- Download the PostgreSQL JDBC driver `.jar` (for example `postgresql-42.x.x.jar`)
- Create a folder `lib` in the project root (same folder as `build.xml`)
- Put the jar in `lib` and name it exactly `postgresql.jar`

This project is configured to load the driver jar from:
- `lib\postgresql.jar` (see `nbproject/project.properties`)

### 2) Configure connection settings
The connection is read from environment variables (with defaults).

- **LIB_DB_URL** (default `jdbc:postgresql://localhost:5432/library`)
- **LIB_DB_USER** (default `postgres`)
- **LIB_DB_PASSWORD** (default `postgres`)

PowerShell example (use **your** PostgreSQL password):

```powershell
$env:LIB_DB_URL = "jdbc:postgresql://localhost:5432/library"
$env:LIB_DB_USER = "postgres"
$env:LIB_DB_PASSWORD = "YOUR_POSTGRES_PASSWORD"
```

macOS / Linux:

```bash
export LIB_DB_URL=jdbc:postgresql://localhost:5432/library
export LIB_DB_USER=postgres
export LIB_DB_PASSWORD=YOUR_POSTGRES_PASSWORD
```

### 3) Create the database + tables
Run this in `psql` (or PgAdmin query tool):

```sql
CREATE DATABASE library;
\c library

CREATE TABLE IF NOT EXISTS login (
  userid   text PRIMARY KEY,
  password text NOT NULL
);

CREATE TABLE IF NOT EXISTS student (
  id       text PRIMARY KEY,
  name     text,
  course   text,
  branch   text,
  semester text
);

CREATE TABLE IF NOT EXISTS book (
  id        text PRIMARY KEY,
  name      text,
  publisher text,
  price     text,
  year      text,
  status    text,
  issue     text,
  due       text,
  studentid text
);

INSERT INTO login(userid, password)
VALUES ('admin', 'admin')
ON CONFLICT (userid) DO UPDATE SET password = EXCLUDED.password;
```

### 4) Run
See **[RUN.md](RUN.md)** for full steps (backend + Swing UI, all platforms).

- **NetBeans**: open project → Run
- **Ant** (from project root, if installed): `ant clean run`


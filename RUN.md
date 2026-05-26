# How to run this project (any computer)

This app has **three parts**. Start them in this order:

1. **PostgreSQL** (database)
2. **Spring Boot backend** (REST API on port **9090**)
3. **Swing desktop UI** (login window)

```
Swing UI  --HTTP-->  Backend (port 9090)  --JDBC-->  PostgreSQL (port 5432)
```

---

## What everyone needs installed

| Software | Minimum version | Why |
|----------|-----------------|-----|
| **Java JDK** | 17+ | Backend and desktop app |
| **PostgreSQL** | 12+ | Stores data |
| **Git** (optional) | any | To clone the repo |

**Optional (pick one way to run the desktop app):**

- **NetBeans** — easiest for the Swing project, or  
- **Command line** — `javac` + `java` (no NetBeans), or  
- **Apache Ant** — only if `ant` is installed (`ant clean run`)

**Not required:** installing Maven globally (the project includes **Maven Wrapper** in `backend/`).

---

## One-time setup (each machine)

### Step 1 — Clone or copy the project

Anyone should have the project folder that contains:

- `build.xml`
- `backend/`
- `src/`

### Step 2 — Install and start PostgreSQL

- Install PostgreSQL for your OS: https://www.postgresql.org/download/
- Make sure the server is running.

Check (any OS, if `pg_isready` is on your PATH):

```bash
pg_isready -h localhost -p 5432
```

Expected: `accepting connections`

### Step 3 — Create database and tables

Open **psql**, **pgAdmin**, or any SQL client and run:

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

Default login after setup: **userid** `admin`, **password** `admin`.

### Step 4 — Set database credentials (your own password)

Do **not** commit real passwords to Git. Each person sets these for their own PostgreSQL user:

| Variable | Meaning | Default if unset |
|----------|---------|------------------|
| `LIB_DB_URL` | JDBC URL | `jdbc:postgresql://localhost:5432/library` |
| `LIB_DB_USER` | DB username | `postgres` |
| `LIB_DB_PASSWORD` | DB password | `postgres` |

Use the password **you** chose when installing PostgreSQL.

### Step 5 — Swing layout library (command-line users only)

If you use **NetBeans**, skip this — NetBeans provides AbsoluteLayout.

If you run from the terminal, download once into `lib/` at the project root (next to `build.xml`):

**Windows (PowerShell):**

```powershell
mkdir lib -Force
Invoke-WebRequest -Uri "https://repo1.maven.org/maven2/org/netbeans/external/AbsoluteLayout/RELEASE290/AbsoluteLayout-RELEASE290.jar" -OutFile "lib\absolutelayout.jar"
```

**macOS / Linux:**

```bash
mkdir -p lib
curl -L -o lib/absolutelayout.jar \
  https://repo1.maven.org/maven2/org/netbeans/external/AbsoluteLayout/RELEASE290/AbsoluteLayout-RELEASE290.jar
```

---

## Every time you run the app

### Step A — Start the backend (keep this terminal open)

Open a terminal in the **`backend`** folder.

**Windows (cmd):**

```cmd
cd backend
set LIB_DB_URL=jdbc:postgresql://localhost:5432/library
set LIB_DB_USER=postgres
set LIB_DB_PASSWORD=YOUR_POSTGRES_PASSWORD
java -Dmaven.multiModuleProjectDirectory=%CD% -classpath .\.mvn\wrapper\maven-wrapper.jar org.apache.maven.wrapper.MavenWrapperMain spring-boot:run
```

**Windows (PowerShell):**

```powershell
cd backend
$env:LIB_DB_URL = "jdbc:postgresql://localhost:5432/library"
$env:LIB_DB_USER = "postgres"
$env:LIB_DB_PASSWORD = "YOUR_POSTGRES_PASSWORD"
java -Dmaven.multiModuleProjectDirectory=$PWD -classpath ".\.mvn\wrapper\maven-wrapper.jar" org.apache.maven.wrapper.MavenWrapperMain spring-boot:run
```

**macOS / Linux:**

```bash
cd backend
export LIB_DB_URL=jdbc:postgresql://localhost:5432/library
export LIB_DB_USER=postgres
export LIB_DB_PASSWORD=YOUR_POSTGRES_PASSWORD
./mvnw spring-boot:run
```

Wait until the log shows **`Started LibraryBackendApplication`**.  
API base URL: **http://localhost:9090**

Quick test (replace user/password if needed):

```bash
curl -X POST "http://localhost:9090/api/auth/login?userid=admin&password=admin"
```

Expected response body: `OK`

### Step B — Start the Swing desktop app

Open a **second** terminal in the **project root** (folder with `build.xml`).

**Option 1 — NetBeans (recommended for beginners)**

1. File → Open Project → select the folder with `build.xml`
2. Clean and Build
3. Run (F6)

The project is already configured to use `http://localhost:9090` (`nbproject/project.properties`).

**Option 2 — Command line**

From project root, compile once:

**Windows:**

```powershell
mkdir build\classes -Force
$files = Get-ChildItem src\*.java | ForEach-Object { $_.FullName }
javac --release 19 -encoding UTF-8 -d build\classes -cp lib\absolutelayout.jar $files
java -DLIB_API_URL=http://localhost:9090 -cp "build\classes;lib\absolutelayout.jar" SignIn
```

**macOS / Linux:**

```bash
mkdir -p build/classes
javac --release 19 -encoding UTF-8 -d build/classes -cp lib/absolutelayout.jar src/*.java
java -DLIB_API_URL=http://localhost:9090 -cp "build/classes:lib/absolutelayout.jar" SignIn
```

**Option 3 — Apache Ant** (only if `ant` is installed)

```bash
ant clean run
```

### Step C — Log in

Use a row from the `login` table (default: **admin** / **admin**).

---

## Checklist (print or share)

- [ ] Java 17+ installed (`java -version`)
- [ ] PostgreSQL installed and running
- [ ] Database `library` created with tables `login`, `student`, `book`
- [ ] `LIB_DB_*` variables set to **your** DB password
- [ ] Backend running on port **9090**
- [ ] Swing app started (NetBeans or `java SignIn`)
- [ ] Logged in with a valid `login` user

---

## Troubleshooting (all platforms)

| Problem | Fix |
|---------|-----|
| Login fails in Swing | Start backend first; confirm `http://localhost:9090` responds |
| Backend won’t start | Wrong `LIB_DB_PASSWORD` or database `library` missing |
| `ClassNotFoundException: AbsoluteLayout` | Add `lib/absolutelayout.jar` (Step 5) |
| Connection refused on 5432 | Start PostgreSQL service |
| Blank UI backgrounds | Image files under `src/img/` may be missing in some downloads; app still works |

---

## Sharing the project with a team

1. Put the code on GitHub/GitLab (do **not** commit passwords).
2. Each developer installs Java + PostgreSQL locally.
3. Each developer runs the SQL in Step 3 once.
4. Each developer sets `LIB_DB_PASSWORD` to their own PostgreSQL password.
5. Everyone follows **Step A → Step B → Step C** every time.

More API details: `backend/README.md`  
PostgreSQL notes: `README_POSTGRES.md`

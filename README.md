# Moon Dance

Moon Dance is an open source note-sharing platform built by the Bergen Open Source Foundation (BOSF), a student-led foundation at Bergen Community College (BCC). It is designed for students and the college community to share course materials, discover study resources, and collaborate through a student-owned engineering project.

## Mission

This project is part of BOSF's broader initiative to empower Bergen Community College students to innovate in open source by:

- Building software that serves real student needs.
- Creating hands-on opportunities for contributors to learn modern engineering practices.
- Maintaining transparent, community-driven development.

## Core Features

- JWT-based authentication (register, login, token refresh, change password).
- Course catalog browsing (schools, departments, courses, sessions, instructors).
- Note upload and discovery with search, trending, recent, and by-type feeds.
- Tagging, voting, and reporting/moderation workflows.
- S3-compatible object storage integration for files.
- OpenAPI/Swagger documentation for API exploration.

## Tech Stack

| Layer | Technologies |
| --- | --- |
| Frontend | React 18, TypeScript, Vite, Tailwind CSS |
| Backend | Spring Boot 4, Java 25, Spring Security, JPA |
| Database | PostgreSQL 18 + Flyway migrations |
| Cache | Redis 7 |
| Storage | AWS S3-compatible storage |
| Local Orchestration | Docker Compose |

## Repository Structure

```text
.
├── frontend/              # React + Vite app
├── backend/               # Spring Boot API
├── docker-compose.yml     # Local multi-service environment
├── CODE_OF_CONDUCT.md
├── CONTRIBUTING.md
├── SECURITY.md
└── LICENSE.md
```

## Prerequisites

Choose one of the following workflows:

- Docker workflow (recommended): Docker + Docker Compose
- Native workflow: Java 25, Node.js (22+ recommended) + npm, PostgreSQL, Redis

You also need credentials for an S3-compatible object storage provider (AWS S3, MinIO, or equivalent).

## Environment Variables

Create a `.env` file at the repository root.

```env
# Database
POSTGRES_DB=moondance
POSTGRES_USER=moondance
POSTGRES_PASSWORD=change-me

# Redis
REDIS_HOST=redis
REDIS_PORT=6379
REDIS_PASSWORD=

# Backend
PORT=8080
DATABASE_URL=jdbc:postgresql://db:5432/moondance
DATABASE_USER=moondance
DATABASE_PASSWORD=change-me
SPRING_PROFILES_ACTIVE=dev

# JWT
JWT_SECRET=replace-with-a-long-random-secret
JWT_ACCESS_EXPIRATION=900000
JWT_REFRESH_EXPIRATION=604800000

# S3/Object Storage
S3_ACCESS_KEY=your-access-key
S3_SECRET_KEY=your-secret-key
S3_BUCKET=moondance
S3_REGION=us-east-1
S3_ENDPOINT=
S3_URL_EXPIRATION=60

# Frontend
VITE_API_URL=http://localhost:8080/api/v1
```

Notes:

- For native backend execution (outside Docker), switch service hosts from `db`/`redis` to `localhost`.
- If using MinIO locally, set `S3_ENDPOINT` (for example `http://localhost:9000`).
- Vite variables are injected at build time. For Docker frontend builds, also create `frontend/.env` with `VITE_API_URL=http://localhost:8080/api/v1`.

## Run Locally (Docker)

1. Build and start all services:

```bash
docker compose up --build
```

2. Open:

- Frontend: `http://localhost:5173`
- Backend API: `http://localhost:8080/api/v1`
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- Health check: `http://localhost:8080/actuator/health`

3. Stop services:

```bash
docker compose down
```

## Run Locally (Native Dev)

This option is useful when developing frontend/backend code directly on your machine.

1. Start dependencies (Postgres + Redis):

```bash
docker compose up -d db redis
```

2. Run backend:

```bash
cd backend
set -a
source ../.env
set +a
./mvnw spring-boot:run
```

3. Run frontend (new terminal):

```bash
cd frontend
npm install
VITE_API_URL=http://localhost:8080/api/v1 npm run dev
```

## Testing and Quality Checks

Backend tests:

```bash
cd backend
./mvnw test
```

Frontend lint/build:

```bash
cd frontend
npm run lint
npm run build
```

## API and Data Notes

- OpenAPI spec: `http://localhost:8080/v3/api-docs`
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- Flyway migrations run on backend startup.
- Seed data includes Bergen Community College school/course context for local development.

## Contributing

Contributions from students, alumni, faculty, and open source collaborators are welcome.

1. Open an issue describing the bug, idea, or enhancement.
2. Create a feature branch from `main`.
3. Keep changes focused and include tests where applicable.
4. Run relevant checks before opening a pull request.
5. Submit a PR with a clear summary and rationale.

Please review `CODE_OF_CONDUCT.md` before participating.

## Security

If you identify a security issue, avoid public disclosure of exploit details. Report responsibly through project maintainers and use `SECURITY.md` once the reporting process is finalized.

## License

This project is licensed under the Apache License 2.0. See `LICENSE.md` for details.

## Student-Centered Open Source

Moon Dance is maintained as a student-centered platform and learning environment. BOSF aims to make this repository a practical place for Bergen Community College students to ship useful software, build public portfolios, and learn collaborative open source development.

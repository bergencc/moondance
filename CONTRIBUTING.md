# Contributing to Moon Dance

Thank you for your interest in contributing to Moon Dance.

This project is maintained by the Bergen Open Source Foundation (BOSF), a student-led foundation at Bergen Community College (BCC). We welcome contributions from students, alumni, faculty, and external open source collaborators.

## Our Goal

Moon Dance is both:

- A production-oriented platform for the BCC community.
- A student-centered learning environment for open source collaboration.

Contributions should improve the product while keeping the codebase understandable for future student contributors.

## Ways to Contribute

- Report bugs and usability issues.
- Propose new features or product improvements.
- Improve documentation.
- Submit code fixes and enhancements.
- Help review pull requests and test changes.

## Before You Start

1. Check open issues to avoid duplicate work.
2. For larger changes, open an issue first to align on scope.
3. Read `README.md` for local setup and project architecture.
4. Read `CODE_OF_CONDUCT.md` before participating.

## Development Setup

Use one of the setup paths documented in `README.md`:

- Docker workflow (`docker compose up --build`)
- Native workflow (backend + frontend run separately)

Core local commands:

```bash
# Backend tests
cd backend
./mvnw test

# Frontend lint + build
cd frontend
npm run lint
npm run build
```

## Branching and Commits

1. Create a branch from `main`.
2. Use clear branch names such as:
   `feature/course-search-improvements`
   `fix/upload-validation`
   `docs/setup-clarifications`
3. Keep commits focused and logically grouped.
4. Write commit messages in imperative style:
   `fix: validate note type before upload`
   `docs: clarify docker env configuration`

## Pull Request Guidelines

When opening a PR:

1. Explain what changed and why.
2. Link related issue(s), if applicable.
3. Include testing notes with exact commands you ran.
4. Add screenshots for UI changes.
5. Call out migrations, config changes, or breaking behavior explicitly.

Keep pull requests reasonably small. Large PRs are harder to review and maintain.

## Quality Expectations

Before requesting review:

1. Confirm the app runs locally.
2. Run backend tests: `./mvnw test` in `backend/`.
3. Run frontend lint/build: `npm run lint && npm run build` in `frontend/`.
4. Ensure new behavior has test coverage where practical.
5. Update documentation when behavior or setup changes.

## Review Process

- Maintainers review for correctness, scope, security, and maintainability.
- Feedback is collaborative and should be addressed in follow-up commits.
- A PR may be merged when it is reviewed and considered ready by maintainers.

## Student-Centered Collaboration Standards

- Prefer clarity over cleverness in implementation choices.
- Document non-obvious decisions so newer contributors can follow.
- Be constructive in review comments and open to iteration.
- If a change is educationally valuable but not merge-ready, preserve lessons in issue/PR discussion.

## Reporting Security Issues

Do not open public issues for sensitive security vulnerabilities. Follow the process in `SECURITY.md` (once finalized) or contact maintainers privately through BOSF channels.

## Code of Conduct

By participating, you agree to follow `CODE_OF_CONDUCT.md`.

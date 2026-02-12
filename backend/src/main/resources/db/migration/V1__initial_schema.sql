-- Schools table
CREATE TABLE schools (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    domain VARCHAR(100) NOT NULL UNIQUE,
    invite_code VARCHAR(50) UNIQUE,
    logo_url VARCHAR(500),
    city VARCHAR(100),
    state VARCHAR(100),
    country VARCHAR(100),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP
);

CREATE INDEX idx_schools_domain ON schools(domain);
CREATE INDEX idx_schools_invite_code ON schools(invite_code);

-- Users table
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    name VARCHAR(100) NOT NULL,
    major VARCHAR(100),
    graduation_year INTEGER,
    avatar_url VARCHAR(500),
    role VARCHAR(20) NOT NULL DEFAULT 'STUDENT',
    reputation_points INTEGER NOT NULL DEFAULT 0,
    email_verified BOOLEAN NOT NULL DEFAULT FALSE,
    verification_token VARCHAR(100),
    school_id BIGINT REFERENCES schools(id),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    banned_at TIMESTAMP,
    deleted_at TIMESTAMP
);

CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_school ON users(school_id);

-- Departments table
CREATE TABLE departments (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(20) NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    school_id BIGINT NOT NULL REFERENCES schools(id),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP,
    UNIQUE(code, school_id)
);

CREATE INDEX idx_departments_school ON departments(school_id);

-- Instructors table
CREATE TABLE instructors (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    title VARCHAR(100),
    department_id BIGINT NOT NULL REFERENCES departments(id),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP
);

CREATE INDEX idx_instructors_department ON instructors(department_id);

-- Courses table
CREATE TABLE courses (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(20) NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    credits INTEGER,
    department_id BIGINT NOT NULL REFERENCES departments(id),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP,
    UNIQUE(code, department_id)
);

CREATE INDEX idx_courses_department ON courses(department_id);
CREATE INDEX idx_courses_code ON courses(code);

-- Sessions table (semesters/terms)
CREATE TABLE sessions (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    type VARCHAR(20) NOT NULL,
    year INTEGER NOT NULL,
    start_date DATE,
    end_date DATE,
    school_id BIGINT NOT NULL REFERENCES schools(id),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_sessions_school ON sessions(school_id);
CREATE INDEX idx_sessions_year ON sessions(year);

-- Course sessions (links courses to sessions with instructors)
CREATE TABLE course_sessions (
    id BIGSERIAL PRIMARY KEY,
    course_id BIGINT NOT NULL REFERENCES courses(id),
    session_id BIGINT NOT NULL REFERENCES sessions(id),
    instructor_id BIGINT REFERENCES instructors(id),
    section VARCHAR(20),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_course_sessions_course ON course_sessions(course_id);
CREATE INDEX idx_course_sessions_session ON course_sessions(session_id);
CREATE INDEX idx_course_sessions_instructor ON course_sessions(instructor_id);

-- Tags table
CREATE TABLE tags (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    color VARCHAR(7),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_tags_name ON tags(name);

-- Notes table
CREATE TABLE notes (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    type VARCHAR(30) NOT NULL DEFAULT 'LECTURE_NOTES',
    file_key VARCHAR(500) NOT NULL,
    file_hash VARCHAR(64),
    file_size BIGINT NOT NULL,
    mime_type VARCHAR(100) NOT NULL,
    original_file_name VARCHAR(255),
    thumbnail_key VARCHAR(500),
    extracted_content TEXT,
    processing_status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    week_label VARCHAR(50),
    course_session_id BIGINT NOT NULL REFERENCES course_sessions(id),
    uploader_id BIGINT NOT NULL REFERENCES users(id),
    view_count INTEGER NOT NULL DEFAULT 0,
    download_count INTEGER NOT NULL DEFAULT 0,
    average_rating DOUBLE PRECISION NOT NULL DEFAULT 0,
    vote_count INTEGER NOT NULL DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP
);

CREATE INDEX idx_notes_course_session ON notes(course_session_id);
CREATE INDEX idx_notes_uploader ON notes(uploader_id);
CREATE INDEX idx_notes_type ON notes(type);
CREATE INDEX idx_notes_processing ON notes(processing_status);
CREATE INDEX idx_notes_created ON notes(created_at DESC);

-- Full-text search index for notes
CREATE INDEX idx_notes_search ON notes USING gin(
    to_tsvector('english', coalesce(title, '') || ' ' || coalesce(description, '') || ' ' || coalesce(extracted_content, ''))
);

-- Note tags junction table
CREATE TABLE note_tags (
    note_id BIGINT NOT NULL REFERENCES notes(id) ON DELETE CASCADE,
    tag_id BIGINT NOT NULL REFERENCES tags(id) ON DELETE CASCADE,
    PRIMARY KEY (note_id, tag_id)
);

-- Votes table
CREATE TABLE votes (
    id BIGSERIAL PRIMARY KEY,
    note_id BIGINT NOT NULL REFERENCES notes(id) ON DELETE CASCADE,
    user_id BIGINT NOT NULL REFERENCES users(id),
    value INTEGER NOT NULL,
    rating INTEGER,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(note_id, user_id)
);

CREATE INDEX idx_votes_note ON votes(note_id);
CREATE INDEX idx_votes_user ON votes(user_id);

-- Reports table
CREATE TABLE reports (
    id BIGSERIAL PRIMARY KEY,
    note_id BIGINT NOT NULL REFERENCES notes(id),
    reporter_id BIGINT NOT NULL REFERENCES users(id),
    reason VARCHAR(30) NOT NULL,
    description TEXT,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    reviewed_by BIGINT REFERENCES users(id),
    moderator_notes TEXT,
    reviewed_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_reports_note ON reports(note_id);
CREATE INDEX idx_reports_status ON reports(status);
CREATE INDEX idx_reports_reporter ON reports(reporter_id);

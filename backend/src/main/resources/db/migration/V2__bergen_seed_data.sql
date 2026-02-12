-- Insert school data for Bergen Community College
INSERT INTO schools (name, domain, invite_code, city, state, country) VALUES
    ('Bergen Community College', 'bergen.edu', 'BCC', 'Paramus', 'New Jersey', 'USA');

-- Insert departments for Bergen Community College (school_id = 1)
INSERT INTO departments (code, name, description, school_id) VALUES
    ('CS', 'Computer Science', 'Department of Computer Science', 1),
    ('MATH', 'Mathematics', 'Department of Mathematics', 1),
    ('PHYS', 'Physical Science', 'Includes chemistry, physics, and general science programs', 1),
    ('BIO', 'Biology', 'Department of Biology', 1),
    ('BUS', 'Business', 'Business Department', 1),
    ('COM', 'Communication', 'Communication Department', 1),
    ('JUS', 'Criminal Justice', 'Criminal Justice and Legal Studies', 1),
    ('EDU', 'Education', 'Education Department', 1),
    ('HEA', 'Health', 'Health Professions Division', 1),
    ('ENG', 'English', 'Department of English and Literature', 1);

-- Insert instructors
-- TODO needs update
INSERT INTO instructors (name, email, title, department_id) VALUES
    ('Dr. Alan Turing', 'turing@bergen.edu', 'Professor', 1),
    ('Dr. Ada Lovelace', 'lovelace@bergen.edu', 'Associate Professor', 1),
    ('Dr. John Nash', 'nash@bergen.edu', 'Professor', 2),
    ('Dr. Richard Feynman', 'feynman@bergen.edu', 'Professor', 3),
    ('Dr. Paul Krugman', 'krugman@bergen.edu', 'Professor', 4),
    ('Dr. Grace Hopper', 'hopper@bergen.edu', 'Professor', 7);

-- Insert courses
-- TODO needs update
INSERT INTO courses (code, title, description, credits, department_id) VALUES
-- CS courses
('CS101', 'Introduction to Computer Science', 'Fundamental concepts of programming and computer science', 4, 1),
('CS201', 'Data Structures', 'Arrays, linked lists, trees, graphs, and algorithms', 4, 1),
('CS301', 'Algorithms', 'Algorithm design and analysis', 4, 1),
('CS401', 'Operating Systems', 'Process management, memory, file systems', 4, 1),
('CS450', 'Machine Learning', 'Introduction to machine learning algorithms', 3, 1),
-- Math courses
('MATH101', 'Calculus I', 'Limits, derivatives, and integrals', 4, 2),
('MATH201', 'Calculus II', 'Advanced integration and series', 4, 2),
('MATH301', 'Linear Algebra', 'Vectors, matrices, and linear transformations', 3, 2),
-- Physics courses
('PHYS101', 'Physics I', 'Mechanics and thermodynamics', 4, 3),
('PHYS201', 'Physics II', 'Electricity and magnetism', 4, 3),
-- Economics courses
('ECON101', 'Microeconomics', 'Introduction to microeconomic theory', 3, 4),
('ECON201', 'Macroeconomics', 'Introduction to macroeconomic theory', 3, 4),
-- Business courses
('BUS101', 'Introduction to Business', 'Overview of business principles', 3, 5),
('BUS301', 'Marketing', 'Marketing strategies and consumer behavior', 3, 5);

-- Insert sessions
INSERT INTO sessions (name, type, year, start_date, end_date, school_id) VALUES
    ('Spring 2026', 'SPRING', 2026, '2026-01-14', '2026-05-15', 1);

-- Insert course sessions
-- TODO needs update
INSERT INTO course_sessions (course_id, session_id, instructor_id, section) VALUES
-- Spring 2026
(1, 1, 1, '001'),  -- CS101 with Turing
(1, 1, 2, '002'),  -- CS101 with Lovelace
(2, 1, 2, '001'),  -- CS201 with Lovelace
(6, 1, 3, '001'),  -- MATH101 with Nash
(9, 1, 4, '001'),  -- PHYS101 with Feynman
(11, 1, 5, '001'); -- ECON101 with Krugman


-- Insert tags
INSERT INTO tags (name, color) VALUES
    ('midterm', '#FF6B6B'),
    ('final', '#4ECDC4'),
    ('lecture', '#45B7D1'),
    ('cheat-sheet', '#96CEB4'),
    ('summary', '#FFEAA7'),
    ('exam-prep', '#DDA0DD'),
    ('notes', '#98D8C8'),
    ('formula-sheet', '#F7DC6F'),
    ('study-guide', '#BB8FCE'),
    ('practice-problems', '#85C1E9');
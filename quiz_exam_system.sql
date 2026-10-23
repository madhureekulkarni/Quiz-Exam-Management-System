CREATE DATABASE IF NOT EXISTS quiz_exam_system;
USE quiz_exam_system;

CREATE TABLE IF NOT EXISTS admin (
    admin_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS students (
    student_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS questions (
    question_id INT PRIMARY KEY AUTO_INCREMENT,
    question_text VARCHAR(500) NOT NULL,
    option_a VARCHAR(255) NOT NULL,
    option_b VARCHAR(255) NOT NULL,
    option_c VARCHAR(255) NOT NULL,
    option_d VARCHAR(255) NOT NULL,
    correct_option CHAR(1) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT chk_correct_option CHECK (correct_option IN ('A', 'B', 'C', 'D'))
);

CREATE TABLE IF NOT EXISTS results (
    result_id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT NOT NULL,
    score INT NOT NULL,
    total_questions INT NOT NULL,
    attempted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_results_student FOREIGN KEY (student_id)
        REFERENCES students(student_id)
        ON DELETE CASCADE
);

INSERT IGNORE INTO admin (username, password)
VALUES ('admin', 'admin123');

INSERT INTO questions
(question_text, option_a, option_b, option_c, option_d, correct_option)
SELECT 'Which keyword is used to inherit a class in Java?',
       'implements', 'extends', 'import', 'package', 'B'
WHERE NOT EXISTS (
    SELECT 1 FROM questions WHERE question_text = 'Which keyword is used to inherit a class in Java?'
);

INSERT INTO questions
(question_text, option_a, option_b, option_c, option_d, correct_option)
SELECT 'Which method is the entry point of a standard Java application?',
       'start()', 'run()', 'main()', 'init()', 'C'
WHERE NOT EXISTS (
    SELECT 1 FROM questions WHERE question_text = 'Which method is the entry point of a standard Java application?'
);

INSERT INTO questions
(question_text, option_a, option_b, option_c, option_d, correct_option)
SELECT 'Which SQL command is used to retrieve records?',
       'GET', 'OPEN', 'SELECT', 'FETCH ALL', 'C'
WHERE NOT EXISTS (
    SELECT 1 FROM questions WHERE question_text = 'Which SQL command is used to retrieve records?'
);

INSERT INTO questions
(question_text, option_a, option_b, option_c, option_d, correct_option)
SELECT 'Which JDBC interface is commonly used to execute a parameterized SQL statement?',
       'PreparedStatement', 'FileReader', 'Scanner', 'StringBuilder', 'A'
WHERE NOT EXISTS (
    SELECT 1 FROM questions WHERE question_text = 'Which JDBC interface is commonly used to execute a parameterized SQL statement?'
);

INSERT INTO questions
(question_text, option_a, option_b, option_c, option_d, correct_option)
SELECT 'Which Java keyword is used to create an object?',
       'class', 'new', 'this', 'void', 'B'
WHERE NOT EXISTS (
    SELECT 1 FROM questions WHERE question_text = 'Which Java keyword is used to create an object?'
);

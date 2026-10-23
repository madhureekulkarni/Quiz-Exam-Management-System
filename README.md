# Quiz and Exam Management System

A beginner-friendly console mini project built using **Core Java, JDBC, and MySQL only**. It does not use Spring Boot, Swing, web frameworks, or any other application framework.

## Features

### Student
- Enter a name and take a multiple-choice quiz.
- Answers are checked automatically.
- Score and attempt date are saved in MySQL.
- View previous quiz results by student name.

### Admin
- Sign in with the initial demo credentials.
- Add, list, update, and delete questions.
- View saved quiz results.

## Requirements
- JDK 8 or later
- MySQL Server 8.x (or a compatible MySQL server)
- MySQL Connector/J JDBC driver (`mysql-connector-j` JAR)
- A terminal/command prompt

> This ZIP contains source code and the SQL setup script. The program has not been run against your local MySQL server, so update the connection settings and test it after you regain access to a development environment.

## 1. Create the database
1. Open MySQL Workbench or the MySQL command-line client.
2. Run the full contents of `database/quiz_exam_system.sql`.
3. The script creates the `quiz_exam_system` database, tables, and sample questions.

The script creates a demo admin:
- Username: `admin`
- Password: `admin123`

Change these credentials before using the project beyond a demo. This beginner project stores the demo password as plain text and is not intended for production use.

## 2. Configure database connection
Open `src/DBConnection.java` and update:
- `URL` if your MySQL host/port differs
- `USER` with your MySQL username
- `PASSWORD` with your MySQL password

Do not upload real database passwords to a public repository.

## 3. Add the JDBC driver
Download MySQL Connector/J from the official MySQL website when you have internet access:
https://dev.mysql.com/downloads/connector/j/

Place the JAR in a folder named `lib/` in the project root.

Expected layout:
```text
QuizExamSystem/
├── database/
│   └── quiz_exam_system.sql
├── lib/
│   └── mysql-connector-j-<version>.jar
├── src/
│   ├── DBConnection.java
│   ├── Question.java
│   ├── QuestionDAO.java
│   ├── StudentDAO.java
│   ├── ResultDAO.java
│   └── Main.java
└── README.md
```

## 4. Compile and run

### Windows (Command Prompt)
From the project root, after adding the driver JAR:

```bat
javac -cp "lib\mysql-connector-j-<version>.jar" -d out src\*.java
java -cp "out;lib\mysql-connector-j-<version>.jar" Main
```

Replace `<version>` with the actual JAR version. In Windows classpaths, use `;` between entries.

### macOS / Linux
```bash
javac -cp "lib/mysql-connector-j-<version>.jar" -d out src/*.java
java -cp "out:lib/mysql-connector-j-<version>.jar" Main
```

Replace `<version>` with the actual JAR version. On macOS/Linux, use `:` between classpath entries.

## 5. Menu
- Admin Login: maintain quiz questions and inspect results.
- Student Quiz: enter a name, answer questions, and save the result.
- View My Results: see saved attempts for a student name.
- Exit: close the application.

## Troubleshooting
- `No suitable driver`: check that the Connector/J JAR is included in the classpath.
- `Access denied`: check the MySQL username/password in `DBConnection.java`.
- `Unknown database`: run `database/quiz_exam_system.sql`.
- `Communications link failure`: ensure MySQL Server is running and the URL/port is correct.

## Limitations
- Console interface only.
- Basic demo admin authentication.
- Questions are selected from the question table; the quiz uses all active questions.
- This is a learning project, not a production examination platform.

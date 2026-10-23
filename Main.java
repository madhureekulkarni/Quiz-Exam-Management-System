import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final QuestionDAO QUESTION_DAO = new QuestionDAO();
    private static final StudentDAO STUDENT_DAO = new StudentDAO();
    private static final ResultDAO RESULT_DAO = new ResultDAO();

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("  QUIZ AND EXAM MANAGEMENT SYSTEM");
        System.out.println("========================================");

        boolean running = true;
        while (running) {
            System.out.println("\n1. Admin Login");
            System.out.println("2. Student Quiz");
            System.out.println("3. View My Results");
            System.out.println("4. Exit");
            int choice = readInt("Enter your choice: ");

            try {
                switch (choice) {
                    case 1:
                        adminLogin();
                        break;
                    case 2:
                        takeQuiz();
                        break;
                    case 3:
                        viewMyResults();
                        break;
                    case 4:
                        running = false;
                        System.out.println("Thank you for using the system.");
                        break;
                    default:
                        System.out.println("Please choose an option from 1 to 4.");
                }
            } catch (SQLException ex) {
                System.out.println("Database error: " + ex.getMessage());
                System.out.println("Check MySQL is running and DBConnection.java settings are correct.");
            }
        }
        SCANNER.close();
    }

    private static void adminLogin() throws SQLException {
        System.out.println("\n===== ADMIN LOGIN =====");
        System.out.print("Username: ");
        String username = SCANNER.nextLine().trim();
        System.out.print("Password: ");
        String password = SCANNER.nextLine();

        String sql = "SELECT admin_id FROM admin WHERE username = ? AND password = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, password);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Login successful.");
                    adminMenu();
                } else {
                    System.out.println("Invalid username or password.");
                }
            }
        }
    }

    private static void adminMenu() throws SQLException {
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println("\n===== ADMIN MENU =====");
            System.out.println("1. Add Question");
            System.out.println("2. View Questions");
            System.out.println("3. Update Question");
            System.out.println("4. Delete Question");
            System.out.println("5. View All Results");
            System.out.println("6. Logout");
            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    addQuestion();
                    break;
                case 2:
                    listQuestions();
                    break;
                case 3:
                    updateQuestion();
                    break;
                case 4:
                    deleteQuestion();
                    break;
                case 5:
                    RESULT_DAO.showAllResults();
                    break;
                case 6:
                    loggedIn = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void addQuestion() throws SQLException {
        Question question = readQuestionDetails(0);
        if (QUESTION_DAO.addQuestion(question)) {
            System.out.println("Question added successfully.");
        }
    }

    private static void listQuestions() throws SQLException {
        List<Question> questions = QUESTION_DAO.getAllQuestions();
        if (questions.isEmpty()) {
            System.out.println("No questions found.");
            return;
        }
        for (Question question : questions) {
            System.out.println("\nID: " + question.getId());
            question.display(question.getId());
            System.out.println("Correct option: " + question.getCorrectOption());
        }
    }

    private static void updateQuestion() throws SQLException {
        listQuestions();
        int id = readInt("\nEnter the question ID to update: ");
        Question question = readQuestionDetails(id);
        if (QUESTION_DAO.updateQuestion(question)) {
            System.out.println("Question updated successfully.");
        } else {
            System.out.println("No question found with that ID.");
        }
    }

    private static void deleteQuestion() throws SQLException {
        listQuestions();
        int id = readInt("\nEnter the question ID to delete: ");
        if (QUESTION_DAO.deleteQuestion(id)) {
            System.out.println("Question deleted successfully.");
        } else {
            System.out.println("No question found with that ID.");
        }
    }

    private static Question readQuestionDetails(int id) {
        System.out.print("Question text: ");
        String text = SCANNER.nextLine().trim();
        System.out.print("Option A: ");
        String a = SCANNER.nextLine().trim();
        System.out.print("Option B: ");
        String b = SCANNER.nextLine().trim();
        System.out.print("Option C: ");
        String c = SCANNER.nextLine().trim();
        System.out.print("Option D: ");
        String d = SCANNER.nextLine().trim();
        String correct;
        while (true) {
            System.out.print("Correct option (A/B/C/D): ");
            correct = SCANNER.nextLine().trim().toUpperCase();
            if (correct.matches("[ABCD]")) {
                break;
            }
            System.out.println("Enter A, B, C, or D.");
        }
        return new Question(id, text, a, b, c, d, correct);
    }

    private static void takeQuiz() throws SQLException {
        System.out.print("Enter your name: ");
        String name = SCANNER.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }

        List<Question> questions = QUESTION_DAO.getActiveQuestions();
        if (questions.isEmpty()) {
            System.out.println("No quiz questions are available yet.");
            return;
        }

        int score = 0;
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            question.display(i + 1);
            String answer;
            while (true) {
                System.out.print("Your answer (A/B/C/D): ");
                answer = SCANNER.nextLine().trim().toUpperCase();
                if (answer.matches("[ABCD]")) {
                    break;
                }
                System.out.println("Please enter A, B, C, or D.");
            }
            if (answer.equals(question.getCorrectOption())) {
                score++;
            }
        }

        int studentId = STUDENT_DAO.findOrCreateStudent(name);
        RESULT_DAO.saveResult(studentId, score, questions.size());
        System.out.println("\n===== QUIZ RESULT =====");
        System.out.println("Student: " + name);
        System.out.println("Score: " + score + " out of " + questions.size());
        System.out.printf("Percentage: %.2f%%%n", (score * 100.0) / questions.size());
        System.out.println("Your result has been saved.");
    }

    private static void viewMyResults() throws SQLException {
        System.out.print("Enter your name: ");
        String name = SCANNER.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }
        RESULT_DAO.showResultsForStudentName(name);
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = SCANNER.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}

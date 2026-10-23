import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultDAO {
    public void saveResult(int studentId, int score, int total) throws SQLException {
        String sql = "INSERT INTO results (student_id, score, total_questions) VALUES (?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, studentId);
            statement.setInt(2, score);
            statement.setInt(3, total);
            statement.executeUpdate();
        }
    }

    public void showResultsForStudentName(String name) throws SQLException {
        String sql = "SELECT s.name, r.score, r.total_questions, r.attempted_at " +
                "FROM results r JOIN students s ON r.student_id = s.student_id " +
                "WHERE s.name = ? ORDER BY r.attempted_at DESC";
        boolean found = false;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    found = true;
                    System.out.println("Student: " + rs.getString("name")
                            + " | Score: " + rs.getInt("score") + "/"
                            + rs.getInt("total_questions")
                            + " | Date: " + rs.getTimestamp("attempted_at"));
                }
            }
        }
        if (!found) {
            System.out.println("No saved results found for that name.");
        }
    }

    public void showAllResults() throws SQLException {
        String sql = "SELECT s.name, r.score, r.total_questions, r.attempted_at " +
                "FROM results r JOIN students s ON r.student_id = s.student_id " +
                "ORDER BY r.attempted_at DESC";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            System.out.println("\n===== ALL QUIZ RESULTS =====");
            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.println("Student: " + rs.getString("name")
                        + " | Score: " + rs.getInt("score") + "/"
                        + rs.getInt("total_questions")
                        + " | Date: " + rs.getTimestamp("attempted_at"));
            }
            if (!found) {
                System.out.println("No results have been recorded yet.");
            }
        }
    }
}

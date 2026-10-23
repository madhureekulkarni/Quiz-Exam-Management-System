import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAO {
    public List<Question> getActiveQuestions() throws SQLException {
        List<Question> questions = new ArrayList<Question>();
        String sql = "SELECT question_id, question_text, option_a, option_b, " +
                "option_c, option_d, correct_option FROM questions " +
                "WHERE active = TRUE ORDER BY question_id";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                questions.add(mapQuestion(rs));
            }
        }
        return questions;
    }

    public List<Question> getAllQuestions() throws SQLException {
        List<Question> questions = new ArrayList<Question>();
        String sql = "SELECT question_id, question_text, option_a, option_b, " +
                "option_c, option_d, correct_option FROM questions ORDER BY question_id";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                questions.add(mapQuestion(rs));
            }
        }
        return questions;
    }

    private Question mapQuestion(ResultSet rs) throws SQLException {
        return new Question(
                rs.getInt("question_id"),
                rs.getString("question_text"),
                rs.getString("option_a"),
                rs.getString("option_b"),
                rs.getString("option_c"),
                rs.getString("option_d"),
                rs.getString("correct_option")
        );
    }

    public boolean addQuestion(Question question) throws SQLException {
        String sql = "INSERT INTO questions (question_text, option_a, option_b, " +
                "option_c, option_d, correct_option) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            fillStatement(statement, question);
            return statement.executeUpdate() == 1;
        }
    }

    public boolean updateQuestion(Question question) throws SQLException {
        String sql = "UPDATE questions SET question_text = ?, option_a = ?, " +
                "option_b = ?, option_c = ?, option_d = ?, correct_option = ? " +
                "WHERE question_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            fillStatement(statement, question);
            statement.setInt(7, question.getId());
            return statement.executeUpdate() == 1;
        }
    }

    private void fillStatement(PreparedStatement statement, Question question)
            throws SQLException {
        statement.setString(1, question.getText());
        statement.setString(2, question.getOptionA());
        statement.setString(3, question.getOptionB());
        statement.setString(4, question.getOptionC());
        statement.setString(5, question.getOptionD());
        statement.setString(6, question.getCorrectOption());
    }

    public boolean deleteQuestion(int id) throws SQLException {
        String sql = "DELETE FROM questions WHERE question_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            return statement.executeUpdate() == 1;
        }
    }
}

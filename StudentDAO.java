import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentDAO {
    public int findOrCreateStudent(String name) throws SQLException {
        String findSql = "SELECT student_id FROM students WHERE name = ? ORDER BY student_id LIMIT 1";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement find = connection.prepareStatement(findSql)) {
            find.setString(1, name);
            try (ResultSet rs = find.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("student_id");
                }
            }

            String insertSql = "INSERT INTO students (name) VALUES (?)";
            try (PreparedStatement insert = connection.prepareStatement(
                    insertSql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                insert.setString(1, name);
                insert.executeUpdate();
                try (ResultSet keys = insert.getGeneratedKeys()) {
                    if (keys.next()) {
                        return keys.getInt(1);
                    }
                }
            }
        }
        throw new SQLException("Could not create or find student record.");
    }
}

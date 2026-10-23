import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL =
            "jdbc:mysql://localhost:3306/quiz_exam_system";
    private static final String USER = "root";
    private static final String PASSWORD = "your_mysql_password";

    private DBConnection() {
        // Utility class; do not create instances.
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

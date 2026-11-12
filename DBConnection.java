
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/library_management";

    private static final String USER = "root";

    private static final String PASSWORD = "LahaChethu@2007";

    public static Connection getConnection() {

        Connection con = null;

        try {
            con = DriverManager.getConnection(URL, USER, PASSWORD);

            System.out.println("Database Connected Successfully!");

        } catch (SQLException e) {

            System.out.println("Database Connection Failed!");
            e.printStackTrace();

        }

        return con;
    }

    public static void main(String[] args) {

        Connection con = getConnection();

        try {
            if (con != null) {
                con.close();
                System.out.println("Connection Closed Successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

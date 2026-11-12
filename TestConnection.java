import java.sql.*;

public class TestConnection {
    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/jdbc_practice";
        String username = "root";
        String password = "MY_PASSWORD";

        try {
            Connection con = DriverManager.getConnection(
                url, username, password
            );

            System.out.println("Database Connected Successfully!");

            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

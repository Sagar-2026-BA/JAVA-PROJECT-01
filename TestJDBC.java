
public class TestJDBC {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("JDBC Driver Loaded Successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

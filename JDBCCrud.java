
import java.sql.*;
import java.util.Scanner;

public class JDBCCrud {

    static final String URL =
        "jdbc:mysql://localhost:3306/jdbc_practice";

    static final String USER = "root";
    static final String PASSWORD = "MY_PASSWORD";

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        try (Connection con =
                DriverManager.getConnection(URL, USER, PASSWORD)) {

            System.out.println("Database Connected Successfully!");

            while (true) {

                System.out.println("\n===== STUDENT MANAGEMENT SYSTEM =====");
                System.out.println("1. Add Student");
                System.out.println("2. Display Students");
                System.out.println("3. Update Student");
                System.out.println("4. Delete Student");
                System.out.println("5. Exit");

                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {

                    // CREATE
                    case 1:

                        System.out.print("Enter student name: ");
                        String name = sc.nextLine();

                        System.out.print("Enter age: ");
                        int age = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Enter course: ");
                        String course = sc.nextLine();

                        String insert =
                            "INSERT INTO students(name, age, course) VALUES (?, ?, ?)";

                        try (PreparedStatement ps =
                                con.prepareStatement(insert)) {

                            ps.setString(1, name);
                            ps.setInt(2, age);
                            ps.setString(3, course);

                            ps.executeUpdate();

                            System.out.println(
                                "Student Added Successfully!");
                        }
                        break;

                    // READ
                    case 2:

                        String select = "SELECT * FROM students";

                        try (Statement st = con.createStatement();
                             ResultSet rs = st.executeQuery(select)) {

                            System.out.println("\nID\tNAME\tAGE\tCOURSE");

                            while (rs.next()) {

                                System.out.println(
                                    rs.getInt("id") + "\t" +
                                    rs.getString("name") + "\t" +
                                    rs.getInt("age") + "\t" +
                                    rs.getString("course")
                                );
                            }
                        }
                        break;

                    // UPDATE
                    case 3:

                        System.out.print("Enter student ID: ");
                        int updateId = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Enter new name: ");
                        String newName = sc.nextLine();

                        System.out.print("Enter new age: ");
                        int newAge = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Enter new course: ");
                        String newCourse = sc.nextLine();

                        String update =
                            "UPDATE students SET name=?, age=?, course=? WHERE id=?";

                        try (PreparedStatement ps =
                                con.prepareStatement(update)) {

                            ps.setString(1, newName);
                            ps.setInt(2, newAge);
                            ps.setString(3, newCourse);
                            ps.setInt(4, updateId);

                            int rows = ps.executeUpdate();

                            if (rows > 0) {
                                System.out.println(
                                    "Student Updated Successfully!");
                            } else {
                                System.out.println(
                                    "Student ID Not Found!");
                            }
                        }
                        break;

                    // DELETE
                    case 4:

                        System.out.print("Enter student ID: ");
                        int deleteId = sc.nextInt();

                        String delete =
                            "DELETE FROM students WHERE id=?";

                        try (PreparedStatement ps =
                                con.prepareStatement(delete)) {

                            ps.setInt(1, deleteId);

                            int rows = ps.executeUpdate();

                            if (rows > 0) {
                                System.out.println(
                                    "Student Deleted Successfully!");
                            } else {
                                System.out.println(
                                    "Student ID Not Found!");
                            }
                        }
                        break;

                    // EXIT
                    case 5:

                        System.out.println("Thank You!");
                        sc.close();
                        return;

                    default:
                        System.out.println("Invalid Choice!");
                }
            }

        } catch (SQLException e) {

            System.out.println("Database Error!");
            e.printStackTrace();

        }
    }
}

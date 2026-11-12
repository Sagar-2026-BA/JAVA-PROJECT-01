
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IssueDAO {

    // 1. Issue a Book
    public void issueBook(int bookId, int memberId,
                          Date issueDate, Date dueDate) {

        String checkBook = "SELECT available_copies FROM books "
                         + "WHERE book_id = ?";

        String checkMember = "SELECT member_id FROM members "
                           + "WHERE member_id = ?";

        String issueSQL = "INSERT INTO issued_books "
                        + "(book_id, member_id, issue_date, due_date) "
                        + "VALUES (?, ?, ?, ?)";

        String updateSQL = "UPDATE books "
                         + "SET available_copies = available_copies - 1 "
                         + "WHERE book_id = ?";

        try (Connection con = DBConnection.getConnection()) {

            if (con == null) {
                return;
            }

            con.setAutoCommit(false);

            try {
                // Check book availability
                try (PreparedStatement ps = con.prepareStatement(checkBook)) {

                    ps.setInt(1, bookId);

                    try (ResultSet rs = ps.executeQuery()) {

                        if (!rs.next() || rs.getInt("available_copies") <= 0) {
                            System.out.println("Book Not Available!");
                            con.rollback();
                            return;
                        }
                    }
                }

                // Check member existence
                try (PreparedStatement ps = con.prepareStatement(checkMember)) {

                    ps.setInt(1, memberId);

                    try (ResultSet rs = ps.executeQuery()) {

                        if (!rs.next()) {
                            System.out.println("Member Not Found!");
                            con.rollback();
                            return;
                        }
                    }
                }

                // Insert issue record
                try (PreparedStatement ps = con.prepareStatement(issueSQL)) {

                    ps.setInt(1, bookId);
                    ps.setInt(2, memberId);
                    ps.setDate(3, issueDate);
                    ps.setDate(4, dueDate);

                    ps.executeUpdate();
                }

                // Reduce available copies
                try (PreparedStatement ps = con.prepareStatement(updateSQL)) {

                    ps.setInt(1, bookId);
                    ps.executeUpdate();
                }

                con.commit();

                System.out.println("Book Issued Successfully!");

            } catch (SQLException e) {
                con.rollback();
                System.out.println("Book Issue Failed!");
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 2. Display Issued Books
    public void displayIssuedBooks() {

        String sql = "SELECT i.issue_id, b.title, m.name, "
                   + "i.issue_date, i.due_date, i.return_date "
                   + "FROM issued_books i "
                   + "JOIN books b ON i.book_id = b.book_id "
                   + "JOIN members m ON i.member_id = m.member_id";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\nISSUED BOOK DETAILS");

            while (rs.next()) {

                System.out.println(
                    "Issue ID: " + rs.getInt("issue_id")
                    + " | Book: " + rs.getString("title")
                    + " | Member: " + rs.getString("name")
                    + " | Issue Date: " + rs.getDate("issue_date")
                    + " | Due Date: " + rs.getDate("due_date")
                    + " | Return Date: " + rs.getDate("return_date")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 3. Return a Book
    public void returnBook(int issueId) {

        String findBook = "SELECT book_id FROM issued_books "
                        + "WHERE issue_id = ? AND return_date IS NULL";

        String returnSQL = "UPDATE issued_books "
                         + "SET return_date = CURDATE() "
                         + "WHERE issue_id = ? AND return_date IS NULL";

        String updateBook = "UPDATE books "
                          + "SET available_copies = available_copies + 1 "
                          + "WHERE book_id = ?";

        try (Connection con = DBConnection.getConnection()) {

            if (con == null) {
                return;
            }

            con.setAutoCommit(false);

            try {
                int bookId;

                // Find active issue record
                try (PreparedStatement ps = con.prepareStatement(findBook)) {

                    ps.setInt(1, issueId);

                    try (ResultSet rs = ps.executeQuery()) {

                        if (!rs.next()) {
                            System.out.println(
                                "Active Issue Record Not Found!"
                            );
                            con.rollback();
                            return;
                        }

                        bookId = rs.getInt("book_id");
                    }
                }

                // Update return date
                try (PreparedStatement ps = con.prepareStatement(returnSQL)) {

                    ps.setInt(1, issueId);

                    if (ps.executeUpdate() == 0) {
                        con.rollback();
                        System.out.println("Return Failed!");
                        return;
                    }
                }

                // Increase available copies
                try (PreparedStatement ps = con.prepareStatement(updateBook)) {

                    ps.setInt(1, bookId);
                    ps.executeUpdate();
                }

                con.commit();

                System.out.println("Book Returned Successfully!");

            } catch (SQLException e) {
                con.rollback();
                System.out.println("Book Return Failed!");
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

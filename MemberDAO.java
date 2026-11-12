
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberDAO {

    // 1. Add Member
    public void addMember(String name, String phone, String email) {

        String sql = "INSERT INTO members (name, phone, email) VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, phone);
            ps.setString(3, email);

            ps.executeUpdate();

            System.out.println("Member Registered Successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 2. Display All Members
    public void displayMembers() {

        String sql = "SELECT * FROM members";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\nMEMBER DETAILS");
            System.out.println("---------------------------------------------");

            System.out.printf("%-8s %-20s %-18s %-25s%n",
                    "ID", "NAME", "PHONE", "EMAIL");

            while (rs.next()) {

                System.out.printf("%-8d %-20s %-18s %-25s%n",
                        rs.getInt("member_id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("email"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 3. Update Member
    public void updateMember(int memberId, String name,
                             String phone, String email) {

        String sql = "UPDATE members SET name = ?, phone = ?, email = ? "
                   + "WHERE member_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, phone);
            ps.setString(3, email);
            ps.setInt(4, memberId);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Member Updated Successfully!");
            } else {
                System.out.println("Member Not Found!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 4. Delete Member
    public void deleteMember(int memberId) {

        String sql = "DELETE FROM members WHERE member_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, memberId);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Member Deleted Successfully!");
            } else {
                System.out.println("Member Not Found!");
            }

        } catch (SQLException e) {
            System.out.println(
                "Could not delete member. Check whether they have issue records."
            );
            e.printStackTrace();
        }
    }
}

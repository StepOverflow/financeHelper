import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.apache.commons.codec.digest.DigestUtils.md5Hex;

class SignUp {
    public static void doSignUp(String newEmail, String newPassword, Connection connection) {
        String passwordHex = md5Hex(newPassword);

        try (PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO users (password, email) VALUES (?, ?)")) {
            ps.setString(1, passwordHex);
            ps.setString(2, newEmail);
            ps.executeUpdate();

            System.out.println("User registered successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

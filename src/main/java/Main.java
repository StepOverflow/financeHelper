import java.sql.*;

import static org.apache.commons.codec.digest.DigestUtils.md5Hex;


public class Main {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5433/postgres";
        String user = "postgres";
        String passwordSql = "Pattaya2023";

        String email = "user1@example.com";
        String password = md5Hex("password1");


        try (Connection conn = DriverManager.getConnection(url, user, passwordSql)) {

            Account account = Account.createAccount(conn, email, password);
            if (account != null) {
                account.getAccounts();
            } else System.out.println("wrong password or email");


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

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


class Authorization {
    public static boolean doAuthorization(String email, String password, Connection connection) {

        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM users WHERE email = ? AND password = ?")) {

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            return rs.next();


        } catch (
                SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}

class LogIn {
    public static void doLogIn(String email, String password, Connection connection) {
        if (Authorization.doAuthorization(email, password, connection)) {
            System.out.println("Hello " + email + " !");
        } else System.out.println("Access denied!");

    }
}

class Account {
    private final String email;
    Connection connection;


    private Account(Connection connection, String email) {
        this.email = email;
        this.connection = connection;

    }

    static Account createAccount(Connection connection, String email, String password) throws SQLException {


        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT * FROM users WHERE email = ? AND password = ?")) {

            ps.setString(1, email);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Account(connection, email);
                } else {
                    return null;
                }
            }
        }


    }

    public void getAccounts() throws SQLException {


        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT * FROM accounts JOIN users " +
                        " ON accounts.user_id = users.id" +
                        " WHERE accounts.user_id = ?")) {
            ps.setInt(1, getId());

            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    long accountId = resultSet.getLong("id");
                    long userId = resultSet.getLong("user_id");
                    String accountName = resultSet.getString("account_name");
                    long balance = resultSet.getLong("balance");

                    System.out.println("[account id - " + accountId + ", " + "user id - " + userId + ", " +
                            "account name - " + accountName + ", " +
                            "balance: " + balance + "]");


                }
            }
        }

    }

    public void createAccount(String newAccountName) throws SQLException {


        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO accounts (account_name, user_id, balance) VALUES (?, ?, ?)")) {
            ps.setString(1, newAccountName);
            ps.setLong(2, getId());
            ps.setLong(3, 0);
            ps.executeUpdate();

            System.out.println("Account created successfully!");
        }

    }


    public void createAccount(String newAccountName, int balance) throws SQLException {

        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO accounts (account_name, user_id, balance) VALUES (?, ?, ?)")) {
            ps.setString(1, newAccountName);
            ps.setLong(2, getId());
            ps.setLong(3, balance);
            ps.executeUpdate();

            System.out.println("Account created successfully!");
        }

    }


    private int getId() throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("SELECT id FROM  users WHERE email = ?")) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                int id = -1;
                while (rs.next()) {
                    id = rs.getInt("id");
                }
                return id;
            }
        }
    }

    public void deleteAccount(int accountId) throws SQLException {

        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM accounts WHERE id = ?")) {
            statement.setInt(1, accountId);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Account with id " + accountId + " was deleted successfully.");
            } else {
                System.out.println("Account with id " + accountId + " was not found.");
            }
        }

    }


}







import java.sql.*;
import java.util.Scanner;

import static org.apache.commons.codec.digest.DigestUtils.md5Hex;


public class Main {
    private static final String url = "jdbc:postgresql://localhost:5433/postgres";
    private static final String user = "postgres";
    private static final String passwordSql = "Pattaya2023";

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in);
             Connection conn = DriverManager.getConnection(url, user, passwordSql)) {

            boolean exit = false;
            while (!exit) {
                System.out.println("Select an action:");
                System.out.println("1. Log In");
                System.out.println("2. Sign Up");
                System.out.println("0. Exit");

                int choice = scanner.nextInt();
                switch (choice) {
                    case 1 -> {
                        System.out.println("Enter email:");
                        String email = scanner.next();
                        System.out.println("Enter password:");
                        String password = md5Hex(scanner.next());
                        if (Authorization.doAuthorization(email, password, conn)) {
                            Account account = Account.createAccount(conn, email, password);
                            System.out.println("Log in successful!");

                            boolean exit2 = false;
                            while (!exit2) {
                                System.out.println("Select an action:");
                                System.out.println("1. View all accounts");
                                System.out.println("2. Create an account");
                                System.out.println("3. Delete an account");
                                System.out.println("0. Log out");

                                int choice2 = scanner.nextInt();
                                switch (choice2) {
                                    case 1 -> {
                                        if (account != null) {
                                            account.getAccounts();
                                        }
                                    }
                                    case 2 -> {
                                        System.out.println("Enter account name:");
                                        String accountName = scanner.next();
                                        if (account != null) {
                                            account.createAccount(accountName);
                                        }
                                    }
                                    case 3 -> {
                                        System.out.println("Enter account ID to delete:");
                                        int accountId = scanner.nextInt();
                                        if (account != null) {
                                            account.deleteAccount(accountId);
                                        }
                                    }
                                    case 0 -> exit2 = true;
                                    default -> System.out.println("Invalid choice!");
                                }
                            }
                        } else {
                            System.out.println("Log in failed!");
                        }
                    }
                    case 2 -> {
                        System.out.println("Enter email:");
                        String newEmail = scanner.next();
                        System.out.println("Enter password:");
                        String newPassword = scanner.next();
                        SignUp.doSignUp(newEmail, newPassword, conn);
                    }
                    case 0 -> exit = true;
                    default -> System.out.println("Invalid choice!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}







import service.AccountDTO;
import service.AccountService;
import service.AuthService;
import service.UserDTO;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AuthService authService = new AuthService();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Select an action:");
            System.out.println("1. Log In");
            System.out.println("2. Sign Up");
            System.out.println("0. Exit");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    String email = request("Enter email:");
                    String password = request("Enter password:");
                    UserDTO userDTO = authService.auth(email, password);
                    if (userDTO != null) {
                        System.out.println("Log in successful!");
                        System.out.println(userDTO);

                        boolean exit2 = false;
                        while (!exit2) {
                            System.out.println("Select an action:");
                            System.out.println("1. View all accounts");
                            System.out.println("2. Create an account");
                            System.out.println("3. Delete an account");
                            System.out.println("0. Log out");

                            AccountService accountService = new AccountService();
                            int choice2 = scanner.nextInt();
                            switch (choice2) {
                                case 1:
                                    List<AccountDTO> accountDTOs = accountService.getAllAccountsByUserId(userDTO.getId());
                                    if (accountDTOs != null) {
                                        for (AccountDTO account : accountDTOs) {
                                            System.out.println(account);
                                        }
                                    }
                                    break;
                                case 2:
                                    String accountName = request("Enter name for new account: ");
                                    AccountDTO accountDTO = accountService.createAccount(accountName, userDTO.getId());
                                    System.out.println(accountDTO);
                                    break;
                                case 3:
                                    System.out.println("Enter account id to delete: ");
                                    int accountId = scanner.nextInt();
                                    boolean isDeleted = accountService.deleteAccount(accountId, userDTO.getId());
                                    if (isDeleted) {
                                        System.out.println("Account " + accountId + " successfully deleted!");
                                    } else {
                                        System.out.println("Account not found or no rights to delete");
                                    }
                                    accountDTOs = accountService.getAllAccountsByUserId(userDTO.getId());
                                    if (accountDTOs != null) {
                                        for (AccountDTO account : accountDTOs) {
                                            System.out.println(account);
                                        }
                                    }
                                    break;
                                case 0:
                                    break;
                                default:
                                    System.out.println("Invalid choice!");
                                    break;
                            }
                        }
                    } else {
                        System.out.println("Log in failed!");
                    }


                    break;
                case 2:
                    email = request("Enter email:");
                    password = request("Enter password:");
                    userDTO = authService.registration(email, password);
                    if (userDTO != null) {
                        System.out.println("Registration is successful!");
                        System.out.println(userDTO);
                    } else {
                        System.out.println("Registration is failed!");
                    }
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private static String request(String title) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(title);
        return scanner.next();
    }
}
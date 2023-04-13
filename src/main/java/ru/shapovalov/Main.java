package ru.shapovalov;

import ru.shapovalov.service.*;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        UserAuthService userAuthService = new UserAuthService();
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
                    Optional<UserDto> optUserDto = userAuthService.auth(email, password);
                    UserDto userDto;
                    if (optUserDto.isPresent()) {
                        userDto = optUserDto.get();
                        System.out.println("Log in successful!");
                        System.out.println(userDto);
                        boolean exit2 = true;
                        while (exit2) {
                            System.out.println("Select an action:");
                            System.out.println("1. View all accounts");
                            System.out.println("2. Create an account");
                            System.out.println("3. Delete an account");
                            System.out.println("4. Create transaction category");
                            System.out.println("5. Delete transaction category");
                            System.out.println("6. Edit transaction category");
                            System.out.println("0. Log out");

                            AccountService accountService = new AccountService();
                            CategoryService categoryService = new CategoryService();
                            int choice2 = scanner.nextInt();
                            switch (choice2) {
                                case 1:
                                    List<AccountDto> accountDtos = accountService.getAll(userDto.getId());
                                    for (AccountDto account : accountDtos) {
                                        System.out.println(account);
                                    }
                                    break;
                                case 2:
                                    String accountName = request("Enter name for new account: ");
                                    AccountDto accountDto = accountService.create(accountName, userDto.getId());
                                    if (accountDto != null) {
                                        System.out.println("Account is created!!");
                                        System.out.println(accountDto);
                                    } else {
                                        System.out.println("Created failed!");
                                    }
                                    break;
                                case 3:
                                    System.out.println("Enter account id to delete: ");
                                    int accountId = scanner.nextInt();
                                    boolean isDeleted = accountService.delete(accountId, userDto.getId());
                                    if (isDeleted) {
                                        System.out.println("Account " + accountId + " successfully deleted!");
                                    } else {
                                        System.out.println("Account not found or no rights to delete");
                                    }
                                    break;
                                case 4:
                                    String categoryName = request("Enter name for new transaction category: ");
                                    CategoryDto categoryDto = categoryService.create(categoryName, userDto.getId());
                                    if (categoryDto != null) {
                                        System.out.println("Category is created!!");
                                        System.out.println(categoryName);
                                    } else {
                                        System.out.println("Created failed!");
                                    }
                                    break;
                                case 5:
                                    int id = requestInt("Enter category id for delete: ");
                                    isDeleted = categoryService.delete(id, userDto.getId());
                                    if (isDeleted) {
                                        System.out.println("Category '" + id + "' successfully deleted!");
                                    } else {
                                        System.out.println("Category not found");
                                    }
                                    break;
                                case 6:
                                    id = requestInt("Enter category id for edit:");
                                    String newCategoryName = request("Enter new name for " + id + " transaction category:");
                                    categoryDto = categoryService.edit(id, newCategoryName, userDto.getId());
                                    System.out.println(categoryDto);
                                    break;
                                case 0:
                                    exit2 = false;
                                    break;
                                default:
                                    System.out.println("Invalid choice!");
                                    break;
                            }
                        }
                    } else {
                        System.out.println("Log in failed!");
                        break;
                    }
                case 2:
                    email = request("Enter email:");
                    password = request("Enter password:");
                    userDto = userAuthService.registration(email, password);
                    if (userDto != null) {
                        System.out.println("Registration is successful!");
                        System.out.println(userDto);
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

    private static int requestInt(String title) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(title);
        return scanner.nextInt();
    }
}
package ru.shapovalov;

import org.springframework.context.ApplicationContext;
import ru.shapovalov.service.*;

import java.util.*;

import static ru.shapovalov.SpringContext.getContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = getContext();
        UserAuthService userAuthService = context.getBean(UserAuthService.class);
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
                            System.out.println("7. Income report");
                            System.out.println("8. Expense report");
                            System.out.println("9. Transfers");
                            System.out.println("0. Log out");

                            AccountService accountService = context.getBean(AccountService.class);
                            CategoryService categoryService = context.getBean(CategoryService.class);
                            TransactionService transactionService = context.getBean(TransactionService.class);
                            int choice2 = scanner.nextInt();
                            switch (choice2) {
                                case 1:
                                    List<AccountDto> accountDtos = accountService.getAll(userDto.getId());
                                    if (!accountDtos.isEmpty()) {
                                        for (AccountDto account : accountDtos) {
                                            System.out.println(account);
                                        }
                                    } else {
                                        System.out.println("No available accounts found!");
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
                                case 7:
                                    int days = requestInt("Report for how many days?");
                                    Map<String, Long> resultIncomeInPeriodByCategory = categoryService.getResultIncomeInPeriodByCategory(userDto.getId(), days);
                                    System.out.println("Result incomes: " + resultIncomeInPeriodByCategory);
                                    break;
                                case 8:
                                    days = requestInt("Report for how many days?");
                                    Map<String, Long> resultExpenseInPeriodByCategory = categoryService.getResultExpenseInPeriodByCategory(userDto.getId(), days);
                                    System.out.println("Result expenses: " + resultExpenseInPeriodByCategory);
                                    break;
                                case 9:
                                    int sender = requestInt("Specify from which account id funds will be debited");
                                    int recipient = requestInt("Specify which account the funds will be transferred to. " +
                                            "if this is an unknown account, enter 0");
                                    int sum = requestInt("Enter the desired amount");
                                    List<Integer> categoryIds = new ArrayList<>();
                                    int categoryId;
                                    do {
                                        categoryId = requestInt("Enter category ID (enter 0 to finish):");
                                        if (categoryId != 0) {
                                            categoryIds.add(categoryId);
                                        }
                                    } while (categoryId != 0);
                                    TransactionDto transactionDto = transactionService.sendMoney(sender, recipient, sum, userDto.getId(), categoryIds);
                                    if (transactionDto != null) {
                                        System.out.println("Successful, your payment receipt: ");
                                        System.out.println(transactionDto);
                                    } else {
                                        System.out.println("Funds transfer failed");
                                    }
                                    break;
                                case 0:
                                    exit2 = false;
                                    break;
                                default:
                                    System.out.println("Invalid choice!");
                                    break;
                            }
                        }
                        continue; //возврат в главное меню
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
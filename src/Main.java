import models.Expense;
import models.User;
import service.UserService;
import service.ExpenseService;
import service.ReportService;
import service.SavingGoalService;

import java.text.SimpleDateFormat;
import java.util.Scanner;

import datastructures.MyLinkedList;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static UserService userService = new UserService();
    private static ExpenseService expenseService = new ExpenseService();
    private static ReportService reportService = new ReportService();

    public static void main(String[] args) {
        System.out.println("===== Welcome to Expense Tracker =====");

        User currentUser = null;
        while (currentUser == null) {
            System.out.println("\n1. Register");
            System.out.println("2. Login");
            System.out.print("Choose option: ");
            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                currentUser = handleRegistration();
            } else if (choice.equals("2")) {
                currentUser = handleLogin();
            } else {
                System.out.println("Invalid choice.");
            }
        }

        System.out.println("Welcome, " + currentUser.getUsername() + "!");

        while (true) {
            System.out.println("\n--- Menu ---");
            System.out.println("1. Add Expense");
            System.out.println("2. Generate Monthly Report");
            System.out.println("3. View Category Insights");
            System.out.println("4. Set Savings Goal");
            System.out.println("5. View Sorted Expenses");
            System.out.println("6. Exit");

            System.out.print("Choose option: ");
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    expenseService.addExpense(currentUser);
                    break;
                case "2":
                    reportService.generateMonthlyReport(currentUser);
                    break;
                case "3":
                    reportService.showCategoryInsights(currentUser);
                    break;
                case "4":
                    System.out.print("Enter your monthly savings goal (₹): ");
                    double goalAmount = Double.parseDouble(scanner.nextLine());
                    SavingGoalService goalService = new SavingGoalService();
                    goalService.setGoal(currentUser.getUsername(), goalAmount);
                    System.out.println("Goal saved.");
                    break;
                case "5":
                    MyLinkedList<Expense> list = expenseService.loadExpenses(currentUser);
                    System.out.print("Sort by (amount/date): ");
                    String sortType = scanner.nextLine();
                    expenseService.sortExpenses(list, sortType);

                    System.out.println("\n Sorted Expenses:");
                    for (int i = 0; i < list.size(); i++) {
                        Expense e = list.get(i);
                        System.out.println("- Rs. " + e.getAmount() + " | " + e.getCategory() + " | " +
                                e.getDescription() + " | " + new SimpleDateFormat("dd-MM-yyyy").format(e.getDate()));
                    }
                    break;
                case "6":
                    System.out.println("Exiting...");
                    System.out.println("Exit Successfull");
                    System.exit(0);
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static User handleRegistration() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        boolean registered = userService.register(username, password);
        if (registered) {
            System.out.println("Registration successful. You can now log in.");
        }
        return null; // User needs to login after registration
    }

    private static User handleLogin() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = userService.login(username, password);
        if (user != null) {
            System.out.println("Login successful.");
            return user;
        } else {
            System.out.println("Invalid credentials.");
            return null;
        }
    }
}

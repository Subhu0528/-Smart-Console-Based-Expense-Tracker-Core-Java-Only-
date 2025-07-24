package service;

import datastructures.MyLinkedList;
import models.Expense;
import models.User;
import utils.FileUtil;
import utils.InputValidator;

import java.util.Date;
import java.util.Scanner;

public class ExpenseService {
    private static final Scanner scanner = new Scanner(System.in);

    public void addExpense(User user) {
        System.out.print("Enter amount: ");
        String amountStr = scanner.nextLine();
        while (!InputValidator.isValidAmount(amountStr)) {
            System.out.print("Invalid amount. Try again: ");
            amountStr = scanner.nextLine();
        }
        double amount = Double.parseDouble(amountStr);

        System.out.print("Enter category (Food, Travel, Rent, etc.): ");
        String category = scanner.nextLine();
        while (!InputValidator.isValidCategory(category)) {
            System.out.print("Invalid category. Try again: ");
            category = scanner.nextLine();
        }

        System.out.print("Enter description: ");
        String description = scanner.nextLine();

        System.out.print("Enter date (dd-MM-yyyy) or press Enter for today: ");
        String dateInput = scanner.nextLine();
        Date date;
        if (dateInput.trim().isEmpty()) {
            date = new Date(); // current date
        } else {
            try {
                date = new java.text.SimpleDateFormat("dd-MM-yyyy").parse(dateInput);
            } catch (Exception e) {
                System.out.println("Invalid date format. Using today’s date.");
                date = new Date();
            }
        }

        System.out.print("Is this a recurring expense? (yes/no): ");
        String recurringInput = scanner.nextLine().trim().toLowerCase();
        boolean isRecurring = recurringInput.equals("yes") || recurringInput.equals("y");

        // ✅ Update constructor to include isRecurring
        Expense expense = new Expense(amount, category, description, date, isRecurring);

        // Save to user-specific file
        String fileName = "expenses_" + user.getUsername() + ".txt";
        FileUtil.writeLine(fileName, expense.toFileString(), true);

        System.out.println("Expense added successfully.");
    }

    public void sortExpenses(MyLinkedList<Expense> list, String type) {
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                Expense e1 = list.get(j);
                Expense e2 = list.get(j + 1);

                boolean shouldSwap = false;
                if (type.equals("amount") && e1.getAmount() > e2.getAmount()) {
                    shouldSwap = true;
                } else if (type.equals("date") && e1.getDate().after(e2.getDate())) {
                    shouldSwap = true;
                }

                if (shouldSwap) {
                    Expense temp = e1;
                    list.set(j, e2);
                    list.set(j + 1, temp);
                }
            }
        }
    }

    public MyLinkedList<Expense> loadExpenses(User user) {
        MyLinkedList<Expense> list = new MyLinkedList<>();
        String fileName = "expenses_" + user.getUsername() + ".txt";

        MyLinkedList<String> lines = FileUtil.readLines(fileName);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            Expense e = Expense.fromFileString(line);
            if (e != null) {
                list.add(e);
            }
        }

        return list;
    }
}

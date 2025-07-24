package service;

import datastructures.MyLinkedList;
import datastructures.MyHashMap;
import models.Expense;
import models.User;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportService {
    private ExpenseService expenseService = new ExpenseService();
    private static final int ALERT_LIMIT = 20000;
    private SavingGoalService goalService = new SavingGoalService();

    public void generateMonthlyReport(User user) {
        MyLinkedList<Expense> expenses = expenseService.loadExpenses(user);

        MyHashMap<String, Double> categoryTotals = new MyHashMap<>();
        double totalExpense = 0;
        Expense highestExpense = null;

        SimpleDateFormat sdf = new SimpleDateFormat("MM-yyyy");
        String currentMonth = sdf.format(new Date());

        for (int i = 0; i < expenses.size(); i++) {
            Expense e = expenses.get(i);
            String month = sdf.format(e.getDate());

            if (month.equals(currentMonth)) {
                totalExpense += e.getAmount();
                categoryTotals.put(e.getCategory(),
                        categoryTotals.getOrDefault(e.getCategory(), 0.0) + e.getAmount());

                if (highestExpense == null || e.getAmount() > highestExpense.getAmount()) {
                    highestExpense = e;
                }
            }
        }

        String fileName = "reports/monthly_" + user.getUsername() + "_" + currentMonth + ".txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("==== Monthly Expense Summary ====\n");
            writer.write("User: " + user.getUsername() + "\n");
            writer.write("Month: " + currentMonth + "\n\n");

            writer.write("Category-wise Breakdown:\n");
            for (String category : categoryTotals.keySet()) {
                writer.write("- " + category + ": Rs. " + categoryTotals.get(category) + "\n");
            }

            writer.write("\nTotal Monthly Expense: Rs." + totalExpense + "\n");

            if (highestExpense != null) {
                writer.write("Highest Expense: Rs. " + highestExpense.getAmount() +
                        " (" + highestExpense.getCategory() + ")\n");
            }

            if (totalExpense > ALERT_LIMIT) {
                writer.write("\n ALERT: Spending crossed Rs. " + ALERT_LIMIT + "!\n");
            }

            writer.flush();
        } catch (IOException e) {
            System.out.println("Error writing report: " + e.getMessage());
        }

        System.out.println("Monthly report generated: " + fileName);
        goalService.checkGoal(user.getUsername(), totalExpense);
    }

    public void showCategoryInsights(User user) {
        MyLinkedList<Expense> expenses = expenseService.loadExpenses(user);
        MyHashMap<String, Double> totals = new MyHashMap<>();
        MyHashMap<String, Integer> counts = new MyHashMap<>();
        double totalMonthlyExpense = 0;

        // Date formatter for month check
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM-yyyy");
        String currentMonth = monthFormat.format(new Date());

        // Accumulate totals and counts per category
        for (int i = 0; i < expenses.size(); i++) {
            Expense e = expenses.get(i);
            String expenseMonth = monthFormat.format(e.getDate());

            if (expenseMonth.equals(currentMonth)) {
                totalMonthlyExpense += e.getAmount();

                String cat = e.getCategory();
                totals.put(cat, totals.getOrDefault(cat, 0.0) + e.getAmount());
                counts.put(cat, counts.getOrDefault(cat, 0) + 1);
            }
        }

        // Most used category
        String mostUsedCategory = null;
        int maxCount = 0;
        String[] keys = counts.keySet();

        for (int i = 0; i < keys.length; i++) {
            String cat = keys[i];
            int count = counts.get(cat);
            if (count > maxCount) {
                maxCount = count;
                mostUsedCategory = cat;
            }
        }

        // Display insights
        System.out.println("\n==== Category Insights (This Month) ====");

        if (mostUsedCategory != null) {
            System.out.println("Most Used Category: " + mostUsedCategory + " (" + maxCount + " entries)");
        } else {
            System.out.println("No expenses found for this month.");
            return;
        }

        System.out.println("\nAverage Spending Per Category:");
        String[] avgKeys = totals.keySet();
        for (int i = 0; i < avgKeys.length; i++) {
            String cat = avgKeys[i];
            double total = totals.get(cat);
            int count = counts.get(cat);
            double avg = total / count;
            System.out.println("- " + cat + ": Rs. " + String.format("%.2f", avg));
        }

        // Alert if over limit
        if (totalMonthlyExpense > 20000) {
            System.out.println("\n ALERT: Your total spending this month is Rs. " + totalMonthlyExpense
                    + ", which exceeds the Rs. 20,000 limit!");
        } else {
            System.out.println("\nTotal Monthly Spending: Rs. " + totalMonthlyExpense);
        }
    }
}

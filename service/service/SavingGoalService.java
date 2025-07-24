package service;

import utils.FileUtil;
import java.io.File;

public class SavingGoalService {
    private static final String FILE_PREFIX = "goal_";

    public void setGoal(String username, double goalAmount) {
        FileUtil.writeLine(FILE_PREFIX + username + ".txt", String.valueOf(goalAmount), false);
    }

    public double getGoal(String username) {
        String file = FILE_PREFIX + username + ".txt";
        if (new File(file).exists()) {
            String line = FileUtil.readLines(file).get(0);
            return Double.parseDouble(line);
        }
        return 0;
    }

    public void checkGoal(String username, double monthlyExpense) {
        double goal = getGoal(username);
        if (goal == 0) {
            System.out.println("No savings goal set.");
            return;
        }

        System.out.println("Savings Goal: ₹" + goal);
        if (monthlyExpense <= goal) {
            System.out.println("You are within your savings goal! Saved Rs. " + (goal - monthlyExpense));
        } else {
            System.out.println("You exceeded your goal by Rs." + (monthlyExpense - goal));
        }
    }
}

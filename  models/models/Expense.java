package models;

import java.util.Date;

public class Expense {
    private double amount;
    private String category;
    private String description;
    private Date date;
    private boolean isRecurring;

    public Expense(double amount, String category, String description, Date date, Boolean isRecurring) {
        this.amount = amount;
        this.category = category;
        this.description = description;
        this.date = date;
        this.isRecurring = isRecurring;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public Date getDate() {
        return date;
    }

    public boolean isRecurring() {
        return isRecurring;
    }

    public String toFileString() {
        return amount + "," + category + "," + description + "," + date.getTime() + "," + isRecurring;
    }

    public static Expense fromFileString(String line) {
        String[] parts = line.split(",");
        if (parts.length != 5)
            return null;
        double amount = Double.parseDouble(parts[0]);
        String category = parts[1];
        String description = parts[2];
        Date date = new Date(Long.parseLong(parts[3]));
        boolean isRecurring = Boolean.parseBoolean(parts[4]);
        return new Expense(amount, category, description, date, isRecurring);
    }
}

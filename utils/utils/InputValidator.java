package utils;

public class InputValidator {

    public static boolean isValidUsername(String username) {
        return username != null && username.matches("^[a-zA-Z0-9_]{3,15}$");
    }

    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 4;
    }

    public static boolean isValidAmount(String input) {
        try {
            double amt = Double.parseDouble(input);
            return amt > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValidCategory(String category) {
        return category != null && category.trim().length() > 0;
    }
}


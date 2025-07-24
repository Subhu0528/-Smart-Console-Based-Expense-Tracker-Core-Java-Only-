package models;

public class User {
    private String username;
    private String passwordHash;

    public User(String username, String passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String toFileString() {
        return username + "," + passwordHash;
    }

    public static User fromFileString(String line) {
        String[] parts = line.split(",");
        if (parts.length != 2) return null;
        return new User(parts[0], parts[1]);
    }
}

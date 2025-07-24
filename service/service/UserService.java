package service;

import models.User;
import utils.FileUtil;
import utils.Hasher;
import utils.InputValidator;
import datastructures.MyLinkedList;

public class UserService {
    private static final String USER_FILE = "users.txt";

    public boolean register(String username, String password) {
        if (!InputValidator.isValidUsername(username)) {
            System.out.println("Invalid username.");
            return false;
        }

        if (!InputValidator.isValidPassword(password)) {
            System.out.println("Invalid password.");
            return false;
        }

        MyLinkedList<User> users = getAllUsers(); // ✅ Use custom list
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            if (user.getUsername().equals(username)) {
                System.out.println("Username already exists.");
                return false;
            }
        }

        String hash = Hasher.simpleHash(password);
        User newUser = new User(username, hash);
        FileUtil.writeLine(USER_FILE, newUser.toFileString(), true);
        return true;
    }

    public User login(String username, String password) {
        MyLinkedList<User> users = getAllUsers(); // ✅ Use custom list
        String hash = Hasher.simpleHash(password);

        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            if (user.getUsername().equals(username) &&
                    user.getPasswordHash().equals(hash)) {
                return user;
            }
        }

        return null;
    }

    private MyLinkedList<User> getAllUsers() {
        MyLinkedList<String> lines = FileUtil.readLines(USER_FILE);
        MyLinkedList<User> users = new MyLinkedList<>();

        for (int i = 0; i < lines.size(); i++) {
            User u = User.fromFileString(lines.get(i));
            if (u != null)
                users.add(u);
        }

        return users;
    }
}

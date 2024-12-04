package JavaProjects.ED_Planner;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.reflect.TypeToken;

import com.google.gson.Gson;

public class UserDatabase {
    private static final String FILE_PATH = "data.json";
    private Map<String, User> users;
    private final Gson gson;

    public UserDatabase() {
        this.users = new HashMap<>();
        this.gson = new Gson();
        loadFromFile();
    }

    // Normalize email for consistent storage and retrieval
    private String normalizeEmail(String email) {
        return email.toLowerCase().trim();
    }

    // Add a new user and save to file
    public boolean addUser(String email, String username, String gradeLevel, String school, String password) {
        email = normalizeEmail(email);
        if (users.containsKey(email)) return false; // Prevent duplicate entries

        users.put(email, new User(email, username, gradeLevel, school, password));
        saveToFile();
        return true;
    }

    // Validate user credentials
    public boolean validateUser(String email, String password) {
        email = normalizeEmail(email);
        User user = users.get(email);
        return user != null && user.verifyPassword(password);
    }

    // Retrieve user by email
    public User getUserByEmail(String email) {
        return users.get(normalizeEmail(email));
    }

    // Save all user data to JSON file
    private void saveToFile() {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            System.err.println("Error saving user data: " + e.getMessage());
        }
    }

    // Load user data from JSON file
    private void loadFromFile() {
        try (FileReader reader = new FileReader(FILE_PATH)) {
            java.lang.reflect.Type type = new TypeToken<Map<String, User>>() {}.getType();
            users = gson.fromJson(reader, type);
            if (users == null) {
                users = new HashMap<>();
            }
        } catch (FileNotFoundException e) {
            System.out.println("No existing user data found. Starting fresh.");
            users = new HashMap<>(); // Initialize if no file exists
        } catch (IOException e) {
            System.err.println("Error loading user data: " + e.getMessage());
        }
    }

    // Save updates for a specific user
    public boolean saveUserData(String email, User user) {
        email = normalizeEmail(email);
        users.put(email, user);
        saveToFile();
        return true;
    }
}
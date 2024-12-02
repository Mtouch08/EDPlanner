package JavaProjects.ED_Planner;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.DefaultTableModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class UserDatabase {
    private static final String FILE_PATH = "user_data.json"; // Use the newly created file path
    private Map<String, User> users;
    private Gson gson;

    public UserDatabase() {
    	 this.users = new HashMap<>();
         this.gson = new Gson();
         loadFromFile();
    }

    private String normalizeEmail(String email) {
        return email.toLowerCase().trim();
    }

    public boolean addUser(String email, String username,String gradeLevel,String school, String password) {
        email = normalizeEmail(email);
        if (users.containsKey(email)) return false; // Email already exists

        users.put(email, new User(email, username,gradeLevel,school, password)); // User handles password hashing
        saveToFile();
        return true;
    }

    public boolean validateUser(String email, String password) {
        email = normalizeEmail(email);
        User user = users.get(email);
        return user != null && user.verifyPassword(password); // Use User's verifyPassword method
    }

    public User getUserByEmail(String email) {
        return users.get(normalizeEmail(email));
    }
    
    
    private void saveToFile() {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadFromFile() {
        try (FileReader reader = new FileReader(FILE_PATH)) {
            users = gson.fromJson(reader, new TypeToken<Map<String, User>>() {}.getType());
        } catch (FileNotFoundException e) {
            System.out.println("No existing user data found. Starting fresh.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean saveUserData(String email, User user) {
        users.put(normalizeEmail(email), user);
        saveToFile(); // Persist changes to file
        return true;
    }

    public User loadUserData(String email) {
        return users.get(normalizeEmail(email));
    }
}

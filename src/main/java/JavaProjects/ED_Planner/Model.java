package JavaProjects.ED_Planner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Model {
    private UserDatabase userDatabase;
   
    
    
    private TreeMap<Integer, String> academicTree; // For academic data hierarchy
    private User currentUser;
    private DataFetcher dataFetcher;

    public Model() {
        this.userDatabase = new UserDatabase();
        this.academicTree = new TreeMap<>();
        this.dataFetcher = new DataFetcher();
        
        

       
    }

    public boolean saveAcademicHistory(String[][] academicData) {
        if (currentUser == null) return false;

        // Convert academicData (table format) into a List of Maps
        List<Map<String, String>> academicHistory = new ArrayList<>();
        for (String[] row : academicData) {
            Map<String, String> record = new HashMap<>();
            record.put("Year", row[0]);
            record.put("Semester", row[0]);
            record.put("CourseName", row[2]);
            record.put("Grade", row[3]);
            record.put("FAST_READ", row[4]);
            record.put("FAST_MATH", row[5]);
            record.put("SAT", row[6]);
            record.put("ACT", row[7]);
            academicHistory.add(record);
        }
        // Save the academic history to the current user's data
        currentUser.setAcademicHistory(academicHistory);
        return userDatabase.saveUserData(currentUser.getEmail(), currentUser);
    }
    public List<Map<String, String>> loadAcademicHistory() {
        if (currentUser != null) {
            return currentUser.getAcademicHistory();
        }
        return new ArrayList<>();
    }

     // Add academic records
    public void addAcademicRecord(int year, String subject) {
        academicTree.put(year, subject);
    }

    // Sort academic records
    public List<String> getSortedAcademicRecords() {
        return new ArrayList<>(academicTree.values());
    }

    public List<Map<String, String>> fetchAllColleges() {
        return dataFetcher.fetchColleges(null, null, null); // Fetch all colleges without filters
    }


    public boolean validateEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    public boolean validatePassword(String password) {
        String pattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*]).{8,}$";
        return password.matches(pattern);
    }

    public boolean registerUser(String email, String username, String gradeLevel, String school, String password) {
        return userDatabase.addUser(email, username, gradeLevel, school, password);
    }

    public boolean authenticateUser(String email, String password) {
        if (userDatabase.validateUser(email, password)) {
            currentUser = userDatabase.getUserByEmail(email);
            return true;
        }
        return false;
    }

    public User getCurrentUser(String email) {
        if (currentUser != null && currentUser.getEmail().equalsIgnoreCase(email)) {
            return currentUser;
        }
        return null;
    }
}


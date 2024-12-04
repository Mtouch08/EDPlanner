package JavaProjects.ED_Planner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Model {
    private UserDatabase userDatabase;
    private Map<String, Integer> assessmentScores;
    private Map<String, List<String>> careerGraph; // Graph representation
    private Queue<String> recommendationQueue; // Career recommendations queue
    //private Stack<String> actionStack; // For undo/redo functionality
    private TreeMap<Integer, String> academicTree; // For academic data hierarchy
    private User currentUser;
    private DataFetcher dataFetcher;

    public Model() {
        this.userDatabase = new UserDatabase();
        this.assessmentScores = new LinkedHashMap<>();
        this.careerGraph = new HashMap<>();
        this.recommendationQueue = new LinkedList<>();
        //this.actionStack = new Stack<>();
        this.academicTree = new TreeMap<>();
        this.dataFetcher = new DataFetcher();
        initializeCareerGraph();
    }

    public boolean saveAcademicHistory(String[][] academicData) {
        if (currentUser == null) return false;

        // Convert academicData (table format) into a List of Maps
        List<Map<String, String>> academicHistory = new ArrayList<>();
        for (String[] row : academicData) {
            Map<String, String> record = new HashMap<>();
            record.put("Year", row[0]);
            record.put("CourseName", row[1]);
            record.put("Grade", row[2]);
            record.put("SAT", row[3]);
            record.put("ACT", row[4]);
            record.put("FAST_READING", row[5]);
            record.put("FAST_MATH", row[6]);
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

    // Initialize a sample career graph
    private void initializeCareerGraph() {
        addCareerPathway("Software Developer", "Data Structures");
        addCareerPathway("Data Structures", "Algorithms");
        addCareerPathway("Algorithms", "Internship");
        addCareerPathway("Internship", "Full-Time Position");
    }

    // Add a career pathway edge
    public void addCareerPathway(String from, String to) {
        careerGraph.computeIfAbsent(from, k -> new ArrayList<>()).add(to);
    }

    // Traverse the graph using BFS to generate a career pathway
    public List<String> bfsPathway(String start) {
        List<String> result = new ArrayList<>();
        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            result.add(current);
            for (String neighbor : careerGraph.getOrDefault(current, new ArrayList<>())) {
                if (!visited.contains(neighbor)) {
                    queue.add(neighbor);
                    visited.add(neighbor);
                }
            }
        }
        return result;
    }

    // Fetch career recommendations based on O*NET Interest Profiler
    public Queue<String> getCareerRecommendationsFromAPI() throws Exception {
        if (assessmentScores == null || assessmentScores.isEmpty()) {
            throw new IllegalStateException("Assessment scores are missing.");
        }

        String hollandCode = generateHollandCode(assessmentScores);
        JsonArray careers = dataFetcher.fetchCareersByInterests(hollandCode);

        recommendationQueue.clear();
        for (JsonElement element : careers) {
            JsonObject career = element.getAsJsonObject();
            String title = career.get("title").getAsString();
            String socCode = career.get("soc_code").getAsString();
            recommendationQueue.add(title + " (SOC: " + socCode + ")");
        }

        return recommendationQueue;
    }

    // Convert assessment scores to Holland Code
    private String generateHollandCode(Map<String, Integer> scores) {
        return scores.entrySet().stream()
                .sorted((a, b) -> Integer.compare(b.getValue(), a.getValue()))
                .limit(3)
                .map(entry -> entry.getKey().substring(0, 1)) // First letter of key
                .reduce("", String::concat);
    }

    // Add academic records
    public void addAcademicRecord(int year, String subject) {
        academicTree.put(year, subject);
    }

    // Sort academic records
    public List<String> getSortedAcademicRecords() {
        return new ArrayList<>(academicTree.values());
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


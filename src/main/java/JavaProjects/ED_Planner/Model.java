package JavaProjects.ED_Planner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import java.util.TreeMap;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Model
{
	private UserDatabase userDatabase;
	private Map<String, Integer> assessmentScores;
	private Stack<String> undoStack;
    private Stack<String> redoStack;
    private Queue<String> recommendationQueue;
    private Map<String, List<String>> pathwayGraph; // Graph representation
    private TreeMap<Integer, String> academicTree; // Tree structure for grade levels
    private User currentUser;
    private DataFetcher dataFetcher; // For API calls or local O*Net data

    
    public Model() {
    	this.userDatabase = new UserDatabase();
    	this.assessmentScores = new LinkedHashMap<>();
        this.undoStack = new Stack<>();
        this.redoStack = new Stack<>();
        this.recommendationQueue = new LinkedList<>();
        this.pathwayGraph = new HashMap<>();
        this.academicTree = new TreeMap<>();
        this.userDatabase = new UserDatabase();
        this.dataFetcher = new DataFetcher();
    }
    public boolean validateEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    public boolean validatePassword(String password) {
        String pattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*]).{8,}$";
        return password.matches(pattern);
    }
    public boolean registerUser(String email, String username,String gradeLevel,String school, String password) {
        return userDatabase.addUser(email, username,gradeLevel,school, password);
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
    public void setAssessmentScores(Map<String, Integer> scores) {
        this.assessmentScores = scores;
    }
    public Map<String, Integer> getAssessmentScores() {
        return assessmentScores;
    }

    public void addTreeNode(int gradeLevel, String subject) {
        academicTree.put(gradeLevel, subject);
    }

    public void addGraphEdge(String from, String to) {
        pathwayGraph.computeIfAbsent(from, k -> new ArrayList<>()).add(to);
    }
   
    public Queue<String> getCareerRecommendationsFromAPI() throws Exception {
        if (assessmentScores == null || assessmentScores.isEmpty()) {
            throw new IllegalStateException("Assessment scores are missing.");
        }

        // Convert assessment scores to a Holland Code
        String hollandCode = generateHollandCode(assessmentScores);

        // Fetch recommended careers using O*NET API
        DataFetcher dataFetcher = new DataFetcher();
        JsonArray careers = dataFetcher.fetchCareersByInterests(hollandCode);

        // Populate the queue with recommended careers
        recommendationQueue.clear();
        for (JsonElement element : careers) {
            JsonObject career = element.getAsJsonObject();
            String title = career.get("title").getAsString();
            String socCode = career.get("soc_code").getAsString();
            recommendationQueue.add(title + " (SOC: " + socCode + ")");
        }

        return recommendationQueue;
    }

    private String generateHollandCode(Map<String, Integer> scores) {
        // Sort scores in descending order and take the top 3
        return scores.entrySet().stream()
                .sorted((a, b) -> Integer.compare(b.getValue(), a.getValue()))
                .limit(3)
                .map(entry -> entry.getKey().substring(0, 1)) // Take the first letter
                .reduce("", String::concat); // Concatenate into a Holland Code
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            String action = undoStack.pop();
            redoStack.push(action);
            System.out.println("Undo: " + action);
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            String action = redoStack.pop();
            undoStack.push(action);
            System.out.println("Redo: " + action);
        }
    }
        
    public UserDatabase getUserDatabase() {
    	return userDatabase;
    }
        
    public Map<String, String> fetchOnetResults(String interestCode) {
        try {
            Map<String, String> onetResults = dataFetcher.fetchOnetResults(interestCode);
            currentUser.setOnetResults(onetResults);
            userDatabase.saveUserData(currentUser.getEmail(), currentUser); // Persist results
            return onetResults;
        } catch (Exception e) {
            System.err.println("Error fetching O*NET results: " + e.getMessage());
            return new HashMap<>();
        }
    }
}


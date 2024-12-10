package JavaProjects.ED_Planner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Model {
    private UserDatabase userDatabase;
    private TreeMap<Integer, String> academicTree; // For academic data hierarchy
    private Map<String, List<String>> courseGraph;
    private User currentUser;
    private DataFetcher dataFetcher;

    public Model() {
        this.userDatabase = new UserDatabase();
        this.academicTree = new TreeMap<>();
        this.dataFetcher = new DataFetcher();
        this.courseGraph = new HashMap<>();
        initializeCourseGraph();       
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

     
     public void addAcademicRecord(int year, String course, String grade) {
        String record = course + " - Grade: " + grade;
        academicTree.put(year, record);
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

    private void initializeCourseGraph() {
        // Add courses and prerequisites
        courseGraph.put("Advanced Math 101", Arrays.asList("Math 101"));
        courseGraph.put("Math 101", new ArrayList<>());
        courseGraph.put("Advanced Science 101", Arrays.asList("Science 101"));
        courseGraph.put("Science 101", new ArrayList<>());
    }

    public List<Map<String, String>> convertTreeMapToAcademicHistory() {
        List<Map<String, String>> academicHistory = new ArrayList<>();
        for (Map.Entry<Integer, String> entry : academicTree.entrySet()) {
            Map<String, String> record = new HashMap<>();
            String[] details = entry.getValue().split(" - ");
            record.put("Year", String.valueOf(entry.getKey()));
            record.put("CourseName", details[0]);
            record.put("Grade", details.length > 1 ? details[1].replace("Grade: ", "") : "");
    
            // Debugging each record
            System.out.println("Converted Record: " + record);
    
            academicHistory.add(record);
        }
        return academicHistory;
    }
    public List<String> recommendCourses(List<Map<String, String>> academicHistory, int testScoreThreshold) {
        List<String> recommendedCourses = new ArrayList<>();

        // Check each course in the graph
        for (String course : courseGraph.keySet()) {
            List<String> prerequisites = courseGraph.get(course);
            boolean eligible = true;

            for (String prerequisite : prerequisites) {
                boolean passed = academicHistory.stream().anyMatch(record ->
                    record.getOrDefault("CourseName", "").equals(prerequisite) &&
                    record.getOrDefault("Grade", "").matches("[AB]") // Only A or B grades qualify
                );

                if (!passed) {
                    eligible = false;
                    break;
                }
            }

            // Check test scores if it's an advanced course
            if (course.startsWith("Advanced")) {
                eligible = eligible && hasSufficientTestScore(academicHistory, testScoreThreshold);
            }

            if (eligible) {
                recommendedCourses.add(course);
            }
        }
        return recommendedCourses;
    }

    private boolean hasSufficientTestScore(List<Map<String, String>> academicHistory, int threshold) {
        int totalScore = 0;
        int count = 0;

        for (Map<String, String> record : academicHistory) {
            String satScore = record.getOrDefault("SAT", "");
            String actScore = record.getOrDefault("ACT", "");

            if (!satScore.isEmpty() && !satScore.equalsIgnoreCase("N/A")) {
                try {
                    totalScore += Integer.parseInt(satScore);
                    count++;
                } catch (NumberFormatException ignored) {}
            }

            if (!actScore.isEmpty() && !actScore.equalsIgnoreCase("N/A")) {
                try {
                    totalScore += Integer.parseInt(actScore) * 40; // ACT to SAT conversion
                    count++;
                } catch (NumberFormatException ignored) {}
            }
        }

        int averageScore = count > 0 ? totalScore / count : 0;
        return averageScore >= threshold;
    }

    public void debugAcademicTree() {
        if (academicTree.isEmpty()) {
            System.out.println("academicTree is empty.");
        } else {
            for (Map.Entry<Integer, String> entry : academicTree.entrySet()) {
                System.out.println("Year: " + entry.getKey() + ", Record: " + entry.getValue());
            }
        }
    }
}


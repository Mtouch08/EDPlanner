package JavaProjects.ED_Planner;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {
    private String email,username,hashedPassword,gradeLevel,school;
    private List<Map<String, String>> academicHistory; // Academic history records
    private Map<String, Integer> onetScores; // O*NET Interest Profiler scores
    private Map<String, String> onetResults; // O*NET career results

    public User(String email, String username, String gradeLevel,String school, String password) {
        this.email = email;
        this.username = username;
        this.gradeLevel = gradeLevel;
        this.school = school;
        this.hashedPassword = hashPassword(password);
        this.academicHistory = new ArrayList<>();
        this.onetScores = new HashMap<>();
        this.onetResults = new HashMap<>();
    }

    // Password hashing method
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    public boolean verifyPassword(String password) {
        return this.hashedPassword.equals(hashPassword(password));
    }

    public String getEmail() {
        return email;
    }
    public String getSchool() {
    	return school;
    }

    public String getUsername() {
        return username;
    }
    public String getGradeLevel() {
    	return gradeLevel;
    }
 
    public Map<String, Integer> getOnetScores() {
        return onetScores;
    }
    public void setOnetScores(Map<String, Integer> onetScores) {
        this.onetScores = onetScores;
    }

    public Map<String, String> getOnetResults() {
        return onetResults;
    }

    public void setOnetResults(Map<String, String> onetResults) {
        this.onetResults = onetResults;
    }
    
    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", gradeLevel='" + gradeLevel + '\'' +
                ", academicHistory=" + academicHistory +
                ", onetScores=" + onetScores +
                ", onetResults=" + onetResults +
                '}';
    }

}

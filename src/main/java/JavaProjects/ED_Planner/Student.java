package JavaProjects.ED_Planner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Student
{
	private String studentName;
	private int gradeLevel, studentID;
	private List<String> subjects;
	private Map<String, List<Integer>> grades;
	private Map<String, Integer> testScores;
	private List<String> interests;
	
	public Student(String studentName, int gradeLevel){
		this.studentName = studentName;
		this.gradeLevel = gradeLevel;
		this.subjects = new ArrayList<>();
        this.grades = new HashMap<>();
        this.testScores = new HashMap<>();
        this.interests = new ArrayList<>();
	}
	public void addSubject(String subject) {
        if (!subjects.contains(subject)) {
            subjects.add(subject);
            grades.put(subject, new ArrayList<>());
        }
    }
    public void addGrade(String subject, int grade) {
        if (grades.containsKey(subject)) {
            grades.get(subject).add(grade);
        }
    }
    public void addTestScore(String subject, int score) {
        testScores.put(subject, score);
    }

    // Add an interest
    public void addInterest(String interest) {
        if (!interests.contains(interest)) {
            interests.add(interest);
        }
    }

    // Getters
    public String getStudentName() {
        return studentName;
    }

    public int getGradeLevel() {
        return gradeLevel;
    }

    public List<String> getSubjects() {
        return subjects;
    }

    public Map<String, List<Integer>> getGrades() {
        return grades;
    }

    public Map<String, Integer> getTestScores() {
        return testScores;
    }

    public List<String> getInterests() {
        return interests;
    }
}


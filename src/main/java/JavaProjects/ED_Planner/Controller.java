package JavaProjects.ED_Planner;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Controller implements ActionListener
{
	private GUI gui;
    private Model model;
    private User currentUser;
    private StudentView studentView;
    private LoginView loginView;
    
        
    public Controller(GUI gui) {
        this.gui = gui;
        this.model = new Model();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();

        switch (actionCommand) {
        	case "Student":
        		handleStudentButton();
        		break;
        	case "Educator":
        		handleEducatorButton();
        		break;
        	case "Create":
        		handleCreate();
        		break;
        	case "CreateAccount":
        		handleCreateAccount();
        		break;
            case "Login":
                handleLogin();
                break;
            case "Help":
            	handleHelp();
            	break;
            case "Upload Grades/Test":
            	handleUploadGrades();
            	break;
            case "Save":
            	handleSave();
            	break;
            case "Delete":
            	handleDelete();
            	break;
            case "Evaluate":
            	handleEvaluate();
            	break;
            case "Generate Pathway":
                handleGeneratePathway();
                break;
            case "Logout":
            	handleLogout();
            	break;
            case "Back":
            	handleBack();
            	break;
            case "Confirm":
            	handleConfirm();
            	break;
            case "Export Report":
                handleExportReport();
                break;
            default:
                System.out.println("Unknown action: " + actionCommand);
                break;
        }
    }

	private void handleStudentButton() {
        gui.switchToLoginView(); // Switch to the LoginView
    }

    private void handleEducatorButton() {       
        // TODO: Implement logic to show the educator login screen
    }

    private void handleCreate() {
    	gui.switchToCreateAccountView();     	
    }
    
    private void handleCreateAccount() {
        CreateAccountView createAccountView = gui.getCreateAccountView();
        String email = createAccountView.getEmail();
        String name = createAccountView.getName();
        String gradeLevel = createAccountView.getGradeLevel();
        String school = createAccountView.getSchool();
        String password = createAccountView.getPassword();
        if (email.isEmpty() || name.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(gui, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!model.validateEmail(email)) {
            JOptionPane.showMessageDialog(gui, "Invalid email format.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!model.validatePassword(password)) {
            JOptionPane.showMessageDialog(gui,
                    "Password must be at least 8 characters long and include a mix of uppercase, lowercase, numbers, and special characters.",
                    "Weak Password",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (model.registerUser(email, name,gradeLevel,school, password)) {
            JOptionPane.showMessageDialog(gui, "Account created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            gui.switchToLoginView();
        } else {
            JOptionPane.showMessageDialog(gui, "Email already exists.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
   
    private void handleLogin() {
        loginView = gui.getLoginView();
        String email = loginView.getEmail();
        String password = loginView.getPassword();
        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(gui, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }        
        if (model.authenticateUser(email, password)) {
            JOptionPane.showMessageDialog(gui, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            currentUser = model.getCurrentUser(email);
            
            // Load academic history
            List<Map<String, String>> academicHistory = model.loadAcademicHistory();
    
            // Switch to StudentView and populate data
        gui.switchToStudentView(currentUser);
        studentView = gui.getStudentView(); // Update the studentView reference
        populateAcademicTable(academicHistory);               
        } else {
            JOptionPane.showMessageDialog(gui, "Invalid email or password.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void handleUploadGrades() {
        StudentView studentView = gui.getStudentView();
        if (studentView == null) {
            System.err.println("StudentView is not initialized!");
            return;
        }
        String[] inputData = showGradeInputDialog("Enter Grade Details");
        if (inputData != null) {
            studentView.getAcademicTableModel().addRow(inputData);
            JOptionPane.showMessageDialog(gui, "Grade added successfully!");
        }
    }
    private String[] showGradeInputDialog(String title) {
        JPanel inputPanel = new JPanel(new GridLayout(8, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    
        // Create text fields for input
        JTextField yearField = new JTextField();
        JTextField semesterField = new JTextField(); // New field for semester
        JTextField subjectField = new JTextField();
        JTextField gradeField = new JTextField();
        JTextField fastReadField = new JTextField();
        JTextField fastMathField = new JTextField();
        JTextField satField = new JTextField();
        JTextField actField = new JTextField();
    
        // Add labels and fields to the panel
        inputPanel.add(new JLabel("Year:"));
        inputPanel.add(yearField);
        inputPanel.add(new JLabel("Semester:"));
        inputPanel.add(semesterField); // Semester input
        inputPanel.add(new JLabel("Course/Subject:"));
        inputPanel.add(subjectField);
        inputPanel.add(new JLabel("Grade:"));
        inputPanel.add(gradeField);
        inputPanel.add(new JLabel("FAST-READ:"));
        inputPanel.add(fastReadField);
        inputPanel.add(new JLabel("FAST-MATH:"));
        inputPanel.add(fastMathField);
        inputPanel.add(new JLabel("SAT:"));
        inputPanel.add(satField);
        inputPanel.add(new JLabel("ACT:"));
        inputPanel.add(actField);
    
        // Show the dialog
        int result = JOptionPane.showConfirmDialog(
            null,
            inputPanel,
            title,
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );
    
        if (result == JOptionPane.OK_OPTION) {
            // Required fields
            String year = yearField.getText().trim();
            String semester = semesterField.getText().trim();
            String subject = subjectField.getText().trim();
            String grade = gradeField.getText().trim();
    
            if (year.isEmpty() || semester.isEmpty() || subject.isEmpty() || grade.isEmpty()) {
                JOptionPane.showMessageDialog(
                    null,
                    "Year, Semester, Course/Subject, and Grade are required fields.",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE
                );
                return null; // Return null to indicate invalid input
            }
    
            // Optional fields
            String fastRead = fastReadField.getText().trim();
            String fastMath = fastMathField.getText().trim();
            String sat = satField.getText().trim();
            String act = actField.getText().trim();
    
            // Return the entered data as a row
            return new String[]{
                year,
                semester,
                subject,
                grade,
                fastRead.isEmpty() ? " " : fastRead,
                fastMath.isEmpty() ? " " : fastMath,
                sat.isEmpty() ? " " : sat,
                act.isEmpty() ? " " : act
            };
        }
        return null; // Return null if the dialog is canceled
    }
   
    
    private void handleHelp() {
        JOptionPane.showMessageDialog(gui, "For help, contact support@academicpathwayplanner.com", 
                                      "Help", JOptionPane.INFORMATION_MESSAGE);
    }
  
   	private void handleDelete() {
   	    StudentView studentView = gui.getStudentView();
   	    if (studentView == null) {
   	        System.err.println("StudentView is not initialized!");
   	        return;
   	    }
   	    JTable academicTable = studentView.getAcademicTable();
   	    DefaultTableModel tableModel = studentView.getAcademicTableModel();

    	    // Get the selected row
    	    int selectedRow = academicTable.getSelectedRow();

    	    if (selectedRow == -1) {
    	        // No row is selected
    	        JOptionPane.showMessageDialog(
    	            gui,
    	            "Please select a row to delete.",
    	            "No Selection",
    	            JOptionPane.WARNING_MESSAGE
    	        );
    	    } else {
    	        // Confirm deletion
    	        int confirm = JOptionPane.showConfirmDialog(
    	            gui,
    	            "Are you sure you want to delete the selected row?",
    	            "Confirm Deletion",
    	            JOptionPane.YES_NO_OPTION
    	        );

    	        if (confirm == JOptionPane.YES_OPTION) {
    	            // Remove the row
    	            tableModel.removeRow(selectedRow);
    	            JOptionPane.showMessageDialog(gui, "Row deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    	        }
    	    }
    	}

    private void handleSave() {
            try {
                // Ensure studentView is not null
                if (studentView == null) {
                    studentView = gui.getStudentView();
                }
        
                // Get the academic table model
                DefaultTableModel academicTableModel = studentView.getAcademicTableModel();
                int rowCount = academicTableModel.getRowCount();
                int colCount = academicTableModel.getColumnCount();
                String[][] academicData = new String[rowCount][colCount];
        
                // Extract data from the table model
                for (int i = 0; i < rowCount; i++) {
                    for (int j = 0; j < colCount; j++) {
                        Object cellValue = academicTableModel.getValueAt(i, j);
                        academicData[i][j] = (cellValue != null) ? cellValue.toString() : "N/A"; // Handle null values
                    }
                }
        
                // Save the academic history
                if (model.saveAcademicHistory(academicData)) {
                    JOptionPane.showMessageDialog(gui, "Academic history saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(gui, "Failed to save academic history.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(gui, "Error saving academic history: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
        private void handleEvaluate() {
            try {
                // Extract SAT/ACT scores and calculate average
                DefaultTableModel academicTableModel = studentView.getAcademicTableModel();
                int rowCount = academicTableModel.getRowCount();
                int totalScore = 0;
                int scoreCount = 0;
        
                for (int i = 0; i < rowCount; i++) {
                    String satScore = (String) academicTableModel.getValueAt(i, 6);
                    String actScore = (String) academicTableModel.getValueAt(i, 7);
        
                    if (satScore != null && !satScore.trim().isEmpty() && !satScore.equalsIgnoreCase("N/A")) {
                        totalScore += Integer.parseInt(satScore);
                        scoreCount++;
                    }
                    if (actScore != null && !actScore.trim().isEmpty() && !actScore.equalsIgnoreCase("N/A")) {
                        totalScore += Integer.parseInt(actScore) * 40;
                        scoreCount++;
                    }
                }
        
                int averageScore = (scoreCount > 0) ? (totalScore / scoreCount) : 0;
                System.out.println("Average SAT/ACT equivalent score calculated: " + averageScore);
        
                // Fetch colleges
                List<Map<String, String>> allColleges = model.fetchAllColleges();
                System.out.println("Fetched " + allColleges.size() + " colleges from the API.");
        
                // Filter colleges
                List<Map<String, String>> filteredColleges = filterCollegesByTestScores(allColleges, averageScore);
        
                if (filteredColleges.isEmpty()) {
                    System.out.println("No colleges passed the filter. Displaying all colleges instead.");
                    filteredColleges = allColleges; // Fallback to all colleges
                }
        
                // Populate college table
                studentView.populateCollegeRecommendations(filteredColleges);
        
                JOptionPane.showMessageDialog(gui, "Evaluation completed. Recommended colleges are listed.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(gui, "Error during evaluation: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }

        private List<Map<String, String>> filterCollegesByTestScores(List<Map<String, String>> colleges, int averageSAT) {
            System.out.println("Applying SAT score filter...");
            return colleges.stream()
                .filter(college -> {
                    String medianSat = college.get("Median SAT Score");
                    if (medianSat == null || medianSat.isEmpty() || medianSat.equalsIgnoreCase("N/A")) {
                        System.out.println("No SAT score available, including college: " + college.get("Name"));
                        return true; // Include colleges without SAT scores
                    }
                    try {
                        int satScore = Integer.parseInt(medianSat);
                        return satScore >= averageSAT - 100 && satScore <= averageSAT + 100;
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid SAT score for college: " + college.get("Name"));
                        return false; // Exclude colleges with invalid SAT scores
                    }
                })
                .peek(college -> System.out.println("College passed filter: " + college.get("Name")))
                .collect(Collectors.toList());
        }

        private void populateAcademicTable(List<Map<String, String>> academicHistory) {
            StudentView studentView = gui.getStudentView();
            DefaultTableModel tableModel = studentView.getAcademicTableModel();
        
            // Clear existing data
            tableModel.setRowCount(0);
        
            // Populate table with academic history
            for (Map<String, String> record : academicHistory) {
                String[] rowData = {
                    record.getOrDefault("Year", ""),
                    record.getOrDefault("Semester", ""),
                    record.getOrDefault("CourseName", ""),
                    record.getOrDefault("Grade", ""),
                    record.getOrDefault("FAST_READ", ""),
                    record.getOrDefault("FAST_MATH", ""),
                    record.getOrDefault("SAT", ""),
                    record.getOrDefault("ACT", "")
                };
                tableModel.addRow(rowData);
            }
        }

  
        private void handleGeneratePathway() {
            try {
                String selectedCollege = studentView.getSelectedCollege();
                if (selectedCollege == null) {
                    JOptionPane.showMessageDialog(gui, "Please select a college to generate a pathway.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
        
                // Generate pathway data for the selected college
                String[][] pathwayData = generateCollegePathway(selectedCollege);
        
                // Switch to PathwayView and populate data
                gui.switchToPathwayView(selectedCollege, pathwayData);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(gui, "Error generating pathway: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    
    private void handleBack() {
    	gui.switchToStudentView(currentUser);
    }
    
    private void handleConfirm() {
    	JOptionPane.showMessageDialog(gui, "Pathway confirmed!", "Success", JOptionPane.INFORMATION_MESSAGE);
        handleBack();
    }
    private void handleLogout() {
        int confirm = JOptionPane.showConfirmDialog(
            gui,
            "Are you sure you want to logout?",
            "Logout Confirmation",
            JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            gui.switchToLoginView();
        }

        
    }
    private void handleExportReport() {
        
    }

    private String[][] generateCollegePathway(String collegeName) {
        return new String[][]{
            {"2024", "Apply to " + collegeName, "Submit applications and financial aid forms", "Application Complete"},
            {"2025", "Enroll and Start Classes", "Begin coursework for selected major", "First Semester Completed"},
            {"2026", "Participate in Internships", "Gain practical experience", "Internship Secured"},
            {"2027", "Graduate", "Complete degree requirements", "Graduation"}
        };
    }
    
    // private String generateReportContent() {
    //     // Collect data from the Model and generate the report content
    //     return "Your Career Pathway Report";
    // }    

    // private boolean exportReport() {
    //     // Simulate exporting a report (replace with actual implementation)
    //     return true; // Assume the export is always successful for now
    // }
    
}

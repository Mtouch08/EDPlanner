package JavaProjects.ED_Planner;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import java.util.Queue;

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
    private DefaultTableModel tableModel;
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
            	handleBack();
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
        JPanel inputPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JTextField yearField = new JTextField();
        JTextField subjectField = new JTextField();
        JTextField gradeField = new JTextField();
        JTextField satField = new JTextField();
        JTextField actField = new JTextField();
        JTextField fastReadField = new JTextField();
        JTextField fastMathField = new JTextField();
        inputPanel.add(new JLabel("Year:"));
        inputPanel.add(yearField);
        inputPanel.add(new JLabel("Course/Subject:"));
        inputPanel.add(subjectField);
        inputPanel.add(new JLabel("Grade:"));
        inputPanel.add(gradeField);
        inputPanel.add(new JLabel("SAT:"));
        inputPanel.add(satField);
        inputPanel.add(new JLabel("ACT:"));
        inputPanel.add(actField);
        inputPanel.add(new JLabel("FAST READING:"));
       	inputPanel.add(fastReadField);
        inputPanel.add(new JLabel("FAST MATH:"));
        inputPanel.add(fastMathField);
        int result = JOptionPane.showConfirmDialog(
                null,
                inputPanel,
                title,
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );
        if (result == JOptionPane.OK_OPTION) {
            try {
                String year = yearField.getText();
                String subject = subjectField.getText();
                String grade = gradeField.getText();
                String sat = satField.getText();
                String act = actField.getText();
                String fastRead = fastReadField.getText();
                String fastMath = fastMathField.getText();
                // Validate input
                if (year.isEmpty() || subject.isEmpty() || grade.isEmpty() || sat.isEmpty() || act.isEmpty() || fastRead.isEmpty() || fastMath.isEmpty()) {
                    throw new IllegalArgumentException("All fields must be filled.");
                }
               // Return data as a row
                return new String[]{year, subject, grade, sat,act,fastRead,fastMath};
            } catch (Exception e) {
                JOptionPane.showMessageDialog(
                        null,
                        "Invalid input: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
        return null; // Return null if canceled
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

    private void handleSave(){
         studentView = gui.getStudentView();
        if (studentView == null) {
            System.err.println("StudentView is not initialized!");
            return;
        }
    
        // Retrieve academic history from the table
         tableModel = studentView.getAcademicTableModel();
        int rowCount = tableModel.getRowCount();
        int colCount = tableModel.getColumnCount();
        String[][] academicData = new String[rowCount][colCount];
    
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                academicData[i][j] = tableModel.getValueAt(i, j).toString();
            }
        }
    
        // Save the academic data
        if (model.saveAcademicHistory(academicData)) {
            JOptionPane.showMessageDialog(gui, "Academic history saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(gui, "Failed to save academic history.", "Error", JOptionPane.ERROR_MESSAGE);
        }  
    }
    private void handleEvaluate() {
            try {
                Queue<String> recommendations = model.getCareerRecommendationsFromAPI();
                StringBuilder recList = new StringBuilder("Career Recommendations:\n");
                for (String rec : recommendations) {
                    recList.append(rec).append("\n");
                }
                JOptionPane.showMessageDialog(gui, recList.toString(), "Recommendations", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(gui, "Error fetching recommendations: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        private void populateAcademicTable(List<Map<String, String>> academicHistory) {
             studentView = gui.getStudentView();
            DefaultTableModel tableModel = studentView.getAcademicTableModel();
        
            // Clear existing data
            tableModel.setRowCount(0);
        
            // Populate table with academic history
            for (Map<String, String> record : academicHistory) {
                String[] rowData = {
                    record.getOrDefault("Year", ""),
                    record.getOrDefault("CourseName", ""),
                    record.getOrDefault("Grade", ""),
                    record.getOrDefault("SAT", ""),
                    record.getOrDefault("ACT", ""),
                    record.getOrDefault("FAST_READING", ""),
                    record.getOrDefault("FAST_MATH", "")
                };
                tableModel.addRow(rowData);
            }
        }

  
        private void handleGeneratePathway() {
            String career = "Software Developer";
            List<String> pathway = model.bfsPathway(career);
            String[][] pathwayData = new String[pathway.size()][4];
            for (int i = 0; i < pathway.size(); i++) {
                pathwayData[i][0] = String.valueOf(2023 + i);
                pathwayData[i][1] = pathway.get(i);
                pathwayData[i][2] = i > 0 ? pathway.get(i - 1) : "None";
                pathwayData[i][3] = "Complete coursework and gain internship experience";
            }
            gui.switchToPathwayView(career, pathwayData);
        }
    
    private void handleBack() {
    	gui.switchToStudentView(currentUser);
    }
    
    private void handleConfirm() {
    	JOptionPane.showMessageDialog(gui, "Pathway confirmed!", "Success", JOptionPane.INFORMATION_MESSAGE);
        handleBack();
    }

    private void handleExportReport() {
        
    }
    
    private String generateReportContent() {
        // Collect data from the Model and generate the report content
        return "Your Career Pathway Report";
    }    

    private boolean exportReport() {
        // Simulate exporting a report (replace with actual implementation)
        return true; // Assume the export is always successful for now
    }
    
}

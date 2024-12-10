package JavaProjects.ED_Planner;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javafx.application.Platform;

public class StudentView extends JPanel {

    private JTextField nameField, gradeField, schoolField;
    private JPanel buttonPanel, profilePanel, academicPanel, assessPanel;
    private JLabel nameLabel, gradeLabel, schoolLabel;
    private JButton uploadGradeTestButton, saveButton,evaluateButton, genPathButton, logoutButton, deleteButton;
    private JTable academicTable;
    private DefaultTableModel academicTableModel;
    private JTable collegeTable;
    private DefaultTableModel collegeTableModel;
    
    

    public StudentView() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        addProfilePanel();
        addAssessPanel();  // Updated to embed O*NET Interest Profiler
        
        addAcademicPanel();
        addButtonPanel();
        addTableSelectionListener();
    }

    private void addProfilePanel() {
        profilePanel = new JPanel();
        profilePanel.setLayout(new GridLayout(2, 4, 10, 10));
        profilePanel.setBorder(BorderFactory.createTitledBorder("Student Profile"));
        profilePanel.setBackground(Color.WHITE);

        nameLabel = UIComponentFactory.createLabel("Name:", 14, JLabel.CENTER);
        nameField = UIComponentFactory.createTextField(" ", 200, 30);
        nameField.setEditable(false);

        gradeLabel = UIComponentFactory.createLabel("Grade Level:", 14, JLabel.CENTER);
        gradeField = UIComponentFactory.createTextField(" ", 200, 30);
        gradeField.setEditable(false);

        schoolLabel = UIComponentFactory.createLabel("School:", 14, JLabel.CENTER);
        schoolField = UIComponentFactory.createTextField(" ", 200, 30);
        schoolField.setEditable(false);

        profilePanel.add(nameLabel);
        profilePanel.add(nameField);
        profilePanel.add(schoolLabel);
        profilePanel.add(gradeLabel);
        profilePanel.add(gradeField);
        profilePanel.add(schoolField);
        add(profilePanel, BorderLayout.NORTH);
    }

    private void addAssessPanel() {
        assessPanel = new JPanel(new BorderLayout());
        assessPanel.setBorder(BorderFactory.createTitledBorder("Career Aptitude Assessment"));
        assessPanel.setPreferredSize(new Dimension(900, 600));
        assessPanel.setBackground(Color.WHITE);

        final JFXPanel fxPanel = new JFXPanel();
        fxPanel.setPreferredSize(new Dimension(800, 600));
        assessPanel.add(fxPanel, BorderLayout.CENTER);

        Platform.runLater(() -> {
            WebView webView = new WebView();
            webView.getEngine().loadContent(
                "<!DOCTYPE html>" +
                "<html lang='en'>" +
                "<head>" +
                "<script src='https://services.onetcenter.org/embed/ip.js?client=edplanner'></script>" +
                "</head>" +
                "<body>" +
                "<div class='embed-onet-ip'></div>" +
                "</body>" +
                "</html>"
            );
            fxPanel.setScene(new Scene(webView));
        });
        add(assessPanel, BorderLayout.WEST);
    }

    
    private void addAcademicPanel() {
        academicPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        academicPanel.setBorder(BorderFactory.createTitledBorder("Academic History and Recommendations"));

        // Academic History Table
        String[] academicColumnNames = {"Year", "Semester", "Course Name", "Grade", "FAST-READ", "FAST-MATH", "SAT", "ACT"};
        academicTableModel = new DefaultTableModel(academicColumnNames, 0);
        academicTable = new JTable(academicTableModel);
        academicTable.setRowHeight(25);
        JScrollPane academicScrollPane = new JScrollPane(academicTable);
        academicScrollPane.setBorder(BorderFactory.createTitledBorder("Academic History"));

        // College Recommendations Table
    String[] collegeColumnNames = {"Name", "In-State Tuition", "Out-of-State Tuition", "Completion Rate", "Median Earnings"};
    collegeTableModel = new DefaultTableModel(collegeColumnNames, 0);
    collegeTable = new JTable(collegeTableModel);
    collegeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Allow single row selection
    JScrollPane collegeScrollPane = new JScrollPane(collegeTable);
    collegeScrollPane.setBorder(BorderFactory.createTitledBorder("Recommended Colleges"));

        // Add components to the panel
        academicPanel.add(academicScrollPane);
        academicPanel.add(collegeScrollPane);

        // Add the panel to the main layout
        add(academicPanel, BorderLayout.CENTER);
    }


    private void addButtonPanel() {
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 5, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        formatButtons();
        addButtons();
        setButtonCommands();
        add(buttonPanel, BorderLayout.SOUTH);
    }
    public void addButtons() {
    	buttonPanel.add(uploadGradeTestButton);
    	buttonPanel.add(saveButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(evaluateButton);
        buttonPanel.add(genPathButton);
        buttonPanel.add(logoutButton);
        
    }
    
    public void setButtonCommands() {
    	uploadGradeTestButton.setActionCommand("Upload Grades/Test");
    	saveButton.setActionCommand("Save");
    	deleteButton.setActionCommand("Delete");
    	evaluateButton.setActionCommand("Evaluate");
    	genPathButton.setActionCommand("Generate Pathway");
    	logoutButton.setActionCommand("Logout");
    }
    public void formatButtons() {
    	uploadGradeTestButton = UIComponentFactory.createButton("Upload Grades", 200, 40, new Color(63, 81, 181));
        saveButton = UIComponentFactory.createButton("Save", 200, 40, new Color(34, 139, 34)); // New Save button
        deleteButton = UIComponentFactory.createButton("Delete Selected Row", 200, 30, Color.RED);
        evaluateButton = UIComponentFactory.createButton("Evaluate", 200, 40, new Color(63, 81, 181));
        genPathButton = UIComponentFactory.createButton("Generate Pathway", 200, 40, new Color(63, 81, 181));
        genPathButton.setEnabled(false);
        logoutButton = UIComponentFactory.createButton("LOGOUT", 200, 40, new Color(63, 81, 181));
    }

    public JButton getUploadGradesButton()
	{
		return uploadGradeTestButton;
	}

	public JButton getDeleteRowButton()
	{
		return deleteButton;
	}
    public JButton getEvaluateButton() {
        return evaluateButton;
    }
    public JButton getSaveButton() {
    	return saveButton;
    }

    public JButton getGeneratePathwayButton() {
        return genPathButton;
    }

    public JButton getLogoutButton() {
        return logoutButton;
    }

    public void setName(String name) {
        nameField.setText(name);
    }

    public void setGrade(String grade) {
    	gradeField.setText(grade);
    }
    public void setSchool(String school) {
    	schoolField.setText(school);
    }

    public JPanel getAssessPanel() {
        return assessPanel;
    }

	public DefaultTableModel getAcademicTableModel() {
        if (academicTableModel == null) {
            String[] academicColumnNames = {"Year", "Semester", "Course Name", "Grade", "FAST-READ", "FAST-MATH", "SAT", "ACT"};
            academicTableModel = new DefaultTableModel(academicColumnNames, 0);
        }
        return academicTableModel;
    }

	public boolean deleteSelectedRow()
	{
		return false;
	}

	public JTable getAcademicTable()
	{
		return academicTable;
	}

    public void populateCollegeRecommendations(List<Map<String, String>> colleges) {
        collegeTableModel.setRowCount(0); // Clear existing rows
        for (Map<String, String> college : colleges) {
            collegeTableModel.addRow(new Object[]{
                college.get("Name"),
                college.get("In-State Tuition"),
                college.get("Out-of-State Tuition"),
                college.get("Completion Rate"),
                college.get("Median Earnings")
            });
        }
    }

    // Retrieve the selected college from the table
    public String getSelectedCollege() {
        int selectedRow = collegeTable.getSelectedRow();
        if (selectedRow == -1) {
            return null; // No selection
        }
        return (String) collegeTableModel.getValueAt(selectedRow, 0); // Return the college name
    }

    private void addTableSelectionListener() {
        collegeTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                // Enable the Generate Pathway button if a row is selected
                int selectedRow = collegeTable.getSelectedRow();
                genPathButton.setEnabled(selectedRow != -1); // Enable if a row is selected
            }
        });

}
}

    

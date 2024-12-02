package JavaProjects.ED_Planner;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

public class GUI extends JFrame
{
	private JPanel mainPanel;
    private JButton studentButton, educatorButton,createButton;
    private JLabel titleLabel, subtitleLabel, footerLabel;
    private Controller controller;
    private LoginView loginView;
    private CreateAccountView createAccountView;
    private StudentView studentView;
    private PathwayView pathwayView;
    
    
    public GUI() {
        initializeUI();
    }

    private void initializeUI() {
        setupFrame();
        addMainPanel();
        setupController();
    }

    private void setupFrame() {
        setTitle("Academic Pathway Planner");
        setSize(400, 500);
        setLocationRelativeTo(null); // Center the frame on the screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void addMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(150, 50, 150, 50));

        addTitle();
        addSubtitle();
        addButtonPanel();
        addFooter();

        add(mainPanel, BorderLayout.CENTER);
        setVisible(true); // Make the frame visible
    }

    private void addTitle() {
    	titleLabel = UIComponentFactory.createLabel("Sign in", 24, JLabel.CENTER);
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(10));//spacer
    }

    private void addSubtitle() {
    	subtitleLabel = UIComponentFactory.createLabel("Which type of account do you want to sign in to?", 14, JLabel.CENTER);
        mainPanel.add(subtitleLabel);
        mainPanel.add(Box.createVerticalStrut(20)); 
    }

    private void addButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1, 10, 10));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        studentButton = UIComponentFactory.createButton("Student", 200, 40, new Color(63, 81, 181));
        educatorButton = UIComponentFactory.createButton("Educator", 200, 40, new Color(63, 81, 181));
        buttonPanel.add(studentButton);
        buttonPanel.add(educatorButton);
        mainPanel.add(buttonPanel);
        mainPanel.add(Box.createVerticalStrut(20)); 
    }

    private void addFooter() {
        JPanel footerPanel = new JPanel();
        footerPanel.setLayout(new BoxLayout(footerPanel, BoxLayout.Y_AXIS));
        footerLabel = UIComponentFactory.createLabel("Don’t have an account?", 12, JLabel.CENTER);
        footerPanel.add(footerLabel);
        createButton = UIComponentFactory.createButton("Create Account", 200, 40, new Color(63, 81, 181));
        createButton.setForeground(new Color(63, 81, 181));
        createButton.setBorderPainted(false);
        createButton.setContentAreaFilled(false);
        createButton.setFocusPainted(false);
        createButton.setActionCommand("Create");
        createButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        footerPanel.add(createButton);
        mainPanel.add(footerPanel);
    }

    private void setupController() {
        controller = new Controller(this);
        studentButton.addActionListener(controller);
        educatorButton.addActionListener(controller);
        createButton.addActionListener(controller);
    }

    public void switchToLoginView() {
        if (loginView == null) {
            loginView = new LoginView();
            // Attach the controller to LoginView buttons
            loginView.getLoginButton().addActionListener(controller);
            loginView.getHelpButton().addActionListener(controller);
        }
        getContentPane().removeAll();
        add(loginView, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public LoginView getLoginView() {
        return loginView;
    }
    
    public void switchToCreateAccountView() {
        if (createAccountView == null) {
            createAccountView = new CreateAccountView();
            createAccountView.getCreateAccButton().addActionListener(controller);
        }
        getContentPane().removeAll();
        add(createAccountView, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
    
    public void switchToStudentView(User currentUser) {
        if (studentView == null) {
            studentView = new StudentView();
            studentView.setName(currentUser.getUsername());
            studentView.setGrade(currentUser.getGradeLevel());
            studentView.setSchool(currentUser.getSchool());
            studentView.getUploadGradesButton().addActionListener(controller);
            studentView.getEvaluateButton().addActionListener(controller);
            studentView.getGeneratePathwayButton().addActionListener(controller);
            studentView.getLogoutButton().addActionListener(controller);
            studentView.getDeleteRowButton().addActionListener(controller);
            studentView.getSaveButton().addActionListener(controller);
        }
        
        getContentPane().removeAll();
        add(studentView);
        revalidate();
        repaint();
    }
    
    public void switchToPathwayView(String career, String[][] pathwayData) {
        if (pathwayView == null) {
            pathwayView = new PathwayView();
            pathwayView.getBackButton().addActionListener(controller);
            pathwayView.getConfirmButton().addActionListener(controller);
        }
        pathwayView.setCareerLabel(career);
        pathwayView.populatePathwayTable(pathwayData);
        getContentPane().removeAll();
        add(pathwayView, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

	public StudentView getStudentView()
	{
		return studentView;
	}
	
	public CreateAccountView getCreateAccountView() {
		return createAccountView;
	}
	
	public PathwayView getPathwayView() {
		return pathwayView;
	}
    public static void main( String[] args )
    {
        new GUI();
    }
}

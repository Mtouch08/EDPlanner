package JavaProjects.ED_Planner;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class CreateAccountView extends JPanel
{
	private JTextField emailField, nameField, schoolField, gradeLevelField;
	private JLabel titleLabel,emailLabel,nameLabel,schoolLabel,gradeLevLabel,passwordLabel;
    private JPasswordField passwordField;
    private JButton createAccButton;

    public CreateAccountView() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        addTitle("Create Account");
        addEmailField();
        addNameField();
        addSchoolField();
        addGradeLevelField();
        addPasswordField();
        addCreateAccountButton();
    }

    private void addTitle(String title) {
        titleLabel = UIComponentFactory.createLabel(title, 24, JLabel.CENTER);
        add(titleLabel);
        add(Box.createVerticalStrut(20)); // Spacer
    }

    private void addEmailField() {
        emailLabel = UIComponentFactory.createLabel("Email Address", 14, JLabel.CENTER);
        add(emailLabel);
        add(Box.createVerticalStrut(10));
        emailField = UIComponentFactory.createTextField("", 300, 30);
        add(emailField);
        add(Box.createVerticalStrut(20));
    }

    private void addNameField() {
        nameLabel = UIComponentFactory.createLabel("Name", 14, JLabel.CENTER);
        add(nameLabel);
        add(Box.createVerticalStrut(10));
        nameField = UIComponentFactory.createTextField("", 300, 30);
        add(nameField);
        add(Box.createVerticalStrut(20));
    }
    private void addSchoolField() {
    	schoolLabel = UIComponentFactory.createLabel("School", 14, JLabel.CENTER);
        add(schoolLabel);
        add(Box.createVerticalStrut(10));
        schoolField = UIComponentFactory.createTextField("", 300, 30);
        add(schoolField);
        add(Box.createVerticalStrut(20));
    }
    private void addGradeLevelField() {
    	gradeLevLabel = UIComponentFactory.createLabel("Grade Level", 14, JLabel.CENTER);
        add(gradeLevLabel);
        add(Box.createVerticalStrut(10));
        gradeLevelField = UIComponentFactory.createTextField("", 300, 30);
        add(gradeLevelField);
        add(Box.createVerticalStrut(20));
    }

    private void addPasswordField() {
        passwordLabel = UIComponentFactory.createLabel("Password", 14, JLabel.CENTER);
        add(passwordLabel);
        add(Box.createVerticalStrut(10));

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setMaximumSize(new Dimension(300, 30));
        add(passwordField);
        add(Box.createVerticalStrut(20));
    }

    private void addCreateAccountButton() {
        createAccButton = UIComponentFactory.createButton("Create Account", 200, 40, new Color(63, 81, 181));
        createAccButton.setActionCommand("CreateAccount");
        createAccButton.setAlignmentX(CENTER_ALIGNMENT);
        add(createAccButton);
    }

    public String getEmail() {
        return emailField.getText();
    }

    public String getName() {
        return nameField.getText();
    }
    public String getSchool() {
    	return schoolField.getText();
    }
    public String getGradeLevel() {
    	return gradeLevelField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public JButton getCreateAccButton() {
        return createAccButton;
    }
}

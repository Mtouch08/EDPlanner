package JavaProjects.ED_Planner;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginView extends JPanel
{
	private JTextField emailField;
    private JPasswordField passField;
    private JCheckBox rememberCheckBox;
    private JButton loginButton,helpButton;
   

    public LoginView() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        addLogo();
        addTitle("Login");
        addEmailField();
        addPassField();
        addRememberCheckbox();
        addLoginButton();
        addHelpButton();
    }

    private void addLogo() {
        // Add a placeholder logo (replace with an icon if available)
        JLabel logoLabel = new JLabel("🌰", JLabel.CENTER); // Replace with an image if needed
        logoLabel.setFont(new Font("Arial", Font.PLAIN, 50)); // Placeholder logo
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(logoLabel);
        add(Box.createVerticalStrut(20)); // Spacer
    }

    private void addTitle(String title) {
        JLabel titleLabel = UIComponentFactory.createLabel(title, 24, JLabel.CENTER);
        add(titleLabel);
        add(Box.createVerticalStrut(20)); // Spacer
    }

    private void addEmailField() {
        emailField = UIComponentFactory.createTextField("Email Address", 300, 30);
        add(emailField);
        add(Box.createVerticalStrut(10)); // Spacer
    }
    
    private void addPassField() {
    	passField = UIComponentFactory.createPassField("Password", 300, 30);
        add(passField);
        add(Box.createVerticalStrut(10)); // Spacer
    }

    private void addRememberCheckbox() {
        rememberCheckBox = new JCheckBox("Remember my email address");
        rememberCheckBox.setFont(new Font("Arial", Font.PLAIN, 14));
        rememberCheckBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(rememberCheckBox);
        add(Box.createVerticalStrut(20)); // Spacer
    }

    private void addLoginButton() {
        loginButton = UIComponentFactory.createButton("Login", 150, 40, new Color(63, 81, 181));
        loginButton.setActionCommand("Login");
        loginButton.setAlignmentX(CENTER_ALIGNMENT);
        add(loginButton);
        add(Box.createVerticalStrut(20)); // Spacer
    }

    private void addHelpButton() {
        helpButton = UIComponentFactory.createButton("Need help logging in?", 250, 40, new Color(63, 81, 181));
        helpButton.setForeground(new Color(63, 81, 181)); // Hyperlink color
        helpButton.setBorderPainted(false);
        helpButton.setContentAreaFilled(false);
        helpButton.setFocusPainted(false);
        helpButton.setAlignmentX(CENTER_ALIGNMENT);
        helpButton.setActionCommand("Help");
        add(helpButton);
        add(Box.createVerticalStrut(10)); // Spacer
    }
    
    public String getEmail() {
        return emailField.getText();
    }

    public String getPassword() {
        return new String(passField.getPassword());
    }

    public boolean isRememberChecked() {
        return rememberCheckBox.isSelected();
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public JButton getHelpButton() {
        return helpButton;
    }
}

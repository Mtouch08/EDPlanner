package JavaProjects.ED_Planner;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class UIComponentFactory
{
	public static JTextField createTextField(String placeholder, int width, int height) {
        JTextField textField = new JTextField(placeholder);
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setMaximumSize(new Dimension(width, height));
        return textField;
    }
	
	public static JPasswordField createPassField(String placeholder, int width, int height) {
		JPasswordField passField = new JPasswordField(placeholder);
		passField.setFont(new Font("Arial", Font.PLAIN, 14));
		passField.setMaximumSize(new Dimension(width, height));
		return passField;
	}
	
	public static JLabel createLabel(String text, int fontSize, int alignment) {
        JLabel label = new JLabel(text, alignment);
        label.setFont(new Font("Arial", Font.PLAIN, fontSize));
        label.setAlignmentX(Component.CENTER_ALIGNMENT); // Default alignment
        return label;
    }

    public static JButton createButton(String text, int width, int height, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setMaximumSize(new Dimension(width, height));
        return button;
    }

    public static void addHyperlinkEffect(JLabel label, String url) {
        label.setCursor(new Cursor(Cursor.HAND_CURSOR));
        label.setForeground(new Color(33, 150, 243));
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI(url));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                label.setForeground(new Color(13, 71, 161));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                label.setForeground(new Color(33, 150, 243));
            }
        });
    }
}


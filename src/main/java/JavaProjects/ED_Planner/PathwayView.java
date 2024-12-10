package JavaProjects.ED_Planner;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class PathwayView extends JPanel {

    private JLabel careerLabel;
    private JTable pathwayTable;
    private DefaultTableModel pathwayTableModel;
    private JButton backButton, confirmButton;

    public PathwayView() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        addHeader();
        addPathwayTable();
        addNavigationButtons();
    }

    private void addHeader() {
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        careerLabel = new JLabel("Pathway for: ");
        careerLabel.setFont(new Font("Arial", Font.BOLD, 18));

        headerPanel.add(careerLabel);
        add(headerPanel, BorderLayout.NORTH);
    }

    private void addPathwayTable() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Recommended Pathway"));

        String[] columnNames = {"Year", "Milestone / Course", "Details", "Status"};
        pathwayTableModel = new DefaultTableModel(columnNames, 0);
        pathwayTable = new JTable(pathwayTableModel);

        JScrollPane tableScrollPane = new JScrollPane(pathwayTable);
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);

        add(tablePanel, BorderLayout.CENTER);
    }

    private void addNavigationButtons() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setActionCommand("Back");

        confirmButton = new JButton("Confirm");
        confirmButton.setFont(new Font("Arial", Font.BOLD, 14));
        confirmButton.setActionCommand("Confirm");

        buttonPanel.add(backButton);
        buttonPanel.add(confirmButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Dynamically populate pathway table
    public void populatePathwayTable(String[][] pathwayData) {
        pathwayTableModel.setRowCount(0); // Clear existing rows
        for (String[] row : pathwayData) {
            pathwayTableModel.addRow(row);
        }
    }

    public void setCareerLabel(String pathwayTitle) {
        careerLabel.setText("Pathway for: " + pathwayTitle);
    }

    public JTable getPathwayTable() {
        return pathwayTable;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public JButton getConfirmButton() {
        return confirmButton;
    }
}



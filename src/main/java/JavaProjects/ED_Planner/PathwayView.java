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
    private JButton backButton, confirmButton;
    private DefaultTableModel pathwayTableModel;

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

        careerLabel = new JLabel("Career Pathway for: ");
        careerLabel.setFont(new Font("Arial", Font.BOLD, 18));

        headerPanel.add(careerLabel);
        add(headerPanel, BorderLayout.NORTH);
    }

    private void addPathwayTable() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Recommended Classes and Milestones"));

        String[] columnNames = {"Year", "Class", "Prerequisites", "Milestones"};
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

    // Methods to populate data dynamically
    public void setCareerLabel(String career) {
        careerLabel.setText("Career Pathway for: " + career);
    }

    public void populatePathwayTable(String[][] pathwayData) {
        pathwayTableModel.setRowCount(0); // Clear existing rows
        for (String[] row : pathwayData) {
            pathwayTableModel.addRow(row);
        }
    }
    
//    public void displayCollegeData(List<Map<String, String>> collegeData) {
//        DefaultTableModel collegeTableModel = new DefaultTableModel(
//            new String[]{"Name", "Tuition", "Completion Rate", "Median Earnings"}, 0
//        );
//
//        for (Map<String, String> college : collegeData) {
//            collegeTableModel.addRow(new Object[]{
//                college.get("Name"),
//                college.get("Tuition"),
//                college.get("Completion Rate"),
//                college.get("Median Earnings")
//            });
//        }
//
//        JTable collegeTable = new JTable(collegeTableModel);
//        JScrollPane scrollPane = new JScrollPane(collegeTable);
//
//        JPanel collegePanel = new JPanel(new BorderLayout());
//        collegePanel.setBorder(BorderFactory.createTitledBorder("Recommended Colleges"));
//        collegePanel.add(scrollPane, BorderLayout.CENTER);
//
//        // Clear existing panel and add the college data
//        removeAll();
//        add(collegePanel, BorderLayout.CENTER);
//        revalidate();
//        repaint();
//    }

    public String[][] getPathwayTableData() {
        int rowCount = pathwayTableModel.getRowCount();
        int colCount = pathwayTableModel.getColumnCount();
        String[][] data = new String[rowCount][colCount];

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                data[i][j] = pathwayTableModel.getValueAt(i, j).toString();
            }
        }
        return data;
    }

    // Getters for controller integration
    public JButton getBackButton() {
        return backButton;
    }

    public JButton getConfirmButton() {
        return confirmButton;
    }

    public JTable getPathwayTable() {
        return pathwayTable;
    }
}



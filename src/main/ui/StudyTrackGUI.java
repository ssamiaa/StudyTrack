package ui;

import javax.swing.*;
import java.awt.*;

public class StudyTrackGUI extends JFrame {
    private JPanel coursesPanel;
    private JPanel mainDisplayPanel;
    private JList<String> courseList;  
    private DefaultListModel<String> courseListModel;

    public StudyTrackGUI() {
        super("StudyTrack - Course Progress Tracker");

        // Set up the main frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        // Initialize and add the courses panel
        setupCoursesPanel();

        // Initialize and add the main display panel
        setupMainDisplayPanel();

        // Add panels to the main frame
        add(coursesPanel, BorderLayout.WEST);
        add(mainDisplayPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void setupCoursesPanel() {
        coursesPanel = new JPanel();
        coursesPanel.setLayout(new BoxLayout(coursesPanel, BoxLayout.Y_AXIS));
        coursesPanel.setPreferredSize(new Dimension(200, getHeight()));
        coursesPanel.setBackground(new Color(200, 255, 200));

        // Add "COURSES" label
        JLabel coursesLabel = new JLabel("COURSES");
        coursesLabel.setFont(new Font("Serif", Font.BOLD, 20));
        coursesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        coursesPanel.add(coursesLabel);

        // Add button for adding new courses
        JButton addCourseButton = new JButton("+ Add Courses");
        addCourseButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addCourseButton.addActionListener(e -> addNewCourse());
        coursesPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        coursesPanel.add(addCourseButton);

        // Add list to display courses
        courseListModel = new DefaultListModel<>();
        courseList = new JList<>(courseListModel);
        JScrollPane courseScrollPane = new JScrollPane(courseList);
        coursesPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        coursesPanel.add(courseScrollPane);
    }

    private void setupMainDisplayPanel() {
        mainDisplayPanel = new JPanel();
        mainDisplayPanel.setBackground(new Color(100, 180, 100));
        mainDisplayPanel.setLayout(new BorderLayout());

        // Initial welcome message
        JLabel welcomeMessage = new JLabel("Welcome to StudyTrack!", SwingConstants.CENTER);
        welcomeMessage.setFont(new Font("Serif", Font.BOLD, 24));
        mainDisplayPanel.add(welcomeMessage, BorderLayout.CENTER);
    }

    private void addNewCourse() {
        // dummy right now
        String courseName = JOptionPane.showInputDialog(this, "Enter course name:");
        if (courseName != null && !courseName.trim().isEmpty()) {
            courseListModel.addElement(courseName);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudyTrackGUI());
    }
}

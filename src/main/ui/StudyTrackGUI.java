package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudyTrackGUI extends JFrame {
    private JPanel coursesPanel;
    private JPanel mainDisplayPanel;
    private JPanel courseListPanel;
    private JPanel topicsPanel;  // Panel to hold individual topics for a selected course

    private List<String> courses;  // List to keep track of course names
    private Map<String, List<String>> courseTopicsMap;  // Map to store topics for each course

    public StudyTrackGUI() {
        super("StudyTrack - Course Progress Tracker");

        courses = new ArrayList<>();
        courseTopicsMap = new HashMap<>();

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

        // Initialize panel to hold course buttons
        courseListPanel = new JPanel();
        courseListPanel.setLayout(new BoxLayout(courseListPanel, BoxLayout.Y_AXIS));
        courseListPanel.setBackground(new Color(200, 255, 200));
        coursesPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        coursesPanel.add(courseListPanel);
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
        // Prompt the user to enter a course name
        String courseName = JOptionPane.showInputDialog(this, "Enter course name:");
        if (courseName != null && !courseName.trim().isEmpty()) {
            courses.add(courseName);
            courseTopicsMap.put(courseName, new ArrayList<>());  // Initialize topic list for this course

            // Create a button for the new course
            JButton courseButton = new JButton(courseName);
            courseButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            courseButton.addActionListener(new CourseButtonListener(courseName));
            courseListPanel.add(courseButton);
            courseListPanel.revalidate();
        }
    }

    private void showCourseDetails(String courseName) {
        // Clear the main display panel
        mainDisplayPanel.removeAll();

        // Display course name and progress message
        JLabel courseLabel = new JLabel(courseName, SwingConstants.CENTER);
        courseLabel.setFont(new Font("Serif", Font.BOLD, 20));
        mainDisplayPanel.add(courseLabel, BorderLayout.NORTH);

        JLabel progressMessage = new JLabel("Track Your Progress", SwingConstants.CENTER);
        progressMessage.setFont(new Font("Serif", Font.ITALIC, 16));
        mainDisplayPanel.add(progressMessage, BorderLayout.CENTER);

        // Initialize topics panel
        topicsPanel = new JPanel();
        topicsPanel.setLayout(new BoxLayout(topicsPanel, BoxLayout.Y_AXIS));
        topicsPanel.setBackground(new Color(180, 220, 180));

        // Display topics for the selected course
        List<String> topics = courseTopicsMap.get(courseName);
        if (topics != null) {
            for (String topic : topics) {
                addTopicToPanel(topic);
            }
        }

        // Add "Add Topic" button
        JButton addTopicButton = new JButton("+ Add Topic");
        addTopicButton.addActionListener(e -> addNewTopic(courseName));
        mainDisplayPanel.add(addTopicButton, BorderLayout.SOUTH);

        // Add topics panel to main display
        mainDisplayPanel.add(new JScrollPane(topicsPanel), BorderLayout.CENTER);

        mainDisplayPanel.revalidate();
        mainDisplayPanel.repaint();
    }

    private void addNewTopic(String courseName) {
        // Prompt the user to enter a topic name
        String topicName = JOptionPane.showInputDialog(this, "Enter topic name:");
        if (topicName != null && !topicName.trim().isEmpty()) {
            courseTopicsMap.get(courseName).add(topicName);  // Add topic to course in map
            addTopicToPanel(topicName);  // Display the topic in the GUI
        }
    }

    private void addTopicToPanel(String topicName) {
        // Create a panel for the topic "card"
        JPanel topicPanel = new JPanel();
        topicPanel.setBackground(new Color(240, 255, 240));
        topicPanel.setLayout(new BoxLayout(topicPanel, BoxLayout.Y_AXIS));
        topicPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add a label for the topic name
        JLabel topicLabel = new JLabel(topicName);
        topicLabel.setFont(new Font("Serif", Font.BOLD, 14));
        topicPanel.add(topicLabel);

        // Add a placeholder button or label for confidence tracking
        JButton confidenceButton = new JButton("Set Confidence");
        topicPanel.add(confidenceButton);

        topicsPanel.add(topicPanel);
        topicsPanel.revalidate();  // Refresh topics panel to display the new topic
        topicsPanel.repaint();
    }

    // Inner class for handling course button clicks
    private class CourseButtonListener implements ActionListener {
        private String courseName;

        public CourseButtonListener(String courseName) {
            this.courseName = courseName;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            showCourseDetails(courseName);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudyTrackGUI());
    }
}

package ui;

import model.Topic;
import model.LessonObjective;
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
    private JPanel topicsPanel;

    private List<String> courses;  
    private Map<String, List<Topic>> courseTopicsMap;  

    public StudyTrackGUI() {
        super("StudyTrack - Course Progress Tracker");

        courses = new ArrayList<>();
        courseTopicsMap = new HashMap<>();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        setupCoursesPanel();
        setupMainDisplayPanel();

        add(coursesPanel, BorderLayout.WEST);
        add(mainDisplayPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void setupCoursesPanel() {
        coursesPanel = new JPanel();
        coursesPanel.setLayout(new BoxLayout(coursesPanel, BoxLayout.Y_AXIS));
        coursesPanel.setPreferredSize(new Dimension(200, getHeight()));
        coursesPanel.setBackground(new Color(200, 255, 200));

        JLabel coursesLabel = new JLabel("COURSES");
        coursesLabel.setFont(new Font("Serif", Font.BOLD, 20));
        coursesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        coursesPanel.add(coursesLabel);

        JButton addCourseButton = new JButton("+ Add Courses");
        addCourseButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addCourseButton.addActionListener(e -> addNewCourse());
        coursesPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        coursesPanel.add(addCourseButton);

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

        JLabel welcomeMessage = new JLabel("Welcome to StudyTrack!", SwingConstants.CENTER);
        welcomeMessage.setFont(new Font("Serif", Font.BOLD, 24));
        mainDisplayPanel.add(welcomeMessage, BorderLayout.CENTER);
    }

    private void addNewCourse() {
        String courseName = JOptionPane.showInputDialog(this, "Enter course name:");
        if (courseName != null && !courseName.trim().isEmpty()) {
            courses.add(courseName);
            courseTopicsMap.put(courseName, new ArrayList<>());  

            JButton courseButton = new JButton(courseName);
            courseButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            courseButton.addActionListener(new CourseButtonListener(courseName));
            courseListPanel.add(courseButton);
            courseListPanel.revalidate();
        }
    }

    private void showCourseDetails(String courseName) {
        mainDisplayPanel.removeAll();

        JLabel courseLabel = new JLabel(courseName, SwingConstants.CENTER);
        courseLabel.setFont(new Font("Serif", Font.BOLD, 20));
        mainDisplayPanel.add(courseLabel, BorderLayout.NORTH);

        JLabel progressMessage = new JLabel("Track Your Progress", SwingConstants.CENTER);
        progressMessage.setFont(new Font("Serif", Font.ITALIC, 16));
        mainDisplayPanel.add(progressMessage, BorderLayout.CENTER);

        topicsPanel = new JPanel();
        topicsPanel.setLayout(new BoxLayout(topicsPanel, BoxLayout.Y_AXIS));
        topicsPanel.setBackground(new Color(180, 220, 180));

        List<Topic> topics = courseTopicsMap.get(courseName);
        if (topics != null) {
            for (Topic topic : topics) {
                addTopicToPanel(topic);
            }
        }

        JButton addTopicButton = new JButton("+ Add Topic");
        addTopicButton.addActionListener(e -> addNewTopic(courseName));
        mainDisplayPanel.add(addTopicButton, BorderLayout.SOUTH);

        mainDisplayPanel.add(new JScrollPane(topicsPanel), BorderLayout.CENTER);

        mainDisplayPanel.revalidate();
        mainDisplayPanel.repaint();
    }

    private void addNewTopic(String courseName) {
        String topicName = JOptionPane.showInputDialog(this, "Enter topic name:");
        if (topicName != null && !topicName.trim().isEmpty()) {
            Topic newTopic = new Topic(topicName);  
            int objectiveCount = Integer.parseInt(JOptionPane.showInputDialog(this, "How many lesson objectives?"));
            for (int i = 1; i <= objectiveCount; i++) {
                String objective = JOptionPane.showInputDialog(this, "Enter objective " + i + ":");
                if (objective != null && !objective.trim().isEmpty()) {
                    newTopic.addLessonObjective(objective);  
                }
            }

            courseTopicsMap.get(courseName).add(newTopic);
            addTopicToPanel(newTopic);
        }
    }

    private void addTopicToPanel(Topic topic) {
        JPanel topicPanel = new JPanel();
        topicPanel.setBackground(new Color(240, 255, 240));
        topicPanel.setLayout(new BoxLayout(topicPanel, BoxLayout.Y_AXIS));
        topicPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel topicLabel = new JLabel(topic.getName() + " - Confidence: " + topic.getConfidenceLevel() + "%");
        topicLabel.setFont(new Font("Serif", Font.BOLD, 14));
        topicPanel.add(topicLabel);

        // Button to open a pop-up window showing lesson objectives
        JButton viewObjectivesButton = new JButton("View Lesson Objectives");
        viewObjectivesButton.addActionListener(e -> openLessonObjectivesDialog(topic, topicLabel));
        topicPanel.add(viewObjectivesButton);

        topicsPanel.add(topicPanel);
        topicsPanel.revalidate();
        topicsPanel.repaint();
    }

    private void openLessonObjectivesDialog(Topic topic, JLabel topicLabel) {
        JDialog objectivesDialog = new JDialog(this, "Lesson Objectives - " + topic.getName(), true);
        objectivesDialog.setSize(400, 300);
        objectivesDialog.setLayout(new BoxLayout(objectivesDialog.getContentPane(), BoxLayout.Y_AXIS));

        // Adding lesson objectives as checkboxes
        for (LessonObjective objective : topic.getLessonObjectives()) {
            JCheckBox objectiveCheckbox = new JCheckBox(objective.getDescription());
            objectiveCheckbox.setSelected(objective.isMastered());
            objectiveCheckbox.addActionListener(e -> {
                if (objectiveCheckbox.isSelected()) {
                    objective.markAsMastered();
                } else {
                    objective.unmarkAsMastered();
                }
                topic.updateConfidenceLevel();
                topicLabel.setText(topic.getName() + " - Confidence: " + topic.getConfidenceLevel() + "%");
            });
            objectivesDialog.add(objectiveCheckbox);
        }

        // Close button to close the dialog
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> objectivesDialog.dispose());
        objectivesDialog.add(closeButton);

        objectivesDialog.setLocationRelativeTo(this);
        objectivesDialog.setVisible(true);
    }

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
}

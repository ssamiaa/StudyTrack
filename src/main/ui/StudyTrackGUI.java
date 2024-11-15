package ui;

import model.Topic;
import persistence.JsonReader;
import persistence.JsonWriter;
import model.Course;
import model.LessonObjective;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StudyTrackGUI extends JFrame {
    private JPanel coursesPanel;
    private JPanel mainDisplayPanel;
    private JPanel courseListPanel;
    private JPanel topicsPanel;
    private CircularProgressBar progressBar;  

    private List<String> courses;  
    private Map<String, List<Topic>> courseTopicsMap;  

    private static final String DATA_FILE = "./data/studyTrack.json";

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
    
    @SuppressWarnings("methodlength")
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
    
        // Add Save button
        JButton saveButton = new JButton("Save Data");
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveButton.addActionListener(e -> saveData());
        coursesPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        coursesPanel.add(saveButton);
    
        // Add Load button
        JButton loadButton = new JButton("Load Data");
        loadButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadButton.addActionListener(e -> loadData());
        coursesPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        coursesPanel.add(loadButton);
    
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

        JLabel welcomeMessage = new JLabel("Welcome to StudyTrack!", SwingConstants.CENTER);
        welcomeMessage.setFont(new Font("Serif", Font.BOLD, 24));
        mainDisplayPanel.add(welcomeMessage, BorderLayout.CENTER);

        // Add the circular progress bar
        progressBar = new CircularProgressBar();
        mainDisplayPanel.add(progressBar, BorderLayout.EAST);
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
        mainDisplayPanel.add(progressBar, BorderLayout.EAST);
    
        setupCourseHeader(courseName);
        setupTopicsPanel(courseName);
        addAddTopicButton(courseName);
    
        mainDisplayPanel.add(new JScrollPane(topicsPanel), BorderLayout.CENTER);
        updateCourseConfidence(courseName);
        mainDisplayPanel.revalidate();
        mainDisplayPanel.repaint();
    }
    
    // Sets up the course header with the course name and a message
    private void setupCourseHeader(String courseName) {
        JLabel courseLabel = new JLabel(courseName, SwingConstants.CENTER);
        courseLabel.setFont(new Font("Serif", Font.BOLD, 20));
        mainDisplayPanel.add(courseLabel, BorderLayout.NORTH);
    
        JLabel progressMessage = new JLabel("Track Your Progress", SwingConstants.CENTER);
        progressMessage.setFont(new Font("Serif", Font.ITALIC, 16));
        mainDisplayPanel.add(progressMessage, BorderLayout.CENTER);
    }
    
    // Initializes the topics panel and adds existing topics
    private void setupTopicsPanel(String courseName) {
        topicsPanel = new JPanel();
        topicsPanel.setLayout(new BoxLayout(topicsPanel, BoxLayout.Y_AXIS));
        topicsPanel.setBackground(new Color(180, 220, 180));
    
        List<Topic> topics = courseTopicsMap.get(courseName);
        if (topics != null) {
            for (Topic topic : topics) {
                addTopicToPanel(topic, courseName);
            }
        }
    }
    
    // Adds the "Add Topic" button and its functionality
    private void addAddTopicButton(String courseName) {
        JButton addTopicButton = new JButton("+ Add Topic");
        addTopicButton.addActionListener(e -> promptForNewTopic(courseName));
        mainDisplayPanel.add(addTopicButton, BorderLayout.SOUTH);
    }
    
    // Prompts the user to add a new topic and its lesson objectives
    private void promptForNewTopic(String courseName) {
        String topicName = JOptionPane.showInputDialog(this, "Enter topic name:");
        if (topicName != null && !topicName.trim().isEmpty()) {
            Topic newTopic = new Topic(topicName);
            int objectiveCount = getObjectiveCount();
            if (objectiveCount > 0) {
                addLessonObjectives(newTopic, objectiveCount);
                addNewTopicToCourse(courseName, newTopic);
            }
        }
    }
    
    // Gets the number of lesson objectives from the user
    private int getObjectiveCount() {
        String objectivesCountStr = JOptionPane.showInputDialog(this, "How many lesson objectives?");
        try {
            return Integer.parseInt(objectivesCountStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number.");
            return 0;
        }
    }
    
    // Prompts the user to enter lesson objectives and adds them to the topic
    private void addLessonObjectives(Topic newTopic, int objectiveCount) {
        for (int i = 1; i <= objectiveCount; i++) {
            String objective = JOptionPane.showInputDialog(this, "Enter objective " + i + ":");
            if (objective != null && !objective.trim().isEmpty()) {
                newTopic.addLessonObjective(objective);
            }
        }
    }
    
    // Adds the new topic to the course and updates the GUI
    private void addNewTopicToCourse(String courseName, Topic newTopic) {
        List<Topic> courseTopics = courseTopicsMap.get(courseName);
        if (courseTopics != null) {
            courseTopics.add(newTopic);
            addTopicToPanel(newTopic, courseName);
            updateCourseConfidence(courseName);
        }
    }
    

    private void addTopicToPanel(Topic topic, String courseName) {
        JPanel topicPanel = new JPanel();
        topicPanel.setBackground(new Color(240, 255, 240));
        topicPanel.setLayout(new BoxLayout(topicPanel, BoxLayout.Y_AXIS));
        topicPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel topicLabel = new JLabel(topic.getName() + " - Confidence: " + topic.getConfidenceLevel() + "%");
        topicLabel.setFont(new Font("Serif", Font.BOLD, 14));
        topicPanel.add(topicLabel);

        JButton viewObjectivesButton = new JButton("View Lesson Objectives");
        viewObjectivesButton.addActionListener(e -> openLessonObjectivesDialog(topic, topicLabel, courseName));
        topicPanel.add(viewObjectivesButton);

        topicsPanel.add(topicPanel);
        topicsPanel.revalidate();
        topicsPanel.repaint();
    }

    private void openLessonObjectivesDialog(Topic topic, JLabel topicLabel, String courseName) {
        JDialog objectivesDialog = new JDialog(this, "Lesson Objectives - " + topic.getName(), true);
        objectivesDialog.setSize(400, 300);
        objectivesDialog.setLayout(new BoxLayout(objectivesDialog.getContentPane(), BoxLayout.Y_AXIS));

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
                updateCourseConfidence(courseName);  
            });
            objectivesDialog.add(objectiveCheckbox);
        }

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> objectivesDialog.dispose());
        objectivesDialog.add(closeButton);

        objectivesDialog.setLocationRelativeTo(this);
        objectivesDialog.setVisible(true);
    }

    private void updateCourseConfidence(String courseName) {
        List<Topic> topics = courseTopicsMap.get(courseName);
        if (topics == null || topics.isEmpty()) {
            progressBar.setProgress(0);
            return;
        }

        int totalConfidence = 0;
        for (Topic topic : topics) {
            totalConfidence += topic.getConfidenceLevel();
        }
        int averageConfidence = totalConfidence / topics.size();
        progressBar.setProgress(averageConfidence);
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

    private void saveData() {
        JsonWriter writer = new JsonWriter(DATA_FILE);
        try {
            writer.open();
            writer.write(new ArrayList<>(courseTopicsMap.keySet().stream()
                    .map(courseName -> {
                        List<Topic> topics = courseTopicsMap.get(courseName);
                        Course course = new Course(courseName);
                        for (Topic topic : topics) {
                            course.addTopicObject(topic);
                        }
                        return course;
                    })
                    .collect(Collectors.toList())));
            writer.close();
            JOptionPane.showMessageDialog(this, "Data saved successfully!");
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Unable to save data: " + e.getMessage());
        }
    }
    

    private void loadData() {
        JsonReader reader = new JsonReader(DATA_FILE);
        try {
            List<Course> loadedCourses = reader.read(); 
            courses.clear();
            courseTopicsMap.clear();

            for (Course course : loadedCourses) {
                courses.add(course.getName());
                courseTopicsMap.put(course.getName(), course.getTopics());
            }

            JOptionPane.showMessageDialog(this, "Data loaded successfully!");
            refreshCourseListPanel();  
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Unable to load data: " + e.getMessage());
        }
    }

    // Helper method to refresh the course list panel after loading
    private void refreshCourseListPanel() {
        courseListPanel.removeAll();
        for (String courseName : courses) {
            JButton courseButton = new JButton(courseName);
            courseButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            courseButton.addActionListener(new CourseButtonListener(courseName));
            courseListPanel.add(courseButton);
        }
        courseListPanel.revalidate();
        courseListPanel.repaint();
    }


}

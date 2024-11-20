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
    
    // EFFECTS: Initializes the StudyTrack GUI window with default settings and layout,
    //          sets up the courses and main display panels, and makes the frame visible.
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
    
    // EFFECTS: Sets up the courses panel on the left side of the frame,
    //          adds buttons for adding courses, saving/loading data, and the course list panel.
    @SuppressWarnings("methodlength")
    private void setupCoursesPanel() {
        coursesPanel = new JPanel();
        coursesPanel.setLayout(new BorderLayout()); 
        coursesPanel.setPreferredSize(new Dimension(200, getHeight()));
        coursesPanel.setBackground(new Color(200, 255, 200));
        
        
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(new Color(92, 64, 51) );
        
        JLabel coursesLabel = new JLabel("COURSES");
        coursesLabel.setFont(new Font("Chalkboard", Font.PLAIN, 35));
        coursesLabel.setForeground(new Color(241, 241, 220));
        coursesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        topPanel.add(coursesLabel);
        
        JButton addCourseButton = new JButton("+ Add Courses");
        styleButton(addCourseButton);
        addCourseButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addCourseButton.addActionListener(e -> addNewCourse());
        topPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        topPanel.add(addCourseButton);
        
        coursesPanel.add(topPanel, BorderLayout.NORTH);
    
        // Middle section for the course list
        courseListPanel = new JPanel();
        courseListPanel.setLayout(new BoxLayout(courseListPanel, BoxLayout.Y_AXIS));
        courseListPanel.setBackground(new Color(92, 64, 51));
        coursesPanel.add(new JScrollPane(courseListPanel), BorderLayout.CENTER);
    
        // Bottom section for Save and Load buttons
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setBackground(new Color(92, 64, 51));
        
        JButton saveButton = new JButton("Save Data");
        styleButton(saveButton);
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveButton.addActionListener(e -> saveData());
        bottomPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        bottomPanel.add(saveButton);
        
        JButton loadButton = new JButton("Load Data");
        styleButton(loadButton);
        loadButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadButton.addActionListener(e -> loadData());
        bottomPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        bottomPanel.add(loadButton);
        
        coursesPanel.add(bottomPanel, BorderLayout.SOUTH); 
    }
    
    // EFFECTS: Sets up the main display panel in the center of the frame with a welcome message
    //          and adds the circular progress bar component to the right side of the display panel.
    private void setupMainDisplayPanel() {
        mainDisplayPanel = new JPanel();
        mainDisplayPanel.setBackground(new Color(62, 39, 35));
        mainDisplayPanel.setLayout(new BorderLayout());
        setSize(1200, 700); 
        setResizable(false);


        // Load the image
        ImageIcon imageIcon = new ImageIcon("src/main/images/animedeskwmessage.png"); 
        JLabel imageLabel = new JLabel(imageIcon);
        Image img = imageIcon.getImage(); 
        Image scaledImg = img.getScaledInstance(1000, 700, Image.SCALE_SMOOTH); 
        ImageIcon scaledIcon = new ImageIcon(scaledImg);
        imageLabel.setIcon(scaledIcon); 

        mainDisplayPanel.add(imageLabel, BorderLayout.CENTER);


        // Add the circular progress bar
        progressBar = new CircularProgressBar();
        mainDisplayPanel.add(progressBar, BorderLayout.EAST);
    }
    
    // EFFECTS: Prompts the user to input a course name. If valid, adds the course name to the
    //          list of courses and creates a button for the course in the course list panel.
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

    // EFFECTS: Clears the main display panel, sets up the course header and topics panel,
    //          adds the circular progress bar, and a button to add topics, then repaints the panel.
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
    
    // EFFECTS: Adds a header to the main display panel with the given course name and a
    //          progress message.
    private void setupCourseHeader(String courseName) {
        JLabel courseLabel = new JLabel(courseName, SwingConstants.CENTER);
        courseLabel.setFont(new Font("Chalkboard", Font.PLAIN, 20));
        courseLabel.setForeground(new Color(241, 241, 220));

        mainDisplayPanel.add(courseLabel, BorderLayout.NORTH);
    
        JLabel progressMessage = new JLabel("Track Your Progress", SwingConstants.CENTER);
        progressMessage.setFont(new Font("Chalkboard", Font.PLAIN, 16));
        mainDisplayPanel.add(progressMessage, BorderLayout.CENTER);
    }
    
    // EFFECTS: Initializes the topics panel, configures its layout, and adds any existing topics
    //          associated with the given course to the panel.
    private void setupTopicsPanel(String courseName) {
        topicsPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imageIcon = new ImageIcon("src/main/images/topicpanelbg.png");
                Image img = imageIcon.getImage();
                g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
            }
        };
        topicsPanel.setLayout(new BoxLayout(topicsPanel, BoxLayout.Y_AXIS));
    
        List<Topic> topics = courseTopicsMap.get(courseName);
        if (topics != null) {
            for (Topic topic : topics) {
                addTopicToPanel(topic, courseName);
            }
        }
    }
    
    
   // EFFECTS: Adds an "Add Topic" button to the main display panel that, when clicked,
    //          prompts the user to add a new topic with lesson objectives.
    private void addAddTopicButton(String courseName) {
        JButton addTopicButton = new JButton("+ Add Topic");
        addTopicButton.addActionListener(e -> promptForNewTopic(courseName));
        mainDisplayPanel.add(addTopicButton, BorderLayout.SOUTH);
    }
    
    // EFFECTS: Prompts the user to input a new topic name. If valid, adds the new topic to the course.
    private void promptForNewTopic(String courseName) {
        String topicName = JOptionPane.showInputDialog(this, "Enter topic name:");
        if (topicName != null && !topicName.trim().isEmpty()) {
            Topic newTopic = new Topic(topicName);
            addNewTopicToCourse(courseName, newTopic); // Just add the topic without lesson objectives
        }
    }

    
    
    // EFFECTS: Adds the specified topic to the course, updates the topics panel and the overall
    //          course confidence level.
    private void addNewTopicToCourse(String courseName, Topic newTopic) {
        List<Topic> courseTopics = courseTopicsMap.get(courseName);
        if (courseTopics != null) {
            courseTopics.add(newTopic);
            addTopicToPanel(newTopic, courseName);
            updateCourseConfidence(courseName);
        }
    }
    
    // EFFECTS: Adds a visual representation of the given topic to the topics panel, including
    //          a button to view its lesson objectives.
    private void addTopicToPanel(Topic topic, String courseName) {
        // Create a panel for the topic "card"
        JPanel topicPanel = new JPanel();
        topicPanel.setBackground(new Color(245, 245, 220)); 
        topicPanel.setLayout(new BoxLayout(topicPanel, BoxLayout.Y_AXIS));
        topicPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topicPanel.setPreferredSize(new Dimension(400, 80)); 
        topicPanel.setMaximumSize(new Dimension(400, 80)); 
        topicPanel.setAlignmentX(Component.CENTER_ALIGNMENT); 
    
        // Add a label for the topic name and confidence level
        JLabel topicLabel = new JLabel(topic.getName() + " - Confidence: " + topic.getConfidenceLevel() + "%");
        topicLabel.setFont(new Font("Chalkboard", Font.PLAIN, 14));
        topicLabel.setForeground(new Color(60, 40, 30)); 
        topicPanel.add(topicLabel);
    
        // Add a button to view lesson objectives
        JButton viewObjectivesButton = new JButton("View Lesson Objectives");
        viewObjectivesButton.setFont(new Font("Chalkboard", Font.PLAIN, 12)); 
        viewObjectivesButton.addActionListener(e -> openLessonObjectivesDialog(topic, topicLabel, courseName));
        topicPanel.add(viewObjectivesButton);
    
        // Add the topic panel to the topicsPanel
        topicsPanel.add(Box.createRigidArea(new Dimension(0, 10))); 
        topicsPanel.add(topicPanel);
        topicsPanel.revalidate();
        topicsPanel.repaint();
    }
    
    
    // EFFECTS: Opens a dialog window displaying the lesson objectives for the specified topic.
    //          Allows users to dynamically add lesson objectives and mark them as mastered/unmastered.
    private void openLessonObjectivesDialog(Topic topic, JLabel topicLabel, String courseName) {
        JDialog objectivesDialog = new JDialog(this, "Lesson Objectives - " + topic.getName(), true);
        objectivesDialog.setSize(400, 300);
        objectivesDialog.setLayout(new BoxLayout(objectivesDialog.getContentPane(), BoxLayout.Y_AXIS));

        // Panel to hold all lesson objectives
        JPanel objectivesPanel = new JPanel();
        objectivesPanel.setLayout(new BoxLayout(objectivesPanel, BoxLayout.Y_AXIS));

        // Display existing objectives
        updateObjectivesList(topic, objectivesPanel, topicLabel, courseName);

        // Button to add new lesson objectives
        JButton addObjectiveButton = new JButton("+ Add Lesson Objective");
        addObjectiveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addObjectiveButton.addActionListener(e -> {
            String newObjective = JOptionPane.showInputDialog(objectivesDialog, "Enter lesson objective:");
            if (newObjective != null && !newObjective.trim().isEmpty()) {
                topic.addLessonObjective(newObjective);
                topic.updateConfidenceLevel(); 
                updateObjectivesList(topic, objectivesPanel, topicLabel, courseName);
            }
        });

        // Add components to the dialog
        objectivesDialog.add(new JScrollPane(objectivesPanel)); 
        objectivesDialog.add(addObjectiveButton);

        JButton closeButton = new JButton("Close");
        closeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        closeButton.addActionListener(e -> objectivesDialog.dispose());
        objectivesDialog.add(closeButton);

        objectivesDialog.setLocationRelativeTo(this);
        objectivesDialog.setVisible(true);
    }

    // Helper method to refresh the objectives list
    private void updateObjectivesList(Topic topic, JPanel objectivesPanel, JLabel topicLabel, String courseName) {
        objectivesPanel.removeAll(); // Clear existing list

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
            objectivesPanel.add(objectiveCheckbox);
        }

        objectivesPanel.revalidate();
        objectivesPanel.repaint();
    }

    // EFFECTS: Calculates the average confidence level for all topics in the specified course
    //          and updates the circular progress bar with the new value.
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
    
    // EFFECTS: Writes the current course data to a JSON file, saving all courses and their associated
    //          topics and objectives. Displays a success or error message.
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
    
    // EFFECTS: Loads course data from a JSON file, repopulating the courses and courseTopicsMap
    //          with previously saved data. Refreshes the GUI with the loaded courses.
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

    // EFFECTS: Clears and repopulates the course list panel with buttons for each course in the
    //          courses list. Refreshes the panel to show the updated list.
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

    // Helper method to style buttons
    private void styleButton(JButton button) {
        button.setPreferredSize(new Dimension(150, 40)); 
        button.setFont(new Font("Chalkboard", Font.PLAIN, 16)); 
        button.setBackground(new Color(241, 241, 200)); 
        button.setForeground(new Color(92, 64, 51)); 
    }
}

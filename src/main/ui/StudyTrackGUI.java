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
    private CircularProgressBar progressBar;  

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
                addTopicToPanel(topic, courseName);
            }
        }

        // "Add Topic" button with inline logic
        JButton addTopicButton = new JButton("+ Add Topic");
        addTopicButton.addActionListener(e -> {
            // Prompt user for topic name
            String topicName = JOptionPane.showInputDialog(this, "Enter topic name:");
            if (topicName != null && !topicName.trim().isEmpty()) {
                // Create a new Topic object
                Topic newTopic = new Topic(topicName);

                // Prompt for lesson objectives count
                String objectivesCountStr = JOptionPane.showInputDialog(this, "How many lesson objectives?");
                int objectiveCount;
                try {
                    objectiveCount = Integer.parseInt(objectivesCountStr);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid number.");
                    return;
                }

                // Collect lesson objectives from the user
                for (int i = 1; i <= objectiveCount; i++) {
                    String objective = JOptionPane.showInputDialog(this, "Enter objective " + i + ":");
                    if (objective != null && !objective.trim().isEmpty()) {
                        newTopic.addLessonObjective(objective);
                    }
                }

                // Add the new topic to the selected course's list
                List<Topic> courseTopics = courseTopicsMap.get(courseName);
                if (courseTopics != null) {
                    courseTopics.add(newTopic);  
                    addTopicToPanel(newTopic, courseName);  
                    updateCourseConfidence(courseName);  
                }
            }
        });
        mainDisplayPanel.add(addTopicButton, BorderLayout.SOUTH);

        mainDisplayPanel.add(new JScrollPane(topicsPanel), BorderLayout.CENTER);

        updateCourseConfidence(courseName);  
        mainDisplayPanel.revalidate();
        mainDisplayPanel.repaint();
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudyTrackGUI());
    }
}

class CircularProgressBar extends JPanel {
    private int progress = 0;

    public CircularProgressBar() {
        setPreferredSize(new Dimension(100, 100));  
    }

    public void setProgress(int progress) {
        this.progress = progress;
        repaint();  
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        int size = Math.min(getWidth(), getHeight());
        int strokeWidth = 8;
        int radius = size / 2 - strokeWidth;

       
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.setStroke(new BasicStroke(strokeWidth));
        g2d.drawArc(strokeWidth, strokeWidth, size - 2 * strokeWidth, size - 2 * strokeWidth, 0, 360);

        
        g2d.setColor(new Color(76, 175, 80));  
        int angle = (int) (360 * (progress / 100.0));
        g2d.drawArc(strokeWidth, strokeWidth, size - 2 * strokeWidth, size - 2 * strokeWidth, 90, -angle);

        
        g2d.setFont(new Font("Serif", Font.BOLD, 18));
        FontMetrics fm = g2d.getFontMetrics();
        String text = progress + "%";
        int textX = (size - fm.stringWidth(text)) / 2;
        int textY = (size + fm.getAscent()) / 2 - 4;
        g2d.drawString(text, textX, textY);

        g2d.dispose();
    }
}

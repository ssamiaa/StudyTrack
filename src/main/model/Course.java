package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a course containing multiple topics.
 */
public class Course {
    private String name;
    private List<Topic> topics;
   
    // Constructs a Course with the given name.
    // REQUIRES: name is a non-empty string.
    // EFFECTS: Initializes the course with the provided name and an empty list of topics.
    public Course(String name) {
        this.name = name;
        this.topics = new ArrayList<>();
      
    }

    // Returns the name of the course.
    public String getName() {
        return name;
    }


    // Adds a topic to the course.
    // REQUIRES: topicName is a non-empty string.
    // MODIFIES: this
    // EFFECTS: Adds a new Topic with the given name to the topics list.
    public void addTopic(String topicName) {
        Topic topic = new Topic(topicName);
        topics.add(topic);
    }

    // Removes a topic from the course.
    // REQUIRES: topicName exists in the course.
    // MODIFIES: this
    // EFFECTS: Removes the Topic with the specified name from the topics list.
    public void removeTopic(String topicName) {
        for (int i = 0; i < topics.size(); i++) {
            if (topics.get(i).getName().equalsIgnoreCase(topicName)) {
                topics.remove(i);
                i--; // Adjust index after removal
            }
        }
    }

        
    // Returns the list of topics in the course.
    public List<Topic> getTopics() {
        return new ArrayList<>(topics); 
    }

    // Returns the overall progress for the course.
    public double getOverallProgress() {
        if (topics.isEmpty()) {
            return 0.0;
        }
    
        double totalConfidence = 0.0;
        for (int i = 0; i < topics.size(); i++) {
            totalConfidence += topics.get(i).getConfidenceLevel();
        }
        
        return totalConfidence / topics.size();
    }

    // Returns a string representation of the course, including overall progress.
    @Override
    public String toString() {  
        double overallProgress = getOverallProgress();
        return name + " (" + ((int) (overallProgress * 100)) / 100.0 + "% overall progress)";
    }
}


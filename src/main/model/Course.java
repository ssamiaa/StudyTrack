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
    }

    // Removes a topic from the course.
    // REQUIRES: topicName exists in the course.
    // MODIFIES: this
    // EFFECTS: Removes the Topic with the specified name from the topics list.
    public void removeTopic(String topicName) {
        
    }


    // Returns the list of topics in the course.
    public List<Topic> getTopics() {
        return new ArrayList<>(topics);
    }

    // Returns the overall progress for the course.
    public double getOverallProgress() {
        return 0.0;
        
    }

    // Returns a string representation of the course, including overall progress.
    @Override
    public String toString() {
        return name;
    }
}


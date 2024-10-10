package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a topic within a course, containing multiple lesson objectives.
 */
public class Topic {
    private String name;
    private List<LessonObjective> lessonObjectives;
    private double confidenceLevel; // percentage of mastered objectives

    /**
     * REQUIRES: name is a non-empty string.
     * EFFECTS: Initializes the topic with the provided name and an empty list of lesson objectives.
     */
    public Topic(String name) {
    }

    /**
     * Returns the name of the topic.
     */
    public String getName() {
        return name;
    }

    /**
     *
     * REQUIRES: objectiveDescription is a non-empty string.
     * MODIFIES: this
     * EFFECTS: Adds a new LessonObjective with the given description to the lessonObjectives list and updates confidence level.
     */
    public void addLessonObjective(String objectiveDescription) {
    
    }

    /**
     *
     * REQUIRES: index is a valid index within lessonObjectives.
     * MODIFIES: this
     * EFFECTS: Marks the lesson objective at the specified index as mastered and updates confidence level.
     */
    public void markObjectiveAsMastered(int index) {
    
    }

    /**
     *
     * REQUIRES: index is a valid index within lessonObjectives.
     * MODIFIES: this
     * EFFECTS: Unmarks the lesson objective at the specified index as mastered and updates confidence level.
     */
    public void unmarkObjectiveAsMastered(int index) {
    }

    /**
     * Returns the list of lesson objectives.
     */
    public List<LessonObjective> getLessonObjectives() {
        return new ArrayList<>(lessonObjectives); // return a copy to preserve encapsulation
    }

    /**
     * Returns the confidence level for the topic.
     */
    public double getConfidenceLevel() {
        return confidenceLevel;
    }

    /**
     * Updates the confidence level based on mastered lesson objectives.
     *
     * MODIFIES: this
     * EFFECTS: Calculates and updates the confidence level as the percentage of mastered objectives.
     */
    private void updateConfidenceLevel() {
    }

    /**
     * EFFECTS: Returns a string representation of the topic, including confidence level.
     */
    @Override
    public String toString() {
        return name;
    }
}

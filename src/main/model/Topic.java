package model;

import java.util.ArrayList;
import java.util.List;

// Represents a topic within a course, containing multiple lesson objectives.
public class Topic {
    private String name;
    private List<LessonObjective> lessonObjectives;
    private double confidenceLevel; // percentage of mastered objectives

    // Constructs a Topic with the given name.
    // REQUIRES: name is a non-empty string.
    // EFFECTS: Initializes the topic with the provided name and an empty list of lesson objectives.
    public Topic(String name) {
    }

    // Returns the name of the topic.
    public String getName() {
        return name;
    }

    // Adds a lesson objective to the topic.
    // REQUIRES: objectiveDescription is a non-empty string.
    // MODIFIES: this
    // EFFECTS: Adds a new LessonObjective with the given description to the lessonObjectives list and updates confidence level.
    public void addLessonObjective(String objectiveDescription) {
        LessonObjective objective = new LessonObjective(objectiveDescription);
        lessonObjectives.add(objective);
        updateConfidenceLevel();
    }

    // Marks a specific lesson objective as mastered.
    // REQUIRES: index is a valid index within lessonObjectives.
    // MODIFIES: this
    // EFFECTS: Marks the lesson objective at the specified index as mastered and updates confidence level.
    public void markObjectiveAsMastered(int index) {
        if (index < 0 || index >= lessonObjectives.size()) {
            throw new IndexOutOfBoundsException("Invalid lesson objective index.");
        }
        lessonObjectives.get(index).markAsMastered();
        updateConfidenceLevel();
    }

    // Unmarks a specific lesson objective as mastered.
    // REQUIRES: index is a valid index within lessonObjectives.
    // MODIFIES: this
    // EFFECTS: Unmarks the lesson objective at the specified index as mastered and updates confidence level.
    public void unmarkObjectiveAsMastered(int index) {
        if (index < 0 || index >= lessonObjectives.size()) {
            throw new IndexOutOfBoundsException("Invalid lesson objective index.");
        }
        lessonObjectives.get(index).unmarkAsMastered();
        updateConfidenceLevel();
    }

    // Returns the list of lesson objectives.
    public List<LessonObjective> getLessonObjectives() {
        return new ArrayList<>(lessonObjectives); // return a copy to preserve encapsulation
    }

    // Returns the confidence level for the topic.
    public double getConfidenceLevel() {
        return confidenceLevel;
    }

    // Updates the confidence level based on mastered lesson objectives.
    // MODIFIES: this
    // EFFECTS: Calculates and updates the confidence level as the percentage of mastered objectives.
    private void updateConfidenceLevel() {
        if (lessonObjectives.isEmpty()) {
            confidenceLevel = 0.0;
            return;
        }
        long masteredCount = lessonObjectives.stream()
                                            .filter(LessonObjective::isMastered)
                                            .count();
        confidenceLevel = ((double) masteredCount / lessonObjectives.size()) * 100.0;
    }

    // Returns a string representation of the topic, including confidence level.
    @Override
    public String toString() {
        return name + " (" + String.format("%.2f", confidenceLevel) + "% confident)";
    }
}

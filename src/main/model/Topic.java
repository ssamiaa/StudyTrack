package model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

/**
 * Represents a topic within a course, containing multiple lesson objectives.
 */
public class Topic implements Writable {
    private String name;
    private List<LessonObjective> lessonObjectives;
    private double confidenceLevel; // percentage of mastered objectives

    /**
     * REQUIRES: name is a non-empty string.
     * EFFECTS: Initializes the topic with the provided name and an empty list of lesson objectives.
     */
    public Topic(String name) {
        this.name = name;
        this.lessonObjectives = new ArrayList<>();
        this.confidenceLevel = 0.0;
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
     * EFFECTS: Adds a new LessonObjective with the given
     *  description to the lessonObjectives list 
     *  and updates confidence level.
     */
    public void addLessonObjective(String objectiveDescription) {
        LessonObjective objective = new LessonObjective(objectiveDescription);
        lessonObjectives.add(objective);
        updateConfidenceLevel();
    
    }

    /**
     *
     * REQUIRES: index is a valid index within lessonObjectives.
     * MODIFIES: this
     * EFFECTS: Marks the lesson objective at the specified index as mastered and updates confidence level.
     */
    public void markObjectiveAsMastered(int index) {
        lessonObjectives.get(index).markAsMastered();
        updateConfidenceLevel();
    
    }

    /**
     *
     * REQUIRES: index is a valid index within lessonObjectives.
     * MODIFIES: this
     * EFFECTS: Unmarks the lesson objective at the specified index as mastered and updates confidence level.
     */
    public void unmarkObjectiveAsMastered(int index) {
        lessonObjectives.get(index).unmarkAsMastered();
        updateConfidenceLevel();
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
    public void updateConfidenceLevel() {
        if (lessonObjectives.isEmpty()) {
            confidenceLevel = 0.0;  
            return;
        }
    
        int masteredCount = 0;
        for (LessonObjective objective : lessonObjectives) {
            if (objective.isMastered()) {
                masteredCount++;
            }
        }
    
        // Confidence level is calculated as the percentage of mastered objectives
        confidenceLevel = ((double) masteredCount / lessonObjectives.size()) * 100.0;
    }

    /**
     * EFFECTS: Returns a string representation of the topic, including confidence level.
     */
    @Override
    public String toString() {
        return name + " (" + ((int) (confidenceLevel * 100)) / 100.0 + "% confident)";
    }
    
    public void setConfidenceLevel(double confidenceLevel) {
        this.confidenceLevel = confidenceLevel;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);  // Topic name
        json.put("confidenceLevel", confidenceLevel);  

        JSONArray jsonLessonObjectives = new JSONArray();
        for (LessonObjective objective : lessonObjectives) {
            jsonLessonObjectives.put(objective.toJson());  
        }
        json.put("lessonObjectives", jsonLessonObjectives);  
        return json;
    }

}



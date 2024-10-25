package model;

import org.json.JSONObject;

import persistence.Writable;

//Represents a single lesson objective within a topic.
public class LessonObjective implements Writable {
    private String description;
    private boolean isMastered;

    /* 
     * REQUIRES: description is a non-empty string.
     * EFFECTS: Initializes the lesson objective with the provided description and sets isMastered to false.
    */
    public LessonObjective(String description) {
        this.description = description;
        this.isMastered = false;
    }

    /* 
     * EFFECTS: Returns the description of the lesson objective. 
    */
    public String getDescription() {
        return description;
    }
    
    /* 
     * EFFECTS: Checks if the lesson objective is mastered.
    */
    public boolean isMastered() {
        return isMastered;
    }

    /* 
     * MODIFIES: this
     * EFFECTS: Returns the description of the lesson objective. 
     * Marks the lesson objective as mastered.Sets isMastered to true.
     * 
    */
    public void markAsMastered() {
        this.isMastered = true;
    }

    /* 
     * MODIFIES: this
     * EFFECTS: Unmarks the lesson objective as mastered. Sets isMastered to false.
     * 
    */
    public void unmarkAsMastered() {
        this.isMastered = false;
    }
    
    /* 
     * MODIFIES: this
     * EFFECTS: Returns a string representation of the lesson objective.
     * Unmarks the lesson objective as mastered. Sets isMastered to false.
     * 
    */
    @Override
    public String toString() {
        String masteryStatus = " [Not Mastered]";
        if (isMastered) {
            masteryStatus = " [Mastered]";
        }
        return description + masteryStatus;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("description", description);
        json.put("isMastered", isMastered);  

        return json;
    }

}

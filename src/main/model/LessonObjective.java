package model;

//Represents a single lesson objective within a topic.
public class LessonObjective {
    private String description;
    private boolean isMastered;

    // Constructs a LessonObjective with the given description.
    // REQUIRES: description is a non-empty string.
    // EFFECTS: Initializes the lesson objective with the provided description and sets isMastered to false.
    public LessonObjective(String description) {
        this.description = description;
        this.isMastered = false;
    }

    // Returns the description of the lesson objective.
    public String getDescription() {
        return description;
    }

    // Checks if the lesson objective is mastered.
    public boolean isMastered() {
        return isMastered;
    }

    
    // Marks the lesson objective as mastered.
    // MODIFIES: this
    // EFFECTS: Sets isMastered to true.
    public void markAsMastered() {
        this.isMastered = true;
    }

    // Unmarks the lesson objective as mastered.
    // MODIFIES: this
    // EFFECTS: Sets isMastered to false.
    public void unmarkAsMastered() {
        this.isMastered = false;
    }

    // Returns a string representation of the lesson objective.
    @Override
    public String toString() {
        String masteryStatus = " [Not Mastered]";
        if (isMastered) {
            masteryStatus = " [Mastered]";
        }
        return description + masteryStatus;
    }
}

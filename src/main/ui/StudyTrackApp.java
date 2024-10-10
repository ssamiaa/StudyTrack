package ui;

import model.Course;
import model.Topic;


import java.util.List;
import java.util.Scanner;

/**
 * Represents the StudyTrack application, handling the main program flow.
 */
public class StudyTrackApp {
    private List<Course> courses;
    private Scanner scanner;

    /**
     * EFFECTS: Constructs a StudyTrackApp with an empty list of courses and initializes the scanner.
     */
    public StudyTrackApp() {
    }

    /**
     * EFFECTS: Runs the StudyTrack application, displaying the main menu and handling user choices.
     */
    public void run() {
    }

    /**
     * EFFECTS: Displays the main menu options to the user.
     */
    private void displayMainMenu() {
    }

    /**
     * EFFECTS: Retrieves the user's menu choice.
     */
    private int getUserChoice() {
        return scanner.nextInt();
    }

    /**
     * EFFECTS: Adds a new course based on user input.
     */
    private void addNewCourse() {
        
    }
    

    /**
     * EFFECTS: Updates lesson objective progress based on user input.
     */
    private void updateLessonObjectiveProgress() {
        
    }

    /**
     * EFFECTS: Adds more topics to an existing course based on user input.
     */
    private void addMoreTopicsToCourse() {
        
    }

    /**
     * EFFECTS: Deletes a topic from an existing course based on user input.
     */
    private void deleteTopic() {
    
    }

    /**
     * EFFECTS: Deletes an existing course based on user input.
     */
    private void deleteCourse() {
    }

    /**
     * EFFECTS: Prompts the user to select a course from the list
     * or null if selection is invalid.
     */
    private Course selectCourse() {
    }

    /**
     * EFFECTS: Prompts the user to select a topic from the selected course 
     * or null if selection is invalid.
     */
    private Topic selectTopic(Course course) {
        
    }

    /**
     * EFFECTS: Retrieves a positive integer input from the user.
     */
    private int getPositiveInt() {
        
    }

    /**
     * EFFECTS: Retrieves a non-empty string input from the user.
     */
    private String getNonEmptyString() {
    }

    /**
     * REQUIRES: totalObjectives >= 0
     * EFFECTS: Prompts the user to enter a number between 0 and totalObjectives.
     */
    private int getConfidentObjectives(int totalObjectives) {
        return 0;
    }

    /**
     *EFFECTS:  Retrieves a yes or no response from the user.
     */
    private boolean getYesOrNo() {
    }

    /**
     * REQUIRES: min <= choice <= max
     * EFFECTS: Prompts the user until a valid choice is entered.
     */
    private int getChoiceInRange(int min, int max) {
    
    }
}

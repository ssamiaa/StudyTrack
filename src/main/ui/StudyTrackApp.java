package ui;

import model.Course;
import model.LessonObjective;
import model.Topic;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
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
        courses = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    /**
     * EFFECTS: Runs the StudyTrack application, displaying the main menu and handling user choices.
     */
    @SuppressWarnings("methodlength")
    public void run() {
        boolean exit = false;
        while (!exit) {
            displayMainMenu();
            int choice = getUserChoice();
            switch (choice) {
                case 1:
                    addNewCourse();
                    break;
                case 2:
                    updateLessonObjectiveProgress();
                    break;
                case 3:
                    addMoreTopicsToCourse();
                    break;
                case 4:
                    deleteTopic();
                    break;
                case 5:
                    deleteCourse();
                    break;
                case 6:
                    saveProgress();
                    break;
                case 7:
                    loadProgress();
                    break;
                case 8:
                    viewProgress();   
                    break;
                case 9:
                    System.out.println("Goodbye!");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
        scanner.close();
    }
    
    

    /**
     * EFFECTS: Displays the main menu options to the user.
     */
    private void displayMainMenu() {
        System.out.println("\nMain Menu:");
        System.out.println("1. Add a new course");
        System.out.println("2. Update lesson objective progress in a topic");
        System.out.println("3. Add more topics to a course");
        System.out.println("4. Delete a topic");
        System.out.println("5. Delete a course");
        System.out.println("6. Save progress");
        System.out.println("7. Load progress");
        System.out.println("8. View Progress");  
        System.out.println("9. Exit");
        System.out.print("Enter your choice: ");
    }
    

    /**
     * EFFECTS: Retrieves the user's menu choice.
     */
    private int getUserChoice() {
        while (!scanner.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    /**
     * EFFECTS: Adds a new course based on user input.
     */
    @SuppressWarnings("methodlength")
    private void addNewCourse() {
        System.out.print("How many courses do you want to add? ");
        int numCourses = getPositiveInt();
        for (int i = 1; i <= numCourses; i++) {
            System.out.print("What is the name of course " + i + "? ");
            String courseName = getNonEmptyString();
            Course course = new Course(courseName);
            courses.add(course);

            System.out.print("How many topics do you want to add in the course? ");
            int numTopics = getPositiveInt();
            for (int j = 1; j <= numTopics; j++) {
                System.out.print("What is the name of topic " + j + "? ");
                String topicName = getNonEmptyString();
                course.addTopic(topicName);

                System.out.print("How many lesson objectives are there? ");
                int numObjectives = getPositiveInt();
                for (int k = 1; k <= numObjectives; k++) {
                    System.out.print("What is lesson objective " + k + "? ");
                    String objective = getNonEmptyString();
                    course.getTopics().get(j - 1).addLessonObjective(objective);
                }

                System.out.print("How many objectives are you confident in? ");
                int confidentObjectives = getConfidentObjectives(numObjectives);
                for (int k = 0; k < confidentObjectives; k++) {
                    course.getTopics().get(j - 1).markObjectiveAsMastered(k);
                }

                System.out.printf("Your progress for topic %s is %.2f%%\n",
                        topicName, course.getTopics().get(j - 1).getConfidenceLevel());
            }

            System.out.printf("Your overall progress for %s is %.2f%%\n",
                    courseName, course.getOverallProgress());
        }
    }

    /**
     * EFFECTS: Updates lesson objective progress based on user input.
     */
    @SuppressWarnings("methodlength")
    private void updateLessonObjectiveProgress() {
        if (courses.isEmpty()) {
            System.out.println("No courses available to update.");
            return;
        }
        Course course = selectCourse();
        if (course == null) {
            return;
        }

        if (course.getTopics().isEmpty()) {
            System.out.println("No topics available in this course to update.");
            return;
        }
        Topic topic = selectTopic(course);
        if (topic == null) {
            return;
        }

        List<LessonObjective> objectives = topic.getLessonObjectives();
        if (objectives.isEmpty()) {
            System.out.println("No lesson objectives available in this topic to update.");
            return;
        }

        System.out.println("Lesson Objectives:");
        for (int i = 0; i < objectives.size(); i++) {
            System.out.println((i + 1) + ". " + objectives.get(i));
        }

        System.out.print("Enter the number of the lesson objective to update: ");
        int objChoice = getChoiceInRange(1, objectives.size());
        LessonObjective selectedObjective = objectives.get(objChoice - 1);

        System.out.print("Mark as mastered? (yes/no): ");
        boolean master = getYesOrNo();
        if (master) {
            selectedObjective.markAsMastered();
            System.out.println("Lesson objective marked as mastered.");
        } else {
            selectedObjective.unmarkAsMastered();
            System.out.println("Lesson objective unmarked as mastered.");
        }

        System.out.printf("Your progress for topic %s is %.2f%%\n",
                topic.getName(), topic.getConfidenceLevel());
        System.out.printf("Your overall progress for %s is %.2f%%\n",
                course.getName(), course.getOverallProgress());
    }

    /**
     * EFFECTS: Adds more topics to an existing course based on user input.
     */
    @SuppressWarnings("methodlength")
    private void addMoreTopicsToCourse() {
        if (courses.isEmpty()) {
            System.out.println("No courses available to add topics.");
            return;
        }
        Course course = selectCourse();
        if (course == null) {
            return;
        }

        System.out.print("How many topics do you want to add to the course? ");
        int numTopics = getPositiveInt();
        for (int j = 1; j <= numTopics; j++) {
            System.out.print("What is the name of topic " + j + "? ");
            String topicName = getNonEmptyString();
            course.addTopic(topicName);

            System.out.print("How many lesson objectives are there? ");
            int numObjectives = getPositiveInt();
            for (int k = 1; k <= numObjectives; k++) {
                System.out.print("What is lesson objective " + k + "? ");
                String objective = getNonEmptyString();
                course.getTopics().get(course.getTopics().size() - 1).addLessonObjective(objective);
            }

            System.out.print("How many objectives are you confident in? ");
            int confidentObjectives = getConfidentObjectives(numObjectives);
            for (int k = 0; k < confidentObjectives; k++) {
                course.getTopics().get(course.getTopics().size() - 1).markObjectiveAsMastered(k);
            }

            System.out.printf("Your progress for topic %s is %.2f%%\n",
                    topicName, course.getTopics().get(course.getTopics().size() - 1).getConfidenceLevel());
        }

        System.out.printf("Your overall progress for %s is %.2f%%\n",
                course.getName(), course.getOverallProgress());
    }

    /**
     * EFFECTS: Deletes a topic from an existing course based on user input.
     */
    @SuppressWarnings("methodlength")
    private void deleteTopic() {
        if (courses.isEmpty()) {
            System.out.println("No courses available to delete topics from.");
            return;
        }
        Course course = selectCourse();
        if (course == null) {
            return;
        }

        if (course.getTopics().isEmpty()) {
            System.out.println("No topics available in this course to delete.");
            return;
        }
        Topic topic = selectTopic(course);
        if (topic == null) {
            return;
        }

        System.out.print("Are you sure you want to delete the topic \"" + topic.getName() + "\"? (yes/no): ");
        boolean confirm = getYesOrNo();
        if (confirm) {
            course.removeTopic(topic.getName());
            System.out.println("Topic deleted successfully.");
            System.out.printf("Your overall progress for %s is %.2f%%\n",
                    course.getName(), course.getOverallProgress());
        } else {
            System.out.println("Deletion canceled.");
        }
    }

    /**
     * EFFECTS: Deletes an existing course based on user input.
     */
    private void deleteCourse() {
        if (courses.isEmpty()) {
            System.out.println("No courses available to delete.");
            return;
        }
        Course course = selectCourse();
        if (course == null) {
            return;
        }

        System.out.print("Are you sure you want to delete the course \"" + course.getName() + "\"? (yes/no): ");
        boolean confirm = getYesOrNo();
        if (confirm) {
            courses.remove(course);
            System.out.println("Course deleted successfully.");
        } else {
            System.out.println("Deletion canceled.");
        }
    }

    /**
     * EFFECTS: Prompts the user to select a course from the list.
     * or null if selection is invalid.
     */
    private Course selectCourse() {
        System.out.println("Available Courses:");
        for (int i = 0; i < courses.size(); i++) {
            System.out.println((i + 1) + ". " + courses.get(i));
        }
        System.out.print("Select a course by number: ");
        int choice = getChoiceInRange(1, courses.size());
        return courses.get(choice - 1);
    }

    /**
     * EFFECTS: Prompts the user to select a topic from the selected course.
     * or null if selection is invalid.   
     */
    private Topic selectTopic(Course course) {
        List<Topic> topics = course.getTopics();
        System.out.println("Available Topics:");
        for (int i = 0; i < topics.size(); i++) {
            System.out.println((i + 1) + ". " + topics.get(i));
        }
        System.out.print("Select a topic by number: ");
        int choice = getChoiceInRange(1, topics.size());
        return topics.get(choice - 1);
    }

    private void saveProgress() {
        JsonWriter writer = new JsonWriter("./data/studyTrack.json");
        try {
            writer.open();
            writer.write(courses);  
            writer.close();
            System.out.println("Progress saved successfully.");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to save progress: " + e.getMessage());
        }
    }    

    private void loadProgress() {
        JsonReader reader = new JsonReader("./data/studyTrack.json");
        try {
            courses = reader.read();  
            System.out.println("Progress loaded successfully.");
        } catch (IOException e) {
            System.out.println("Unable to load progress: " + e.getMessage());
        }
    }    
    
    @SuppressWarnings("methodlength")
    private void viewProgress() {
        if (courses.isEmpty()) {
            System.out.println("No courses available.");
            return;
        }
    
        for (Course course : courses) {
            System.out.println("Course: " + course.getName());
            System.out.printf("Overall Progress: %.2f%%\n", course.getOverallProgress());
    
            List<Topic> topics = course.getTopics();
            if (topics.isEmpty()) {
                System.out.println("  No topics available.");
            } else {
                for (Topic topic : topics) {
                    System.out.println("  Topic: " + topic.getName());
                    System.out.printf("  Topic Progress: %.2f%%\n", topic.getConfidenceLevel());
    
                    List<LessonObjective> objectives = topic.getLessonObjectives();
                    if (objectives.isEmpty()) {
                        System.out.println("    No lesson objectives.");
                    } else {
                        for (LessonObjective objective : objectives) {
                            String status = objective.isMastered() ? "Mastered" : "Not Mastered";
                            System.out.println("    Objective: " + objective.getDescription() + " [" + status + "]");
                        }
                    }
                }
            }
            System.out.println();  
        }
    }
    

    /**
     * EFFECTS: Retrieves a positive integer input from the user.
     */
    private int getPositiveInt() {
        int num;
        do {
            while (!scanner.hasNextInt()) {
                System.out.print("Please enter a valid number: ");
                scanner.next();
            }
            num = scanner.nextInt();
            if (num < 0) {
                System.out.print("Please enter a non-negative number: ");
            }
        } while (num < 0);
        scanner.nextLine(); // consume the remaining newline
        return num;
    }

    /**
     * EFFECTS: Retrieves a non-empty string input from the user.
     */
    private String getNonEmptyString() {
        String input;
        do {
            input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.print("Input cannot be empty. Please enter again: ");
            }
        } while (input.isEmpty());
        return input;
    }

    /**
     * REQUIRES: totalObjectives >= 0
     * EFFECTS: Prompts the user to enter a number between 0 and totalObjectives.
     */
    private int getConfidentObjectives(int totalObjectives) {
        int num;
        do {
            while (!scanner.hasNextInt()) {
                System.out.print("Please enter a valid number: ");
                scanner.next();
            }
            num = scanner.nextInt();
            if (num < 0 || num > totalObjectives) {
                System.out.print("Please enter a number between 0 and " + totalObjectives + ": ");
            }
        } while (num < 0 || num > totalObjectives);
        scanner.nextLine(); // consume the remaining newline
        return num;
    }

    /**
     * Retrieves a yes or no response from the user.
     */
    private boolean getYesOrNo() {
        String response;
        while (true) {
            response = scanner.nextLine().trim().toLowerCase();
            if (response.equals("yes") || response.equals("y")) {
                return true;
            } else if (response.equals("no") || response.equals("n")) {
                return false;
            } else {
                System.out.print("Please enter 'yes' or 'no': ");
            }
        }
    }

    /**
     * REQUIRES: min <= choice <= max
     * EFFECTS: Prompts the user until a valid choice is entered.
     */
    private int getChoiceInRange(int min, int max) {
        int choice;
        do {
            while (!scanner.hasNextInt()) {
                System.out.print("Please enter a valid number: ");
                scanner.next();
            }
            choice = scanner.nextInt();
            if (choice < min || choice > max) {
                System.out.print("Please enter a number between " + min + " and " + max + ": ");
            }
        } while (choice < min || choice > max);
        scanner.nextLine(); // consume the remaining newline
        return choice;
    }
}

package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseManager {
    private Map<String, Course> courses;

    // EFFECTS: Initializes a new CourseManager with no courses
    public CourseManager() {
        courses = new HashMap<>();
    }

    // EFFECTS: Adds a new course with the given name and logs the event.
    //          Returns true if the course was added, false if it already exists.
    public boolean addCourse(String courseName) {
        if (courses.containsKey(courseName)) {
            return false; // Course already exists
        }
        Course course = new Course(courseName);
        courses.put(courseName, course);
        EventLog.getInstance().logEvent(new Event("Added course '" + courseName + "' to the system."));
        return true;
    }

    // EFFECTS: Removes a course with the given name and logs the event.
    //          Returns true if the course was removed, false if it did not exist.
    public boolean removeCourse(String courseName) {
        if (!courses.containsKey(courseName)) {
            return false;
        }
        courses.remove(courseName);
        EventLog.getInstance().logEvent(new Event("Removed course '" + courseName + "' from the system."));
        return true;
    }

    // EFFECTS: Gets the course with the given name, or null if it does not exist.
    public Course getCourse(String courseName) {
        return courses.get(courseName);
    }

    // EFFECTS: Returns a list of all course names.
    public List<String> getCourseNames() {
        return List.copyOf(courses.keySet());
    }
    
}

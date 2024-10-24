package persistence;

import model.Course;
import model.LessonObjective;
import model.Topic;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads the StudyTrack state from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads list of courses from file and returns it;
    // throws IOException if an error occurs reading data from file
    public List<Course> read() throws IOException {
        String jsonData = readFile(source);
        JSONArray jsonArray = new JSONArray(jsonData);
        return parseCourses(jsonArray);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses list of courses from JSON array and returns it
    private List<Course> parseCourses(JSONArray jsonArray) {
        List<Course> courses = new ArrayList<>();
        for (Object obj : jsonArray) {
            JSONObject jsonCourse = (JSONObject) obj;
            courses.add(parseCourse(jsonCourse));
        }
        return courses;
    }

    // EFFECTS: parses a course from JSON object and returns it
    private Course parseCourse(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Course course = new Course(name);
        JSONArray topicsArray = jsonObject.getJSONArray("topics");
    
        for (Object obj : topicsArray) {
            JSONObject jsonTopic = (JSONObject) obj;
            Topic topic = parseTopic(jsonTopic);
    
            // Get the name from the parsed Topic and add it using the existing method
            course.addTopic(topic.getName());  // Using getName() to pass a String
        }
    
        return course;
    }
    

    // EFFECTS: parses a topic from JSON object and returns it
    private Topic parseTopic(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Topic topic = new Topic(name);
        JSONArray objectivesArray = jsonObject.getJSONArray("lessonObjectives");
    
        for (Object obj : objectivesArray) {
            JSONObject jsonObjective = (JSONObject) obj;
            LessonObjective lessonObjective = parseLessonObjective(jsonObjective);
    
            // Use the description to add the lesson objective to the topic
            topic.addLessonObjective(lessonObjective.getDescription());  // Using getDescription() to pass a String
        }
    
        return topic;
    }
    

    // EFFECTS: parses a lesson objective from JSON object and returns it
    private LessonObjective parseLessonObjective(JSONObject jsonObject) {
        String description = jsonObject.getString("description");
        boolean isMastered = jsonObject.getBoolean("isMastered");

        LessonObjective objective = new LessonObjective(description);
        if (isMastered) {
            objective.markAsMastered();
        }

        return objective;
    }
}

package persistence;

import model.Course;
import org.json.JSONArray;
import java.io.*;
import java.util.List;

// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// Represents a writer that writes JSON representation of the courses to file
public class JsonWriter {
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of the courses list to file
    public void write(List<Course> courses) {
        JSONArray jsonCourses = new JSONArray();
    
        for (Course course : courses) {
            jsonCourses.put(course.toJson());  // Convert each course to JSON
        }
    
        saveToFile(jsonCourses.toString(4));  // Write the JSON to file with indentation
    }
    

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}

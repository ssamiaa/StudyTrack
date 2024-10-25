package persistence;

import model.Course;
import model.Topic;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            List<Course> courses = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass, expected exception
        }
    }
    

    @Test
    void testReaderEmptyCourses() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyCourses.json");
        try {
            List<Course> courses = reader.read();
            assertEquals(0, courses.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralCourses() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralCourses.json");
        try {
            List<Course> courses = reader.read();
            assertEquals(2, courses.size());

            Course course1 = courses.get(0);
            assertEquals("Math 101", course1.getName());
            List<Topic> topics1 = course1.getTopics();
            assertEquals(2, topics1.size());
            checkTopic("Algebra", 100.0, topics1.get(0));
            checkTopic("Calculus", 50.0, topics1.get(1));

            Course course2 = courses.get(1);
            assertEquals("Science 101", course2.getName());
            List<Topic> topics2 = course2.getTopics();
            assertEquals(1, topics2.size());
            checkTopic("Biology", 80.0, topics2.get(0));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderWithConfidenceLevel() {
        JsonReader reader = new JsonReader("./data/testReaderWithConfidenceLevel.json");
        try {
            List<Course> courses = reader.read();
            assertEquals(1, courses.size());

            Course course = courses.get(0);
            assertEquals("Math 101", course.getName());

            List<Topic> topics = course.getTopics();
            assertEquals(2, topics.size());

            // Check first topic's confidence level
            Topic algebra = topics.get(0);
            assertEquals("Algebra", algebra.getName());
            assertEquals(100.0, algebra.getConfidenceLevel(), 0.01); // Ensure confidence level is loaded

            // Check second topic's confidence level
            Topic calculus = topics.get(1);
            assertEquals("Calculus", calculus.getName());
            assertEquals(50.0, calculus.getConfidenceLevel(), 0.01); // Ensure confidence level is loaded

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
    
}

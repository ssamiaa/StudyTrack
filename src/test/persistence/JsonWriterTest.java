package persistence;

import model.Course;
import model.Topic;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Course course = new Course("Math 101");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyCourses() {
        try {
            List<Course> courses = List.of();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyCourses.json");
            writer.open();
            writer.write(courses);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyCourses.json");
            courses = reader.read();
            assertEquals(0, courses.size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralCourses() {
        try {
            List<Course> courses = createSampleCourses();
    
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralCourses.json");
            writer.open();
            writer.write(courses);
            writer.close();
    
            validateWrittenData();
    
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
    
    private List<Course> createSampleCourses() {
        Course math = createMathCourse();
        Course science = createScienceCourse();
        return List.of(math, science);
    }
    
    private Course createMathCourse() {
        Course math = new Course("Math 101");
        Topic algebra = new Topic("Algebra");
        algebra.addLessonObjective("Understand variables");
        algebra.markObjectiveAsMastered(0);
        math.addTopicObject(algebra);
        math.addTopic("Calculus");
        return math;
    }
    
    private Course createScienceCourse() {
        Course science = new Course("Science 101");
        science.addTopic("Biology");
        science.getTopics().get(0).addLessonObjective("Understand cell structure");
        return science;
    }
    
    private void validateWrittenData() throws IOException {
        JsonReader reader = new JsonReader("./data/testWriterGeneralCourses.json");
        List<Course> courses = reader.read();
    
        assertEquals(2, courses.size());
    
        Course course1 = courses.get(0);
        assertEquals("Math 101", course1.getName());
        List<Topic> topics1 = course1.getTopics();
        assertEquals(2, topics1.size());
        checkTopic("Algebra", 100.0, topics1.get(0));
        checkTopic("Calculus", 0.0, topics1.get(1));
    
        Course course2 = courses.get(1);
        assertEquals("Science 101", course2.getName());
        List<Topic> topics2 = course2.getTopics();
        assertEquals(1, topics2.size());
        checkTopic("Biology", 0.0, topics2.get(0));
    }
    
}

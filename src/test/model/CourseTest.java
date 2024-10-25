package model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;


public class CourseTest {
    private Course course;
    private Course emptycourse;
    private Topic cells;
    private Topic genetics;

    @BeforeEach
    void setUp() {
        course = new Course("Biology 111");
        emptycourse = new Course("Math 101");
        course.addTopic("Cells");
        course.addTopic("Genetics");
        List<Topic> topics = course.getTopics();
        cells = topics.get(0);
        genetics = topics.get(1);
    }

    @Test
    void testInitialState() {
        assertEquals("Biology 111", course.getName());
        List<Topic> topics = course.getTopics();
        assertEquals(2, topics.size());
        assertEquals(0.0, course.getOverallProgress());
    }

    @Test
    void testAddTopic() {
        course.addTopic("Ecology");
        List<Topic> topics = course.getTopics();
        assertEquals(3, topics.size());
        assertEquals("Ecology", topics.get(2).getName());
        assertEquals(0.0, course.getOverallProgress());
    }

    @Test
    void testRemoveTopic() {
        course.removeTopic("Cells");
        List<Topic> topics = course.getTopics();
        assertEquals(1, topics.size());
        assertEquals("Genetics", topics.get(0).getName());
        assertEquals(0.0, course.getOverallProgress());
    }

    @Test
    void testOverallProgressSingleTopic() {

        assertEquals(0.0, emptycourse.getOverallProgress());
        cells.addLessonObjective("Define cell");
        cells.markObjectiveAsMastered(0);
        double expectedProgress = (100.0 + 0.0) / 2.0; 
        assertEquals(expectedProgress, course.getOverallProgress());
    }

    @Test
    void testOverallProgressMultipleTopics() {
        
        cells.addLessonObjective("Define cell");
        cells.markObjectiveAsMastered(0);

        
        genetics.addLessonObjective("Understand DNA replication");
        genetics.markObjectiveAsMastered(0);
        genetics.addLessonObjective("Learn Mendelian genetics");

        double expectedCellsProgress = 100.0;
        double expectedGeneticsProgress = (1.0 / 2.0) * 100.0; 
        double expectedOverall = (expectedCellsProgress + expectedGeneticsProgress) / 2.0; 
        assertEquals(expectedOverall, course.getOverallProgress());
    }

    @Test
    void testToString() {
        String expectedInitial = "Biology 111 (0.0% overall progress)";
        assertEquals(expectedInitial, course.toString());

        
        cells.addLessonObjective("Define cell");
        cells.markObjectiveAsMastered(0);
        String expectedAfterProgress = "Biology 111 (50.0% overall progress)";
        assertEquals(expectedAfterProgress, course.toString());
    }

    @Test
    void testToJson() {
        Course course = createSampleCourse(); // Create sample course
        JSONObject json = course.toJson();  // Convert course to JSON
        
        assertEquals("Math 101", json.getString("name"));  // Check course name
        
        JSONArray jsonTopics = json.getJSONArray("topics");
        assertEquals(2, jsonTopics.length());  // Ensure 2 topics exist

        validateAlgebraTopic(jsonTopics.getJSONObject(0)); // Validate first topic (Algebra)
        validateCalculusTopic(jsonTopics.getJSONObject(1)); // Validate second topic (Calculus)
    }

    private Course createSampleCourse() {
        Course course = new Course("Math 101");
        Topic algebra = new Topic("Algebra");
        algebra.addLessonObjective("Understand variables");
        algebra.markObjectiveAsMastered(0);

        Topic calculus = new Topic("Calculus");
        calculus.addLessonObjective("Understand derivatives");

        course.addTopicObject(algebra);
        course.addTopicObject(calculus);
        return course;
    }

    private void validateAlgebraTopic(JSONObject algebraJson) {
        assertEquals("Algebra", algebraJson.getString("name"));
        JSONArray objectives = algebraJson.getJSONArray("lessonObjectives");
        assertEquals(1, objectives.length());

        JSONObject objectiveJson = objectives.getJSONObject(0);
        assertEquals("Understand variables", objectiveJson.getString("description"));
        assertTrue(objectiveJson.getBoolean("isMastered"));
    }

    private void validateCalculusTopic(JSONObject calculusJson) {
        assertEquals("Calculus", calculusJson.getString("name"));
        JSONArray objectives = calculusJson.getJSONArray("lessonObjectives");
        assertEquals(1, objectives.length());

        JSONObject objectiveJson = objectives.getJSONObject(0);
        assertEquals("Understand derivatives", objectiveJson.getString("description"));
        assertFalse(objectiveJson.getBoolean("isMastered"));
    }
}


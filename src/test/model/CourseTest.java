package model;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;


public class CourseTest {
    private Course course;
    private Topic cells;
    private Topic genetics;

    @BeforeEach
    public void setUp() {
        course = new Course("Biology 111");
        course.addTopic("Cells");
        course.addTopic("Genetics");
        List<Topic> topics = course.getTopics();
        cells = topics.get(0);
        genetics = topics.get(1);
    }

    @Test
    public void testInitialState() {
        assertEquals("Biology 111", course.getName());
        List<Topic> topics = course.getTopics();
        assertEquals(2, topics.size());
        assertEquals(0.0, course.getOverallProgress());
    }

    @Test
    public void testAddTopic() {
        course.addTopic("Ecology");
        List<Topic> topics = course.getTopics();
        assertEquals(3, topics.size());
        assertEquals("Ecology", topics.get(2).getName());
        assertEquals(0.0, course.getOverallProgress());
    }

    @Test
    public void testRemoveTopic() {
        course.removeTopic("Cells");
        List<Topic> topics = course.getTopics();
        assertEquals(1, topics.size());
        assertEquals("Genetics", topics.get(0).getName());
        assertEquals(0.0, course.getOverallProgress());
    }

    @Test
    public void testOverallProgressSingleTopic() {
        cells.addLessonObjective("Define cell");
        cells.markObjectiveAsMastered(0);
        double expectedProgress = (100.0 + 0.0) / 2.0; 
        assertEquals(expectedProgress, course.getOverallProgress());
    }

    @Test
    public void testOverallProgressMultipleTopics() {
        
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
    public void testToString() {
        String expectedInitial = "Biology 111 (0.0% overall progress)";
        assertEquals(expectedInitial, course.toString());

        
        cells.addLessonObjective("Define cell");
        cells.markObjectiveAsMastered(0);
        String expectedAfterProgress = "Biology 111 (50.0% overall progress)";
        assertEquals(expectedAfterProgress, course.toString());
    }
}


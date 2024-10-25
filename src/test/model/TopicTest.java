package model;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

/**
 * Test class for Topic.
 */
public class TopicTest {
    private Topic topic;

    @BeforeEach
    void setUp() {
        topic = new Topic("Photosynthesis");
    }

    @Test
    void testInitialState() {
        assertEquals("Photosynthesis", topic.getName());
        List<LessonObjective> objectives = topic.getLessonObjectives();
        assertTrue(objectives.isEmpty());
        assertEquals(0.0, topic.getConfidenceLevel());
    }

    @Test
    void testAddLessonObjective() {
        topic.addLessonObjective("Define photosynthesis");
        List<LessonObjective> objectives = topic.getLessonObjectives();
        assertEquals(1, objectives.size());
        assertEquals("Define photosynthesis", objectives.get(0).getDescription());
        assertFalse(objectives.get(0).isMastered());
        assertEquals(0.0, topic.getConfidenceLevel());
    }

    @Test
    void testMarkObjectiveAsMastered() {
        topic.addLessonObjective("Define photosynthesis");
        topic.markObjectiveAsMastered(0);
        assertTrue(topic.getLessonObjectives().get(0).isMastered());
        assertEquals(100.0, topic.getConfidenceLevel());
    }

    @Test
    void testUnmarkObjectiveAsMastered() {
        topic.addLessonObjective("Define photosynthesis");
        topic.markObjectiveAsMastered(0);
        assertTrue(topic.getLessonObjectives().get(0).isMastered());
        topic.unmarkObjectiveAsMastered(0);
        assertFalse(topic.getLessonObjectives().get(0).isMastered());
        assertEquals(0.0, topic.getConfidenceLevel());
    }

    @Test
    void testConfidenceLevelMultipleObjectives() {
        topic.addLessonObjective("Define photosynthesis");
        topic.addLessonObjective("Explain the light-dependent reactions");
        topic.addLessonObjective("Describe the Calvin cycle");

        topic.markObjectiveAsMastered(0);
        topic.markObjectiveAsMastered(2);
        double expectedConfidence = ((2.0 / 3.0) * 100.0);
        assertEquals(expectedConfidence, topic.getConfidenceLevel());
    }

    @Test
    void testToString() {
        String expectedInitial = "Photosynthesis (0.0% confident)";
        assertEquals(expectedInitial, topic.toString());

        topic.addLessonObjective("Define photosynthesis");
        topic.markObjectiveAsMastered(0);
        String expectedAfterMaster = "Photosynthesis (100.0% confident)";
        assertEquals(expectedAfterMaster, topic.toString());
    }
    
    @Test
    void testUpdateConfidenceLevelEmptyObjectives() {
        
        Topic topic = new Topic("Algebra");

        
        topic.updateConfidenceLevel();

        assertEquals(0.0, topic.getConfidenceLevel(), 0.01, "Confidence level should be 0.0 when no lesson objectives");
    }
}

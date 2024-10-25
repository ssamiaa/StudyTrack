package model;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONObject;

public class LessonObjectiveTest {
    private LessonObjective lessonObjective;

    @BeforeEach
    void setUp() {
        lessonObjective = new LessonObjective("Understand photosynthesis");
    }

    @Test
    void testInitialState() {
        assertEquals("Understand photosynthesis", lessonObjective.getDescription());
        assertFalse(lessonObjective.isMastered());
    }

    @Test
    void testMarkAsMastered() {
        lessonObjective.markAsMastered();
        assertTrue(lessonObjective.isMastered());
    }

    @Test
    void testUnmarkAsMastered() {
        lessonObjective.markAsMastered();
        assertTrue(lessonObjective.isMastered());
        lessonObjective.unmarkAsMastered();
        assertFalse(lessonObjective.isMastered());
    }

    @Test
    void testToStringNotMastered() {
        String expected = "Understand photosynthesis [Not Mastered]";
        assertEquals(expected, lessonObjective.toString());
    }

    @Test
    void testToStringMastered() {
        lessonObjective.markAsMastered();
        String expected = "Understand photosynthesis [Mastered]";
        assertEquals(expected, lessonObjective.toString());
    }
    
    @Test
    void testToJson() {
        LessonObjective lessonObjective = createSampleLessonObjective();  // Create sample lesson objective
        JSONObject json = lessonObjective.toJson();  // Convert to JSON

        validateJson(json);  // Validate the JSON structure
    }

    private LessonObjective createSampleLessonObjective() {
        LessonObjective lessonObjective = new LessonObjective("Understand variables");
        lessonObjective.markAsMastered();
        return lessonObjective;
    }

    private void validateJson(JSONObject json) {
        assertEquals("Understand variables", json.getString("description"));  // Validate description
        assertTrue(json.getBoolean("isMastered"));  // Validate mastery status
    }
}

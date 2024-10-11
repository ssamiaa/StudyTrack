package model;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

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
}

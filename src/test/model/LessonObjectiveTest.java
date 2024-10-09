package model;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class LessonObjectiveTest {
    private LessonObjective lessonObjective;

    @BeforeEach
    public void setUp() {
        lessonObjective = new LessonObjective("Understand photosynthesis");
    }

    @Test
    public void testInitialState() {
        assertEquals("Understand photosynthesis", lessonObjective.getDescription());
        assertFalse(lessonObjective.isMastered());
    }

    @Test
    public void testMarkAsMastered() {
        lessonObjective.markAsMastered();
        assertTrue(lessonObjective.isMastered());
    }

    @Test
    public void testUnmarkAsMastered() {
        lessonObjective.markAsMastered();
        assertTrue(lessonObjective.isMastered());
        lessonObjective.unmarkAsMastered();
        assertFalse(lessonObjective.isMastered());
    }

    @Test
    public void testToStringNotMastered() {
        String expected = "Understand photosynthesis [Not Mastered]";
        assertEquals(expected, lessonObjective.toString());
    }

    @Test
    public void testToStringMastered() {
        lessonObjective.markAsMastered();
        String expected = "Understand photosynthesis [Mastered]";
        assertEquals(expected, lessonObjective.toString());
    }
}

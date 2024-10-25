package persistence;

import model.Topic;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkTopic(String name, double confidenceLevel, Topic topic) {
        assertEquals(name, topic.getName());
        assertEquals(confidenceLevel, topic.getConfidenceLevel());
    }
}


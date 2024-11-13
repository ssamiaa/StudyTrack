package ui;

import javax.swing.SwingUtilities;

/**
 * The entry point of the StudyTrack application.
 */
public class Main {
    public static void main(String[] args) {
        // StudyTrackApp app = new StudyTrackApp();
        // app.run();
        SwingUtilities.invokeLater(() -> new StudyTrackGUI());
    }
}

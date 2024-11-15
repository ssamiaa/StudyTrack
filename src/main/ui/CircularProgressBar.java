package ui;

import javax.swing.*;
import java.awt.*;

public class CircularProgressBar extends JPanel {
    private int progress = 0;

    public CircularProgressBar() {
        setPreferredSize(new Dimension(100, 100));  // Set a default size
    }

    public void setProgress(int progress) {
        this.progress = progress;
        repaint();  // Repaint the component whenever the progress changes
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        int size = Math.min(getWidth(), getHeight());
        int strokeWidth = 8;
        int radius = size / 2 - strokeWidth;

        // Draw background circle
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.setStroke(new BasicStroke(strokeWidth));
        g2d.drawArc(strokeWidth, strokeWidth, size - 2 * strokeWidth, size - 2 * strokeWidth, 0, 360);

        // Draw progress arc
        g2d.setColor(new Color(76, 175, 80));  // Green color for progress
        int angle = (int) (360 * (progress / 100.0));
        g2d.drawArc(strokeWidth, strokeWidth, size - 2 * strokeWidth, size - 2 * strokeWidth, 90, -angle);

        // Draw percentage text
        g2d.setFont(new Font("Serif", Font.BOLD, 18));
        FontMetrics fm = g2d.getFontMetrics();
        String text = progress + "%";
        int textX = (size - fm.stringWidth(text)) / 2;
        int textY = (size + fm.getAscent()) / 2 - 4;
        g2d.drawString(text, textX, textY);

        g2d.dispose();
    }
}

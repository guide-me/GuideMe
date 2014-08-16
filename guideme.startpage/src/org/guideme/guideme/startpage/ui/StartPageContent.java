package org.guideme.guideme.startpage.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class StartPageContent extends JPanel {

    private final static Color COLOR_TOP_START = new Color(233, 91, 51);
    private final static Color COLOR_TOP_END = new Color(254, 234,139);

    public StartPageContent() {
        super(new BorderLayout());

        add(new ShowOnStartup(), BorderLayout.SOUTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int width = getWidth();
        int height = getHeight();

        g2d.setPaint(new GradientPaint(0, 0, COLOR_TOP_START, 0, height, COLOR_TOP_END));
        g2d.fillRect(0, 0, width, height);
    }
}

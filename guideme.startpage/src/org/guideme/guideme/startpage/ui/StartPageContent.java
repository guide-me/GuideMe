package org.guideme.guideme.startpage.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JPanel;

public class StartPageContent extends JPanel {

    private final static Color COLOR_TOP_START = new Color(233, 91, 51);
    private final static Color COLOR_TOP_END = new Color(254, 234, 139);

    public StartPageContent() {
        super(new BorderLayout());
        
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new GridBagLayout());
        
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(40, 40, 40, 40);
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 1;
        panel.add(new RecentGuidesPanel(), gridBagConstraints);
        
        add(panel, BorderLayout.CENTER);
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

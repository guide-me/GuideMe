package org.guideme.guideme.startpage.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import org.guideme.guideme.filesupport.HistoryItem;
import org.guideme.guideme.filesupport.RecentGuideAction;
import org.guideme.guideme.filesupport.RecentGuides;
import org.openide.awt.StatusDisplayer;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.WindowManager;

@Messages({
    "LBL_NoRecentGuides=No recent guides",
    "LBL_RecentGuidesTitle=Recent guides"
})
public class RecentGuidesPanel extends JPanel implements Runnable {

    private static final int MAX_GUIDES = 10;

    public RecentGuidesPanel() {
        super(new BorderLayout());

        setOpaque(false);

        WindowManager.getDefault().invokeWhenUIReady(this);
    }

    @Override
    public void run() {
        removeAll();
        add(titleLabel(), BorderLayout.NORTH);
        add(rebuildContent(RecentGuides.getRecentGuides()), BorderLayout.CENTER);
        invalidate();
        revalidate();
        repaint();
    }

    private JLabel titleLabel() {
        JLabel titleLabel = new JLabel(Bundle.LBL_RecentGuidesTitle());
        titleLabel.setFont(new Font(getFont().getName(), Font.BOLD, getFont().getSize() + 7));
        return titleLabel;
    }

    private JPanel rebuildContent(List<HistoryItem> recentGuides) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        int row = 0;
        for (HistoryItem hItem : recentGuides) {
            addProject(panel, row++, hItem);
            if (row >= MAX_GUIDES) {
                break;
            }
        }
        if (0 == row) {
            panel.add(new JLabel(Bundle.LBL_NoRecentGuides()),
                    new GridBagConstraints(0, row, 1, 1, 1.0, 1.0,
                            GridBagConstraints.CENTER, GridBagConstraints.NONE,
                            new Insets(10, 10, 10, 10), 0, 0));
        } else {
            panel.add(new JLabel(), new GridBagConstraints(0, row, 1, 1, 0.0, 1.0,
                    GridBagConstraints.CENTER, GridBagConstraints.NONE,
                    new Insets(0, 0, 0, 0), 0, 0));
        }
        return panel;
    }

    private void addProject(JPanel panel, int row, final HistoryItem historyItem) {
        panel.add(new RecentGuideButton(historyItem), new GridBagConstraints(0, row, 1, 1, 1.0, 0.0,
                GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
    }

    private class RecentGuideButton extends JButton implements MouseListener, ActionListener {

        private final HistoryItem historyItem;

        public RecentGuideButton(HistoryItem historyItem) {
            this.historyItem = historyItem;

            setText("<html><u>" + historyItem.getGuideTitle() + "</u></html>");
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder());

            addMouseListener(this);
            addActionListener(this);
        }

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            StatusDisplayer.getDefault().setStatusText(historyItem.getPath());
        }

        @Override
        public void mouseExited(MouseEvent e) {
            StatusDisplayer.getDefault().setStatusText("");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            RecentGuideAction.openFile(historyItem.getPath());
        }

    }
}

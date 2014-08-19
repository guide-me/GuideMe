package org.guideme.guideme.startpage.ui;

import java.awt.BorderLayout;
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
import org.guideme.guideme.filesupport.RecentGuide;
import org.guideme.guideme.filesupport.RecentGuideAction;
import org.guideme.guideme.filesupport.RecentGuides;
import org.guideme.guideme.utilities.CentralLookup;
import org.openide.awt.StatusDisplayer;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.WindowManager;

@Messages({
    "LBL_NoRecentGuides=No recent guides",
    "LBL_RecentGuidesTitle=Recent guides"
})
public class RecentGuidesPanel extends JPanel implements Runnable, LookupListener {

    private static final int MAX_GUIDES = RecentGuides.getDefault().getMaxNumberOfGuides();

    Lookup.Result<RecentGuide> lookupResult;

    public RecentGuidesPanel() {
        super(new BorderLayout());

        setOpaque(false);

        lookupResult = CentralLookup.getDefault().lookupResult(RecentGuide.class);
        lookupResult.addLookupListener(this);

        WindowManager.getDefault().invokeWhenUIReady(this);
    }

    @Override
    public void run() {
        removeAll();
        add(titleLabel(), BorderLayout.NORTH);
        add(rebuildContent(RecentGuides.getDefault().getGuides()), BorderLayout.CENTER);
        invalidate();
        revalidate();
        repaint();
    }

    @Override
    public void resultChanged(LookupEvent ev) {
        SwingUtilities.invokeLater(this);
    }

    private JLabel titleLabel() {
        JLabel titleLabel = new JLabel(Bundle.LBL_RecentGuidesTitle());
        titleLabel.setFont(new Font(getFont().getName(), Font.BOLD, getFont().getSize() + 7));
        return titleLabel;
    }

    private JPanel rebuildContent(List<RecentGuide> recentGuides) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        int row = 0;
        for (RecentGuide hItem : recentGuides) {
            addProject(panel, row++, hItem);
            if (row >= MAX_GUIDES) {
                break;
            }
        }
        if (0 == row) {
            JLabel noGuides = new JLabel(Bundle.LBL_NoRecentGuides());
            noGuides.setFont(new Font(noGuides.getFont().getName(), Font.ITALIC, noGuides.getFont().getSize()));
            panel.add(noGuides,
                    new GridBagConstraints(0, row, 1, 1, 1.0, 0.0,
                            GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
        }
        panel.add(new JLabel(),
                new GridBagConstraints(0, row, 1, 1, 0.0, 1.0,
                        GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        return panel;
    }

    private void addProject(JPanel panel, int row, final RecentGuide historyItem) {
        panel.add(new RecentGuideButton(historyItem), new GridBagConstraints(0, row, 1, 1, 1.0, 0.0,
                GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
    }

    private class RecentGuideButton extends JButton implements MouseListener, ActionListener {

        private final RecentGuide historyItem;

        public RecentGuideButton(RecentGuide historyItem) {
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

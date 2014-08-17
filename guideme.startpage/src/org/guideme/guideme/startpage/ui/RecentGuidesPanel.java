package org.guideme.guideme.startpage.ui;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.guideme.guideme.filesupport.HistoryItem;
import org.guideme.guideme.filesupport.RecentGuides;
import org.openide.windows.WindowManager;

public class RecentGuidesPanel extends JPanel {

    public RecentGuidesPanel() {

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        WindowManager.getDefault().invokeWhenUIReady(() -> {
            RecentGuides.getRecentGuides().stream()
                    .forEach((HistoryItem hItem) -> {
                        add(new JLabel(hItem.getGuideTitle()));
                    });

            invalidate();
            revalidate();
            repaint();
        });
    }

}

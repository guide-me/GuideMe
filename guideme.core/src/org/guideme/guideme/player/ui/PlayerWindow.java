package org.guideme.guideme.player.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Insets;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import org.guideme.guideme.controls.GmButton;
import org.guideme.guideme.controls.ImagePanel;
import org.guideme.guideme.model.Button;
import org.guideme.guideme.model.Guide;
import org.guideme.guideme.player.CurrentPageChangeEvent;
import org.guideme.guideme.player.CurrentPageChangeListener;
import org.guideme.guideme.player.GuidePlayer;
import org.openide.filesystems.FileObject;

/**
 * Window for the GuidePlayer.
 *
 */
public class PlayerWindow extends javax.swing.JFrame implements CurrentPageChangeListener {

    private GuidePlayer guidePlayer;

    private final JPanel contentContainer;
    private final ImagePanel imagePanel;
    private final JTextPane textPane;
    private final JPanel buttonsContainer;

    public PlayerWindow() {
        setLayout(new BorderLayout());

        contentContainer = new JPanel();
        contentContainer.setLayout(new BorderLayout());

        imagePanel = new ImagePanel();
        contentContainer.add(imagePanel, BorderLayout.CENTER);

        textPane = new JTextPane();
        textPane.setBackground(this.getBackground());
        textPane.setMargin(new Insets(20, 20, 20, 20));
        textPane.setContentType("text/html");
        // TODO read horizontal/vertical layout user preferences.
        contentContainer.add(textPane, BorderLayout.SOUTH);
        
        add(contentContainer, BorderLayout.CENTER);

        buttonsContainer = new JPanel();
        buttonsContainer.setLayout(new FlowLayout(FlowLayout.RIGHT));
        add(buttonsContainer, BorderLayout.SOUTH);

        pack();
    }

    /**
     * Starts to play the Guide.
     *
     * @param guide
     */
    public void playGuide(Guide guide, FileObject guideDirectory) {
        setTitle(guide.getTitle());

        guidePlayer = new GuidePlayer(guide, guideDirectory);
        guidePlayer.addCurrentPageChangeListener(this);
        guidePlayer.start();
    }

    /**
     * Updates the window after the current page has changed.
     *
     * @param ev
     */
    @Override
    public void currentPageChanged(CurrentPageChangeEvent ev) {

        if (ev.getImage() != null) {
            imagePanel.showImage(ev.getImage());
            imagePanel.scaleImage(contentContainer.getSize());
            // TODO else : show empty image.
        }

        textPane.setText(ev.getText());

        buttonsContainer.removeAll();
        for (Button btn : ev.getButtons()) {
            GmButton button = new GmButton(btn);
            button.addActionListener((java.awt.event.ActionEvent evt) -> {
                guidePlayer.buttonPressed(button.getButton());
            });
            buttonsContainer.add(button);
        }

        // TODO start delay, play audio, play video etc.
        
        revalidate();
        repaint();
    }
}

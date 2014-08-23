package org.guideme.guideme.player.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import org.guideme.guideme.controls.GmButton;
import org.guideme.guideme.controls.ImagePanel;
import org.guideme.guideme.model.Button;
import org.guideme.guideme.model.Guide;
import org.guideme.guideme.player.CountdownChangeEvent;
import org.guideme.guideme.player.CountdownChangeListener;
import org.guideme.guideme.player.CurrentPageChangeEvent;
import org.guideme.guideme.player.CurrentPageChangeListener;
import org.guideme.guideme.player.GuidePlayer;
import org.openide.filesystems.FileObject;

/**
 * Window for the GuidePlayer.
 *
 */
public class PlayerWindow extends javax.swing.JFrame
        implements CurrentPageChangeListener, CountdownChangeListener {

    private GuidePlayer guidePlayer;

    private boolean showRemainingSeconds;

    private final JPanel contentContainer;
    private final ImagePanel imagePanel;
    private final JTextPane textPane;
    private final JPanel buttonsContainer;
    private final JLabel timeRemainingLabel;

    public PlayerWindow() {
        setLayout(new BorderLayout());

        contentContainer = new JPanel();
        contentContainer.setLayout(new BorderLayout());

        imagePanel = new ImagePanel();
        contentContainer.add(imagePanel, BorderLayout.CENTER);

        timeRemainingLabel = new JLabel();
        contentContainer.add(timeRemainingLabel, BorderLayout.EAST);

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

        showRemainingSeconds = false;
        timeRemainingLabel.setText("");
        if (ev.getDelay() != null) {
            switch (ev.getDelay().getStyle()) {
                case Normal: {
                    showRemainingSeconds = true;
                    timeRemainingLabel.setText(formatCountdownTime(ev.getDelay().getPeriodInSeconds()));
                    break;
                }
                case Secret: {
                    timeRemainingLabel.setText("??:??:??");
                    break;
                }
                case Hidden: {
                    timeRemainingLabel.setText("");
                    break;
                }
            }
            guidePlayer.addCountdownChangeListener(this);
            guidePlayer.startCountdown();
        }

        // TODO play audio, play video etc.
        
        revalidate();
        repaint();
    }

    @Override
    public void countdownChanged(CountdownChangeEvent ev) {
        if (showRemainingSeconds) {
            timeRemainingLabel.setText(formatCountdownTime(ev.getSecondsRemaining()));
        }
    }

    private String formatCountdownTime(int secondsRemaining) {
        int hrs = secondsRemaining / (60 * 60);
        int min = (secondsRemaining - (hrs * 60 * 60)) / 60;
        int sec = secondsRemaining - (hrs * 60 * 60) - (min * 60);
        if (hrs > 0) {
            return String.format("%d:%02d:%02d", hrs, min, sec);
        }
        return String.format("%d:%02d", min, sec);
    }
}

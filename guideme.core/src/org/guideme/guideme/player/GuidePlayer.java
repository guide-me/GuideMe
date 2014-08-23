package org.guideme.guideme.player;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import org.guideme.guideme.Constants;
import org.guideme.guideme.model.Button;
import org.guideme.guideme.model.Delay;
import org.guideme.guideme.model.Guide;
import org.guideme.guideme.model.Image;
import org.guideme.guideme.model.Page;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;

public class GuidePlayer {

    private final Set<CurrentPageChangeListener> currentPagelisteners = new HashSet<>();
    private final Set<CountdownChangeListener> currentCountdownlisteners = new HashSet<>();

    private final Guide guide;
    private final FileObject guideDirectory;

    private Page currentPage;
    private Delay currentDelay;
    private int secondsRemaining;

    public GuidePlayer(Guide guide, FileObject guideDirectory) {
        this.guide = guide;
        this.guideDirectory = guideDirectory;
    }

    public final void addCurrentPageChangeListener(CurrentPageChangeListener l) {
        synchronized (currentPagelisteners) {
            currentPagelisteners.add(l);
        }
    }

    public final void removeCurrentPageChangeListener(CurrentPageChangeListener l) {
        synchronized (currentPagelisteners) {
            currentPagelisteners.remove(l);
        }
    }

    protected void fireCurrentPageChangeEvent() {
        Set<CurrentPageChangeListener> ls;
        synchronized (currentPagelisteners) {
            ls = new HashSet<>(currentPagelisteners);
        }
        CurrentPageChangeEvent ev = createCurrentPageChangeEvent();
        for (CurrentPageChangeListener l : ls) {
            l.currentPageChanged(ev);
        }
    }

    public final void addCountdownChangeListener(CountdownChangeListener l) {
        synchronized (currentCountdownlisteners) {
            currentCountdownlisteners.add(l);
        }
    }

    public final void removeCountdownChangeListener(CountdownChangeListener l) {
        synchronized (currentCountdownlisteners) {
            currentCountdownlisteners.remove(l);
        }
    }
    
    private final void removeAllCountdownChangeListeners() {
        synchronized (currentCountdownlisteners) {
            currentCountdownlisteners.clear();
        }
    }
    
    protected void fireCountdownChangeEvent(int remaining) {
        Set<CountdownChangeListener> ls;
        synchronized (currentCountdownlisteners) {
            ls = new HashSet<>(currentCountdownlisteners);
        }
        CountdownChangeEvent ev = new CountdownChangeEvent(this, remaining);
        for (CountdownChangeListener l : ls) {
            l.countdownChanged(ev);
        }
    }

    public void startCountdown() {
        int initialSeconds = secondsRemaining;
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        ScheduledFuture<?> countdownHandle
                = scheduler.scheduleAtFixedRate(() -> {
                    fireCountdownChangeEvent(--secondsRemaining);
                }, 1, 1, TimeUnit.SECONDS);
        scheduler.schedule(() -> {
            removeAllCountdownChangeListeners();
            countdownHandle.cancel(true);
            // TODO target might return wildcards.
            currentPage = guide.findPage(currentDelay.getTarget());
            fireCurrentPageChangeEvent();
        }, initialSeconds, TimeUnit.SECONDS);
    }


    CurrentPageChangeEvent createCurrentPageChangeEvent() {
        // TODO substitute variables...
        String text = currentPage.getText();

        // TODO calculate using flags...
        File imageFile = null;
        Image gmImage = currentPage.getImages().iterator().hasNext() ? currentPage.getImages().iterator().next() : null;
        if (gmImage != null) {
            // TODO getSrc() might return image name with wildcards. 
            imageFile = FileUtil.toFile(guideDirectory.getFileObject(gmImage.getSrc()));
        }

        currentDelay = null;
        if (!currentPage.getDelays().isEmpty()) {
            // TODO calculate using flags..
            currentDelay = currentPage.getDelays().get(0);
            secondsRemaining = currentDelay.getPeriodInSeconds();
        }

        // TODO calculate using flags...
        List<Button> buttons = currentPage.getButtons();

        return new CurrentPageChangeEvent(this, text, imageFile, currentDelay, buttons);
    }

    public String getTitle() {
        return guide.getTitle();
    }

    public Page getCurrentPage() {
        return currentPage;
    }

    public void start() {
        currentPage = guide.findPage(Constants.START_PAGE_ID);
        fireCurrentPageChangeEvent();
    }

    public void buttonPressed(Button button) {
        currentPage = guide.findPage(button.getTarget());
        fireCurrentPageChangeEvent();
    }
}

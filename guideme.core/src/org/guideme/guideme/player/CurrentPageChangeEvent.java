package org.guideme.guideme.player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.event.ChangeEvent;
import org.guideme.guideme.model.Button;
import org.guideme.guideme.model.Delay;

/**
 * Event raised when the current page changed. The get-methods give the
 * properties after calculation using the if-set/if-not-set flags etc.
 */
public class CurrentPageChangeEvent extends ChangeEvent {

    private final String text;
    private final File imageFile;
    private final Delay delay;
    private final List<Button> buttons = new ArrayList<>();

    public CurrentPageChangeEvent(Object source, String text, File imageFile, Delay delay, List<Button> buttons) {
        super(source);
        this.text = text;
        this.imageFile = imageFile;
        this.delay = delay;
        
        if (buttons != null) {
            this.buttons.addAll(buttons);
        }
    }

    /**
     * The final text to be shown to the user. Variables are already
     * substituted.
     *
     * @return
     */
    public String getText() {
        // TODO substitute the variables in the text.
        return text;
    }

    /**
     * The final image file to be shown to the user.
     *
     * @return
     */
    public File getImage() {
        return imageFile;
    }

    /**
     * The delay used on the page.
     * @return 
     */
    public Delay getDelay() {
        return delay;
    }
    
    /**
     * The final buttons to be shown to the user.
     *
     * @return
     */
    public List<Button> getButtons() {
        return buttons;
    }
}

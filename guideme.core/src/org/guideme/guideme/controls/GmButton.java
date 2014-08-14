package org.guideme.guideme.controls;

import javax.swing.JButton;
import org.guideme.guideme.model.Button;

public class GmButton extends JButton {

    private final Button button;

    public GmButton(Button button) {
        this.button = button;
        
        setText(button.getText());
    }

    public Button getButton() {
        return button;
    }
    
}

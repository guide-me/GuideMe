package org.guideme.guideme.startpage;

import java.awt.Font;
import javax.swing.UIManager;

public final class Constants {

    static final int START_PAGE_MIN_WIDTH = 700;
    
    static final int FONT_SIZE = getDefaultFontSize();

    
    // TODO move to general utils class.
    static int getDefaultFontSize() {
        Integer customFontSize = (Integer) UIManager.get("customFontSize"); // NOI18N
        if (customFontSize != null) {
            return customFontSize;
        } else {
            Font systemDefaultFont = UIManager.getFont("TextField.font"); // NOI18N
            return (systemDefaultFont != null)
                    ? systemDefaultFont.getSize()
                    : 11;
        }
    }
}

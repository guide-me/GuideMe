package org.guideme.guideme.nb.viewer.resources;

import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import org.netbeans.api.annotations.common.StaticResource;
import org.openide.util.ImageUtilities;

public final class Icons {

    @StaticResource()
    public static final String GUIDE_PLAYER_ICON = "org/guideme/guideme/nb/viewer/resources/book_go.png";
    
    @StaticResource()
    public static final String PLAY_GUIDE_ICON = "org/guideme/guideme/nb/viewer/resources/bullet_go.png";

    
    public static final Image getGuidePlayerImage() {
        return ImageUtilities.loadImage(GUIDE_PLAYER_ICON);
    }

    public static final Icon getGuidePlayerIcon() {
        return new ImageIcon(getGuidePlayerImage());
    }

    public static final Image getPlayGuideImage() {
        return ImageUtilities.loadImage(PLAY_GUIDE_ICON);
    }

    public static final Icon getPlayGuideIcon() {
        return new ImageIcon(getPlayGuideImage());
    }
    
}

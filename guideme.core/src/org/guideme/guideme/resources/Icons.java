package org.guideme.guideme.resources;

import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import org.netbeans.api.annotations.common.StaticResource;
import org.openide.util.ImageUtilities;

public final class Icons {

    @StaticResource()
    public static final String GUIDE_ICON = "org/guideme/guideme/resources/guide.png";
    
    @StaticResource()
    public static final String GUIDE_OPEN_ICON = "org/guideme/guideme/resources/guideOpen.png";

    
    @StaticResource()
    public static final String CHAPTER_ICON = "org/guideme/guideme/resources/chapter.png";
    
    @StaticResource()
    public static final String CHAPTER_OPEN_ICON = "org/guideme/guideme/resources/chapterOpen.png";
    
    @StaticResource()
    public static final String PAGE_ICON = "org/guideme/guideme/resources/page.png";

    @StaticResource()
    public static final String GUIDE_PLAYER_ICON = "org/guideme/guideme/resources/playGuide.png";
    
    @StaticResource()
    public static final String PLAY_GUIDE_ICON = "org/guideme/guideme/resources/play.png";

        
    public static final Image getGuideImage() {
        return ImageUtilities.loadImage(GUIDE_ICON);
    }

    public static final Icon getGuideIcon() {
        return new ImageIcon(getGuideImage());
    }

    public static final Image getOpenedGuideImage() {
        return ImageUtilities.loadImage(GUIDE_OPEN_ICON);
    }

    public static final Icon getOpenedGuideIcon() {
        return new ImageIcon(getGuideImage());
    }

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

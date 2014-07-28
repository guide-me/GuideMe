package org.guideme.guideme.nb.project.resources;

import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import org.netbeans.api.annotations.common.StaticResource;
import org.openide.util.ImageUtilities;

public final class Icons {

    @StaticResource()
    public static final String GUIDE_ICON = "org/guideme/guideme/nb/project/resources/guide.png";
    
    @StaticResource()
    public static final String GUIDE_OPEN_ICON = "org/guideme/guideme/nb/project/resources/guideOpen.png";

    
    @StaticResource()
    public static final String CHAPTER_ICON = "org/guideme/guideme/nb/project/resources/chapter.png";
    
    @StaticResource()
    public static final String CHAPTER_OPEN_ICON = "org/guideme/guideme/nb/project/resources/chapterOpen.png";
    
    @StaticResource()
    public static final String PAGE_ICON = "org/guideme/guideme/nb/project/resources/page.png";

    
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
}

package org.guideme.guideme.nb.project.resources;

import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import org.netbeans.api.annotations.common.StaticResource;
import org.openide.util.ImageUtilities;

public final class Icons {

    @StaticResource()
    public static final String LIBRARY_ICON = "org/guideme/guideme/nb/project/resources/library_icon.png";

    @StaticResource()
    public static final String GUIDE_ICON = "org/guideme/guideme/nb/project/resources/guide_icon.png";

    @StaticResource()
    public static final String GUIDE_OPEN_ICON = "org/guideme/guideme/nb/project/resources/guide_open_icon.png";
    
    
    public static final Image getLibraryImage() {
        return ImageUtilities.loadImage(LIBRARY_ICON);
    }

    public static final Icon getLibraryIcon() {
        return new ImageIcon(getLibraryImage());
    }

    public static final Image getGuideImage() {
        return ImageUtilities.loadImage(GUIDE_ICON);
    }

    public static final Icon getGuideIcon() {
        return new ImageIcon(getGuideImage());
    }
    
    public static final Image getGuideOpenImage() {
        return ImageUtilities.loadImage(GUIDE_OPEN_ICON);
    }

    public static final Icon getGuideOpenIcon() {
        return new ImageIcon(getGuideOpenImage());
    }

}

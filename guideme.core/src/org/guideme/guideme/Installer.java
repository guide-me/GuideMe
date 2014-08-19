package org.guideme.guideme;

import org.guideme.guideme.filesupport.RecentGuides;
import org.openide.modules.ModuleInstall;

/**
 * Installer for the module. 
 */
public class Installer extends ModuleInstall {

    /**
     * Restores module.
     */
    @Override
    public void restored() {
        RecentGuides.getDefault().initialize();
    }

}

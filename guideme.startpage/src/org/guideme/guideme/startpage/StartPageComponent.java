// Implementation is taken and adapted from org.netbeans.modules.welcome.WelcomeComponent.
package org.guideme.guideme.startpage;

import java.lang.ref.WeakReference;
import org.openide.util.NbBundle;
import org.openide.windows.*;
import java.awt.*;
import javax.swing.*;
import org.guideme.guideme.startpage.ui.StartPageContent;
import org.openide.ErrorManager;
import org.openide.nodes.Node;
import org.openide.util.NbPreferences;

public class StartPageComponent extends TopComponent {

    private static WeakReference<StartPageComponent> component = new WeakReference<>(null);

    private JComponent content;

    private boolean initialized = false;

    private StartPageComponent() {
        setLayout(new BorderLayout());
        setName(NbBundle.getMessage(StartPageComponent.class, "LBL_Tab_Title"));
        content = null;
        initialized = false;
        putClientProperty("activateAtStartup", Boolean.TRUE);
        putClientProperty("KeepNonPersistentTCInModelWhenClosed", Boolean.TRUE);
    }

    @Override
    protected String preferredID() {
        return "StartPageComponent";
    }

    /**
     * lazy addition of GUI components
     */
    private void doInitialize() {
        if (null == content) {
            JScrollPane scroll = new JScrollPane(new StartPageContent());
            scroll.setBorder(BorderFactory.createEmptyBorder());
            scroll.getViewport().setOpaque(false);
            scroll.setOpaque(false);
            scroll.getViewport().setPreferredSize(new Dimension(Constants.START_PAGE_MIN_WIDTH, 100));
            JScrollBar vertical = scroll.getVerticalScrollBar();
            if (null != vertical) {
                vertical.setBlockIncrement(30 * Constants.FONT_SIZE);
                vertical.setUnitIncrement(Constants.FONT_SIZE);
            }
            content = scroll;
            add(content, BorderLayout.CENTER);
            setFocusable(false);
        }
    }

    /* Singleton accessor. As StartPageComponent is persistent singleton this
     * accessor makes sure that StartPageComponent is deserialized by window system.
     * Uses known unique TopComponent ID "StartPage" to get StartPageComponent instance
     * from window system. "StartPage" is name of settings file defined in module layer.
     */
    public static StartPageComponent findComp() {
        StartPageComponent wc = component.get();
        if (wc == null) {
            TopComponent tc = WindowManager.getDefault().findTopComponent("StartPage"); // NOI18N
            if (tc != null) {
                if (tc instanceof StartPageComponent) {
                    wc = (StartPageComponent) tc;
                    component = new WeakReference<>(wc);
                } else {
                    //Incorrect settings file?
                    IllegalStateException exc = new IllegalStateException("Incorrect settings file. Unexpected class returned." // NOI18N
                            + " Expected:" + StartPageComponent.class.getName() // NOI18N
                            + " Returned:" + tc.getClass().getName()); // NOI18N
                    ErrorManager.getDefault().notify(ErrorManager.INFORMATIONAL, exc);
                    //Fallback to accessor reserved for window system.
                    wc = StartPageComponent.createComp();
                }
            } else {
                //StartPageComponent cannot be deserialized
                //Fallback to accessor reserved for window system.
                wc = StartPageComponent.createComp();
            }
        }
        return wc;
    }

    /* Singleton accessor reserved for window system ONLY. Used by window system to create
     * StartPageComponent instance from settings file when method is given. Use <code>findComp</code>
     * to get correctly deserialized instance of StartPageComponent. */
    public static StartPageComponent createComp() {
        StartPageComponent wc = component.get();
        if (wc == null) {
            wc = new StartPageComponent();
            component = new WeakReference<>(wc);
        }
        return wc;
    }

    @Override
    public int getPersistenceType() {
        return TopComponent.PERSISTENCE_ONLY_OPENED;
    }

    /**
     * Called when <code>TopComponent</code> is about to be shown. Shown here
     * means the component is selected or resides in it own cell in container in
     * its <code>Mode</code>. The container is visible and not minimized.
     * <p>
     * <em>Note:</em> component is considered to be shown, even its container
     * window is overlapped by another window.</p>
     *
     * @since 2.18
     *
     * #38900 - lazy addition of GUI components
     *
     */
    @Override
    protected void componentShowing() {
        if (!initialized) {
            initialized = true;
            doInitialize();
        }
        if (null != content && getComponentCount() == 0) {
            //notify components down the hierarchy tree that they should 
            //refresh their content (e.g. RSS feeds)
            add(content, BorderLayout.CENTER);
        }
        super.componentShowing();
        setActivatedNodes(new Node[]{});
    }

    private static boolean firstTimeOpen = true;

    @Override
    protected void componentOpened() {
        super.componentOpened();
        if (firstTimeOpen) {
            firstTimeOpen = false;
            if( !StartPageOptions.getDefault().isShowOnStartup() ) {
                close();
            }
        }
    }

    @Override
    protected void componentClosed() {
        super.componentClosed();
        TopComponentGroup group = WindowManager.getDefault().findTopComponentGroup("InitialLayout"); //NOI18N
        if (null != group) {
            group.open();
            boolean firstTimeClose = NbPreferences.forModule(StartPageComponent.class).getBoolean("firstTimeClose", true); //NOI18N
            NbPreferences.forModule(StartPageComponent.class).putBoolean("firstTimeClose", false); //NOI18N
            if (firstTimeClose) {
                TopComponent tc = WindowManager.getDefault().findTopComponent("projectTabLogical_tc"); //NOI18N
                if (null != tc && tc.isOpened()) {
                    tc.requestActive();
                }
            }
        }
    }

    @Override
    protected void componentHidden() {
        super.componentHidden();
        if (null != content) {
            //notify components down the hierarchy tree that they no long 
            //need to periodically refresh their content (e.g. RSS feeds)
            remove(content);
        }
    }

    @Override
    public void requestFocus() {
        if (null != content) {
            content.requestFocus();
        }
    }

    @Override
    public boolean requestFocusInWindow() {
        if (null != content) {
            return content.requestFocusInWindow();
        }
        return super.requestFocusInWindow();
    }

}

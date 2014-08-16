// Implementation is taken and adapted from org.netbeans.modules.openfile.RecentFileAction.
package org.guideme.guideme.filesupport;

import java.awt.Component;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.MenuElement;
import javax.swing.MenuSelectionManager;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import org.guideme.guideme.filesupport.RecentGuides.HistoryItem;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.awt.DynamicMenuContent;
import org.openide.awt.StatusDisplayer;
import org.openide.cookies.OpenCookie;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle;
import org.openide.util.RequestProcessor;
import org.openide.util.Utilities;
import org.openide.util.actions.Presenter;

@ActionRegistration(
        lazy = false,
        displayName = "#LBL_RecentGuideAction_Name")
@ActionID(
        category = "Guide",
        id = "org.guideme.guideme.filesupport.RecentGuideAction")
@ActionReference(
        path = "Menu/File",
        position = 20)
@NbBundle.Messages({
    "LBL_RecentGuideAction_Name=Recent Guides",
    "OFMSG_NO_RECENT_FILE=No recent Guide.",
    "OFMSG_PATH_IS_NOT_DEFINED=Path is not defined.",
    "OFMSG_FILE_NOT_EXISTS=File does not exist."
})
public class RecentGuideAction extends AbstractAction
        implements Presenter.Menu, PopupMenuListener, ChangeListener {

    private static final Logger LOG
            = Logger.getLogger(RecentGuideAction.class.getName());

    private static final RequestProcessor RP
            = new RequestProcessor(RecentGuideAction.class);

    /**
     * property of menu items where we store file path to open
     */
    private static final String PATH_PROP = "RecentGuideAction.Recent_Guide_Path";

    private JMenu menu;

    public RecentGuideAction() {
        super(Bundle.LBL_RecentGuideAction_Name());
    }

    @Override
    public JMenuItem getMenuPresenter() {
        if (menu == null) {
            menu = new UpdatingMenu(this);
            // #115277 - workaround, PopupMenuListener don't work on Mac 
            if (!Utilities.isMac()) {
                menu.getPopupMenu().addPopupMenuListener(this);
            } else {
                menu.addChangeListener(this);
            }
        }
        return menu;
    }

    @Override
    public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
        fillSubMenu();
    }

    @Override
    public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
        menu.removeAll();
    }

    @Override
    public void popupMenuCanceled(PopupMenuEvent e) {
    }

    /**
     * Delegates to popupMenuListener based on menu current selection status
     */
    @Override
    public void stateChanged(ChangeEvent e) {
        if (menu.isSelected()) {
            popupMenuWillBecomeVisible(null);
        } else {
            popupMenuWillBecomeInvisible(null);
        }
    }

    /**
     * Fills sub menu with recently closed guides got from RecentGuides support
     */
    private void fillSubMenu() {
        RecentGuides.getRecentGuides().stream().forEach((hItem) -> {
            try {
                menu.add(newSubMenuItem(hItem));
            } catch (Exception ex) {
                LOG.log(Level.FINE, "Exception while filling submenu: {0}", ex.getMessage());
            }
        });
        ensureSelected();
    }

    /**
     * Creates and configures an item of the sub menu according to the given
     * {@code HistoryItem}.
     *
     * @param hItem the {@code HistoryItem}.
     * @return the menu item.
     */
    private JMenuItem newSubMenuItem(final HistoryItem hItem) {
        final String path = hItem.getPath();
        final JMenuItem jmi = new JMenuItem(hItem.getGuideTitle()) {

            @Override
            public void menuSelectionChanged(boolean isIncluded) {
                super.menuSelectionChanged(isIncluded);
                if (isIncluded) {
                    StatusDisplayer.getDefault().setStatusText(path);
                }
            }
        };
        jmi.putClientProperty(PATH_PROP, path);
        jmi.addActionListener(this);
        jmi.setToolTipText(path);
        return jmi;
    }

    /**
     * Workaround for JDK bug 6663119, it ensures that first item in sub menu is
     * correctly selected during keyboard navigation.
     */
    private void ensureSelected() {
        if (menu.getMenuComponentCount() <= 0) {
            return;
        }

        Component first = menu.getMenuComponent(0);
        if (!(first instanceof JMenuItem)) {
            return;
        }
        PointerInfo pointerInfo = MouseInfo.getPointerInfo();
        if (pointerInfo == null) {
            return; // probably a mouseless computer
        }
        Point loc = pointerInfo.getLocation();
        SwingUtilities.convertPointFromScreen(loc, menu);
        MenuElement[] selPath
                = MenuSelectionManager.defaultManager().getSelectedPath();

        // apply workaround only when mouse is not hovering over menu
        // (which signalizes mouse driven menu traversing) and only
        // when selected menu path contains expected value - submenu itself 
        if (!menu.contains(loc) && selPath.length > 0
                && menu.getPopupMenu() == selPath[selPath.length - 1]) {
            // select first item in submenu through MenuSelectionManager
            MenuElement[] newPath = new MenuElement[selPath.length + 1];
            System.arraycopy(selPath, 0, newPath, 0, selPath.length);
            JMenuItem firstItem = (JMenuItem) first;
            newPath[selPath.length] = firstItem;
            MenuSelectionManager.defaultManager().setSelectedPath(newPath);
        }
    }

    /**
     * Opens recently closed file, using OpenFile support.
     *
     * Note that method works as action handler for individual sub menu items
     * created in fillSubMenu, not for whole RecentGuideAction.
     *
     * @param evt
     */
    @Override
    public void actionPerformed(ActionEvent evt) {
        Object source = evt.getSource();
        String path = null;
        String msg = null;
        if (source instanceof JMenuItem) {
            JMenuItem menuItem = (JMenuItem) source;
            path = (String) menuItem.getClientProperty(PATH_PROP);
        } else {
            List<HistoryItem> items = RecentGuides.getRecentGuides();
            if (!items.isEmpty()) {
                HistoryItem item = items.get(0);
                path = item.getPath();
            } else {
                msg = Bundle.OFMSG_NO_RECENT_FILE();
            }
        }
        if (null == msg) {
            msg = openFile(path);
        }
        if (msg != null) {
            StatusDisplayer.getDefault().setStatusText(msg);
            Toolkit.getDefaultToolkit().beep();
            RecentGuides.pruneHistory();
        }
    }

    /**
     * Open a file.
     *
     * @param path the path to the file or {@code null}.
     * @return error message or {@code null} on success.
     */
    private String openFile(String path) {
        if (path == null || path.length() == 0) {
            return Bundle.OFMSG_PATH_IS_NOT_DEFINED();
        }
        File f = new File(path);
        if (!f.exists()) {
            return Bundle.OFMSG_FILE_NOT_EXISTS();
        }
        File nf = FileUtil.normalizeFile(f);
        //Original code: return OpenFile.open(FileUtil.toFileObject(nf), -1);
        try {
            DataObject file = DataObject.find(FileUtil.toFileObject(nf));
            file.getLookup().lookup(OpenCookie.class).open();
            return null;
        } catch (DataObjectNotFoundException ex) {
            Exceptions.printStackTrace(ex);
            return ex.getMessage();
        }
    }

    /**
     * Menu that checks its enabled state just before is populated
     */
    private class UpdatingMenu extends JMenu
            implements DynamicMenuContent {

        private final JComponent[] content = new JComponent[]{this};

        public UpdatingMenu(Action action) {
            super(action);
        }

        @Override
        public JComponent[] getMenuPresenters() {
            setEnabled(RecentGuides.hasRecentGuides());
            return content;
        }

        @Override
        public JComponent[] synchMenuPresenters(JComponent[] items) {
            return getMenuPresenters();
        }
    }
}

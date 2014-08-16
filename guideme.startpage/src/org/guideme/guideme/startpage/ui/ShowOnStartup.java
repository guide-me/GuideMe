package org.guideme.guideme.startpage.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import org.guideme.guideme.startpage.StartPageOptions;
import org.openide.util.NbBundle.Messages;

@Messages("LBL_ShowOnStartUp=Show on startup")
class ShowOnStartup extends JPanel
        implements ActionListener, PropertyChangeListener {

    private JCheckBox button;

    public ShowOnStartup() {
        super( new BorderLayout() );
        setOpaque(false);

        button = new JCheckBox( Bundle.LBL_ShowOnStartUp() );
        button.setSelected( StartPageOptions.getDefault().isShowOnStartup() );
        button.setOpaque( false );
        button.setForeground( Color.BLACK );
        button.setHorizontalTextPosition( SwingConstants.LEFT );
        add( button, BorderLayout.CENTER );
        button.addActionListener( this );
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        StartPageOptions.getDefault().setShowOnStartup( button.isSelected() );
    }

    @Override
    public void addNotify() {
        super.addNotify();
        StartPageOptions.getDefault().addPropertyChangeListener( this );
    }

    @Override
    public void removeNotify() {
        super.removeNotify();
        StartPageOptions.getDefault().removePropertyChangeListener( this );
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        button.setSelected( StartPageOptions.getDefault().isShowOnStartup() );
    }
    
    
}


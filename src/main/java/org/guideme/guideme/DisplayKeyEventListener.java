package org.guideme.guideme;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.guideme.guideme.ui.MainShell;

public class DisplayKeyEventListener implements Listener {
	private Logger logger = LogManager.getLogger();
	private MainShell mainShell;
	
	public DisplayKeyEventListener() {
	}

	
	public void setMainShell(MainShell mainShell) {
		this.mainShell = mainShell;  
	}
	
	@Override
	public void handleEvent(Event event) {
		try {
			logger.trace(event.character + "|" + event.keyCode + "|" + event.keyLocation + "|" + event.stateMask);
			if (((event.stateMask & SWT.ALT) == SWT.ALT)) {
				switch (event.character) {
				case 'd' :
					mainShell.showDebug();
					break;
				case 'D' :
					mainShell.showDebug();
					break;
				}
		}
		}
		catch (Exception ex) {
			logger.error(" DisplayKeyEventListener " + ex.getLocalizedMessage(), ex);
		}
	}


}

package org.guideme.guideme.ui;

import java.util.Iterator;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.guideme.guideme.settings.AppSettings;

public class MainUI implements Runnable {
	  private static Logger logger = LogManager.getLogger();

	@Override
	public void run() {
	    try {
	        logger.trace("Enter MainUI");

	  		AppSettings appSettings = new AppSettings();
	  		Display display;
	  		if (appSettings.getDebug()) {
	  			Properties properties = java.lang.System.getProperties();
	  			Iterator<Object> it = properties.keySet().iterator();
	  			while (it.hasNext()) {
	  				String key = String.valueOf(it.next());
	  				String value = String.valueOf(properties.get(key));
	  				System.out.println(key + " - " + value);
	  			}
	  		}
	  		appSettings = null;

	  		display = new Display();

	  		logger.trace("create clasicshell");
	        MainShell mainShell = new MainShell();
	        logger.trace("create shell");
	        Shell shell = mainShell.createShell(display);
	        logger.trace("open shell");
	        shell.open();
	        while (!shell.isDisposed())
	          if (!display.readAndDispatch())
	          {
	            display.sleep();
	          }
	      }
	      catch (Exception ex) {
	        logger.error("MainUI error " + ex.getLocalizedMessage(), ex);
	      }
	      logger.trace("Exit MainUI");
	}

}

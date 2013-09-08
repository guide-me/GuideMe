package org.guideme.guideme;

//import java.io.File;
//import java.io.IOException;

import java.util.Iterator;
import java.util.Properties;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.eclipse.swt.graphics.DeviceData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.guideme.guideme.settings.AppSettings;
import org.guideme.guideme.ui.MainShell;

public class App 
{
	private static Logger logger = LogManager.getLogger();
	
    public static void main(String[] args)
    {
        try {
            logger.trace("Enter main");
      		boolean loadSleak = false;

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

      		if (loadSleak) {
      			DeviceData data = new DeviceData();
      			data.tracking = true;
      			display = new Display(data);
      			Sleak sleak = new Sleak();
      			sleak.open();
      		} else {
      			display = new Display();
      		}

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
            logger.error("Main error " + ex.getLocalizedMessage(), ex);
          }
          logger.trace("Exit main");
    }
}
    
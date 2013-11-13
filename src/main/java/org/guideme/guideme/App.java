package org.guideme.guideme;

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
	/*
	 This is where it all starts from
	 main will create and display the first shell (window) 
	 */
	private static Logger logger = LogManager.getLogger();
	
    
	public static void main(String[] args)
    {
        try {
            logger.trace("Enter main");
            //Sleak will help diagnose SWT memory leaks
            //if you set this to true you will get an additional window
            //that allows you to track resources that are created and not destroyed correctly
      		boolean loadSleak = false;

      		AppSettings appSettings = AppSettings.getAppSettings();
      		Display display;
      		//user debug setting
      		if (appSettings.getDebug()) {
      			//debug level logging for video lan (VLC)
        		System.setProperty("vlcj.log", "DEBUG");
      			Properties properties = java.lang.System.getProperties();
      			Iterator<Object> it = properties.keySet().iterator();
      			//display all the jvm properties in the log file
      			while (it.hasNext()) {
      				String key = String.valueOf(it.next());
      				String value = String.valueOf(properties.get(key));
      				//write out at error level even though it is a debug message
      				//so we can turn it on, on a users machine
      				logger.error(key + " - " + value);
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

      		logger.trace("create main shell");
            MainShell mainShell = new MainShell();
            logger.trace("create shell");
            Shell shell = mainShell.createShell(display);
            logger.trace("open shell");
            shell.open();
            //loop round until the window is closed
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
    
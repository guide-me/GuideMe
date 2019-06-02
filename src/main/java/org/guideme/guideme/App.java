package org.guideme.guideme;

import java.io.File;
import java.io.FileFilter;
//import java.io.PrintStream;
import java.util.Iterator;
import java.util.Properties;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.DeviceData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.guideme.guideme.settings.AppSettings;
import org.guideme.guideme.settings.ComonFunctions;
import org.guideme.guideme.ui.MainShell;

import uk.co.caprica.vlcj.discovery.NativeDiscovery;

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
			
			System.setProperty("org.eclipse.swt.browser.IEVersion", "11000");
			
			logger.trace("Enter main");
			logger.error("GuideMe Version - " + ComonFunctions.getVersion());
			//Sleak will help diagnose SWT memory leaks
			//if you set this to true you will get an additional window
			//that allows you to track resources that are created and not destroyed correctly
			boolean loadSleak = false;
			if (args.length > 0 && args[0].equals("sleak"))
			{
				loadSleak = true;
			}

			AppSettings appSettings = AppSettings.getAppSettings();
			
			File directory = new File(appSettings.getTempDir());
			  
			File[] toBeDeleted = directory.listFiles(new FileFilter() {
				public boolean accept(File theFile) {
				if (theFile.isFile()) {
					return theFile.getName().startsWith("tmpImage");
				}
				return false;
				}
			});
			  
			for(File deletableFile:toBeDeleted){
				deletableFile.delete();
			}
			
			if (args.length > 1) {
				appSettings.setDataDirectory(args[0]);
				appSettings.setComandLineGuide(args[1]);
			}
			
			Display display;
			//user debug setting
			if (appSettings.getDebug()) {
				//debug level logging for video lan (VLC)
				//System.setProperty("vlcj.log", "DEBUG");
				//PrintStream stdOutStrm = new PrintStream(new File("logs/vlc.log"));
				//System.setOut(stdOutStrm);
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

			if (loadSleak) {
				DeviceData data = new DeviceData();
				data.tracking = true;
				display = new Display(data);
				Sleak sleak = new Sleak();
				sleak.open();
			} else {
				display = new Display();
			}

			NativeDiscovery nativeDiscovery = new NativeDiscovery();
			boolean vlcFound = nativeDiscovery.discover();
			logger.trace("test for vlc: " + vlcFound);

			MainShell mainShell;
			Shell shell;
			DisplayKeyEventListener keylistener = new DisplayKeyEventListener();
			display.addFilter(SWT.KeyDown, keylistener);

			do {
				appSettings.setMonitorChanging(false);
				logger.trace("create main shell");
				mainShell = new MainShell();
				logger.trace("create shell");
				shell = mainShell.createShell(display);
				logger.trace("open shell");
				shell.open();
				if (mainShell.getMultiMonitor()){
					mainShell.getShell2().open();
				}
				keylistener.setMainShell(mainShell);
				
				//loop round until the window is closed
				while (!shell.isDisposed()) {
					if (appSettings.isMonitorChanging()) {
						try {
							shell.close();
						}
						catch (Exception ex) {
							logger.error("Main shell close " + ex.getLocalizedMessage(), ex);
						}					
					}
					if (!display.readAndDispatch())
					{
						display.sleep();
					}
				}
			} while (appSettings.isMonitorChanging());
			display.dispose();
		}
		catch (Exception ex) {
			logger.error("Main error " + ex.getLocalizedMessage(), ex);
		}
		logger.trace("Exit main");
	}
}

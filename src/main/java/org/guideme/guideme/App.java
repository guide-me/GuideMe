package org.guideme.guideme;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.eclipse.swt.widgets.*;

/**
 * Hello world!
 *
 */
public class App 
{
	private static Logger log = LogManager.getLogger();
	
    public static void main(String[] args)
    {
        System.out.println("Hello World!");
        log.info("Hello Logging World!");
        
        Display display = new Display();
        Shell shell = new Shell(display);
        shell.open();
        while (!shell.isDisposed()) {
    		if (!display.readAndDispatch()) {
    			display.sleep();
    		}
    	}
    	display.dispose ();
    }
}

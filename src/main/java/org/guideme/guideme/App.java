package org.guideme.guideme;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.eclipse.swt.widgets.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

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
        
        String title = "Hello World!";
        
        try {
        	File guideFile = new File("data/sample-1.html");
			Document guideDocument = Jsoup.parse(guideFile, "UTF-8", "");
			
			title = guideDocument.select("head title").text();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        Display display = new Display();
        Shell shell = new Shell(display);
        shell.setText(title);
        shell.open();
        while (!shell.isDisposed()) {
    		if (!display.readAndDispatch()) {
    			display.sleep();
    		}
    	}
    	display.dispose ();
    }
}

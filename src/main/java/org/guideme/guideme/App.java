package org.guideme.guideme;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
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
        
        Guide guide = new Guide();
        guide.setTitle("Title of my guide");
        guide.setAuthorName("Name of the author");
        guide.setDescription("A short description of the tease.");
        guide.getKeywords().add("first");
        guide.getKeywords().add("second");
        guide.setOriginalUrl("http://url.to/original/story");
        guide.setAuthorUrl("http://url.to/author");
        guide.setThumbnail(new Image("http://url.to/thumbnail.png", 100, 100, "image/png"));
        
        System.out.println(new HtmlGuideWriter().Write(guide));
        
        System.exit(0);
//        
//        String title = "Hello World!";
//        
//    	File guideFile = new File("data/sample-1.html");
//        try {
//			Document guideDocument = Jsoup.parse(guideFile, "UTF-8", "");
//			
//			title = guideDocument.select("head title").text();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//        Display display = new Display();
//		final Shell shell = new Shell(display);
//		GridLayout gridLayout = new GridLayout();
//		gridLayout.numColumns = 1;
//		shell.setLayout(gridLayout);
//		
//		final Browser browser;
//		try {
//			browser = new Browser(shell, SWT.NONE);
//		} catch (SWTError e) {
//			System.out.println("Could not instantiate Browser: " + e.getMessage());
//			display.dispose();
//			return;
//		}
//		GridData data = new GridData();
//		data.horizontalAlignment = GridData.FILL;
//		data.verticalAlignment = GridData.FILL;
//		data.horizontalSpan = 1;
//		data.grabExcessHorizontalSpace = true;
//		data.grabExcessVerticalSpace = true;
//		browser.setLayoutData(data);
//		
//		shell.open();
//		browser.setUrl(guideFile.getAbsolutePath());
//		
//		while (!shell.isDisposed()) {
//			if (!display.readAndDispatch())
//				display.sleep();
//		}
//		display.dispose();
//        
    }
}

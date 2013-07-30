package org.guideme.guideme;

//import java.io.File;
//import java.io.IOException;

import java.io.File;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
//import org.eclipse.swt.SWT;
//import org.eclipse.swt.SWTError;
//import org.eclipse.swt.browser.Browser;
//import org.eclipse.swt.layout.GridData;
//import org.eclipse.swt.layout.GridLayout;
//import org.eclipse.swt.widgets.*;

import org.guideme.guideme.model.*;
import org.guideme.guideme.readers.*;
import org.guideme.guideme.writers.*;

public class App 
{
	private static Logger log = LogManager.getLogger();
	
    public static void main(String[] args)
    {
        System.out.println("Hello World!");
        log.info("Hello Logging World!");
        
//        Guide guide = new Guide();
//        guide.setTitle("Title of my guide");
//        guide.setAuthorName("Name of the author");
//        guide.setAuthorUrl("http://url.to/author");
//        guide.setDescription("A short description of the tease.");
//        guide.getKeywords().add("first");
//        guide.getKeywords().add("second");
//        guide.setOriginalUrl("http://url.to/original/story");
//        guide.setThumbnail(new Image("http://url.to/thumbnail.png", 100, 100, "image/png"));
//        
//        Chapter chapter = new Chapter();
//        guide.getChapters().add(chapter);
//        
//        Page start = new Page("start");
//        start.setText("<p>start</p>");
//        start.getButtons().add(new GotoButton("p-2", "next"));
//        chapter.getPages().add(start);
//        
//        Page p2 = new Page("p-2");
//        p2.setText("<p>Page 2</p>");
//        p2.getButtons().add(new GotoButton("p-4", "skip to 4"));
//        p2.getButtons().add(new GotoButton("p-3"));
//        chapter.getPages().add(p2);
//        
//        Page p3 = new Page("p-3");
//        p3.setText("<p>Page 3</p>");
//        chapter.getPages().add(p3);
//        
//        Page p4 = new Page("p-4");
//        p4.getButtons().add(new GotoButton("p-3", "back to 3"));
//        chapter.getPages().add(p4);
        
        Guide guide = new HtmlGuideReader().loadFromFile(new File("data/sample-1.html"));
        
        
        System.out.println(new HtmlGuideWriter().Write(guide));
        
        System.exit(0);
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

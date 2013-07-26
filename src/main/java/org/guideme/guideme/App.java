package org.guideme.guideme;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


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
    }
}

package org.guideme.guideme;

/**
 * Constants used in the application.
 */
public class Constants {

    /**
     * Directory within the guide directory created and used by the application
     * to store temporary files like state etc.
     * The user can delete this directory to reset the guide to its original
     * state.
     */
    public static final String GM_DIR = "_gm";

    /**
     * The directory in the guide directory containing the images, audio and
     * video files.
     */
    public static final String DEFAULT_MEDIA_DIRECTORY = "media";

    /**
     * ID of the start page of a guide. 
     */
    public static final String START_PAGE_ID = "start";
    
    /**
     * XML namespace for the current Guide Schema.
     */
    public static final String XML_NS = "org.guideme.guideme/v2";
}

package org.guideme.guideme.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A chapter of a guide contains pages.
 */
public class Chapter {

    private String id;
    private String title;

    private final ArrayList<Page> pages = new ArrayList<>();

    /**
     * Default constructor.
     */
    public Chapter() {
    }

    /**
     * Constructor setting the ID of the chapter.
     *
     * @param id
     */
    public Chapter(String id) {
        this.id = id;
    }

    /**
     * ID of the chapter (can be used for scripting purposes).
     *
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * ID of the chapter (can be used for scripting purposes).
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Title of the chapter (used for display).
     *
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * Title of the chapter (used for display).
     *
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * All pages in the chapter.
     *
     * @return
     */
    public List<Page> getPages() {
        return pages;
    }

    /**
     * All pages in the chapter.
     *
     * @param pages
     */
    public void setPages(List<Page> pages) {
        this.pages.clear();
        this.pages.addAll(pages);
    }

    /**
     * Adds a single page to the chapter (at the end).
     *
     * @param page
     * @return
     */
    public Page addPage(Page page) {
        pages.add(page);
        return page;
    }
}

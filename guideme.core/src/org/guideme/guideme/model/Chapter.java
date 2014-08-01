package org.guideme.guideme.model;

import java.util.ArrayList;
import java.util.List;

public class Chapter {

    private String id;
    private String title;
    
    private final ArrayList<Page> pages = new ArrayList<>();

    
    public Chapter() {
    }

    public Chapter(String id) {
        this.id = id;
    }

    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }

    public List<Page> getPages() {
        return pages;
    }

    public void setPages(List<Page> pages) {
        this.pages.clear();
        this.pages.addAll(pages);
    }

    public Page addPage(String id) {
        return addPage(new Page(id));
    }

    public Page addPage(Page page) {
        pages.add(page);
        return page;
    }
}

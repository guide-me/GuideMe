package org.guideme.guideme.model;

import java.util.ArrayList;
import java.util.List;

public class Chapter {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private List<Page> pages = new ArrayList<>();

    public List<Page> getPages() {
        return pages;
    }

    public void setPages(List<Page> pages) {
        this.pages = pages;
    }

    public Chapter() {
    }

    public Chapter(String id) {
        this.id = id;
    }

    public Page addPage(String id) {
        return addPage(new Page(id));
    }

    public Page addPage(Page page) {
        pages.add(page);
        return page;
    }
}

package org.guideme.guideme.model;

import java.util.ArrayList;
import java.util.List;

public class Guide {

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    private List<Page> pages = new ArrayList<>();

    public List<Page> getPages() {
        return pages;
    }

    public void setPages(List<Page> pages) {
        this.pages = pages;
    }

    private List<Chapter> chapters = new ArrayList<>();

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }

    public Page addPage(String id) {
        return addPage(new Page(id));
    }

    public Page addPage(Page page) {
        pages.add(page);
        return page;
    }

    public Chapter addChapter(String id) {
        return addChapter(new Chapter(id));
    }

    public Chapter addChapter(Chapter chapter) {
        chapters.add(chapter);
        return chapter;
    }

}

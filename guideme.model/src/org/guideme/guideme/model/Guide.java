package org.guideme.guideme.model;

import java.util.ArrayList;
import java.util.List;

public class Guide {

    private String title;
    private final ArrayList<Page> pages = new ArrayList<>();
    private final ArrayList<Chapter> chapters = new ArrayList<>();

    
    public Guide() {
    }
    
    public Guide(String title) {
        this.title = title;
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

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters.clear();
        this.chapters.addAll(chapters);
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

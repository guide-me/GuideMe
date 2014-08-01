package org.guideme.guideme.model;

import java.util.ArrayList;
import java.util.List;

public class Guide {

    private String id;
    private String title;
    private String originalUrl;
    private String authorName;
    private String authorUrl;
    private String description;
    private String thumbnail;
    private final ArrayList<String> keywords = new ArrayList<>();
    private final ArrayList<Page> pages = new ArrayList<>();
    private final ArrayList<Chapter> chapters = new ArrayList<>();

    
    public Guide() {
    }
    
    
    public Guide(String id) {
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
    
    public String getOriginalUrl() {
        return originalUrl;
    }
    
    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }
    
    public String getAuthorName() {
        return authorName;
    }
    
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
    
    public String getAuthorUrl() {
        return authorUrl;
    }
    
    public void setAuthorUrl(String authorUrl) {
        this.authorUrl = authorUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
    
    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords.clear();
        this.keywords.addAll(keywords);
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
    
    public Page addPage(Page page) {
        pages.add(page);
        return page;
    }

    public Chapter addChapter(Chapter chapter) {
        chapters.add(chapter);
        return chapter;
    }

}

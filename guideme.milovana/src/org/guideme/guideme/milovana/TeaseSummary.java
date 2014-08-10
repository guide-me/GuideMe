package org.guideme.guideme.milovana;

import java.util.ArrayList;
import java.util.List;

public class TeaseSummary {

    private String teaseId;
    private String title;
    private String authorId;
    private String authorName;
    private boolean flash;
    private String thumbnail;
    private String description;
    private final List<String> keywords = new ArrayList<>();

    public String getTeaseId() {
        return teaseId;
    }

    public void setTeaseId(String teaseId) {
        this.teaseId = teaseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public boolean isFlash() {
        return flash;
    }

    public void setFlash(boolean flash) {
        this.flash = flash;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords.clear();
        this.keywords.addAll(keywords);
    }

}

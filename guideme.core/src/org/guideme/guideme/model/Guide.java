package org.guideme.guideme.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Guide.
 */
public class Guide {

    private String title;
    private String originalUrl;
    private String authorName;
    private String authorUrl;
    private String description;
    private String thumbnail;
    private final ArrayList<String> keywords = new ArrayList<>();
    private final ArrayList<Page> pages = new ArrayList<>();
    private final ArrayList<Chapter> chapters = new ArrayList<>();

    /**
     * Default constructor.
     */
    public Guide() {
    }

    /**
     * Title of the guide.
     *
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * Title of the guide.
     *
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * The URL where the original guide can be found.
     *
     * @return
     */
    public String getOriginalUrl() {
        return originalUrl;
    }

    /**
     * The URL where the original guide can be found.
     *
     * @param originalUrl
     */
    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    /**
     * Name of the author.
     *
     * @return
     */
    public String getAuthorName() {
        return authorName;
    }

    /**
     * Name of the author.
     *
     * @param authorName
     */
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    /**
     * URL of the website of the author.
     *
     * @return
     */
    public String getAuthorUrl() {
        return authorUrl;
    }

    /**
     * URL of the website of the author.
     *
     * @param authorUrl
     */
    public void setAuthorUrl(String authorUrl) {
        this.authorUrl = authorUrl;
    }

    /**
     * Short description what this guide is about.
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * Short description what this guide is about.
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * URL to a thumbnail for the guide (cover).
     *
     * @return
     */
    public String getThumbnail() {
        return thumbnail;
    }

    /**
     * URL to a thumbnail for the guide (cover).
     *
     * @param thumbnail
     */
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    /**
     * List of keywords to describe the contents of this guide.
     *
     * @return
     */
    public List<String> getKeywords() {
        return keywords;
    }

    /**
     * List of keywords to describe the contents of this guide.
     *
     * @param keywords
     */
    public void setKeywords(List<String> keywords) {
        this.keywords.clear();
        this.keywords.addAll(keywords);
    }

    /**
     * List of pages in this guide not contained in a chapter.
     *
     * @return
     */
    public List<Page> getPages() {
        return pages;
    }

    /**
     * List of pages in this guide not contained in a chapter.
     *
     * @param pages
     */
    public void setPages(List<Page> pages) {
        this.pages.clear();
        this.pages.addAll(pages);
    }

    /**
     * List of chapters in this guide.
     *
     * @return
     */
    public List<Chapter> getChapters() {
        return chapters;
    }

    /**
     * List of chapters in this guide.
     *
     * @param chapters
     */
    public void setChapters(List<Chapter> chapters) {
        this.chapters.clear();
        this.chapters.addAll(chapters);
    }

    /**
     * List of all pages in this guide (including all pages in all chapters).
     *
     * @return
     */
    public List<Page> getAllPages() {
        List<Page> result = new ArrayList<>();
        result.addAll(this.pages);
        for (Chapter chapter : this.chapters) {
            result.addAll(chapter.getPages());
        }
        return result;
    }

    /**
     * Finds a single page by its ID (returns null if not found).
     *
     * @param pageId
     * @return
     */
    public Page findPage(String pageId) {
        if (pageId != null) {
            for (Page page : this.pages) {
                if (pageId.equals(page.getId())) {
                    return page;
                }
            }
            for (Chapter chapter : this.chapters) {
                for (Page page : chapter.getPages()) {
                    if (pageId.equals(page.getId())) {
                        return page;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Adds a page to this guide (not in a chapter) at the end.
     *
     * @param page
     * @return
     */
    public Page addPage(Page page) {
        pages.add(page);
        return page;
    }

    /**
     * Adds a chapters to this guide at the end.
     *
     * @param chapter
     * @return
     */
    public Chapter addChapter(Chapter chapter) {
        chapters.add(chapter);
        return chapter;
    }

}

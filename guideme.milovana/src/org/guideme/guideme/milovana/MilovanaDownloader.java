package org.guideme.guideme.milovana;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.guideme.guideme.Constants;
import org.guideme.guideme.model.Guide;
import org.guideme.guideme.model.Image;
import org.guideme.guideme.model.Page;
import org.guideme.guideme.serialization.GuideSerializer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.Exceptions;

public class MilovanaDownloader {

    public TeaseSummary getSummary(String teaseId) {
        try {
            TeaseSummary summary = new TeaseSummary();
            summary.setTeaseId(teaseId);
            
            String url = "http://www.milovana.com/webteases/showtease.php?id=" + teaseId;
            Document document = Jsoup.connect(url).get();
            if (document != null) {
                if (document.title().startsWith("Milovana.com - FlashTease #")) {
                    summary.setFlash(true);
                    if (document.select("#headerbar .title").size() > 0) {
                        Element titleElement = document.select("#headerbar .title").first();
                        String titleHtml = StringUtils.remove(titleElement.html(), "\n");
                        // <div class="title">Tease title by <a href="webteases/#author=12345" style="color: #933737;">author name</a></div>

                        summary.setTitle(StringUtils.substringBefore(titleHtml, " by <a"));
                        summary.setAuthorName(titleElement.select("a").text().trim());

                        String authorRef = titleElement.select("a").attr("href"); // webteases/#author=1234
                        String authorId = StringUtils.substringAfterLast(authorRef, "=");
                        summary.setAuthorId(authorId);
                    }
                } else {
                    summary.setFlash(false);
                    String titleHtml = document.select("#tease_title").html();

                    summary.setTitle(titleHtml.substring(0, titleHtml.indexOf(" <span")));
                    summary.setAuthorName(document.select(".tease_author a").text().trim());

                    String authorRef = document.select(".tease_author a").attr("href"); // webteases/#author=1234
                    String authorId = authorRef.substring(authorRef.lastIndexOf("=") + 1);
                    summary.setAuthorId(authorId);
                }

                Element teaseElement = findTeaseElement(teaseId, summary.getAuthorId(), 0);
                if (teaseElement != null) {
                    Elements desc = teaseElement.select(".desc");
                    if (desc.size() > 0) {
                        summary.setDescription(desc.first().text().trim());
                    }
                    Elements thumb = teaseElement.select(".img a img");
                    if (thumb.size() > 0) {
                        summary.setThumbnail(thumb.first().attr("src"));
                    }
                    Elements tags = teaseElement.select(".tags a");
                    if (tags.size() > 0) {
                        ArrayList<String> keywords = new ArrayList<>();
                        for (Element tag : tags) {
                            keywords.add(tag.text().trim());
                        }
                        summary.setKeywords(keywords);
                    }
                }

            }
            return summary;
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
            return null;
        }

    }

    private Element findTeaseElement(String teaseId, String authorId, int start) {
        try {
            String url = "http://www.milovana.com/webteases/list.php?pp=20&start=" + String.valueOf(start) + "&author=" + authorId;
            Document doc = Jsoup.connect(url).get();
            if (doc != null) {

                Elements teases = doc.select(".tease");
                int i = 0;
                while (i < teases.size()) {
                    Element tease = teases.get(i);
                    String teaseUrl = tease.select(".bubble h1 a").first().attr("href");
                    if (teaseUrl.endsWith("=" + teaseId)) {
                        // found.
                        return tease;
                    }
                    i++;
                }

                // Not found, go to next page.
                Pattern pattern = Pattern.compile("Page (?<current>\\d+) of (?<total>\\d+)");

                Elements pages = doc.select(".paging .pages span");
                if (pages.size() > 0) {
                    // Multiple pages.
                    String pageNrOfTotal = pages.first().text(); // Page 1 of 7

                    Matcher matcher = pattern.matcher(pageNrOfTotal);
                    if (matcher.matches()) {
                        String current = matcher.group("current");
                        String total = matcher.group("total");

                        while (!current.equals(total)) {
                            start += 20;
                            Element tease = findTeaseElement(teaseId, authorId, start);
                            if (tease != null) {
                                // found.
                                return tease;
                            }
                        }
                    }
                }
            }
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
        return null;
    }

    public FileObject saveGuide(TeaseSummary teaseSummary, File destinationFolder) throws IOException {
        if (destinationFolder.mkdir()) {

            FileObject projectDir = FileUtil.toFileObject(destinationFolder);

            Guide guide = createGuide(teaseSummary);

            // TODO download images and change Image ids.
            FileObject imagesFolder = projectDir.createFolder(Constants.MEDIA_DIR);
            for (Page page : guide.getPages()) {
                for (Image image : page.getImages()) {
                    if (image.getSrc() != null) {
                        URL url;
                        String imageName;
                        if (image.getSrc().startsWith("http")) {
                            url = new URL(image.getSrc());
                            imageName = extractFileNameFromURL(url);
                        } else {
                            url = new URL(String.format("http://www.milovana.com/media/get.php?folder=%s/%s&name=%s", teaseSummary.getAuthorId(), teaseSummary.getTeaseId(), image.getSrc()));
                            if (image.getSrc().contains("*")) {
                                imageName = StringUtils.replace(image.getSrc(), "*", UUID.randomUUID().toString());
                            } else {
                                imageName = image.getSrc();
                            }
                        }
                        
                        
                        
                        if (imagesFolder.getFileObject(imageName) == null) {
                            try (InputStream in = new BufferedInputStream(url.openStream())) {
                                try (OutputStream fileStream = imagesFolder.createAndOpen(imageName)) {
                                    try (OutputStream out = new BufferedOutputStream(fileStream)) {
                                        for (int i; (i = in.read()) != -1;) {
                                            out.write(i);
                                        }
                                    }
                                }
                            }
                        }

                        // image ID is url relative to guide.xml.
                        image.setSrc(imagesFolder.getName() + "/" + imageName);
                    }
                }
            }

            if (guide.getThumbnail() != null && guide.getThumbnail().length() > 0) {
                URL url = new URL(guide.getThumbnail());
                String imageName = "thumbnail." + FileUtil.getExtension(extractFileNameFromURL(url));
                if (imagesFolder.getFileObject(imageName) == null) {
                    try (InputStream in = new BufferedInputStream(url.openStream())) {
                        try (OutputStream fileStream = imagesFolder.createAndOpen(imageName)) {
                            try (OutputStream out = new BufferedOutputStream(fileStream)) {
                                for (int i; (i = in.read()) != -1;) {
                                    out.write(i);
                                }
                            }
                        }
                    }
                }
                guide.setThumbnail(imagesFolder.getName() + "/" + imageName);
            }

            try (OutputStream stream = projectDir.createAndOpen(Constants.GUIDE_FILE)) {
                GuideSerializer.getDefault().WriteGuide(guide, stream);
            }
            return projectDir.getFileObject(Constants.GUIDE_FILE);
        }
        return null;
    }

    private String extractFileNameFromURL(URL url) {
        return url.getPath().substring(url.getPath().lastIndexOf("/") + 1);
    }

    private Guide createGuide(TeaseSummary teaseSummary) {

        Guide guide = new Guide();
        guide.setTitle(teaseSummary.getTitle());
        guide.setAuthorName(teaseSummary.getAuthorName());
        guide.setAuthorUrl("http://www.milovana.com/forum/memberlist.php?mode=viewprofile&u=" + teaseSummary.getAuthorId());
        guide.setDescription(teaseSummary.getDescription());
        guide.setKeywords(teaseSummary.getKeywords());
        guide.setThumbnail(teaseSummary.getThumbnail());

        if (teaseSummary.isFlash()) {
            guide.setOriginalUrl("http://www.milovana.com/webteases/showflash.php?id=" + teaseSummary.getTeaseId());
            new FlashTeaseConverter().loadPages(teaseSummary.getTeaseId(), guide);
        } else {
            guide.setOriginalUrl("http://www.milovana.com/webteases/showtease.php?id=" + teaseSummary.getTeaseId());
            new HtmlTeaseConverter().loadPages(teaseSummary.getTeaseId(), guide);
        }

        // TODO load pages.
        return guide;
    }

}

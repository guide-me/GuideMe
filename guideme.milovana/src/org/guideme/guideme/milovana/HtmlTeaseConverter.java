package org.guideme.guideme.milovana;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.CharSetUtils;
import org.guideme.guideme.Constants;
import org.guideme.guideme.editor.GuideEditor;
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

public class HtmlTeaseConverter {

    public void SaveGuide(String teaseId, File destinationFolder) throws IOException {
        if (destinationFolder.mkdir()) {

            FileObject projectDir = FileUtil.toFileObject(destinationFolder);

            Guide guide = new HtmlTeaseConverter().createGuide(teaseId, true);

            // TODO download images and change Image ids.
            FileObject imagesFolder = projectDir.createFolder(Constants.IMAGES_DIR);
            for (Page page : guide.getPages()) {
                for (Image image : page.getImages()) {
                    if (image.getSrc() != null && image.getSrc().startsWith("http")) {
                        URL url = new URL(image.getSrc());
                        String imageName = extractFileNameFromURL(url);

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
        }
    }

    private String extractFileNameFromURL(URL url) {
        return url.getPath().substring(url.getPath().lastIndexOf("/") + 1);
    }


    public Guide createGuide(String id, boolean loadAll) {
        Guide guide = new Guide(id);

        int pageNr = 1;
        Document document = loadPage(id, pageNr);
        if (document != null && document.select("#tease_title").size() > 0) {
            readGeneralInformation(guide, document, loadAll);
            Page page = guide.addPage(readPage(document, pageNr));
            if (loadAll) {
                while (page.getButtons().size() > 0) {
                    pageNr++;
                    document = loadPage(id, pageNr);
                    if (document != null) {
                        page = guide.addPage(readPage(document, pageNr));
                    }
                }
            }
        }

        return guide;
    }

    protected Document loadPage(String teaseId, int pageNr) {
        try {
            String url = "http://www.milovana.com/webteases/showtease.php?id=" + teaseId + "&p=" + String.valueOf(pageNr);
            return Jsoup.connect(url).get();
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
            return null;
        }
    }

    private void readGeneralInformation(Guide guide, Document doc, boolean loadAll) {
        String titleHtml = doc.select("#tease_title").html();

        guide.setTitle(titleHtml.substring(0, titleHtml.indexOf(" <span")));
        guide.setOriginalUrl("http://www.milovana.com/webteases/showtease.php?id=" + guide.getId());
        guide.setAuthorName(doc.select(".tease_author a").text().trim());
        
        String authorRef = doc.select(".tease_author a").attr("href"); // webteases/#author=1234
        String authorId = authorRef.substring(authorRef.lastIndexOf("=") + 1);
        guide.setAuthorUrl("http://www.milovana.com/forum/memberlist.php?mode=viewprofile&u=" + authorId);
        
        if (loadAll) {
            Element teaseSummary = findTeaseSummary(guide.getId(), authorId, 0);
            if (teaseSummary != null) {
                Elements desc = teaseSummary.select(".desc");
                if (desc.size() > 0) {
                    guide.setDescription(desc.first().text().trim());
                }
                Elements thumb = teaseSummary.select(".img a img");
                if (thumb.size() > 0) {
                    guide.setThumbnail(thumb.first().attr("src"));
                }
                Elements tags = teaseSummary.select(".tags a");
                if (tags.size() > 0) {
                    ArrayList<String> keywords = new ArrayList<>();
                    for (Element tag : tags) {
                        keywords.add(tag.text().trim());
                    }
                    guide.setKeywords(keywords);
                }
            }
        }
    }
    
    private Element findTeaseSummary(String teaseId, String authorId, int start) {
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
                if (pages.size() > 0)
                {
                    // Multiple pages.
                    String pageNrOfTotal = pages.first().text(); // Page 1 of 7
                    
                    Matcher matcher = pattern.matcher(pageNrOfTotal);
                    if (matcher.matches()) {
                        String current = matcher.group("current");
                        String total = matcher.group("total");

                        while (!current.equals(total)) {
                            start += 20;
                            Element tease = findTeaseSummary(teaseId, authorId, start);
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
    

    private Page readPage(Document doc, int pageNr) {
        String pageId = (pageNr > 1) ? String.valueOf(pageNr) : Constants.START_PAGE_ID;
        Page page = new Page(pageId);

        Elements elm = doc.select("#tease_content").select(".text");
        if (elm.size() > 0) {
            String text = elm.first().html().replace('\r', ' ').replace('\n', ' ');
            text = CharSetUtils.squeeze(text, " ");
            page.setText("<p>" + text + "</p>");
        }

        Elements elmImg = doc.select(".tease_pic");
        if (elmImg.size() > 0) {
            String imgSrc = elmImg.first().attr("src");
            Image img = new Image();
            img.setSrc(imgSrc);
            page.addImage(img);
        }

        if (doc.select("#continue").size() > 0) {
            page.addButton(GuideEditor.createContinueButton(String.valueOf(pageNr + 1)));
        }

        return page;
    }
}

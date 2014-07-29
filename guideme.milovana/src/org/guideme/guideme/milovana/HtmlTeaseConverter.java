package org.guideme.guideme.milovana;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.apache.commons.lang3.CharSetUtils;
import org.guideme.guideme.model.Button;
import org.guideme.guideme.model.Guide;
import org.guideme.guideme.model.Page;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openide.util.Exceptions;

public class HtmlTeaseConverter {

    public Guide createGuide(String id) {
        Guide guide = new Guide();
        
        int pageNr = 1;
        Document document = loadPage(id, pageNr);
        if (document != null) {
            readGeneralInformation(guide, document);
            Page page = guide.addPage(readPage(guide, document, pageNr));
            while (page.getButtons().size() > 0) {
                pageNr++;
                document = loadPage(id, pageNr);
                if (document != null) {
                    page = guide.addPage(readPage(guide, document, pageNr));
                }
            }
        }        
        return guide;
    }

    protected Document loadPage(String teaseId, int pageNr) {
        return null;
    }

    private void readGeneralInformation(Guide guide, Document doc) {
        guide.setTitle(doc.select("head title").text());
//		guide.setAuthorName(doc.select("head meta[name=author]").attr("content"));
//		guide.setKeywordsString(doc.select("head meta[name=keywords]").attr("content"));
//		guide.setDescription(doc.select("head meta[name=description]").attr("content"));
//		if (doc.select("head link[rel=alternate]").size() > 0) {
//			guide.setOriginalUrl(doc.select("head link[rel=alternate]").attr("href"));
//		}
//		if (doc.select("head link[rel=author]").size() > 0) {
//			guide.setAuthorUrl(doc.select("head link[rel=author]").attr("href"));
//		}
//		if (doc.select("head link[rel=icon]").size() > 0) {
//			String url = doc.select("head link[rel=icon]").attr("href");
//			//String mimeType = doc.select("head link[rel=icon]").attr("type");
//			//String sizes = doc.select("head link[rel=icon]").attr("sizes");
//			//int width = Integer.parseInt(sizes.split("x")[0]);
//			//int height = Integer.parseInt(sizes.split("x")[1]);
//			guide.setThumbnail(new Image(url, "", ""));
//		}
    }

    private Page readPage(Guide guide, Document doc, int pageNr) {
        String pageId = (pageNr > 1) ? String.valueOf(pageNr) : org.guideme.guideme.model.Constants.START_PAGE_ID;
        Page page = new Page(pageId);

        Elements elm = doc.select("#tease_content").select(".text");
        if (elm.size() > 0) {
                String text = elm.first().text().replace('\r', ' ').replace('\n', ' ');
                text = CharSetUtils.squeeze(text, " ");
                page.setText(text);
        }

        Elements elmImg = doc.select(".tease_pic");
        if (elmImg.size() > 0) {
            String imgSrc = elmImg.first().attr("src");
            page.addImage(imgSrc);
        }
        
        if (doc.select("#continue").size() > 0) {
            page.addButton(Button.Continue(String.valueOf(pageNr + 1)));
        }
        
        return page;
    }
}

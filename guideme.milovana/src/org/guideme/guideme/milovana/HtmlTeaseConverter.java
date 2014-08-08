package org.guideme.guideme.milovana;

import java.io.IOException;
import org.apache.commons.lang3.CharSetUtils;
import org.guideme.guideme.Constants;
import org.guideme.guideme.editor.GuideEditor;
import org.guideme.guideme.model.Guide;
import org.guideme.guideme.model.Image;
import org.guideme.guideme.model.Page;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openide.util.Exceptions;

public class HtmlTeaseConverter {

    public void loadPages(String teaseId, Guide guide) {
        int pageNr = 1;
        Document document = loadPage(teaseId, pageNr);
        if (document != null && document.select("#tease_title").size() > 0) {
            Page page = guide.addPage(readPage(document, pageNr));
            while (page.getButtons().size() > 0) {
                pageNr++;
                document = loadPage(teaseId, pageNr);
                if (document != null) {
                    page = guide.addPage(readPage(document, pageNr));
                }
            }
        }
    }

    private Document loadPage(String teaseId, int pageNr) {
        try {
            String url = "http://www.milovana.com/webteases/showtease.php?id=" + teaseId + "&p=" + String.valueOf(pageNr);
            return Jsoup.connect(url).get();
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
            return null;
        }
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

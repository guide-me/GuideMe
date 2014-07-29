package org.guideme.guideme.milovana;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openide.util.Exceptions;

public class LocalHtmlTeaseConverter extends HtmlTeaseConverter {

    @Override
    protected Document loadPage(String teaseId, int pageNr) {
            try {
            byte[] encoded = Files.readAllBytes(Paths.get("~/projects/analyseme/data/download/00/100/100-" + String.valueOf(pageNr) + ".html"));
            String html = new String(encoded, Charset.forName("UTF-8"));
            return Jsoup.parse(html);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
            return null;
        }
    }
}

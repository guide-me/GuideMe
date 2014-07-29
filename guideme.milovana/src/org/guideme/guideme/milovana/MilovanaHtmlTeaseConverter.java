package org.guideme.guideme.milovana;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openide.util.Exceptions;

public class MilovanaHtmlTeaseConverter extends HtmlTeaseConverter {

    @Override
    protected Document loadPage(String teaseId, int pageNr) {
        try {
            String url = "http://www.milovana.com/webteases/showtease.php?id=" + teaseId + "&p=" + String.valueOf(pageNr);
            return Jsoup.connect(url).get();
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
            return null;
        }
    }
}

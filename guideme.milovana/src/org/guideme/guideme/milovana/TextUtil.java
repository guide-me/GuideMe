package org.guideme.guideme.milovana;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Entities.EscapeMode;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Whitelist;

public class TextUtil {
    
    public static String sanitizeNyxText(String str) {
        Document doc = Jsoup.parse(str);

        Whitelist whitelist = Whitelist.relaxed();
        whitelist.addTags("font");
        whitelist.addAttributes("font", "color");
        
        doc = new Cleaner(whitelist).clean(doc);
        doc.outputSettings().escapeMode(EscapeMode.xhtml);

        return doc.body().html().replaceAll("\r", "").replaceAll("\n", "");
    }
}

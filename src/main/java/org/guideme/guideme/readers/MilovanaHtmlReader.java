package org.guideme.guideme.readers;

import java.io.IOException;

import org.apache.commons.lang.CharSetUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.guideme.guideme.model.Chapter;
import org.guideme.guideme.model.Guide;
import org.guideme.guideme.model.Page;
import org.guideme.guideme.model.Text;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class MilovanaHtmlReader {
	private static Logger log = LogManager.getLogger();
	
	public Guide loadFromUrl(String url) {
		try {
			Document doc = Jsoup.connect(url).get();
			Guide guide = createFromDocument(doc);
			
			String nextPage = url;
			int pageNr = 1;
			
			while (doc.select("#continue").size() > 0)
			{
				nextPage = "http://www.milovana.com/" + doc.select("#continue").first().attr("href");
				pageNr += 1;
				
				doc = Jsoup.connect(nextPage).get();
				addPage(guide.getChapters().get("chapter-1"), doc, pageNr);
			}
			
			return guide;
		} catch (IOException err) {
			log.error(err);
			return null;
		}
	}
	
	private Guide createFromDocument(Document doc) {
		Guide guide = Guide.getGuide();
		
		readGeneralInformation(guide, doc);
		Chapter chapter = createChapter(guide);
		
		addPage(chapter, doc, 1);
		
		return guide;
	}
	
	private void readGeneralInformation(Guide guide, Document doc) {
		// TODO
	}
	
	private Chapter createChapter(Guide guide) {
		Chapter chapter = new Chapter("chapter-1");
		guide.getChapters().put(chapter.getId(), chapter);
		return chapter;
	}
	

	private void addPage(Chapter chapter, Document doc, int pageNr) {

		Page page = new Page(String.valueOf(pageNr));

		Elements elm = doc.select("#tease_content").select(".text");
		if (elm.size() > 0) {
			String text = elm.first().text().replace('\r', ' ').replace('\n', ' ');
			text = CharSetUtils.squeeze(text, " ");
			page.addText(new Text(text));
		}
			
		chapter.getPages().put(page.getId(), page);
	}
		
}
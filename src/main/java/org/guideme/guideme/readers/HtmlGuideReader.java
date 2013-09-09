package org.guideme.guideme.readers;

import java.io.File;
import java.io.IOException;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.guideme.guideme.model.*;

public class HtmlGuideReader {
	private static Logger log = LogManager.getLogger();
	
	public Guide loadFromFile(File file) {
		try {
			Document doc = Jsoup.parse(file, "UTF-8", "");
			return CreateFromDocument(doc);
		} catch (IOException err) {
			log.error(err);
			return null;
		}
	}
	
	public Guide loadFromString(String html) { 
		Document doc = Jsoup.parse(html, "");
		return CreateFromDocument(doc);
	}
	
	private Guide CreateFromDocument(Document doc) {
		Guide guide = new Guide();
		
		readGeneralInformation(guide, doc);
		readChapters(guide, doc);
		
		return guide;
	}
	
	private void readGeneralInformation(Guide guide, Document doc) {
		guide.setTitle(doc.select("head title").text());
		guide.setAuthorName(doc.select("head meta[name=author]").attr("content"));
		guide.setKeywordsString(doc.select("head meta[name=keywords]").attr("content"));
		guide.setDescription(doc.select("head meta[name=description]").attr("content"));
		if (doc.select("head link[rel=alternate]").size() > 0) {
			guide.setOriginalUrl(doc.select("head link[rel=alternate]").attr("href"));
		}
		if (doc.select("head link[rel=author]").size() > 0) {
			guide.setAuthorUrl(doc.select("head link[rel=author]").attr("href"));
		}
		if (doc.select("head link[rel=icon]").size() > 0) {
			String url = doc.select("head link[rel=icon]").attr("href");
			//String mimeType = doc.select("head link[rel=icon]").attr("type");
			//String sizes = doc.select("head link[rel=icon]").attr("sizes");
			//int width = Integer.parseInt(sizes.split("x")[0]);
			//int height = Integer.parseInt(sizes.split("x")[1]);
			guide.setThumbnail(new Image(url, "", ""));
		}
	}

	private void readChapters(Guide guide, Document doc) {
		Elements articles = doc.select("body article");
		for (int i = 0; i < articles.size(); i++) {
			Element article = articles.get(i);
			Chapter chapter = new Chapter(articles.attr("id"));
			
			readPages(chapter, article);
			
			guide.getChapters().put(articles.attr("id"), chapter);
		}
	}
	
	private void readPages(Chapter chapter, Element articleElement) {
		Elements sections = articleElement.select("section");
		for (int i = 0; i < sections.size(); i++) {
			Element section = sections.get(i);
			
			//TODO need to add in the rest of the page stuff
			Page page = new Page(section.attr("id"), "", "", "", "", false, "");
			
			chapter.getPages().put(section.attr("id"), page);
		}
		
	}
}

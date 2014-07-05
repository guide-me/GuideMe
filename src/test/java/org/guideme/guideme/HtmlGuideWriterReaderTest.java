package org.guideme.guideme;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.guideme.guideme.model.*;
import org.guideme.guideme.readers.*;
import org.guideme.guideme.writers.*;

public class HtmlGuideWriterReaderTest {

	private HtmlGuideWriter writer;
	private HtmlGuideReader reader;
	private Guide originalGuide;

	@Before
	public void setUp() {
		writer = new HtmlGuideWriter();
		reader = new HtmlGuideReader();

		originalGuide = Guide.getGuide();
	}
	
	@Test
	public void authorName() {
		originalGuide.setAuthorName("Name of the author");
		
		Guide guide = reader.loadFromString(writer.Write(originalGuide));
		
		assertEquals(originalGuide.getAuthorName(), guide.getAuthorName());
	}
	
	@Test
	public void authorUrl() {
		originalGuide.setAuthorUrl("http://url.to/author");
		
		Guide guide = reader.loadFromString(writer.Write(originalGuide));
		
		assertEquals(originalGuide.getAuthorUrl(), guide.getAuthorUrl());
	}
	
	@Test
	public void description() {
		originalGuide.setDescription("Short description.");
		
		Guide guide = reader.loadFromString(writer.Write(originalGuide));
		
		assertEquals(originalGuide.getDescription(), guide.getDescription());
	}
	
	@Test
	public void keywords() {
		originalGuide.setKeywords("A", "B", "C");
		
		Guide guide = reader.loadFromString(writer.Write(originalGuide));
		
		assertEquals(originalGuide.getKeywordsString(), guide.getKeywordsString());
	}
	
	@Test
	public void originalUrl() {
		originalGuide.setOriginalUrl("http://url.to/original");
		
		Guide guide = reader.loadFromString(writer.Write(originalGuide));
		
		assertEquals(originalGuide.getOriginalUrl(), guide.getOriginalUrl());
	}
	
	@Test
	public void title() {
		originalGuide.setTitle("Title of the guide");
		
		Guide guide = reader.loadFromString(writer.Write(originalGuide));
		
		assertEquals(originalGuide.getTitle(), guide.getTitle());
	}
	
	@Test
	public void thumbnail() {
		originalGuide.setThumbnail(new Image("http://url.to/thumbnail.jpg", "", ""));
		
		Guide guide = reader.loadFromString(writer.Write(originalGuide));
		
		assertNotNull(guide.getThumbnail());
		assertEquals(originalGuide.getThumbnail().getId(), guide.getThumbnail().getId());
		//assertEquals(originalGuide.getThumbnail().getWidth(), guide.getThumbnail().getWidth());
		//assertEquals(originalGuide.getThumbnail().getHeight(), guide.getThumbnail().getHeight());
		//assertEquals(originalGuide.getThumbnail().getMimeType(), guide.getThumbnail().getMimeType());
	}

	@Test
	public void chapters() {
		originalGuide.getChapters().put("c-1", new Chapter("c-1"));
		originalGuide.getChapters().put("c-2", new Chapter("c-2"));
		
		Guide guide = reader.loadFromString(writer.Write(originalGuide));

		assertNotNull(guide.getChapters().get("c-1"));
		assertNotNull(guide.getChapters().get("c-2"));
	}

	@Test
	public void pages() {
		Chapter first = new Chapter("c-1");
		first.getPages().put("start", new Page("start", "", "", "", "", false));
		first.getPages().put("p-2", new Page("p-2", "", "", "", "", false));
		originalGuide.getChapters().put("first", first);
		
		Guide guide = reader.loadFromString(writer.Write(originalGuide));

                assertEquals(2, guide.getChapters().get("c-1").getPages().size());
	}

}

package org.guideme.guideme;

import static org.junit.Assert.*;

import java.util.Set;

import org.guideme.guideme.model.Chapter;
import org.guideme.guideme.model.Guide;
import org.guideme.guideme.model.Page;
import org.guideme.guideme.readers.XmlGuideReader;
import org.guideme.guideme.settings.AppSettings;
import org.guideme.guideme.ui.MainShell;
import org.guideme.guidme.mock.AppSettingsMock;
import org.guideme.guidme.mock.MainShellMock;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class MainLogicTest {
	private MainLogic mainLogic = MainLogic.getMainLogic();
	private Guide guide = Guide.getGuide();
	private MainShell mainShell = new MainShellMock(); 
	private AppSettings appSettings = AppSettingsMock.getAppSettings();
	private XmlGuideReader xmlGuideReader = XmlGuideReader.getXmlGuideReader();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testDisplayPageStringBooleanGuideMainShellAppSettings() {
		String pageid;
		appSettings.setDataDirectory("Y:\\TM\\Teases");
		pageid = xmlGuideReader.loadXML("Y:\\TM\\Teases\\Danni's friends.xml", guide);
		mainLogic.displayPage(pageid, false, guide, mainShell, appSettings);
		assertTrue(true);
	}

	@Test
	public void testDisplayPageStringStringBooleanGuideMainShellAppSettings() {
		appSettings.setDataDirectory("Y:\\TM\\Teases");
		xmlGuideReader.loadXML("Y:\\TM\\Teases\\Your choice!.xml", guide);
		Set<String> chapters = guide.getChapters().keySet();
		for (String chapterId : chapters) {
		    Chapter chapter = guide.getChapters().get(chapterId);
		    Set<String> pages = chapter.getPages().keySet();
				for (String pageId : pages) {
					Page page = chapter.getPages().get(pageId);
					mainLogic.displayPage(chapter.getId(), page.getId(), false, guide, mainShell, appSettings);		
				}
		}
		assertTrue(true);
	}

}

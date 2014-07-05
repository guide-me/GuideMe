package org.guideme.guideme;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;

import org.guideme.guideme.model.Chapter;
import org.guideme.guideme.model.Guide;
import org.guideme.guideme.model.Page;
import org.guideme.guideme.readers.XmlGuideReader;
import org.guideme.guideme.settings.AppSettings;
import org.guideme.guideme.settings.ComonFunctions;
import org.guideme.guideme.settings.GuideSettings;
import org.guideme.guideme.settings.UserSettings;
import org.guideme.guideme.ui.DebugShell;
import org.guideme.guideme.ui.MainShell;
import org.guideme.guidme.mock.AppSettingsMock;
import org.guideme.guidme.mock.MainShellMock;
import org.junit.Test;

public class MainLogicTest {
	private MainLogic mainLogic = MainLogic.getMainLogic();
	private Guide guide = Guide.getGuide();
	private MainShell mainShell = new MainShellMock(); 
	private DebugShell debugShell = new DebugShell();
	private AppSettings appSettings = AppSettingsMock.getAppSettings();
	private UserSettings userSettings = UserSettings.getUserSettings();
	private ComonFunctions comonFunctions = ComonFunctions.getComonFunctions();
	private GuideSettings guideSettings;
	private XmlGuideReader xmlGuideReader = XmlGuideReader.getXmlGuideReader();
	private String dataDirectory = "Y:\\TM\\Teases";
	private Boolean singlePage = false;
	private Boolean allGuides = false;
	private Boolean oneGuide = true;
	private Boolean scriptedTest = false;

	@Test
	public void testDisplayPageStringBooleanGuideMainShellAppSettings() {
		if (singlePage) {
			String guideFileName = "\\A tribute to Jurgita Valts.xml";
			String pageId = "page21";
			appSettings.setDataDirectory(dataDirectory);
			xmlGuideReader.loadXML(dataDirectory + guideFileName, guide, appSettings, debugShell);
			guideSettings = guide.getSettings();
			mainLogic.displayPage(pageId, false, guide, mainShell, appSettings, userSettings, guideSettings, debugShell);
		}
		assertTrue(true);
	}

	@Test
	public void testDisplayPageOneGuide() {
		if (oneGuide) {
			appSettings.setDataDirectory(dataDirectory);
			String guideId = "SWTPortTest";
			String guideFileName = "\\" + guideId + ".xml";
			guide.reset(guideId);
			xmlGuideReader.loadXML(dataDirectory + guideFileName, guide, appSettings, debugShell);
			guideSettings = guide.getSettings();
			Set<String> chapters = guide.getChapters().keySet();
			for (String chapterId : chapters) {
				Chapter chapter = guide.getChapters().get(chapterId);
				Set<String> pages = chapter.getPages().keySet();
				for (String pageId : pages) {
					Page page = chapter.getPages().get(pageId);
					mainLogic.displayPage(chapter.getId(), page.getId(), false, guide, mainShell, appSettings, userSettings, guideSettings, debugShell);		
				}
			}
		}
		assertTrue(true);
	}

		@Test
	public void testDisplayPageStringStringBooleanGuideMainShellAppSettings() {
		if (allGuides) {
			appSettings.setDataDirectory(dataDirectory);
			File f = new File(dataDirectory);
			// wildcard filter class handles the filtering
			ComonFunctions.WildCardFileFilter WildCardfilter = comonFunctions.new WildCardFileFilter();
			WildCardfilter.setFilePatern("*.xml");
			if (f.isDirectory()) {
				// return a list of matching files
				File[] children = f.listFiles(WildCardfilter);
				for (File file : children) {
					guide.reset(file.getName().substring(0, file.getName().length() - 4));
					xmlGuideReader.loadXML(file.getAbsolutePath(), guide, appSettings, debugShell);
					guideSettings = guide.getSettings();
					Set<String> chapters = guide.getChapters().keySet();
					for (String chapterId : chapters) {
						Chapter chapter = guide.getChapters().get(chapterId);
						Set<String> pages = chapter.getPages().keySet();
						for (String pageId : pages) {
							Page page = chapter.getPages().get(pageId);
							mainLogic.displayPage(chapter.getId(), page.getId(), false, guide, mainShell, appSettings, userSettings, guideSettings, debugShell);		
						}
					}
				}
			}
		}
		assertTrue(true);
	}
	
	@Test
	public void scriptedTest() {
		if (scriptedTest) {
			String script = "data\\pageScript.txt";
			appSettings.setDataDirectory(dataDirectory);
			 try {
				BufferedReader instructions = new BufferedReader(new FileReader(script));
				String guideFile = instructions.readLine();
				if (!guideFile.equals(null)) {
					guide.reset(guideFile);
					xmlGuideReader.loadXML(dataDirectory + "\\" + guideFile + ".xml", guide, appSettings, debugShell);
					guideSettings = guide.getSettings();
				}
				String instruction = instructions.readLine();
				String fields[];
				while (!(instruction == null)) {
					fields = instruction.split(",");
					mainLogic.displayPage(fields[0], fields[1], false, guide, mainShell, appSettings, userSettings, guideSettings, debugShell);
					instruction = instructions.readLine();
				}
				instructions.close();
			} catch (FileNotFoundException e) {
				fail("scriptedTest input file not found: " + script);
			} catch (IOException e) {
				fail("IO Error on script file " + script);
			}
		}
		assertTrue(true);
	}

}

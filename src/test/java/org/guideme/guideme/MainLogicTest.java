package org.guideme.guideme;

import static org.junit.Assert.*;

import org.guideme.guideme.model.Guide;
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
		pageid = xmlGuideReader.loadXML("Y:\\TM\\Teases\\_Tutorial.xml", guide);
		mainLogic.displayPage(pageid, false, guide, mainShell, appSettings);
		assertTrue(true);
	}

	@Test
	public void testDisplayPageStringStringBooleanGuideMainShellAppSettings() {
		String pageid;
		appSettings.setDataDirectory("Y:\\TM\\Teases");
		pageid = xmlGuideReader.loadXML("Y:\\TM\\Teases\\_Tutorial.xml", guide);
		mainLogic.displayPage("default", pageid, false, guide, mainShell, appSettings);
		mainLogic.displayPage("default", "endtext", false, guide, mainShell, appSettings);
		mainLogic.displayPage("default", "audio", false, guide, mainShell, appSettings);
		mainLogic.displayPage("default", "video", false, guide, mainShell, appSettings);
		mainLogic.displayPage("default", "video2", false, guide, mainShell, appSettings);
		mainLogic.displayPage("default", "randomimages1", false, guide, mainShell, appSettings);
		mainLogic.displayPage("default", "randomimages2", false, guide, mainShell, appSettings);
		mainLogic.displayPage("default", "delay_and_metronome1", false, guide, mainShell, appSettings);
		mainLogic.displayPage("default", "delay_and_metronome2", false, guide, mainShell, appSettings);
		mainLogic.displayPage("default", "delayadvanced", false, guide, mainShell, appSettings);
		mainLogic.displayPage("default", "randomtarget", false, guide, mainShell, appSettings);
		mainLogic.displayPage("default", "randomtarget1", false, guide, mainShell, appSettings);
		mainLogic.displayPage("default", "randomtarget2", false, guide, mainShell, appSettings);
		mainLogic.displayPage("default", "randomtarget3", false, guide, mainShell, appSettings);
		mainLogic.displayPage("default", "randomtarget4", false, guide, mainShell, appSettings);
		mainLogic.displayPage("default", "randomtarget5", false, guide, mainShell, appSettings);
		mainLogic.displayPage("default", "randomtarget6", false, guide, mainShell, appSettings);
		mainLogic.displayPage("default", "randomtarget7", false, guide, mainShell, appSettings);
		mainLogic.displayPage("default", "randomtarget8", false, guide, mainShell, appSettings);
		mainLogic.displayPage("default", "randomtarget9", false, guide, mainShell, appSettings);
		mainLogic.displayPage("default", "randomtarget10", false, guide, mainShell, appSettings);
		mainLogic.displayPage("default", "flags", false, guide, mainShell, appSettings);
		mainLogic.displayPage("default", "flags2", false, guide, mainShell, appSettings);
		mainLogic.displayPage("default", "flags3", false, guide, mainShell, appSettings);
		mainLogic.displayPage("default", "e1_start", false, guide, mainShell, appSettings);
		mainLogic.displayPage("default", "e1_shop", false, guide, mainShell, appSettings);
		mainLogic.displayPage("default", "e1_gift", false, guide, mainShell, appSettings);
		mainLogic.displayPage("default", "e1_result1", false, guide, mainShell, appSettings);
		mainLogic.displayPage("default", "e1_result2", false, guide, mainShell, appSettings);
		mainLogic.displayPage("default", "e2_start", false, guide, mainShell, appSettings);
		mainLogic.displayPage("default", "e2_break", false, guide, mainShell, appSettings);
		mainLogic.displayPage("default", "e2_end", false, guide, mainShell, appSettings);
		mainLogic.displayPage("default", "e3_start", false, guide, mainShell, appSettings);
		mainLogic.displayPage("default", "e3_page10", false, guide, mainShell, appSettings);
		mainLogic.displayPage("default", "e3_page11", false, guide, mainShell, appSettings);
		mainLogic.displayPage("default", "e3_page12", false, guide, mainShell, appSettings);
		mainLogic.displayPage("default", "e3_page13", false, guide, mainShell, appSettings);
		mainLogic.displayPage("default", "e3_page14", false, guide, mainShell, appSettings);
		mainLogic.displayPage("default", "e3_end", false, guide, mainShell, appSettings);
		mainLogic.displayPage("default", "end", false, guide, mainShell, appSettings);		assertTrue(true);
	}

}

package org.guideme.guideme.settings;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.guideme.guideme.model.Guide;
import org.guideme.guideme.settings.AppSettings;
import org.guideme.guideme.settings.GuideSettings;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class GuideSettingsTest {
	private static GuideSettings guideSettings;
	private static AppSettings appSettings;
	private static String dataDirectory;
	private static Guide guide;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		appSettings = AppSettings.getAppSettings();
		dataDirectory = appSettings.getDataDirectory();
		appSettings.setDataDirectory(appSettings.getUserDir());
		appSettings.saveSettings();
		guideSettings = new GuideSettings("GuideTest");
		guide = Guide.getGuide();
		HashMap<String, Object> scriptVar = new HashMap<String, Object>();
		scriptVar = guideSettings.getScriptVariables();
		scriptVar.put("pl1Count","3");
		scriptVar.put("pl2Count","4");
		scriptVar.put("failCount","5");
		guideSettings.setScriptVariables(scriptVar);
		guide.getSettings().saveSettings();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		guide.getSettings().saveSettings();
		appSettings.setDataDirectory(dataDirectory);
		appSettings.saveSettings();
	}

	@Test
	public void testGetPage() {
		String returned = guideSettings.getPage();
		assertEquals("start", returned);
	}

	@Test
	public void testSetPage() {
		guideSettings.setPage("page1");
		String returned = guideSettings.getPage();
		assertEquals("page1", returned);
		guideSettings.setPage("start");
		returned = guideSettings.getPage();
		assertEquals("start", returned);
	}

	@Test
	public void testGetFlags() {
		String returned = guideSettings.getFlags();
		assertEquals("", returned);
	}

	@Test
	public void testSetFlags() {
		guideSettings.setFlags("page3,page4");
		String returned = guideSettings.getFlags();
		assertEquals("page3,page4", returned);
		guideSettings.setFlags("page1,page2");
		returned = guideSettings.getFlags();
		assertEquals("page1,page2", returned);
	}

	@Test
	public void testGetScriptVariables() {
		HashMap<String, Object> scriptVar = new HashMap<String, Object>();
		scriptVar = guideSettings.getScriptVariables();
		int count = scriptVar.size();
		assertEquals("Variable count", 3, count);
		String pl1Count = scriptVar.get("pl1Count").toString();
		assertEquals("pl1Count","3",pl1Count);
		String pl2Count = scriptVar.get("pl2Count").toString();
		assertEquals("pl2Count","4",pl2Count);
		String failCount = scriptVar.get("failCount").toString();
		assertEquals("failCount","5",failCount);
	}

	@Test
	public void testSetScriptVariables() {
		HashMap<String, Object> scriptVar = new HashMap<String, Object>();
		scriptVar = guideSettings.getScriptVariables();
		scriptVar.put("testKey", "TestValue");
		scriptVar.put("pl1Count","4");
		scriptVar.put("failCount","7");
		guideSettings.setScriptVariables(scriptVar);
		HashMap<String, Object> scriptVar2 = new HashMap<String, Object>();
		scriptVar2 = guideSettings.getScriptVariables();
		int count = scriptVar2.size();
		assertEquals("Variable count", 4, count);
		String pl1Count = scriptVar.get("pl1Count").toString();
		assertEquals("pl1Count","4",pl1Count);
		String pl2Count = scriptVar.get("pl2Count").toString();
		assertEquals("pl2Count","4",pl2Count);
		String failCount = scriptVar.get("failCount").toString();
		assertEquals("failCount","7",failCount);
		scriptVar2.remove("testKey");
		scriptVar2.put("pl1Count","3");
		scriptVar2.put("pl2Count","4");
		scriptVar2.put("failCount","5");
		guideSettings.setScriptVariables(scriptVar2);
		guide.getSettings().saveSettings();
	}

	@Test 
	public void testAddPrefernces() {
		guideSettings.addPref("favpet", "Daisy", "Favourite pet's name");
		guideSettings.addPref("shop", "New Look", "Favourite clothes shop");
		guideSettings.addPref("soccer", false, "Do you like soccer");
		guideSettings.addPref("clean", true, "Do you enjoy cleaning");
		guideSettings.addPref("doughnuts", 0.0, "How many doughnuts have you eaten this week");
		guideSettings.addPref("pegs", 25.0, "How many clothes pegs do you own");
	}
	
	
}

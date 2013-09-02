package org.guideme.guideme.settings;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.guideme.guideme.settings.AppSettings;
import org.guideme.guideme.settings.GuideSettings;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class GuideSettingsTest {
	private static GuideSettings guideSettings;
	private static AppSettings appSettings;
	private static String dataDirectory;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		appSettings = new AppSettings();
		dataDirectory = appSettings.getDataDirectory();
		appSettings.setDataDirectory(appSettings.getUserDir());
		appSettings.saveSettings();
		guideSettings = new GuideSettings("GuideTest");
		HashMap<String, String> scriptVar = new HashMap<String, String>();
		scriptVar = guideSettings.getScriptVariables();
		scriptVar.put("pl1Count","3");
		scriptVar.put("pl2Count","4");
		scriptVar.put("failCount","5");
		guideSettings.setScriptVariables(scriptVar);
		guideSettings.saveSettings();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		guideSettings.saveSettings();
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
		assertEquals("page1,page2", returned);
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
		HashMap<String, String> scriptVar = new HashMap<String, String>();
		scriptVar = guideSettings.getScriptVariables();
		int count = scriptVar.size();
		assertEquals("Variable count", 3, count);
		String pl1Count = scriptVar.get("pl1Count");
		assertEquals("pl1Count","3",pl1Count);
		String pl2Count = scriptVar.get("pl2Count");
		assertEquals("pl2Count","4",pl2Count);
		String failCount = scriptVar.get("failCount");
		assertEquals("failCount","5",failCount);
	}

	@Test
	public void testSetScriptVariables() {
		HashMap<String, String> scriptVar = new HashMap<String, String>();
		scriptVar = guideSettings.getScriptVariables();
		scriptVar.put("testKey", "TestValue");
		scriptVar.put("pl1Count","4");
		scriptVar.put("failCount","7");
		guideSettings.setScriptVariables(scriptVar);
		HashMap<String, String> scriptVar2 = new HashMap<String, String>();
		scriptVar2 = guideSettings.getScriptVariables();
		int count = scriptVar2.size();
		assertEquals("Variable count", 4, count);
		String pl1Count = scriptVar.get("pl1Count");
		assertEquals("pl1Count","4",pl1Count);
		String pl2Count = scriptVar.get("pl2Count");
		assertEquals("pl2Count","4",pl2Count);
		String failCount = scriptVar.get("failCount");
		assertEquals("failCount","7",failCount);
		scriptVar2.remove("testKey");
		scriptVar2.put("pl1Count","3");
		scriptVar2.put("pl2Count","4");
		scriptVar2.put("failCount","5");
		guideSettings.setScriptVariables(scriptVar2);
		guideSettings.saveSettings();
	}

	@Test
	public void testGetHtml() {
		String returned = guideSettings.getHtml();
		assertEquals("", returned);
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
	
	
	@Test
	public void testSetHtml() {
		guideSettings.setHtml("Phil's card is Queen of Spades <p/>Kate's card is Ace of Hearts. <p/>Score is Phil 2 Kate 11<p/><p/>Congratulations <em>Kate</em> you have won this game.");
		String returned = guideSettings.getHtml();
		assertEquals("Phil's card is Queen of Spades <p/>Kate's card is Ace of Hearts. <p/>Score is Phil 2 Kate 11<p/><p/>Congratulations <em>Kate</em> you have won this game.", returned);
		guideSettings.setHtml("");
		returned = guideSettings.getHtml();
		assertEquals("", returned);
	}
}

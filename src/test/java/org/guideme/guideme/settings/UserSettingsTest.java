package org.guideme.guideme.settings;

import static org.junit.Assert.*;

import java.util.Set;

import org.guideme.guideme.settings.UserSettings;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class UserSettingsTest {
	private static UserSettings userSettings;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		userSettings = UserSettings.getUserSettings();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testGetStringKeys() {
		Set<String> returned;
		returned = userSettings.getStringKeys();
		assertEquals("Size of Set Returned ", 6, returned.size());
		assertTrue("myName", returned.contains("myName"));
		assertTrue("herName1", returned.contains("herName1"));
		assertTrue("herName2", returned.contains("herName2"));
		assertTrue("hisName1", returned.contains("hisName1"));
		assertTrue("hisName2", returned.contains("hisName2"));
		assertTrue("petName", returned.contains("petName"));
	}

	@Test
	public void testGetNumberKeys() {
		Set<String> returned;
		returned = userSettings.getNumberKeys();
		assertEquals("Size of Set Returned ", 2, returned.size());
		assertTrue("myage", returned.contains("myage"));
		assertTrue("size", returned.contains("size"));
	}

	@Test
	public void testGetBooleanKeys() {
		Set<String> returned;
		returned = userSettings.getBooleanKeys();
		assertEquals("Size of Set Returned ", 3, returned.size());
		assertTrue("chocolate", returned.contains("chocolate"));
		assertTrue("spicyfood", returned.contains("spicyfood"));
		assertTrue("cbt", returned.contains("cbt"));
	}

	@Test
	public void testGetPref() {
		assertEquals("Phil", userSettings.getPref("myName"));
	}

	@Test
	public void testGetPrefNumber() {
		double returned = userSettings.getPrefNumber("size");
		double expected = 5.0;
		assertEquals(Double.doubleToLongBits(expected), Double.doubleToLongBits(returned));
	}

	@Test
	public void testSetPrefStringString() {
		userSettings.setPref("myName", "Oswald");
		assertEquals("Oswald", userSettings.getPref("myName"));
		userSettings.setPref("myName", "Phil");
		assertEquals("Phil", userSettings.getPref("myName"));
	}

	@Test
	public void testIsPref() {
		assertTrue(userSettings.isPref("chocolate"));
	}

	@Test
	public void testSetPrefStringBoolean() {
		userSettings.setPref("chocolate", false);
		assertTrue(!userSettings.isPref("chocolate"));
		userSettings.setPref("chocolate", true);
		assertTrue(userSettings.isPref("chocolate"));
	}

	@Test
	public void testSetPrefStringDouble() {
		userSettings.setPref("size", 8.0);
		double returned = userSettings.getPrefNumber("size");
		double expected = 8.0;
		assertEquals(Double.doubleToLongBits(expected), Double.doubleToLongBits(returned));
		userSettings.setPref("size", 5.0);
		returned = userSettings.getPrefNumber("size");
		expected = 5.0;
		assertEquals(Double.doubleToLongBits(expected), Double.doubleToLongBits(returned));
	}

	@Test
	public void testGetScreenDesc() {
		assertEquals("My Name", userSettings.getScreenDesc("myName", UserSettings.STRING));
		assertEquals("How long in inches is your hand", userSettings.getScreenDesc("size", UserSettings.NUMBER));
		assertEquals("Do you like chocolate?", userSettings.getScreenDesc("chocolate", UserSettings.BOOLEAN));
	}

}

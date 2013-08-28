package org.guideme.guideme;

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
		userSettings = new UserSettings();
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
		fail("Not yet implemented");
	}

	@Test
	public void testGetPrefNumber() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetPrefStringString() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsPref() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetPrefStringBoolean() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetPrefStringDouble() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetScreenDesc() {
		fail("Not yet implemented");
	}

	@Test
	public void testClone() {
		fail("Not yet implemented");
	}

}

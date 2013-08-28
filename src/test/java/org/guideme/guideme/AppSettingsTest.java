package org.guideme.guideme;

import static org.junit.Assert.*;

import org.guideme.guideme.settings.AppSettings;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;


public class AppSettingsTest {
	private static AppSettings myAppSettings;
	private static int FontSize;
	private static int HtmlFontSize;
	private static boolean Debug;
	private static String DataDirectory = new String();
	private static int[] sash1Weights = new int[2];
	private static int[] sash2Weights = new int[2];

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		myAppSettings = new AppSettings();
		FontSize = myAppSettings.getFontSize();
		HtmlFontSize = myAppSettings.getHtmlFontSize();
		Debug = myAppSettings.getDebug();
		DataDirectory = myAppSettings.getDataDirectory();
		int[] tmpWeights = new int[2];
		tmpWeights = myAppSettings.getSash1Weights();
		sash1Weights[0] = tmpWeights[0];
		sash1Weights[1] = tmpWeights[1];
		tmpWeights = myAppSettings.getSash2Weights();
		sash2Weights[0] = tmpWeights[0];
		sash2Weights[1] = tmpWeights[1];
		myAppSettings.setFontSize(10);
		myAppSettings.setHtmlFontSize(18);
		myAppSettings.setDebug(false);
		myAppSettings.setDataDirectory("TestDir");
		int[] sw1 = new int[2];
		sw1[0] = 100;
		sw1[1] = 200;
		myAppSettings.setSash1Weights(sw1);
		int[] sw2 = new int[2];
		sw2[0] = 300;
		sw2[1] = 400;
		myAppSettings.setSash2Weights(sw2);
		myAppSettings.saveSettings();
		myAppSettings= null;
		myAppSettings = new AppSettings();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		myAppSettings.setFontSize(FontSize);
		myAppSettings.setHtmlFontSize(HtmlFontSize);
		myAppSettings.setDebug(Debug);
		myAppSettings.setDataDirectory(DataDirectory);
		myAppSettings.setSash1Weights(sash1Weights);
		myAppSettings.setSash2Weights(sash2Weights);
		myAppSettings.saveSettings();
	}


	@Test
	public void testGetFontSize() {
		int returned = myAppSettings.getFontSize();
		assertEquals(10, returned);
	}

	@Test
	public void testSetFontSize() {
		myAppSettings.setFontSize(12);
		int returned = myAppSettings.getFontSize();
		assertEquals("Set from 10 to 12", 12, returned);
		myAppSettings.setFontSize(10);
		assertEquals("Set from 12 to 10", 10, myAppSettings.getFontSize());
	}

	@Test
	public void testGetDebug() {
		Boolean returned = myAppSettings.getDebug();
		assertEquals(false, returned);
	}

	@Test
	public void testSetDebug() {
		myAppSettings.setDebug(true);
		Boolean returned = myAppSettings.getDebug();
		assertEquals(true, returned);
		myAppSettings.setDebug(false);
		assertEquals(false, myAppSettings.getDebug());
	}

	@Test
	public void testGetDataDirectory() {
		String returned = myAppSettings.getDataDirectory();
		assertEquals("TestDir", returned);
	}

	@Test
	public void testSetDataDirectory() {
		myAppSettings.setDataDirectory("DiffDir");
		String returned = myAppSettings.getDataDirectory();
		assertEquals("DiffDir", returned);
		myAppSettings.setDataDirectory("TestDir");
		assertEquals("TestDir", myAppSettings.getDataDirectory());
	}

	@Test
	public void testGetSash1Weights() {
		int[] returned = myAppSettings.getSash1Weights();
		assertEquals("Sash1Weight[0] ", 100, returned[0]);
		assertEquals("Sash1Weight[1] ", 200, returned[1]);
	}

	@Test
	public void testSetSash1Weights() {
		int[] tmp = new int[2];
		tmp[0] = 500;
		tmp[1] = 600;
		myAppSettings.setSash1Weights(tmp);
		int[] returned = myAppSettings.getSash1Weights();
		int Returned1 = returned[0];
		int Returned2 = returned[1];
		tmp[0] = 100;
		tmp[1] = 200;
		myAppSettings.setSash1Weights(tmp);
		assertEquals(500, Returned1);
		assertEquals(600, Returned2);
	}

	@Test
	public void testGetSash2Weights() {
		int[] returned = myAppSettings.getSash2Weights();
		assertEquals("Sash2Weight[0] ", 300, returned[0]);
		assertEquals("Sash2Weight[1] ", 400, returned[1]);
	}

	@Test
	public void testSetSash2Weights() {
		int[] tmp = new int[2];
		tmp[0] = 700;
		tmp[1] = 800;
		myAppSettings.setSash2Weights(tmp);
		int[] returned = myAppSettings.getSash2Weights();
		int Returned1 = returned[0];
		int Returned2 = returned[1];
		tmp[0] = 300;
		tmp[1] = 400;
		myAppSettings.setSash2Weights(tmp);
		assertEquals(700, Returned1);
		assertEquals(800, Returned2);
	}

	@Test
	public void testGetHtmlFontSize() {
		int returned = myAppSettings.getHtmlFontSize();
		assertEquals(18,returned);
	}

	@Test
	public void testSetHtmlFontSize() {
		myAppSettings.setHtmlFontSize(20);
		int returned = myAppSettings.getHtmlFontSize();
		assertEquals(20, returned);
		myAppSettings.setHtmlFontSize(18);
		assertEquals(18, myAppSettings.getHtmlFontSize());
	}

}

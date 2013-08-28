package org.guideme.guideme;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.guideme.guideme.settings.ComonFunctions;
import org.junit.Test;

public class ComonFunctionsTest {

	@Test
	public void testCanShowArrayListOfStringStringString() {
		ArrayList<String> flags = new ArrayList<String>();
		flags.add("start");
		flags.add("page1");
		flags.add("page2");
		
		Boolean returned = ComonFunctions.canShow(flags, "start", "page3");
		assertTrue("Test 1", returned);
		returned = ComonFunctions.canShow(flags, "start", "");
		assertTrue("Test 2", returned);
		returned = ComonFunctions.canShow(flags, "start", "page2");
		assertTrue("Test 3", !returned);
		returned = ComonFunctions.canShow(flags, "page4", "page3");
		assertTrue("Test 4", !returned);
		returned = ComonFunctions.canShow(flags, "start|page3", "");
		assertTrue("Test 5", returned);
		returned = ComonFunctions.canShow(flags, "start+page2", "");
		assertTrue("Test 6", returned);
		returned = ComonFunctions.canShow(flags, "", "page3|start");
		assertTrue("Test 7", returned);
		returned = ComonFunctions.canShow(flags, "", "page3+page4");
		assertTrue("Test 8", returned);
		returned = ComonFunctions.canShow(flags, "start+page2", "page3|start");
		assertTrue("Test 9", returned);
		
	}

	@Test
	public void testCanShowArrayListOfStringStringStringString() {
		ArrayList<String> flags = new ArrayList<String>();
		flags.add("start");
		flags.add("page1");
		flags.add("page2");
		
		Boolean returned = ComonFunctions.canShow(flags, "start", "page3", "page4");
		assertTrue("Test 1", returned);
		returned = ComonFunctions.canShow(flags, "start", "", "page4");
		assertTrue("Test 2", returned);
		returned = ComonFunctions.canShow(flags, "start", "page2", "page4");
		assertTrue("Test 3", !returned);
		returned = ComonFunctions.canShow(flags, "page4", "page3", "page4");
		assertTrue("Test 4", !returned);
		returned = ComonFunctions.canShow(flags, "start|page3", "", "page4");
		assertTrue("Test 5", returned);
		returned = ComonFunctions.canShow(flags, "start+page2", "", "page4");
		assertTrue("Test 6", returned);
		returned = ComonFunctions.canShow(flags, "", "page3|start", "page4");
		assertTrue("Test 7", returned);
		returned = ComonFunctions.canShow(flags, "", "page3+page4", "page4");
		assertTrue("Test 8", returned);
		returned = ComonFunctions.canShow(flags, "start+page2", "page3|start", "page4");
		assertTrue("Test 9", returned);

		returned = ComonFunctions.canShow(flags, "start", "page3", "page2");
		assertTrue("Test 10", !returned);
		returned = ComonFunctions.canShow(flags, "start", "", "page2");
		assertTrue("Test 11", !returned);
		returned = ComonFunctions.canShow(flags, "start|page3", "", "page2");
		assertTrue("Test 12", !returned);
		returned = ComonFunctions.canShow(flags, "start+page2", "", "page2");
		assertTrue("Test 13", !returned);
		returned = ComonFunctions.canShow(flags, "", "page3|start", "page2");
		assertTrue("Test 14", !returned);
		returned = ComonFunctions.canShow(flags, "", "page3+page4", "page2");
		assertTrue("Test 15", !returned);
		returned = ComonFunctions.canShow(flags, "start+page2", "page3|start", "page2");
		assertTrue("Test 16", !returned);

	}

	@Test
	public void testSetFlags() {
		ArrayList<String> flags = new ArrayList<String>();
		flags.add("start");
		flags.add("page1");
		flags.add("page2");

		ArrayList<String> flags2 = new ArrayList<String>();
		flags2.add("start");
		flags2.add("page1");
		flags2.add("page2");
		flags2.add("fred");
		flags2.add("george");

		ComonFunctions.SetFlags("fred, george", flags);
		assertTrue(flags.equals(flags2));
	}

	@Test
	public void testGetFlags() {
		ArrayList<String> flags = new ArrayList<String>();
		flags.add("start");
		flags.add("page1");
		flags.add("page2");
		String Returned = ComonFunctions.GetFlags(flags);
		assertTrue(Returned.equals("start,page1,page2"));
	}

	@Test
	public void testUnsetFlags() {
		ArrayList<String> flags = new ArrayList<String>();
		flags.add("start");
		flags.add("page1");
		flags.add("page2");

		ArrayList<String> flags2 = new ArrayList<String>();
		flags2.add("start");
		flags2.add("page1");
		flags2.add("page2");
		flags2.add("fred");
		flags2.add("george");

		ComonFunctions.UnsetFlags("fred, george", flags2);
		assertTrue(flags.equals(flags2));
	}

	@Test
	public void testGetRandom() {
		int returned = ComonFunctions.getRandom("5");
		assertTrue("Test 1", (returned > 0 && returned < 6));
		returned = ComonFunctions.getRandom("(15..29)");
		assertTrue("Test 2", (returned > 14 && returned < 30));
	}

	@Test
	public void testGetMilisecFromTime() {
		assertEquals("Test1 ", 1000, ComonFunctions.getMilisecFromTime("00:00:01"));
		assertEquals("Test2 ", 305000, ComonFunctions.getMilisecFromTime("00:05:05"));
		assertEquals("Test3 ", 4350000, ComonFunctions.getMilisecFromTime("01:12:30"));
		assertEquals("Test4 ", 1800000, ComonFunctions.getMilisecFromTime("00:30:00"));
		assertEquals("Test5 ", 15000, ComonFunctions.getMilisecFromTime("00:00:15"));
	}

}

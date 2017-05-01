function test() {
	jscriptLog("-- start of script --");
	var random = comonFunctions.getRandom("(1..52)"); 
	var random2 = comonFunctions.getRandom("(10..52)"); 
	var card;
	var card2;
	var pl1Count = Number(scriptVars.get("pl1Count"));
	var pl2Count = Number(scriptVars.get("pl2Count"));
	jscriptLog("-- random " + random + ", random2 " + random2 + ", pl1Count " + pl1Count + ", pl2Count " + pl2Count + " --");
	if (pl1Count == 11 | pl2Count == 11) {
		pl1Count = 0;
		pl2Count = 0;
	}
	var petName = guideSettings.getPref("favpet");
	jscriptLog("-- Pet name is " + petName);
	switch(random)
	{
	case 1:
	  card = "two of hearts" 
	  break;
	case 2:
	  card = "two of diamonds" 
	  break;
	case 3:
	  card = "two of clubs" 
	  break;
	case 4:
	  card = "two of spades" 
	  break;
	case 5:
	  card = "three of hearts" 
	  break;
	case 6:
	  card = "three of diamonds" 
	  break;
	case 7:
	  card = "three of clubs" 
	  break;
	case 8:
	  card = "three of spades" 
	  break;
	case 9:
	  card = "four of hearts" 
	  break;
	case 10:
	  card = "four of diamonds" 
	  break;
	case 11:
	  card = "four of clubs" 
	  break;
	case 12:
	  card = "four of spades" 
	  break;
	case 13:
	  card = "five of hearts" 
	  break;
	case 14:
	  card = "five of diamonds" 
	  break;
	case 15:
	  card = "five of clubs" 
	  break;
	case 16:
	  card = "five of spades" 
	  break;
	case 17:
	  card = "six of hearts" 
	  break;
	case 18:
	  card = "six of diamonds" 
	  break;
	case 19:
	  card = "six of clubs" 
	  break;
	case 20:
	  card = "six of spades" 
	  break;
	case 21:
	  card = "seven of hearts" 
	  break;
	case 22:
	  card = "seven of diamonds" 
	  break;
	case 23:
	  card = "seven of clubs" 
	  break;
	case 24:
	  card = "seven of spades" 
	  break;
	case 25:
	  card = "eight of hearts" 
	  break;
	case 26:
	  card = "eight of diamonds" 
	  break;
	case 27:
	  card = "eight of clubs" 
	  break;
	case 28:
	  card = "eight of spades" 
	  break;
	case 29:
	  card = "nine of hearts" 
	  break;
	case 30:
	  card = "nine of diamonds" 
	  break;
	case 31:
	  card = "nine of clubs" 
	  break;
	case 32:
	  card = "nine of spades" 
	  break;
	case 33:
	  card = "ten of hearts" 
	  break;
	case 34:
	  card = "ten of diamonds" 
	  break;
	case 35:
	  card = "ten of clubs" 
	  break;
	case 36:
	  card = "ten of spades" 
	  break;
	case 37:
	  card = "jack of hearts" 
	  break;
	case 38:
	  card = "jack of diamonds" 
	  break;
	case 39:
	  card = "jack of clubs" 
	  break;
	case 40:
	  card = "jack of spades" 
	  break;
	case 41:
	  card = "queen of hearts" 
	  break;
	case 42:
	  card = "queen of diamonds" 
	  break;
	case 43:
	  card = "queen of clubs" 
	  break;
	case 44:
	  card = "queen of spades" 
	  break;
	case 45:
	  card = "king of hearts" 
	  break;
	case 46:
	  card = "king of diamonds" 
	  break;
	case 47:
	  card = "king of clubs" 
	  break;
	case 48:
	  card = "king of spades" 
	  break;
	case 49:
	  card = "ace of hearts" 
	  break;
	case 50:
	  card = "ace of diamonds" 
	  break;
	case 51:
	  card = "ace of clubs" 
	  break;
	case 52:
	  card = "ace of spades" 
	  break;
	}
	jscriptLog("-- card is " + card);
	switch(random2)
	{
	case 1:
	  card2 = "two of hearts" 
	  break;
	case 2:
	  card2 = "two of diamonds" 
	  break;
	case 3:
	  card2 = "two of clubs" 
	  break;
	case 4:
	  card2 = "two of spades" 
	  break;
	case 5:
	  card2 = "three of hearts" 
	  break;
	case 6:
	  card2 = "three of diamonds" 
	  break;
	case 7:
	  card2 = "three of clubs" 
	  break;
	case 8:
	  card2 = "three of spades" 
	  break;
	case 9:
	  card2 = "four of hearts" 
	  break;
	case 10:
	  card2 = "four of diamonds" 
	  break;
	case 11:
	  card2 = "four of clubs" 
	  break;
	case 12:
	  card2 = "four of spades" 
	  break;
	case 13:
	  card2 = "five of hearts" 
	  break;
	case 14:
	  card2 = "five of diamonds" 
	  break;
	case 15:
	  card2 = "five of clubs" 
	  break;
	case 16:
	  card2 = "five of spades" 
	  break;
	case 17:
	  card2 = "six of hearts" 
	  break;
	case 18:
	  card2 = "six of diamonds" 
	  break;
	case 19:
	  card2 = "six of clubs" 
	  break;
	case 20:
	  card2 = "six of spades" 
	  break;
	case 21:
	  card2 = "seven of hearts" 
	  break;
	case 22:
	  card2 = "seven of diamonds" 
	  break;
	case 23:
	  card2 = "seven of clubs" 
	  break;
	case 24:
	  card2 = "seven of spades" 
	  break;
	case 25:
	  card2 = "eight of hearts" 
	  break;
	case 26:
	  card2 = "eight of diamonds" 
	  break;
	case 27:
	  card2 = "eight of clubs" 
	  break;
	case 28:
	  card2 = "eight of spades" 
	  break;
	case 29:
	  card2 = "nine of hearts" 
	  break;
	case 30:
	  card2 = "nine of diamonds" 
	  break;
	case 31:
	  card2 = "nine of clubs" 
	  break;
	case 32:
	  card2 = "nine of spades" 
	  break;
	case 33:
	  card2 = "ten of hearts" 
	  break;
	case 34:
	  card2 = "ten of diamonds" 
	  break;
	case 35:
	  card2 = "ten of clubs" 
	  break;
	case 36:
	  card2 = "ten of spades" 
	  break;
	case 37:
	  card2 = "jack of hearts" 
	  break;
	case 38:
	  card2 = "jack of diamonds" 
	  break;
	case 39:
	  card2 = "jack of clubs" 
	  break;
	case 40:
	  card2 = "jack of spades" 
	  break;
	case 41:
	  card2 = "queen of hearts" 
	  break;
	case 42:
	  card2 = "queen of diamonds" 
	  break;
	case 43:
	  card2 = "queen of clubs" 
	  break;
	case 44:
	  card2 = "queen of spades" 
	  break;
	case 45:
	  card2 = "king of hearts" 
	  break;
	case 46:
	  card2 = "king of diamonds" 
	  break;
	case 47:
	  card2 = "king of clubs" 
	  break;
	case 48:
	  card2 = "king of spades" 
	  break;
	case 49:
	  card2 = "ace of hearts" 
	  break;
	case 50:
	  card2 = "ace of diamonds" 
	  break;
	case 51:
	  card2 = "ace of clubs" 
	  break;
	case 52:
	  card2 = "ace of spades" 
	  break;
	}
	jscriptLog("-- card2 is " + card2);
	overRide.html = "pref PetName = " + guideSettings.getPref("favpet") + "<p/>";
	overRide.html  = overRide.html + "I like soccer: " + guideSettings.isPref("soccer") +  "<p/>";
	overRide.html  = overRide.html + "I ate " + guideSettings.getPrefNumber("doughnuts") + " doughnuts this week " + "<p/>";
	if (random > random2) {
		pl1Count = pl1Count + 1;
		if (pl1Count == 11) {
			overRide.html = overRide.html + userSettings.getPref("myName") + "'s card is " + card + " <p/>" + userSettings.getPref("herName1") + "'s card is " + card2 + ". <p/>Score is " + userSettings.getPref("myName") + " " + pl1Count + " " + userSettings.getPref("herName1") + " " + pl2Count + "<p/><p/>Congratulations <em>" + userSettings.getPref("myName") + "</em> you have won this game.";
		} else {
			overRide.html = overRide.html + userSettings.getPref("myName") + "'s card is " + card + " <p/>" + userSettings.getPref("herName1") + "'s card is " + card2 + ". <p/><em>" + userSettings.getPref("myName") + "</em> wins this round.<p/>Score is " + userSettings.getPref("myName") + " " + pl1Count + " " + userSettings.getPref("herName1") + " " + pl2Count;
		}
	} else {
		pl2Count = pl2Count + 1;
		if (pl2Count == 11) {
			overRide.html = overRide.html + userSettings.getPref("myName") + "'s card is " + card + " <p/>" + userSettings.getPref("herName1") + "'s card is " + card2 + ". <p/>Score is " + userSettings.getPref("myName") + " " + pl1Count + " " + userSettings.getPref("herName1") + " " + pl2Count + "<p/><p/>Congratulations <em>" + userSettings.herName1 + "</em> you have won this game.";
		} else {
			overRide.html = overRide.html + userSettings.getPref("myName") + "'s card is " + card + " <p/>" + userSettings.getPref("herName1") + "'s card is " + card2 + ". <p/><em>" + userSettings.getPref("herName1") + "</em> wins this round.<p/>Score is " + userSettings.getPref("myName") + " " + pl1Count + " " + userSettings.getPref("herName1") + " " + pl2Count;
		}
	}
	scriptVars.put("pl1Count", pl1Count);
	scriptVars.put("pl2Count", pl2Count);
}
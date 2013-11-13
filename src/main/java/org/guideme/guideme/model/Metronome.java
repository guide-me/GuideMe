package org.guideme.guideme.model;

import java.util.ArrayList;

import org.guideme.guideme.settings.ComonFunctions;

public class Metronome {
	private String ifSet;
	private String ifNotSet;
	private String bpm; //beats per minute
	private int resolution = 4; //ticks per beat
	private int loops = -1;//number of loops, -1 infinite, 0 play once, 1 repeat once
	private String rhythm = ""; //beat rhythm 
	/*
	 Rhythm is a list of ticks so at 60 bpm, resolution of 4 we get 4 ticks per second
	 a straight 1 beat per second would be 0,4,8,12,16,20,24 (giving 7 one second beats)
	 So if we want 20 fast beats (at 120 bpm) then 5 slow we can use
	 0,2,4,6,8,10,12,14,16,18,20,22,24,26,28,30,32,34,36,38,42,46,50,54,58 
	 */
	private ComonFunctions comonFunctions = ComonFunctions.getComonFunctions();

	public Metronome(String bpm, String ifSet, String ifNotSet) {
		this(bpm, ifSet, ifNotSet, 4, -1, "");
	}
	
	public Metronome(String bpm, String ifSet, String ifNotSet, int resolution,	int loops, String rhythm) {
		this.bpm = bpm;
		this.ifSet = ifSet;
		this.ifNotSet = ifNotSet;
		this.resolution = resolution;
		this.loops = loops;
		this.rhythm = rhythm;
	}

	public boolean canShow(ArrayList<String> setList) {
		return comonFunctions.canShow(setList, ifSet, ifNotSet);
	}

	public int getbpm() {
		//will either return bpm as an integer 
		//or if it is in the format (1..20) it will return a random number between 1 and 20
		return comonFunctions.getRandom(bpm);
	}

	public int getResolution() {
		return resolution;
	}

	public int getLoops() {
		return loops;
	}

	public String getRhythm() {
		return rhythm;
	}

}

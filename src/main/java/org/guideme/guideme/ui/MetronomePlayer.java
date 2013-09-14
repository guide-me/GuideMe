package org.guideme.guideme.ui;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MetronomePlayer  implements Runnable {
	// uses the built in midi functionality 
	int metronomeBPM;
	int instrument; 
	int loops; 
	int resolution; 
	String rhythm;
	Sequencer sequencer = null;
	private static Logger logger = LogManager.getLogger();

	public MetronomePlayer(int metronomeBPM, int instrument, int loops,
			int resolution, String rhythm) {
		this.metronomeBPM = metronomeBPM;
		this.instrument = instrument;
		this.loops = loops;
		this.resolution = resolution;
		this.rhythm = rhythm;
	}

	public void metronomeStop() {
		//stop the sequencer 
		//there is a loop lower which checks if the sequencer is still running
		//and will exit the thread if it stops
		sequencer.stop();
	}

	public void run() {
		Track track;
		Sequence sequence = null;
		int channel = 9; //Percussion track
		try {
			sequence = new Sequence(Sequence.PPQ, resolution);
			track = sequence.createTrack();
			ShortMessage sm = new ShortMessage( );
			sm.setMessage(ShortMessage.PROGRAM_CHANGE, channel, 0, 0);
			track.add(new MidiEvent(sm, 0));
			if (rhythm.equals("")) {
				// simple bpm over ride any loops / resolution
				loops = -1;
				resolution = 4;
				// add 60 beats to the file and loop indefinitely
				for (int i = 1 ; i < (60 * resolution) ; i = i + resolution) {
					ShortMessage on = new ShortMessage( );
					on.setMessage(ShortMessage.NOTE_ON,  channel, instrument, 64);
					ShortMessage off = new ShortMessage( );
					off.setMessage(ShortMessage.NOTE_OFF, channel, instrument, 64);
					track.add(new MidiEvent(on, i));
					track.add(new MidiEvent(off, i + 1));
				}
			} else {
				// rhythm
				// this string contains the ticks we want to generate sound on
				// we can just add a note at each of the ticks in the array
				String[] splits = rhythm.split(",");
				int tick = 0;
				for (String split: splits)
				{
					tick = Integer.parseInt(split);
					ShortMessage on = new ShortMessage( );
					on.setMessage(ShortMessage.NOTE_ON,  channel, instrument, 64);
					ShortMessage off = new ShortMessage( );
					off.setMessage(ShortMessage.NOTE_OFF, channel, instrument, 64);
					track.add(new MidiEvent(on, tick));
					track.add(new MidiEvent(off, tick + 1));
				}
			}
			long totalTicks = track.ticks();
			long padTicks = totalTicks % resolution;
			if (padTicks > 0) {
				padTicks = resolution - padTicks;
				ShortMessage on = new ShortMessage( );
				on.setMessage(ShortMessage.NOTE_ON,  channel, instrument, 0);
				ShortMessage off = new ShortMessage( );
				off.setMessage(ShortMessage.NOTE_OFF, channel, instrument, 0);
				track.add(new MidiEvent(on, totalTicks + 1));
				track.add(new MidiEvent(off, totalTicks + padTicks));
			}
			sequencer = MidiSystem.getSequencer();
			sequencer.open();
			sequencer.setTempoInBPM(metronomeBPM);
			sequencer.setSequence(sequence);
			// if we have a number of loops do some additional processing
			if (loops != 0) {
				logger.debug("displayPage loops " + loops);
				if (loops > 0) {
					sequencer.setLoopCount(loops);
				}  else {
					// loop 30000 times
					sequencer.setLoopCount(30000);
				}
				sequencer.start();
			} else {
				sequencer.start();
			}
			while (sequencer.isRunning()) {
				Thread.sleep(5000);
			}
		} catch (Exception e) {
			logger.error("displayPage Metronome ", e);
		}
		if (sequencer != null) {
			sequencer.close();
			sequencer = null;
		}
	}

}

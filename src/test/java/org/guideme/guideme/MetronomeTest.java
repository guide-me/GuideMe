package org.guideme.guideme;

import org.guideme.guideme.ui.MetronomePlayer;

public class MetronomeTest {
	private static Thread threadMetronome;
	private static MetronomePlayer metronome;

	public static void main(String[] args) {
		Boolean playing = false;
		for (int i = 34; i < 80; i++) {
			//metronome = new MetronomePlayer(90, i, 0, 4, "1,3,5,7,9,11,13,15,17,19,21,23,25,27,29,31,33,35,37,39,43,47,51,55,59");
			metronome = new MetronomePlayer(60, i, 0, 4, "1,3,5,7,9,11,13,15,17,19");
			//metronome = new MetronomePlayer(120, 49, -1, 4, "");
			threadMetronome = new Thread(metronome);
			threadMetronome.start();

			while (threadMetronome.isAlive()) {
				try {
					Thread.sleep(1000);
					if (playing) {
						playing = false;
						metronome.metronomeStop();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

}

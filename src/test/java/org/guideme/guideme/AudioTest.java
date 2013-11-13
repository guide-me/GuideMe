package org.guideme.guideme;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class AudioTest {
	public static void main(String[] args) {
		Display myDisplay = new Display();
		Shell audioShell = new AudioTestShell().createShell(myDisplay);
		audioShell.open();
		while (!audioShell.isDisposed()) {
			if (!myDisplay.readAndDispatch())
				myDisplay.sleep();
		}
	}


}

package org.guideme.guideme;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class MetronomeTest {
	public static void main(String[] args) {
		Display myDisplay = new Display();
		Shell metronomeShell = new MetronomeTestShell().createShell(myDisplay);
		metronomeShell.open();
		while (!metronomeShell.isDisposed()) {
			if (!myDisplay.readAndDispatch())
				myDisplay.sleep();
		}
	}


}

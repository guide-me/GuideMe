package org.guideme.guideme.settings;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.guideme.guideme.settings.AppSettings;
import org.guideme.guideme.settings.PreferenceShell;
import org.guideme.guideme.settings.UserSettings;

public class PreferenceShellTest {
	public PreferenceShellTest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		Display myDisplay = new Display();
		UserSettings userSettings = new UserSettings();
		AppSettings appSettings = new AppSettings();
		Shell prefShell = new PreferenceShell().createShell(myDisplay, userSettings, appSettings);
		prefShell.open();
		while (!prefShell.isDisposed()) {
			if (!myDisplay.readAndDispatch())
				myDisplay.sleep();
		}
	}

}

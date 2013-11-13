package org.guideme.guideme.settings;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.guideme.guideme.settings.AppSettings;
import org.guideme.guideme.settings.GuideSettings;
import org.guideme.guideme.ui.GuidePreferenceShell;

public class GuidePreferenceShellTest {
	public GuidePreferenceShellTest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		Display myDisplay = new Display();
		AppSettings appSettings = AppSettings.getAppSettings();
		String dataDirectory = appSettings.getDataDirectory();
		appSettings.setDataDirectory(appSettings.getUserDir());
		appSettings.saveSettings();
		GuideSettings guideSettings = new GuideSettings("GuideTest");

		Shell prefShell = new GuidePreferenceShell().createShell(myDisplay, guideSettings, appSettings);
		prefShell.open();
		while (!prefShell.isDisposed()) {
			if (!myDisplay.readAndDispatch())
				myDisplay.sleep();
		}
		appSettings.setDataDirectory(dataDirectory);
		appSettings.saveSettings();
	}

}

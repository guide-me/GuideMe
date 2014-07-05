package org.guideme.guidme.mock;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.guideme.guideme.model.Button;
import org.guideme.guideme.model.Guide;
import org.guideme.guideme.ui.MainShell;

public class MainShellMock extends MainShell {
	private Shell shell;
	private Display myDisplay;
	private Calendar calCountDown;
	private static Logger logger = LogManager.getLogger();
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
	private Calendar cal = Calendar.getInstance();
	
	
	@Override
	public Shell createShell(Display display) {
		logger.debug("MainShellMock Enter CreateShell");
		myDisplay = display;
		shell = new Shell(myDisplay);
		logger.debug("MainShellMock Exit CreateShell");
		return shell;
	}

	@Override
	public Calendar getCalCountDown() {
		logger.debug("MainShellMock Now: "  + sdf.format(cal.getTime())+ " getCalCountDown: " + sdf.format(calCountDown.getTime()));
		return calCountDown;
	}

	@Override
	public void setCalCountDown(Calendar calCountDown) {
		this.calCountDown = calCountDown;
		logger.debug("MainShellMock Now: "  + sdf.format(cal.getTime())+ " setCalCountDown:" + sdf.format(this.calCountDown.getTime()));
	}

	@Override
	public void setLblLeft(String lblLeft) {
		logger.debug("MainShellMock setLblLeft:" + lblLeft);
	}

	@Override
	public void setLblCentre(String lblCentre) {
		logger.debug("MainShellMock setLblCentre:" + lblCentre);
	}

	@Override
	public void setLblRight(String lblRight) {
		logger.debug("MainShellMock setLblRight:" + lblRight);
	}

	@Override
	public void setImageLabel(String imgPath, String strImage) {
		logger.debug("MainShellMock setImageLabel Path:" + imgPath + " Image:" + strImage);
		File f = new File(imgPath);
		if(!f.exists()){
			logger.error("setImageLabel File " + imgPath + " does not exist");
		}
	}

	@Override
	public void playVideo(String video, int startAt, int stopAt, int loops,
			String target, String jscript) {
		logger.debug("MainShellMock playVideo video:" + video + " startAt:" + startAt + " stopAt:" + stopAt + " loops:" + loops + " target:" + target + " jscript:" + jscript);
		File f = new File(video);
		if(!f.exists()){
			logger.error("playVideo File " + video + " does not exist");
		}
	}

	@Override
	public void clearImage() {
		logger.debug("MainShellMock clearImage");
	}

	@Override
	public void playAudio(String audio, int startAt, int stopAt, int loops,
			String target, String jscript) {
		logger.debug("MainShellMock playAudio audio:" + audio + " startAt:" + startAt + " stopAt:" + stopAt + " loops:" + loops + " target:" + target+ " jscript:" + jscript);
		File f = new File(audio);
		if(!f.exists()){
			logger.error("playAudio File " + audio + " does not exist");
		}
	}

	@Override
	public void setBrwsText(String brwsText, String overrideCss) {
		logger.debug("MainShellMock brwsText:" + brwsText +" CSS:" + overrideCss);
	}

	@Override
	public void removeButtons() {
		logger.debug("MainShellMock removeButtons");
	}

	@Override
	public void addDelayButton(Guide guide) {
		logger.debug("MainShellMock addDelayButton style:" + guide.getDelStyle() + " StartAtOffSet:" + guide.getDelStartAtOffSet() + " Target:" + guide.getDelTarget() + " Set:" + guide.getDelaySet() + " UnSet:" + guide.getDelayUnSet());
	}

	@Override
	public void addButton(Button button, String javascript) {
		logger.debug("MainShellMock addButton text:" + button.getText() + " Set:" + button.getSet() + " UnSet:" + button.getUnSet() + " javascript:" + javascript);
	}

	@Override
	public void layoutButtons() {
		logger.debug("MainShellMock layoutButtons");
	}

	@Override
	public void setMetronomeBPM(int metronomeBPM, int loops,
			int resolution, String Rhythm) {
		logger.debug("MainShellMock setMetronomeBPM metronomeBPM:" + metronomeBPM + " loops:" + loops + " resolution:" + resolution + " Rhythm:" + Rhythm);
	}

	@Override
	public void displayPage(String target) {
		logger.debug("MainShellMock displayPage target:" + target);
	}

	@Override
	public void stopMetronome() {
		logger.debug("MainShellMock stopMetronome");
	}

	@Override
	public void stopAudio() {
		logger.debug("MainShellMock stopAudio");
	}

	@Override
	public void stopVideo() {
		logger.debug("MainShellMock stopVideo");
	}

	@Override
	public void stopDelay() {
		logger.debug("MainShellMock stopDelay");
	}

	@Override
	public void stopAll() {
		logger.debug("MainShellMock stopAll");
	}

}

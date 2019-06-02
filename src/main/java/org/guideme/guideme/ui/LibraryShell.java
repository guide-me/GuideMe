package org.guideme.guideme.ui;

import com.snapps.swt.SquareButton;

import java.awt.Desktop;
import java.net.URI;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.guideme.guideme.model.Library;
import org.guideme.guideme.settings.AppSettings;
import org.guideme.guideme.settings.ComonFunctions;

public class LibraryShell {
	private Shell shell = null;
	private Display myDisplay;
	private static Logger logger = LogManager.getLogger();
	private Font controlFont;
	private MainShell myMainShell;
	private int pageSize = 100;
	private int originalPageSize = 100;
	private int currentStart = 0;
	private ArrayList<Library> originalGuides;
	private ArrayList<Library> guides;
	private Composite composite;
	private ScrolledComposite sc;
	private ComonFunctions comonFunctions;
	private Label paging;
	private Composite toolBar;
	private Text searchText;
	private Combo searchFilter;
	private Combo sortFilter; 
	private AppSettings appSettings;
	private int buttonCharacters;
	private String authorUrl = "https://milovana.com/webteases/?author=";
	private ResourceBundle displayText;
	private String SortByTitle;
	private String SortByAuthor;
	private String SortByDate;
	private String SearchByContent;
	private String SearchByTitle;
	private shellKeyEventListener keyListener;
	private static String searchBoxName = "SearchText";
	private ArrayList<Image> thumbs = new ArrayList<Image>();
	
	public Shell createShell(Display display, AppSettings pappSettings, MainShell mainShell) {
		logger.trace("Enter createShell");
		try {
			displayText = pappSettings.getDisplayText();
			SortByTitle = displayText.getString("FileLibrarySortTitle");
			SortByAuthor = displayText.getString("FileLibrarySortAuthor");
			SortByDate = displayText.getString("FileLibrarySortDate");
			SearchByContent = displayText.getString("FileLibrarySearchByContent");
			SearchByTitle = displayText.getString("FileLibrarySearchByTitle");
			
			comonFunctions = ComonFunctions.getComonFunctions();
			appSettings = pappSettings;

			myDisplay = display;
			myMainShell = mainShell;

			shell = new Shell(myDisplay, SWT.APPLICATION_MODAL + SWT.CLOSE);

			shell.setText(displayText.getString("FileLibraryTitle"));
			FormLayout layout = new FormLayout();
			shell.setLayout(layout);
			Font sysFont = display.getSystemFont();
			FontData[] fD = sysFont.getFontData();
			fD[0].setHeight(appSettings.getFontSize());
			controlFont = new Font(display, fD);
			
			toolBar = new Composite(shell, SWT.NONE);
			FormData tbFormData = new FormData();
			tbFormData.top = new FormAttachment(0, 5);
			tbFormData.left = new FormAttachment(0, 5);
			tbFormData.right = new FormAttachment(100, -5);
			toolBar.setLayoutData(tbFormData);
			toolBar.setLayout(new RowLayout());

			SquareButton btnGuide = new SquareButton(toolBar, SWT.PUSH);
			btnGuide.setText(displayText.getString("FileLibraryButtonPrev"));
			btnGuide.setFont(controlFont);
			btnGuide.addSelectionListener(new PrevButtonListener());

			btnGuide = new SquareButton(toolBar, SWT.PUSH);
			btnGuide.setText(displayText.getString("FileLibraryButtonNext"));
			btnGuide.setFont(controlFont);
			btnGuide.addSelectionListener(new NextButtonListener());
			
			paging = new Label(toolBar, SWT.NULL);
			paging.setFont(controlFont);

			sortFilter = new Combo(toolBar, SWT.READ_ONLY);
			sortFilter.setFont(controlFont);
			String[] sortTtems = { SortByTitle, SortByAuthor, SortByDate };
			sortFilter.setItems(sortTtems);
			sortFilter.select(0);
			sortFilter.addSelectionListener(new sortListener());
			
			searchText = new Text(toolBar, SWT.SINGLE);
			GC gc = new GC(shell);
			gc.setFont(controlFont);
			FontMetrics fm = gc.getFontMetrics();
			int pixelWidth = (display.getPrimaryMonitor().getClientArea().width - 420) / 2;
			buttonCharacters = pixelWidth / gc.getCharWidth('A');
			RowData seachData = new RowData();
			seachData.height = fm.getHeight();
			seachData.width = gc.getCharWidth('A') * 15;
			searchText.setLayoutData(seachData);
			searchText.setData(searchBoxName);
			gc.dispose();

			SquareButton btnSearch = new SquareButton(toolBar, SWT.PUSH);
			btnSearch.setText(displayText.getString("FileLibraryButtonGo"));
			btnSearch.setFont(controlFont);
			btnSearch.addSelectionListener(new SearchButtonListener());

			searchFilter = new Combo(toolBar, SWT.READ_ONLY);
			searchFilter.setFont(controlFont);
			String[] SearchItems = { SearchByContent, SearchByTitle };
			searchFilter.setItems(SearchItems);
			searchFilter.select(0);

			btnGuide = new SquareButton(toolBar, SWT.PUSH);
			btnGuide.setText(displayText.getString("FileLibraryButtonRandom"));
			btnGuide.setFont(controlFont);
			btnGuide.addSelectionListener(new RandomButtonListener());

			SquareButton btnDir = new SquareButton(toolBar, SWT.PUSH);
			btnDir.setText(displayText.getString("FileLibraryButtonFolder"));
			btnDir.setFont(controlFont);
			btnDir.addSelectionListener(new FolderButtonListener());

			sc = new ScrolledComposite(shell, SWT.V_SCROLL | SWT.BORDER);
			FormData scFormData = new FormData();
			scFormData.top = new FormAttachment(toolBar, 5);
			scFormData.left = new FormAttachment(0, 5);
			scFormData.right = new FormAttachment(100, -5);
			scFormData.bottom = new FormAttachment(100, -5);
			sc.setLayoutData(scFormData);

			composite = new Composite(sc, SWT.NONE);
			composite.setLayout(new FormLayout());

			originalGuides = comonFunctions.ListGuides(); 
			guides = originalGuides;
			setPageSize();
			sortTitle();
			shell.setMaximized(true);
			keyListener = new shellKeyEventListener();
			myDisplay.addFilter(SWT.KeyDown, keyListener);
			shell.addShellListener(new shellCloseListen());
			showGuides();
		} catch (Exception ex) {
			logger.error(ex.getLocalizedMessage());
		}
		logger.trace("Exit createShell");
		return shell;
	}

	// hotkey stuff here
	class shellKeyEventListener implements Listener {
		@Override
		public void handleEvent(Event event) {
			try {
				if (event.display.getActiveShell().getText().equals(shell.getText()))
				{
					logger.trace(event.character + "|" + event.keyCode + "|" + event.keyLocation + "|" + event.stateMask);
					if (event.keyCode == SWT.CR || event.keyCode == SWT.KEYPAD_CR) {
						search();
					}
					if ((event.stateMask & SWT.ALT) == SWT.ALT) 
					{
						switch (event.character) {
							case 'n':
							case 'N':
								NextPage();
								break;
							case 'p':
							case 'P':
								PreviousPage();
								break;
							case 't':
							case 'T':
								sortFilter.select(0);
								sortTitle();
								break;
							case 'a':
							case 'A':
								sortFilter.select(1);
								sortAuthor();
								break;
							case 'd':
							case 'D':
								sortFilter.select(2);
								sortDate();
								break;
							case 'r':
							case 'R':
								RandomGuide();
								break;
							default:
								break;
						}
					}
				}

			} catch (Exception ex) {
				logger.error(" hot key " + ex.getLocalizedMessage(), ex);
			}
		}
	}	
	
	
	private void sortAuthor() {
		Comparator<Library> comparator = Comparator.comparing(guide -> guide.author.toLowerCase());
		comparator = comparator.thenComparing(Comparator.comparing(guide -> guide.title.toLowerCase()));
		guides.sort(comparator);
		showGuides();
	}

	private void sortTitle() {
		Comparator<Library> comparator = Comparator.comparing(guide -> guide.title.toLowerCase());
		comparator = comparator.thenComparing(Comparator.comparing(guide -> guide.author.toLowerCase()));
		guides.sort(comparator);
		showGuides();
	}

	private void sortDate() {
		Comparator<Library> comparator = Comparator.comparing(guide -> guide.date);
		guides.sort(comparator.reversed());
		showGuides();
	}

	private void disposeThumbs()
	{
		for(Image thumb: thumbs)
		{
			thumb.dispose();
		}
		thumbs.clear();
	}
	
	private void showGuides() {
		disposeThumbs();
		Control[] arrayOfControl;
		int j = (arrayOfControl = composite.getChildren()).length;
		for (int i = 0; i < j; i++) {
			Control control = arrayOfControl[i];
			control.dispose();
		}
		Control lastLeftButton = composite;
		Control lastRightButton = composite;
		int pageEnd = currentStart + pageSize;
		int end = pageEnd;
		if (pageEnd > guides.size())
		{
			end = pageEnd - guides.size();
		}
		paging.setText("" + (currentStart + 1) + " to " + (end) + " of " + guides.size());
		toolBar.pack(true);
		for (int i = currentStart; i < pageEnd; i++) {
			try {
				int guidePosition = i;
				if ((guidePosition) >= guides.size())
				{
					guidePosition = guidePosition - guides.size() - 1;
				}
				Library guide = (Library) guides.get(guidePosition);
				SquareButton btnGuide = new SquareButton(composite, SWT.PUSH);
				String title = comonFunctions.splitButtonText(guide.title, buttonCharacters);
				if (guide.author.length() > 0) {
					btnGuide.setText(title + "\n(" + guide.author + ")");
				} else {
					btnGuide.setText(title);
				}
				btnGuide.setFont(controlFont);
				try
				{
					Image thumb = new Image(myDisplay, guide.image);
					thumbs.add(thumb);
					Image croppedThumb = comonFunctions.cropImageWidth(thumb,200);
					thumbs.add(croppedThumb);
					btnGuide.setImage(croppedThumb);
				} catch (Exception ex) {
					logger.error(" showGuides setImage " + ex.getLocalizedMessage());
				}
				FormData btnGuideFormData = new FormData();
				if (i % 2 == 0)
				{
					btnGuideFormData.top = new FormAttachment(lastLeftButton, 5);
					btnGuideFormData.left = new FormAttachment(0, 5);
					btnGuideFormData.right = new FormAttachment(50, -5);
					lastLeftButton = btnGuide;
				}
				else
				{
					btnGuideFormData.top = new FormAttachment(lastRightButton, 5);
					btnGuideFormData.left = new FormAttachment(50, 5);
					btnGuideFormData.right = new FormAttachment(100, -5);
					lastRightButton = btnGuide;
				}
				btnGuide.setLayoutData(btnGuideFormData);
				btnGuide.addSelectionListener(new GuideButtonListener());
				btnGuide.setData("Guide", guide.file);
				btnGuide.setData("Author", guide.author);
			} catch (Exception ex) {
				logger.error(" showGuides " + ex.getLocalizedMessage());
			}
		}
		sc.setContent(composite);
		sc.setExpandHorizontal(true);
		sc.setExpandVertical(true);
		sc.setMinSize(composite.computeSize(-1, -1));

		shell.layout();
	}

	private void setPageSize() {
		if (pageSize != originalPageSize)
		{
			pageSize = originalPageSize;
		}
		if (pageSize > guides.size())
		{
			pageSize = guides.size();
		}
		if (currentStart > guides.size())
		{
			currentStart = 0;
		}
	}

	private void sortGuides()
	{
		String selected = sortFilter.getText();
		if (selected.equals(SortByTitle)) {
			sortTitle();
		}
		if (selected.equals(SortByAuthor)) {
			sortAuthor();
		}
		if (selected.equals(SortByDate)) {
			sortDate();
		}		
	}
	
	class CancelButtonListener extends SelectionAdapter {
		CancelButtonListener() {
		}

		public void widgetSelected(SelectionEvent event) {
			try {
				logger.trace("Enter CancelButtonListener");
				shell.close();
			} catch (Exception ex) {
				logger.error(" CancelButtonListener " + ex.getLocalizedMessage());
			}
			logger.trace("Exit CancelButtonListener");
		}
	}

	class PrevButtonListener extends SelectionAdapter {
		PrevButtonListener() {
		}

		public void widgetSelected(SelectionEvent event) {
			try {
				logger.trace("Enter PrevButtonListener");
				PreviousPage();
			} catch (Exception ex) {
				logger.error(" PrevButtonListener " + ex.getLocalizedMessage());
			}
			logger.trace("Exit PrevButtonListener");
		}
	}

	public void PreviousPage()
	{
		currentStart -= pageSize;
		if (currentStart < 0) {
			currentStart = guides.size() + currentStart;
		}
		showGuides();
	}
	
	
	class NextButtonListener extends SelectionAdapter {
		NextButtonListener() {
		}

		public void widgetSelected(SelectionEvent event) {
			try {
				logger.trace("Enter NextButtonListener");
				NextPage();
			} catch (Exception ex) {
				logger.error(" NextButtonListener " + ex.getLocalizedMessage());
			}
			logger.trace("Exit NextButtonListener");
		}
	}
	
	public void NextPage()
	{
		currentStart += pageSize;
		if (currentStart >= guides.size()) {
			currentStart = (currentStart - guides.size());
		}
		showGuides();
	}
	

	class GuideButtonListener extends SelectionAdapter {
		GuideButtonListener() {
		}

		public void widgetSelected(SelectionEvent event) {
			try {
				logger.trace("Enter GuideButtonListener");
				if ((int) event.data == 1)
				{
					String guideFile = (String) event.widget.getData("Guide");
					logger.trace("Guide File:" + guideFile);
					myMainShell.loadGuide(guideFile);
					shell.close();
				}
				else
				{
					String author = (String) event.widget.getData("Author");
					author = authorUrl + author;
					if(Desktop.isDesktopSupported())
					{
					  Desktop.getDesktop().browse(new URI(author));
					}
				}
			} catch (Exception ex) {
				logger.error(" GuideButtonListener " + ex.getLocalizedMessage());
			}
			logger.trace("Exit GuideButtonListener");
		}
	}

	class RandomButtonListener extends SelectionAdapter {
		RandomButtonListener() {
		}

		public void widgetSelected(SelectionEvent event) {
			try {
				logger.trace("Enter RandomButtonListener");
				RandomGuide();
			} catch (Exception ex) {
				logger.error(" RandomButtonListener " + ex.getLocalizedMessage());
			}
			logger.trace("Exit RandomButtonListener");
		}
	}
	
	public void RandomGuide()
	{
		int randNo = comonFunctions.getRandom(0, guides.size() - 1);
		String guideFile = ((Library) guides.get(randNo)).file;
		logger.trace("Guide File:" + guideFile);
		myMainShell.loadGuide(guideFile);
		shell.close();
	}

	class sortListener extends SelectionAdapter {
		sortListener() {
		}

		public void widgetSelected(SelectionEvent event) {
			sortGuides();
		}
	}

	class SearchButtonListener extends SelectionAdapter {
		SearchButtonListener() {
		}

		public void widgetSelected(SelectionEvent event) {
			logger.trace("Enter SearchButtonListener");
			search();
			logger.trace("Exit SearchButtonListener");
		}

	}
	
	private void search() {
		try {
			String selected = searchFilter.getText();

			guides = new ArrayList<Library>();
			for (Library guide : originalGuides) {
				if (selected.equals(SearchByTitle)) {
					if (comonFunctions.searchText(searchText.getText(), guide.author + guide.title)) {
						guides.add(guide);
					}
				}
				if (selected.equals(SearchByContent)) {
					if (comonFunctions.searchGuide(searchText.getText(), guide.file)) {
						guides.add(guide);
					}
				}
			}
			setPageSize();
			showGuides();
		} catch (Exception ex) {
			logger.error(" SearchButtonListener " + ex.getLocalizedMessage());
		}
	}

	class FolderButtonListener extends SelectionAdapter {
		FolderButtonListener() {
		}

		public void widgetSelected(SelectionEvent event) {
			try {
				String folder = appSettings.getDataDirectory();
				DirectoryDialog dirDialog = new DirectoryDialog(shell);
				dirDialog.setFilterPath(folder);
				dirDialog.setMessage("Select a Folder to scan");
				folder = dirDialog.open();
				if (folder != null)
				{
					appSettings.setDataDirectory(folder);
					appSettings.saveSettings();
					originalGuides = comonFunctions.ListGuides(); 
					guides = originalGuides;
					setPageSize();
					sortGuides();
					showGuides();				
				}
			} catch (Exception ex) {
				logger.error(" FolderButtonListener " + ex.getLocalizedMessage());
			}
			logger.trace("Exit FolderButtonListener");
		}

	}

	class shellCloseListen  extends ShellAdapter {
		// Clean up stuff when the application closes
		@Override
		public void shellClosed(ShellEvent e) {
			try {
				disposeThumbs();
				controlFont.dispose();
			}
			catch (Exception ex) {
				logger.error("shellCloseListen ", ex);
			}
			super.shellClosed(e);
		}

		public void handleEvent(Event event) {
		}
	}
	

}

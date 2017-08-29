package hr.fer.zemris.java.hw10.jnotepadpp;

import hr.fer.zemris.java.hw10.jnotepadpp.actions.AscendingAction;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.CloseDocumentAction;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.CopyAction;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.CreateNewDocumentAction;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.CroatianLanguage;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.CutAction;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.DescendingAction;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.DeutschLanguage;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.EnglishLanguage;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.Exit;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.InvertCaseAction;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.OpenDocumentAction;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.PasteAction;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.SaveAsDocumentAction;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.SaveDocumentAction;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.StatisticsAction;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.ToLoweCaseAction;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.ToUpperCaseAction;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.UniqueAction;
import hr.fer.zemris.java.hw10.jnotepadpp.i18n.FormLocalizationProvider;
import hr.fer.zemris.java.hw10.jnotepadpp.i18n.ILocalizationListener;
import hr.fer.zemris.java.hw10.jnotepadpp.i18n.LJMenu;
import hr.fer.zemris.java.hw10.jnotepadpp.i18n.LocalizationProvider;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Collator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;

/**
 * JNotepad++ represents simple text editor that supports multiplie languages; Croatian, English
 * and German. Have basic editing tools and supports opening files from disk.
 * @author Borna
 *
 */
public class JNotepadPP extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTabbedPane tabbedPane;
	private Action openDocumentAction;
	private Action saveAsDocumentAction;
	private Action saveDocumentAction;
	private Action createNewDocumentAction;
	private Action copyAction;
	private Action cutAction;
	private Action pasteAction;
	private Action closeTabAction;
	private Action exitAction;
	private Action statisticsAction;
	private JMenu changeCase;
	private JLabel length;
	private JLabel caretStats;
	private FormLocalizationProvider flp;
	private JLabel langIcon;
	
	private int counter;
	private boolean stopRequested;
	private List<Tab> tabs;
	private String buffer;
	private HashMap<String, ImageIcon> icons;
	
	/**
	 * Constructs frame with title, creates actions and initialize GUI
	 */
	public JNotepadPP() {
		setLocation(100, 100);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		initVars();
		createActions();
		setTitle(flp.getString("newDocument") + (counter + 1) + " - JNotepad++");
		initGUI();
	}
	
	/**
	 * Initialize variables and puts frequently used icons in icon map.
	 */
	private void initVars() {
		flp = new FormLocalizationProvider(LocalizationProvider.getInstance(),
				this);
		tabs = new ArrayList<Tab>();
		buffer = "";
		counter = 0;
		icons = new HashMap<String, ImageIcon>();
		icons.put("modified", new ImageIcon("icons/red_save.png"));
		icons.put("unmodified", new ImageIcon("icons/blue_save.png"));
		icons.put("en", new ImageIcon("icons/flags/english.png"));
		icons.put("de", new ImageIcon("icons/flags/german.png"));
		icons.put("hr", new ImageIcon("icons/flags/croatian.png"));
		icons.put("close", new ImageIcon("icons/close.png"));

	}
	
	/**
	 * Creates actions for editor
	 */
	private void createActions() {
		openDocumentAction = new OpenDocumentAction(this, flp);
		saveAsDocumentAction = new SaveAsDocumentAction(this, flp);
		saveDocumentAction = new SaveDocumentAction(this, flp);
		createNewDocumentAction = new CreateNewDocumentAction(this, flp);
		copyAction = new CopyAction(this, flp);
		cutAction = new CutAction(this, flp);
		pasteAction = new PasteAction(this, flp);
		closeTabAction = new CloseDocumentAction(this, flp);
		exitAction = new Exit(this, flp);
		statisticsAction = new StatisticsAction(this, flp);
		 
	}
	
	/**
	 * Creates toolbar, menus and tabs
	 */
	private void initGUI() {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException ignorable) {
		}
		
		UIManager.getDefaults().put("TextArea.font", UIManager.getFont("TextField.font"));
		setLayout(new BorderLayout());
		/**
		 * Window adapter for stoping time thread
		 */
		addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent e) {
				if(JNotepadPP.this.checkModifiedDocuments()){
					stopRequested = true;
					dispose();
				}
			}
		});
		
		createToolbar();		
		createMenus();
		createTabs();
		createStatusBar();
		setDimensions(400.0);

	}
	
	
	/**
	 * Cheks are there modified documents and ask for saving them before exiting
	 * aplication.
	 * 
	 * @return true if aplication will be closed, otherwise false
	 */
	private boolean checkModifiedDocuments() {
		int size = tabs.size();
		for (int i = 0; i < size; i++) {
			if (!closeSelectedTab()) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Create menus with actions
	 */
	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new LJMenu("file", flp);
		fileMenu.add(new JMenuItem(openDocumentAction));
		fileMenu.add(new JMenuItem(createNewDocumentAction));	
		fileMenu.add(new JMenuItem(saveAsDocumentAction));
		fileMenu.add(new JMenuItem(saveDocumentAction));
		fileMenu.add(new JMenuItem(closeTabAction));
		fileMenu.add(new JMenuItem(statisticsAction));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(exitAction));
		menuBar.add(fileMenu);

		
		JMenu editMenu = new LJMenu("edit", flp);
		editMenu.add(new JMenuItem(copyAction));
		editMenu.add(new JMenuItem(cutAction));
		editMenu.add(new JMenuItem(pasteAction));
		menuBar.add(editMenu);
		
		JMenu lang = new LJMenu("lang", flp);
		lang.add(new JMenuItem(new CroatianLanguage(this, flp)));
		lang.add(new JMenuItem(new EnglishLanguage(this, flp)));
		lang.add(new JMenuItem(new DeutschLanguage(this, flp)));
		menuBar.add(lang);
		
		JMenu tools = new LJMenu("tools", flp);
	    changeCase = new LJMenu("changeCase", flp);
	    changeCase.setToolTipText(flp.getString("changeCaseTT"));
	    
	    ILocalizationListener listener = () -> {
		    changeCase.setToolTipText(flp.getString("changeCaseTT"));

		};
		
		flp.addLocalizationListener(listener);
		tools.add(changeCase);
		changeCase.add(new JMenuItem (new InvertCaseAction(this, flp)));
		changeCase.add(new JMenuItem (new ToUpperCaseAction(this, flp)));
		changeCase.add(new JMenuItem (new ToLoweCaseAction(this, flp)));
		JMenu sort = new LJMenu("sort", flp);
		tools.add(sort);
		sort.add(new JMenuItem(new AscendingAction(this, flp)));
		sort.add(new JMenuItem(new DescendingAction(this, flp)));
		
		tools.add(new JMenuItem(new UniqueAction(this, flp)));
		menuBar.add(tools);
	
		setJMenuBar(menuBar);

		
	}
	
	/**
	 * Creates toolbar with actions and given icons
	 */
	private JToolBar createToolbar() {
		JToolBar toolbar = new JToolBar(flp.getString("tools"));
		toolbar.setFloatable(true);
		
		JButton button = new JButton(openDocumentAction);
		button.setIcon(new ImageIcon("icons/toolbar/openFile.png"));
		toolbar.add(button);
		
		button = new JButton(createNewDocumentAction);
		button.setIcon(new ImageIcon("icons/toolbar/newFile.png"));
		toolbar.add(button);
		
		button = new JButton(saveAsDocumentAction);
		button.setIcon(new ImageIcon("icons/toolbar/saveFile.png"));
		toolbar.add(button);
		
		button = new JButton(saveDocumentAction);
		button.setIcon(new ImageIcon("icons/toolbar/saveFile.png"));
		toolbar.add(button);
		
		button = new JButton(closeTabAction);
		button.setIcon(new ImageIcon("icons/toolbar/closeFile.png"));
		toolbar.add(button);
		
		button = new JButton(statisticsAction);
		button.setIcon(new ImageIcon("icons/toolbar/statistics.png"));
		toolbar.add(button);
		
		button = new JButton(exitAction);
		button.setIcon(new ImageIcon("icons/toolbar/closeTab.png"));
		toolbar.add(button);

		toolbar.addSeparator();
		button = new JButton(copyAction);
		button.setIcon(new ImageIcon("icons/toolbar/copy.png"));
		toolbar.add(button);
		
		button = new JButton(cutAction);
		button.setIcon(new ImageIcon("icons/toolbar/cut.png"));
		toolbar.add(button);
		
		button = new JButton(pasteAction);
		button.setIcon(new ImageIcon("icons/toolbar/paste.png"));
		toolbar.add(button);

		langIcon = new JLabel(icons.get("en"));
		langIcon.setToolTipText(flp.getString("currentLanguage")+" "+flp.getString(
				LocalizationProvider.getInstance().getLanguage()));
		toolbar.add(langIcon);
		getContentPane().add(toolbar, BorderLayout.PAGE_START);
		
		return toolbar;
	}
	
	/**
	 * Creates tabs and initialize Localization listener, and change title listeners on tab
	 */
	private void createTabs() {
		tabbedPane = new JTabbedPane();
		createTab(null);
		
		ILocalizationListener changeTabName = () -> {
			Pattern pattern = Pattern.compile("\\d+");
			for (Tab tab : tabs) {
				if(tab.getOpenedFilePath() != null){
					continue;
				}
				Matcher matcher = pattern.matcher(tab.getName().getText());
				matcher.find();

				String number = matcher.group();
				tab.getName().setText(flp.getString("newDocument") + number);

			}
			JTextArea editor = getSelectedTab().getEditor();
			JNotepadPP.this.setStatusBar(length, caretStats, editor);
		};
		flp.addLocalizationListener(changeTabName);

		ILocalizationListener changeTitle = () -> {
			JNotepadPP.this.changeTitle();

		};
		flp.addLocalizationListener(changeTitle);

		tabbedPane.addChangeListener((o) -> {
			JNotepadPP.this.changeTitle();
			JTextArea editor = getSelectedTab().getEditor();
			JNotepadPP.this.setStatusBar(length, caretStats, editor);
		});
		
		JPanel container = new JPanel(new BorderLayout());
		container.add(tabbedPane,BorderLayout.CENTER);
		JToolBar toolbar = createToolbar();
		container.add(toolbar,BorderLayout.PAGE_START);
		getContentPane().add(container);
	}
	
	/**
	 * Changes title depending on opened file path
	 */
	private void changeTitle(){
		Tab tab = getSelectedTab();
		Path filePath=tab.getOpenedFilePath();
		if(filePath == null){					
			setTitle(tab.getName().getText() +
					" - JNotepad++");

		}else{
			setTitle(filePath.toString()+" - JNotepad++");
		}
	}
	
	/**
	 * Creates tab with given path file name
	 * 
	 * @param openedFilePath
	 *            tabs path is set to this path
	 * @return created tab
	 */
	private Tab createTab(Path openedFilePath) {

		counter++;
		JPanel container = new JPanel(new BorderLayout());

		JTextArea editor = new JTextArea();
		editor.getDocument().addUndoableEditListener(
				(e) -> {
					Tab selectedTab = JNotepadPP.this.getSelectedTab();
					if (!selectedTab.isModified()) {
						selectedTab.getIcon().setIcon(
								icons.get("modified"));
						selectedTab.setisModified(true);
					}

				});
		
		editor.addCaretListener((e)-> {
			setStatusBar(length, caretStats, editor);
		});
					
		JPanel tabPanel = new JPanel(new FlowLayout());
		JLabel name = new JLabel();
		//seting tab name
		tabbedPane.addTab(flp.getString("newDocument") + counter, container);
		if (openedFilePath != null) {
			name.setText(openedFilePath.getFileName().toString());
			tabPanel.setName(openedFilePath.toAbsolutePath().toString());
			
		} else {
			name.setText(flp.getString("newDocument") + counter);
		}
		
		JLabel iconIm = new JLabel(icons.get("unmodified"));
		
		tabPanel.add(iconIm);
		tabPanel.add(name);
		JButton close = new CloseTabButton(container, icons.get("close"));		
		close.setFocusPainted(false);
		close.addActionListener(closeAction);
		tabPanel.add(close);
		tabPanel.setOpaque(false);
		int index = tabbedPane.getTabCount() - 1 ;
		tabbedPane.setTabComponentAt(index, tabPanel);

		Tab tab = new Tab(openedFilePath, editor, name, iconIm);
		tabs.add(tab);
		JScrollPane scroll = new JScrollPane(tab.getEditor());
		container.add(scroll, BorderLayout.CENTER);
		
		
		tabbedPane.setSelectedIndex(index);
		return tab;
		
	}
	
	/**
	 * Action for closing tab button
	 */
	ActionListener closeAction = (e)->{
		CloseTabButton button = (CloseTabButton) e.getSource();
		tabbedPane.setSelectedComponent(button.getParentComp());
		closeSelectedTab();
	};
	
	/**
	 * Class extendes {@link JButton} and have special future. Remember
	 * reference to container on which tab this button is added.
	 * 
	 * @author Borna
	 *
	 */
	class CloseTabButton extends JButton{
	
		private static final long serialVersionUID = 1L;
		JPanel parent;

		/**
		 * 
		 * @param container
		 *            parent component
		 * @param icon
		 *            button icon
		 */
		public CloseTabButton(JPanel container, ImageIcon icon) {
			super(icon);
			this.parent = container;
			setSize(10, 10);
			setPreferredSize(getSize());
		}

		/**
		 * Returns parent component
		 * 
		 * @return parent component
		 */
		public JPanel getParentComp() {
			return parent;
		}
		
	}
	
	/**
	 * Creates time thread for clock on status bar
	 * 
	 * @param timeDate
	 *            reference on {@link JLabel} where time value is writen
	 */
	private void createTimeThread(JLabel timeDate){
		Thread t = new Thread(() -> {
			
			while (true) {
				SwingUtilities.invokeLater(() -> {
					
					timeDate.setText(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
					.format(new Date()));
				});

				try {
					Thread.sleep(500);
				} catch (Exception ignorable) {
				}
				if (stopRequested) {
					break;
				}
			}
		});
		t.setDaemon(true);
		t.start();
	}
	
	/**
	 * Creates status bar that showes document length, column and row of cursor
	 * and length of selected text
	 * 
	 * @return reference on status bar for adding it in container
	 */
	private void createStatusBar(){
		JPanel statusBar = new JPanel();
		statusBar.setLayout(new GridLayout(1, 0));
		length = new JLabel(flp.getString("lengthInit"));
		length.setBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createMatteBorder(0, 1, 0, 0,Color.LIGHT_GRAY),
				BorderFactory.createEmptyBorder(2, 5, 2, 2)));
		statusBar.add(length);

		caretStats = new JLabel(flp.getString("caretStats"));
		caretStats.setBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createMatteBorder(0, 1, 0, 0,Color.LIGHT_GRAY),
				BorderFactory.createEmptyBorder(2, 5, 2, 2)));
				
		statusBar.add(caretStats);

		JLabel timeDate = new JLabel();
		timeDate.setBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createMatteBorder(0, 1, 0, 0,Color.LIGHT_GRAY),
				BorderFactory.createEmptyBorder(2, 5, 2, 2)));
		timeDate.setHorizontalAlignment(SwingConstants.RIGHT);
		createTimeThread(timeDate);
		statusBar.add(timeDate);
		statusBar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0,
				Color.BLACK));

		setStatusBar(length, caretStats, getSelectedTab().getEditor());
		getContentPane().add(statusBar, BorderLayout.PAGE_END);

	}
	/**
	 * Sets status bar value
	 * 
	 * @param length
	 *            referencee on length label
	 * @param caretStats
	 *            reference on caretStatus label
	 * @param editor
	 *            reference on editor of which data is used
	 */
	private void setStatusBar(JLabel length, JLabel caretStats, JTextArea editor){
		String text = editor.getText();
		
		int line = 1;
		int col = 1;
		int caretPos = editor.getCaretPosition();
		try {
			line = editor.getLineOfOffset(caretPos);
		    Element map = editor.getDocument().getDefaultRootElement();
	        Element lineElem = map.getElement(line);
	        col = caretPos - lineElem.getStartOffset();
		} catch (BadLocationException ignorable) {
		}
		
		String selected =  editor.getSelectedText();
		int sel;
		if (selected == null) {
			sel = 0;
			if(changeCase.isEnabled()){
				changeCase.setEnabled(false);
			}
			
		} else {
			sel = selected.length();
			if(!changeCase.isEnabled()){
				changeCase.setEnabled(true);
			}
			
		}
		
		length.setText(flp.getString("length")+ ":" + text.length());
		caretStats.setText(flp.getString("ln") + ":" + (line + 1) + "  "
				+ flp.getString("col") + ":" + (col + 1) + "  "
				+ flp.getString("sel") + ":" + sel);
		
	}
	
	/**
	 * Return sellected {@link Tab} 
	 * @return
	 */
	public Tab  getSelectedTab(){
		int selectedIndex = tabbedPane.getSelectedIndex();
		return tabs.get(selectedIndex);
        
	}
	
	/**
	 * Creates new blank document
	 */
	public void createNewBlankDoc(){
		createTab(null);
	}
	
	/**
	 * Save current selected document on disk.
	 */
	public void saveFile(){
        
        Tab selectedTab= getSelectedTab();
        
        Path openedFilePath = selectedTab.getOpenedFilePath();
		if (openedFilePath == null) {
			saveAsFile();
			return;
		}		
        writeDocumentOnDisk();
	}
	
	/**
	 * Saves current document on disk as new file.
	 */
	public void saveAsFile(){
			
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle(flp.getString("saveDocuments"));
			
			if (fc.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			Path file = fc.getSelectedFile().toPath();

			if (Files.exists(file)) {
				int r = JOptionPane
						.showConfirmDialog(
								this,
								flp.getString("file")+" "
										+ file
										+ flp.getString("overwrite"),
								flp.getString("confirmSaveAs"), JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE);
				if (r != JOptionPane.YES_OPTION) {
					return;
				}
			}
			
		Tab selectedTab = getSelectedTab();
		selectedTab.setOpenedFilePath(file);
		int selectedIndex = tabbedPane.getSelectedIndex();
		selectedTab.getName().setText(file.getFileName().toString());
		tabbedPane.setToolTipTextAt(selectedIndex, file.toString());
		setTitle(file.toString() + " - JNotepad++");
		writeDocumentOnDisk();

	}
	
	/**
	 * Writes document on disk and sets tab to unmodified
	 */
	private void writeDocumentOnDisk() {
		Tab selectedTab = getSelectedTab();
		Path openedFilePath = selectedTab.getOpenedFilePath();
		
		try {
			Files.write(openedFilePath, selectedTab.getEditor().getText()
					.getBytes(StandardCharsets.UTF_8));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(),
					flp.getString("error"), JOptionPane.ERROR_MESSAGE);
			return;
		}
		selectedTab.setisModified(false);
		selectedTab.getIcon().setIcon(icons.get("unmodified"));
	}
	
	/**
	 * Open and load file in tab. If current tab is not modified document is loaded in him,
	 * otherwise in new tab.
	 */
	public void openFile() {
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle(flp.getString("openFile"));
		if (fc.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
			return;
		}

		Path openedFilePath = fc.getSelectedFile().toPath();

		if (!Files.isReadable(openedFilePath)) {
			JOptionPane.showMessageDialog(this, flp.getString("file") + " " + openedFilePath
					+ " " + flp.getString("notExist"), flp.getString("error"),
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		if(isAlreadyOpened(openedFilePath)){
			return;
		}
		
		byte[] bytes = null;

		try {
			bytes = Files.readAllBytes(openedFilePath);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), flp.getString("error"),
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		Tab selectedTab = getSelectedTab();
		Tab tab;
		if (selectedTab.getOpenedFilePath() == null
				&& !selectedTab.isModified()) {
			selectedTab.getName().setText(openedFilePath.getFileName().toString());
			selectedTab.setOpenedFilePath(openedFilePath);
			tab = selectedTab;
		}else{
			 tab = createTab(openedFilePath);
		}
		
		tab.getEditor().setText(new String(bytes, StandardCharsets.UTF_8));
		tab.setisModified(false);
		tab.getIcon().setIcon(
				icons.get("unmodified"));
		tabbedPane.setToolTipTextAt(tabbedPane.getSelectedIndex(), 
				openedFilePath.toString());

	}

	/**
	 * Returns true if tab with given file path is already opened and selectes
	 * tab with the same file path.
	 * 
	 * @param openedFilePath
	 *            given file path
	 * @return true if tab with given file path is already opened, otherwise
	 *         false
	 */
	private boolean isAlreadyOpened(Path openedFilePath){
		int tabCoun = 0;
		for(Tab tab : tabs){
			Path path = tab.getOpenedFilePath();
			if(path != null && path.equals(openedFilePath)){
				String message = flp.getString("tabAlreadyOpened");
				message = message.replaceAll(":0:", path.toString());
				JOptionPane.showConfirmDialog(this, message, 
						flp.getString("warning"), JOptionPane.PLAIN_MESSAGE,
						JOptionPane.INFORMATION_MESSAGE);
				tabbedPane.setSelectedIndex(tabCoun);
				return true;
			}
			tabCoun++;

		}
		return false;
	}
	
	/**
	 * Copies text to buffer and deletes selected text
	 */
	public void cutText(){
		copyTextToBuffer(true);
	}
	
	/**
	 * Copies selected text in buffer
	 */
	public void copyText(){
		copyTextToBuffer(false);
	}
	
	/**
	 * Copies selected text in buffer and if deleteSelected is true deletes
	 * selected text from document, otherwise not
	 * 
	 * @param deleteSelected
	 *            if true deletes selected text from document, otherwise not
	 */
	private void copyTextToBuffer(boolean deleteSelected){
		Tab selectedTab= getSelectedTab();
		JTextArea editor=selectedTab.getEditor();
		Document doc = editor.getDocument();
		
		
		int offset = Math.min(editor.getCaret().getDot(), editor.getCaret()
				.getMark());
		int length = Math.abs(editor.getCaret().getDot()
				- editor.getCaret().getMark());
		
		if (length == 0) {
			return;
		}

		try {
			buffer = doc.getText(offset, length);
			if(deleteSelected){
				doc.remove(offset, length);
			}
		} catch (BadLocationException ignorable) {		
		}
		
		
	}
	
	/**
	 * Closes selected tab. If tab is modified user is asked for saving
	 * document. In that case user can abort closing.
	 * 
	 * @return true if tab is closed, otherwise false
	 */
	public boolean closeSelectedTab(){
		
			Tab selectedTab= getSelectedTab();
			if(selectedTab.isModified()){
				int index= getTitle().lastIndexOf(" -");
				String message = flp.getString("saveFile")+" \""
				+getTitle().substring(0,index)+"\"?";
				
				int r = JOptionPane
						.showConfirmDialog(
								this,
								message,
								flp.getString("warning"), JOptionPane.YES_NO_CANCEL_OPTION,
								JOptionPane.QUESTION_MESSAGE);
				if (r == JOptionPane.CANCEL_OPTION || r == JOptionPane.CLOSED_OPTION) {
					return false;
					
				}else if(r == JOptionPane.YES_OPTION){
					saveFile();					
				}
			} else if (tabbedPane.getTabCount() == 1 && selectedTab.getOpenedFilePath() == null) {
				//one tab must be always opened, true is returned because of 
				// checkModifiedDocuments() method
				return true;

			}
			
			int indexToDelete = tabbedPane.getSelectedIndex();
			if(tabbedPane.getTabCount() == 1){
				createTab(null);
			}
			
			tabs.remove(selectedTab);
			tabbedPane.remove(indexToDelete);
			return true;
						
	}
	
	/**
	 * Showes document statistics in new frame. Statistics are document length,
	 * number of non blan characters and number of lines.
	 */
	public void statistics(){
		Tab selectedTab= getSelectedTab();
		String text = selectedTab.getEditor().getText();
		int nonBlank = 0;
		int lines = 1;
		for(int i = 0; i < text.length(); i++){
			//non blank characters
			if(!String.valueOf(text.charAt(i)).matches("\\s")){
				nonBlank++;
			}
			
			if(text.charAt(i) == '\n'){
				lines++;
			}
		}
		
		int index = getTitle().lastIndexOf(" -");
		String message = flp.getString("statisticsMess");
		
		message = message.replaceAll(":0:",getTitle().substring(0, index))
				.replaceAll(":1:", Integer.valueOf(text.length()).toString())
				.replaceAll(":2:", Integer.valueOf(nonBlank).toString())
				.replaceAll(":3:", Integer.valueOf(lines).toString());
		
		JOptionPane
				.showConfirmDialog(
						this,
						message,
						flp.getString("statistics"), JOptionPane.PLAIN_MESSAGE,
						JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * Exits program. Ask user to save modified files
	 */
	public void exit(){
		if(checkModifiedDocuments()){
			stopRequested = true;
			dispose();
		}
	}

	/**
	 * Interface used for strategy pattern 
	 * @author Borna
	 *
	 */
	interface ChangeDoc{
		
		/**
		 * Selected text is transformed in new value and that new value is returned
		 * @param selected selected text
		 * @return new transformed value
		 */
		String changeText(String selected);
	}
	
	/**
	 * Inserts new String on start of selection and removes selection
	 * @param changer {@link ChangeDoc} returns new String
	 */
	private void insertNewString(ChangeDoc changer) {
		Tab selectedTab = getSelectedTab();
		JTextArea editor = selectedTab.getEditor();
		Document doc = editor.getDocument();

		int offset = Math.min(editor.getCaret().getDot(), editor.getCaret()
				.getMark());
		String selectedText = editor.getSelectedText();
		int length;
		if(selectedText == null){
			length = 0;
			
		} else {
			length = selectedText.length();

		}
		try {
			doc.remove(offset, length );
			doc.insertString(offset, changer.changeText(selectedText), null);
		} catch (BadLocationException ignorable) {
		}
	}
	
	/**
	 * Insert buffer text instead selected text
	 */
	public void pasteText(){
		insertNewString((s) -> buffer);
	}
	
	/**
	 * Inserts new string with inverted case instead selected text
	 */
	public void invertCase(){
		insertNewString((s) -> toggleCase(s));
	}
	
	/**
	 * Inserts new string with upper case instead selected text
	 */
	public void toUpperCase(){
		insertNewString((s) -> s.toUpperCase());
		
	}
	
	/**
	 * Inserts new string with lower case instead selected text
	 */
	public void toLowerCase(){
		insertNewString((s) -> s.toLowerCase());

	}
	
	/**
	 * Inverts given string case.
	 * 
	 * @param text
	 *            given string
	 * @return new string with inverted case
	 */
	private String toggleCase(String text) {
		char[] znakovi = text.toCharArray();
		for (int i = 0; i < znakovi.length; i++) {
			if (Character.isUpperCase(znakovi[i])) {
				znakovi[i] = Character.toLowerCase(znakovi[i]);
			} else if (Character.isLowerCase(znakovi[i])) {
				znakovi[i] = Character.toUpperCase(znakovi[i]);
			}
		}

		return new String(znakovi);
	}
	
	/**
	 * Method deletes selected lines and inserts new string
	 * 
	 * @param changer
	 *            string that changeText return is inserted
	 */
	private void changeSelLines(ChangeDoc changer){
		Tab selectedTab = getSelectedTab();
		JTextArea editor = selectedTab.getEditor();
		
		if(editor.getSelectedText() == null){
			return;
		}
		
		int offset;
		int endOffset;
		if(editor.getSelectedText() == null){
			offset = endOffset = editor.getCaretPosition();
		}else{
		 offset = Math.min(editor.getCaret().getDot(), editor.getCaret()
				.getMark());
		 endOffset = Math.max(editor.getCaret().getDot(), editor.getCaret()
					.getMark());
		}
		
		int startLine = 0;
		int endLine = 0;
		try {
			startLine = editor.getLineOfOffset(offset);
			endLine = editor.getLineOfOffset(endOffset);
		} catch (BadLocationException ignorable) {
		}
		
	    Element map = editor.getDocument().getDefaultRootElement();
        Element startLineElem = map.getElement(startLine);
		int lineStart = startLineElem.getStartOffset();
		
		Element endLineElem = map.getElement(endLine);
		int lineEnd = endLineElem.getEndOffset();		
		try {
			String text = editor.getText(lineStart, lineEnd
					- lineStart);
			//because \n is always added
			text = text.substring(0, text.length() - 1);
			String newText = changer.changeText(text);
			Document doc = editor.getDocument();
			doc.remove(lineStart, text.length());
			doc.insertString(lineStart, newText, null);
		} catch (BadLocationException ignorable) {
			ignorable.printStackTrace();
		}
	}
	
	/**
	 * Sort selected lines ascending
	 */
	public void sortAscending(){
		changeSelLines((s) -> sortText(s, true));
	}
	
	/**
	 * Sort selected lines descending
	 */
	public void sortDescending(){
		changeSelLines((s) -> sortText(s, false));
	}
	
	/**
	 * Sortes selected text ascending if isAscending is true, otherwise sort
	 * descending.
	 * 
	 * @param text
	 *            text to be sorted
	 * @param isAscending
	 *            if true text is sorted ascending, otherwise descending
	 * @return new one lined sorted string
	 */
	private String sortText(String text, boolean isAscending) {
		
		if(text.isEmpty()){
			return text;
		}
		
		String[] lines = text.split("\n");		
		List<String> wordsList = Arrays.asList(lines);
		Locale locale = new Locale(LocalizationProvider.getInstance()
				.getLanguage());

		Comparator<Object> collator = Collator.getInstance(locale);
		collator = (isAscending ? collator : collator.reversed());
		Collections.sort(wordsList, collator);
		
		StringBuilder strBl = new StringBuilder();
		for (String str : wordsList) {
			strBl.append(str).append("\n");
		}
		int length = strBl.length();
		strBl.replace(length - 1, length, "");

		return strBl.toString();
	}
	
	/**
	 * Deletes multiplied lines
	 */
	public void unique(){
		changeSelLines((s) -> removeDuplicatedLines(s));		
		
	}
	
	/**
	 * Return new string that does not contains duplicates
	 * 
	 * @param text
	 *            string from which duplicates are removed
	 * @return new string without duplicates
	 */
	private String removeDuplicatedLines(String text) {
		if(text.isEmpty()){
			return text;
		}
		
		text += "\n";
		String[] lines = text.split("\n");
		for (int i = 0; i < lines.length; i++) {
			for (int j = i + 1; j < lines.length; j++) {
				if (lines[i] == null) {
					continue;
				}

				if (lines[i].equals(lines[j])) {
					lines[j] = null;
				}
			}
		}

		StringBuilder builder = new StringBuilder();
		for (String str : lines) {
			if (str != null) {
				builder.append(str).append("\n");
			}
		}
		int length = builder.length();
		builder.replace(length - 1, length, "");
		
		return builder.toString();
	}
	
	/**
	 * Sets new program language
	 * 
	 * @param lang
	 *            new langugae
	 */
	public void setLanguage(String lang){
		LocalizationProvider.getInstance().setLanguage(lang);
		
		//change language icon
		langIcon.setIcon(icons.get(lang));
		langIcon.setToolTipText(flp.getString("currentLanguage")+" "+flp.getString(
				LocalizationProvider.getInstance().getLanguage()));
		//change window dimensions to fit new language
		setDimensions(getSize().getHeight());
	}
	
	/**
	 * Sets frame dimensions on preffered width and given height
	 * 
	 * @param height
	 *            given height
	 */
	private void setDimensions(Double height){
		int space = 20;
		setSize(new Dimension(Double.valueOf(getPreferredSize().getWidth() + space).intValue(), 
				height.intValue()));
	}
	
	/**
	 * Main method
	 * 
	 * @param args
	 *            main method arguments
	 */
	public static void main(String[] args) {

		SwingUtilities.invokeLater(() -> {
			JFrame jNotepadPP = new JNotepadPP();
			jNotepadPP.setVisible(true);
		});
	}

}

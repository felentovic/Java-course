package hr.fer.zemris.java.hw10.jnotepadpp;

import java.nio.file.Path;

import javax.swing.JLabel;
import javax.swing.JTextArea;

public class Tab {
	private Path openedFilePath;
	private JTextArea editor;
	private boolean isModified;
	private JLabel name;
	private JLabel icon;

	public Tab(Path openedFilePath, JTextArea editor, JLabel name, JLabel icon) {
		super();
		this.openedFilePath = openedFilePath;
		this.editor = editor;
		this.name = name;
		this.icon = icon;
		isModified = false;
	}

	public JLabel getIcon() {
		return icon;
	}

	public JLabel getName() {
		return name;
	}

	public Path getOpenedFilePath() {
		return openedFilePath;
	}

	public JTextArea getEditor() {
		return editor;
	}

	public boolean isModified() {
		return isModified;
	}

	public void setisModified(boolean isModified) {
		this.isModified = isModified;
	}

	public void setOpenedFilePath(Path openedFilePath) {
		this.openedFilePath = openedFilePath;
	}

}

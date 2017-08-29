package hr.fer.zemris.java.hw12.jvdraw.components.actions;

import hr.fer.zemris.java.hw12.jvdraw.components.JVDraw;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Class extends {@link AbstractAction} and represents open action. Opens file
 * chooser and selected file if it have {@code .jvd} extension. And adds objects
 * parsed from file to model.
 * 
 * @author Borna
 *
 */
public class OpenAction extends AbstractAction {

	/**
	 * default serial
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * reference to {@link JVDraw}
	 */
	private JVDraw jvdraw;

	/**
	 * Constructs action with name {@code "Open"}
	 * 
	 * @param jvdraw
	 *            reference
	 */
	public OpenAction(JVDraw jvdraw) {
		putValue(Action.NAME, "Open");
		this.jvdraw = jvdraw;

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		if (jvdraw.isModified && !jvdraw.askToSave()) {
			return;
		}

		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(Paths.get("./").toFile());
		FileNameExtensionFilter jvdfilter = new FileNameExtensionFilter(
				"jvd files (*.jvd)", "jvd");
		fc.setFileFilter(jvdfilter);
		fc.setDialogTitle("Open file");
		if (fc.showOpenDialog(jvdraw) != JFileChooser.APPROVE_OPTION) {
			return;
		}
		Path openedFilePath = fc.getSelectedFile().toPath();
		if (!Files.isReadable(openedFilePath)
				|| !openedFilePath.toString().endsWith(".jvd")) {
			JOptionPane.showMessageDialog(jvdraw, "Invalid file: "
					+ openedFilePath.getFileName(), "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		jvdraw.source = openedFilePath;
		byte[] bytes = null;

		try {
			bytes = Files.readAllBytes(openedFilePath);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(jvdraw, e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		String file = new String(bytes, StandardCharsets.UTF_8);
		try {
			jvdraw.addToModel(file);
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(jvdraw,
					"File error!\n " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		jvdraw.isModified = false;
		jvdraw.setTitle(jvdraw.source.toString());
	}

}

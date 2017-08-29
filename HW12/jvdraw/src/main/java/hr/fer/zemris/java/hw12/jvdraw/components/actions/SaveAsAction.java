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
 * Saves current model to file. Opens {@link JFileChooser} for saving.
 * 
 * @author Borna
 *
 */
public class SaveAsAction extends AbstractAction {

	/**
	 * default serial
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * reference to {@link JVDraw}
	 */
	private JVDraw jvdraw;

	/**
	 * Constructs action with name {@code "SaveAs"}
	 * 
	 * @param jvdraw
	 *            reference
	 */
	public SaveAsAction(JVDraw jvdraw) {
		putValue(Action.NAME, "SaveAs");
		this.jvdraw = jvdraw;

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(Paths.get("./").toFile());
		fc.setDialogTitle("Save document");
		FileNameExtensionFilter jvdfilter = new FileNameExtensionFilter(
				"jvd files (*.jvd)", "jvd");
		fc.setFileFilter(jvdfilter);
		if (fc.showSaveDialog(jvdraw) != JFileChooser.APPROVE_OPTION) {
			return;
		}
		Path file = fc.getSelectedFile().toPath();

		if (Files.exists(file)) {
			int r = JOptionPane.showConfirmDialog(jvdraw, "File " + file
					+ "already exist.\n Do you want to replace it?",
					"Confirm SaveAs", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE);
			if (r != JOptionPane.YES_OPTION) {
				return;
			}
		}

		jvdraw.source = file;

		try {
			Files.write(file,
					jvdraw.getModelAsText().getBytes(StandardCharsets.UTF_8));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(jvdraw, e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		jvdraw.isModified = false;
		jvdraw.setTitle(file.toString());
	}

}

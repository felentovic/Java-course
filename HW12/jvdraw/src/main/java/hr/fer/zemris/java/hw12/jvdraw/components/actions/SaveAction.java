package hr.fer.zemris.java.hw12.jvdraw.components.actions;

import hr.fer.zemris.java.hw12.jvdraw.components.JVDraw;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;

/**
 * Saves current model to file. If not path is selected action opens
 * {@link javax.swing.JFileChooser}
 * 
 * @author Borna
 *
 */
public class SaveAction extends AbstractAction {

	/**
	 * default serial
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * reference to {@link JVDraw}
	 */
	private JVDraw jvdraw;

	/**
	 * Constructs action with name {@code "Save"}
	 * 
	 * @param jvdraw
	 *            reference
	 */
	public SaveAction(JVDraw jvdraw) {
		putValue(Action.NAME, "Save");
		this.jvdraw = jvdraw;

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (jvdraw.source == null) {
			jvdraw.saveAs.actionPerformed(arg0);
			return;
		}

		try {
			Files.write(jvdraw.source,
					jvdraw.getModelAsText().getBytes(StandardCharsets.UTF_8));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(jvdraw, e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		jvdraw.isModified = false;
	}

}

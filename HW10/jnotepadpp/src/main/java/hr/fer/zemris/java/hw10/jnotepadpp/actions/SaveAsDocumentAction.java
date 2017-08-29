package hr.fer.zemris.java.hw10.jnotepadpp.actions;

import hr.fer.zemris.java.hw10.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw10.jnotepadpp.i18n.FormLocalizationProvider;
import hr.fer.zemris.java.hw10.jnotepadpp.i18n.LocalizableAction;

import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

/**
 * Action calls {@link JNotepadPP} saveAsFile method
 * 
 * @author Borna
 *
 */
public class SaveAsDocumentAction extends LocalizableAction {

	private static final long serialVersionUID = 1L;
	private JNotepadPP jNotepadPP;

	/**
	 * Constructs action with name, description and accelerator key
	 * 
	 * @param jNotepadPP
	 *            reference to {@link JNotepadPP}
	 * @param flp
	 *            {@link FormLocalizationProvider} that gives translation for
	 *            name and description
	 */
	public SaveAsDocumentAction(JNotepadPP jNotepadPP,
			FormLocalizationProvider flp) {
		super("saveAs", flp);
		this.jNotepadPP = jNotepadPP;
		putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("control alt S"));
	}

	/**
	 * {@link JNotepadPP} saveAsFile method
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		jNotepadPP.saveAsFile();
	}
}

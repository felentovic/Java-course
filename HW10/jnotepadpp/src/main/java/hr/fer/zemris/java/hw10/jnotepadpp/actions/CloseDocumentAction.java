package hr.fer.zemris.java.hw10.jnotepadpp.actions;

import hr.fer.zemris.java.hw10.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw10.jnotepadpp.i18n.FormLocalizationProvider;
import hr.fer.zemris.java.hw10.jnotepadpp.i18n.LocalizableAction;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

/**
 * Action calls {@link JNotepadPP} closeSelectedTab method
 * 
 * @author Borna
 *
 */
public class CloseDocumentAction extends LocalizableAction {
	private static final long serialVersionUID = 1L;
	private JNotepadPP jNotepadPP;

	/**
	 * Constructs action with name, description, mnemonic key and accelerator key
	 * 
	 * @param jNotepadPP
	 *            reference to {@link JNotepadPP}
	 * @param flp
	 *            {@link FormLocalizationProvider} that gives translation for
	 *            name and description
	 */
	public CloseDocumentAction(JNotepadPP jNotepadPP,
			FormLocalizationProvider flp) {
		super("close", flp);
		this.jNotepadPP = jNotepadPP;
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control W"));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);

	}
	
	/**
	 * {@link JNotepadPP} actionPerformed method
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		jNotepadPP.closeSelectedTab();
	}
}

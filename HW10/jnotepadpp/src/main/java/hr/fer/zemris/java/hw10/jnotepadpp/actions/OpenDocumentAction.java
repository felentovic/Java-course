package hr.fer.zemris.java.hw10.jnotepadpp.actions;

import hr.fer.zemris.java.hw10.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw10.jnotepadpp.i18n.FormLocalizationProvider;
import hr.fer.zemris.java.hw10.jnotepadpp.i18n.LocalizableAction;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

/**
 * Action calls {@link JNotepadPP} openFile method
 * 
 * @author Borna
 *
 */
public class OpenDocumentAction extends LocalizableAction{
	
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
	public OpenDocumentAction(JNotepadPP jNotepadPP, FormLocalizationProvider flp) {
		super("open", flp);
		this.jNotepadPP=jNotepadPP;
		putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("control O"));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		
	}
	
	/**
	 * {@link JNotepadPP} openFile method
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
			jNotepadPP.openFile();
	}
	
	
}

package hr.fer.zemris.java.hw10.jnotepadpp.actions;

import hr.fer.zemris.java.hw10.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw10.jnotepadpp.i18n.FormLocalizationProvider;
import hr.fer.zemris.java.hw10.jnotepadpp.i18n.LocalizableAction;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

/**
 * Action calls {@link JNotepadPP} cutText method
 * 
 * @author Borna
 *
 */
public class CutAction extends LocalizableAction{
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
	public CutAction(JNotepadPP jNotepadPP, FormLocalizationProvider flp) {
		super("cut", flp);
		this.jNotepadPP=jNotepadPP;
		putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("control shift X"));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
				
	}
	
	/**
	 * {@link JNotepadPP} cutText method
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		jNotepadPP.cutText();
	}

}

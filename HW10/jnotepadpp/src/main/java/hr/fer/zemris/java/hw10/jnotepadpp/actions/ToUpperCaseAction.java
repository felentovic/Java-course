package hr.fer.zemris.java.hw10.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw10.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw10.jnotepadpp.i18n.FormLocalizationProvider;
import hr.fer.zemris.java.hw10.jnotepadpp.i18n.LocalizableAction;

/**
 * Action calls {@link JNotepadPP} toUpperCase method
 * 
 * @author Borna
 *
 */
public class ToUpperCaseAction extends LocalizableAction{
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
	public ToUpperCaseAction(JNotepadPP jNotepadPP, FormLocalizationProvider flp) {
		super("toUpper", flp);
		this.jNotepadPP=jNotepadPP;
		putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("control U"));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);
		
		
	}
	
	/**
	 * {@link JNotepadPP} toUpperCase method
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		jNotepadPP.toUpperCase();
	}
}

package hr.fer.zemris.java.hw10.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw10.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw10.jnotepadpp.i18n.FormLocalizationProvider;
import hr.fer.zemris.java.hw10.jnotepadpp.i18n.LocalizableAction;

/**
 * Action calls {@link JNotepadPP} invertCase method
 * 
 * @author Borna
 *
 */
public class InvertCaseAction extends LocalizableAction{
	
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
	public InvertCaseAction(JNotepadPP jNotepadPP, FormLocalizationProvider flp) {
		super("invert", flp);
		this.jNotepadPP = jNotepadPP;
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control I"));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_I);

	}
	
	/**
	 * {@link JNotepadPP} invertCase method
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		jNotepadPP.invertCase();
	}
}

package hr.fer.zemris.java.hw10.jnotepadpp.actions;

import hr.fer.zemris.java.hw10.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw10.jnotepadpp.i18n.FormLocalizationProvider;
import hr.fer.zemris.java.hw10.jnotepadpp.i18n.LocalizableAction;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;

/**
 * Action calls {@link JNotepadPP} sortAscending method
 * 
 * @author Borna
 *
 */
public class AscendingAction extends LocalizableAction {
	private static final long serialVersionUID = 1L;
	private JNotepadPP jNotepadPP;

	/**
	 * Constructs action with name, description and mnemonic key
	 * 
	 * @param jNotepadPP
	 *            reference to {@link JNotepadPP}
	 * @param flp
	 *            {@link FormLocalizationProvider} that gives translation for
	 *            name and description
	 */
	public AscendingAction(JNotepadPP jNotepadPP, FormLocalizationProvider flp) {
		super("ascending", flp);
		this.jNotepadPP = jNotepadPP;
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);

	}

	/**
	 * {@link JNotepadPP} sortAscending method
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		jNotepadPP.sortAscending();
	}

}

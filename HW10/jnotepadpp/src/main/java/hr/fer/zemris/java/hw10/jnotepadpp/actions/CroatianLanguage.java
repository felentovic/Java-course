package hr.fer.zemris.java.hw10.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import hr.fer.zemris.java.hw10.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw10.jnotepadpp.i18n.FormLocalizationProvider;
import hr.fer.zemris.java.hw10.jnotepadpp.i18n.LocalizableAction;

/**
 * Action calls {@link JNotepadPP} setLanguage method with argument {@code "hr"}
 * 
 * @author Borna
 *
 */
public class CroatianLanguage extends LocalizableAction {
	private static final long serialVersionUID = 1L;
	JNotepadPP jNotepadPP;
	
	/**
	 * Constructs action with name, description
	 * 
	 * @param jNotepadPP
	 *            reference to {@link JNotepadPP}
	 * @param flp
	 *            {@link FormLocalizationProvider} that gives translation for
	 *            name and description
	 */
	public CroatianLanguage(JNotepadPP jNotepadPP, FormLocalizationProvider flp) {
		super("hr", flp);
		this.jNotepadPP = jNotepadPP;

	}
	
	/**
	 * {@link JNotepadPP} setLanguage method with argument {@code "hr"}
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		jNotepadPP.setLanguage("hr");
	}
}

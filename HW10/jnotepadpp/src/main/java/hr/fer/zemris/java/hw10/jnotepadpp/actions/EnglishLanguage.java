package hr.fer.zemris.java.hw10.jnotepadpp.actions;

import hr.fer.zemris.java.hw10.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw10.jnotepadpp.i18n.FormLocalizationProvider;
import hr.fer.zemris.java.hw10.jnotepadpp.i18n.LocalizableAction;

import java.awt.event.ActionEvent;

/**
 * Action calls {@link JNotepadPP} setLanguge method with argument {@code "en"}
 * 
 * @author Borna
 *
 */
public class EnglishLanguage extends LocalizableAction{
	
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
	public EnglishLanguage(JNotepadPP jNotepadPP, FormLocalizationProvider flp) {
		super("en", flp);
		this.jNotepadPP=jNotepadPP;
		
	}
	
	/**
	 * {@link JNotepadPP} setLanguage method with argument {@code "en"}
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		jNotepadPP.setLanguage("en");
	}

}

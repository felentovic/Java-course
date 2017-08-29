package hr.fer.zemris.java.hw10.jnotepadpp.i18n;

import javax.swing.AbstractAction;
import javax.swing.Action;

/**
 * Class extends {@link AbstractAction} and registers itself as listener to given
 * provider
 * 
 * @author Borna
 *
 */
public abstract class LocalizableAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs LocalizableAction with text for given key
	 * 
	 * @param key
	 *            given key for text
	 * @param lp
	 *            {@link ILocalizationProvider} that gives translation for given
	 *            key
	 */
	public LocalizableAction(String key, ILocalizationProvider lp) {
		putValue(Action.NAME, lp.getString(key));
		putValue(Action.SHORT_DESCRIPTION, lp.getString(key + "Desc"));

		ILocalizationListener listener = () -> {
			putValue(Action.NAME, lp.getString(key));
			putValue(Action.SHORT_DESCRIPTION, lp.getString(key + "Desc"));
		};

		lp.addLocalizationListener(listener);
	}

}

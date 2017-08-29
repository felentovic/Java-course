package hr.fer.zemris.java.hw10.jnotepadpp.i18n;

import javax.swing.JLabel;

/**
 * Class extends {@link JLabel} and registers itself as listener to given
 * provider
 * 
 * @author Borna
 *
 */
public class LJLabel extends JLabel {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs LJLabel with text for given key
	 * 
	 * @param key
	 *            given key for text
	 * @param lp
	 *            {@link ILocalizationProvider} that gives translation for given
	 *            key
	 */
	public LJLabel(String key, ILocalizationProvider lp) {
		setText(lp.getString(key));

		ILocalizationListener listener = () -> {
			setText(lp.getString(key));
		};

		lp.addLocalizationListener(listener);
	}

}

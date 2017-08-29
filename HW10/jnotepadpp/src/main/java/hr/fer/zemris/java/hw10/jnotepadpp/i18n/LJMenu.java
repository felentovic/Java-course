package hr.fer.zemris.java.hw10.jnotepadpp.i18n;

import javax.swing.JMenu;

/**
 * Class extends {@link JMenu} and registers itself as listener to given
 * provider
 * 
 * @author Borna
 *
 */
public class LJMenu extends JMenu{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs LJMenu with text for given key
	 * 
	 * @param key
	 *            given key for text
	 * @param lp
	 *            {@link ILocalizationProvider} that gives translation for given
	 *            key
	 */
	public LJMenu(String key, ILocalizationProvider flp) {
			setText(flp.getString(key));
			
			ILocalizationListener listener = () -> {
				setText(flp.getString(key));

			};
			
			flp.addLocalizationListener(listener);
	}
}

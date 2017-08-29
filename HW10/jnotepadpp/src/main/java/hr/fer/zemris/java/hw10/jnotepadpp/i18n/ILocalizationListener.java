package hr.fer.zemris.java.hw10.jnotepadpp.i18n;

/**
 * Interface that is used for observer pattern
 * @author Borna
 *
 */
public interface ILocalizationListener {
	
	/**
	 * Action that is done by listener when localization changed
	 */
	public void localizationChanged();
}

package hr.fer.zemris.java.hw10.jnotepadpp.i18n;

/**
 * Interface used for observer pattern. Class that implements this interface is
 * subject.
 * 
 * @author Borna
 *
 */
public interface ILocalizationProvider {

	/**
	 * Given listener is added into intern listeners list
	 * 
	 * @param listener
	 *            given listener
	 */
	public void addLocalizationListener(ILocalizationListener listener);

	/**
	 * Given listener is removed from intern listeners list
	 * 
	 * @param listener
	 *            given listener
	 */
	public void removeLocalizationListener(ILocalizationListener listener);

	/**
	 * Return translation for given key
	 * 
	 * @param key
	 *            key for translation
	 * @return translation for given key
	 */
	public String getString(String key);

}

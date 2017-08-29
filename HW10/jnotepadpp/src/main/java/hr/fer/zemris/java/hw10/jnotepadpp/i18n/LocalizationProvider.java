package hr.fer.zemris.java.hw10.jnotepadpp.i18n;

import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Class extends {@link AbstractLocalizationProvider} and implements getString
 * method. Class is used for translation.
 * 
 * @author Borna
 *
 */
public class LocalizationProvider extends AbstractLocalizationProvider {
	private static LocalizationProvider instance;
	private ResourceBundle bundle;
	private String language;

	/**
	 * Constructs default english language
	 */
	private LocalizationProvider() {
		language = "";
		setLanguage("en");
	}

	/**
	 * Method always return same instance. If instance is null it creates it.
	 * Singletone pattern.
	 * 
	 * @return instance of {@link LocalizationProvider}
	 */
	public static LocalizationProvider getInstance() {
		if (instance == null) {
			instance = new LocalizationProvider();
		}

		return instance;
	}

	/**
	 * Sets new language and notify all listeners that new language is seted
	 * 
	 * @param language
	 *            new language
	 */
	public void setLanguage(String language) {
		if (this.language.equals(language)) {
			return;
		}

		this.language = language;
		Locale locale = Locale.forLanguageTag(language);
		bundle = ResourceBundle.getBundle(
				"hr.fer.zemris.java.hw10.jnotepadpp.i18n.lang.trans", locale);
		fire();
	}

	/**
	 * Getter for language
	 * 
	 * @return
	 */
	public String getLanguage() {
		return language;
	}

	@Override
	public String getString(String key) {
		return new String(bundle.getString(key).getBytes(
				StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
	}

}

package hr.fer.zemris.java.hw10.jnotepadpp.i18n;

import java.util.LinkedList;
import java.util.List;

/**
 * Class implements {@link ILocalizationProvider} methods
 * addLocalizationListener and removeLocalizationListener and has method fire
 * that notify all listeners
 * 
 * @author Borna
 *
 */
public abstract class AbstractLocalizationProvider implements
		ILocalizationProvider {

	List<ILocalizationListener> listeners;
	
	/**
	 * Constructor
	 */
	public AbstractLocalizationProvider() {
		listeners = new LinkedList<ILocalizationListener>();
	}

	@Override
	public void addLocalizationListener(ILocalizationListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException("Listener cant be null.");
		}

		listeners.add(listener);
	}

	@Override
	public void removeLocalizationListener(ILocalizationListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException("Listener cant be null.");
		}

		listeners.remove(listener);
	}
	
	/**
	 * Notify all listeners that localization has changed
	 */
	public void fire() {
		for (ILocalizationListener listener : listeners) {
			listener.localizationChanged();

		}
	}

}

package hr.fer.zemris.java.hw10.jnotepadpp.i18n;

/**
 * Class is a decorator for some other IlocalizationProvider. This class offers
 * two additional methods: connect() and disconnect(), and it manages a
 * connection status (so that you can not connect if you are already connected).
 * 
 * @author Borna
 *
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {
	private boolean connected;
	private ILocalizationProvider provider;
	private ILocalizationListener listener;

	/**
	 * Constructor addes itself as listener to given provider
	 * 
	 * @param provider
	 *            provider on which this class is added as listener
	 */
	public LocalizationProviderBridge(ILocalizationProvider provider) {
		super();
		this.provider = provider;
		listener = new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				LocalizationProviderBridge.this.fire();

			}
		};
	}
	
	/**
	 * Method that disconnects this class as listener from provider
	 */
	public void disconnect() {
		if (connected) {
			provider.removeLocalizationListener(listener);
		}
		connected = false;

	}
	
	/**
	 * Method that connects this class as listener to provider
	 */
	public void connect() {
		if (!connected) {
			provider.addLocalizationListener(listener);
		}
		connected = true;
	}
	
	@Override
	public String getString(String key) {
		return provider.getString(key);
	}
}

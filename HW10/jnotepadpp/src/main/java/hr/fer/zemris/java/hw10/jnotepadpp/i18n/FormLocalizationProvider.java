package hr.fer.zemris.java.hw10.jnotepadpp.i18n;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

/**
 * Class is derived from LocalizationProviderBridge in its constructor it
 * registeres itself as a WindowListener to its JFrame when frame is opened, it
 * calls connect and when frame is closed, it calls disconnect.
 * 
 * @author Borna
 *
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {

	/**
	 * Constructor where class registeres itself as a WindowListener to its
	 * JFrame when frame is opened, it calls connect and when frame is closed,
	 * it calls disconnect.
	 * 
	 * @param provider {@link LocalizationProvider} for lozalization
	 * @param frame on which this class is registered as {@link WindowListener}
	 */
	public FormLocalizationProvider(LocalizationProvider provider, JFrame frame) {
		super(provider);
		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowOpened(WindowEvent e) {
				connect();
				super.windowOpened(e);
			}

			@Override
			public void windowClosed(WindowEvent e) {
				disconnect();
				super.windowClosed(e);
			}
		});
	}

}

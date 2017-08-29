package hr.fer.zemris.java.fractals.Mandelbrot;

import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;

import javax.swing.SwingUtilities;

/**
 * Pomoćni razred koji omogućava prikaz fraktala. Za generiranje fraktala
 * zaduženi su objekti koji implementiraju sučelje {@link IFractalProducer}.
 * Izgenerirani rezultat ti objekti moraju vratiti grafičkoj komponenti koja se
 * generatorima predstavlja kao {@link IFractalResultObserver}.
 * 
 * @author marcupic
 *
 */
public class FractalViewer {

	/**
	 * Pokreće prikaz Mandelbrotovog fraktala izračunatog slijedno.
	 * 
	 * @param args
	 *            argumenti naredbenog retka: ne koriste se.
	 */
	public static void main(String[] args) {
	   Mandelbrot.showParallel();
		
	}

	/**
	 * Metoda pokreće grafički preglednik u kojem će prikazati fraktal čiji je
	 * generator predan kao parametar.
	 * 
	 * @param iFractalProducer
	 *            generator fraktala; ne smije biti <code>null</code>
	 */
	public static void show(final IFractalProducer iFractalProducer) {
		if (iFractalProducer == null) {
			throw new IllegalArgumentException(
					"Generator fraktala ne smije biti null.");
		}
		if (SwingUtilities.isEventDispatchThread()) {
			showInEDT(iFractalProducer);
		} else {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					showInEDT(iFractalProducer);
				}
			});
		}
	}

	/**
	 * Pomoćna metoda koja pokreće prikaz uz pretpostavku da je pozvana iz
	 * EDT-a.
	 * 
	 * @param iFractalProducer
	 *            generator fraktala
	 */
	private static void showInEDT(IFractalProducer iFractalProducer) {
		FractalViewerFrame fvf = new FractalViewerFrame(iFractalProducer);
		fvf.setVisible(true);
	}

}

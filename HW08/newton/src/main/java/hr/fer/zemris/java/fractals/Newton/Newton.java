package hr.fer.zemris.java.fractals.Newton;

import hr.fer.zemris.java.fractals.Mandelbrot.FractalViewer;
import hr.fer.zemris.java.fractals.Mandelbrot.FractalViewerFrame;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.swing.SwingUtilities;

/**
 * Klasa sluzi za prikaz Newtonovog fraktala; višedretveno
 * 
 * @author Borna Feldšar
 * @version 1.0
 *
 */
public class Newton {
	static ComplexRootedPolynomial polynomial;
	static ComplexPolynomial derived;

	/**
	 * Pokreće prikaz Newtonovog fraktala izračunatog paralelno.
	 * 
	 * @param args
	 *            argumenti naredbenog retka: ne koriste se.
	 */
	public static void main(String[] args) {

		System.out
				.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out
				.println("Please enter at least two roots, one root per line. "
						+ "Enter 'done' when done.");
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new BufferedInputStream(System.in)));
		List<Complex> list = new LinkedList<>();
		int counter = 1;
		while (true) {
			String line = null;
			System.out.print("Root " + counter + "> ");
			try {
				line = reader.readLine();
			} catch (IOException e) {
				System.out.println("Reader error. ");
				System.exit(-1);
			}
			if (line.equalsIgnoreCase("done")) {
				break;
			}
			try {
				list.add(Complex.parseFromString(line));
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
				continue;
			}
			counter++;
		}
		if (list.size() < 2) {
			System.out.println("Please enter at least two roots");
			return;
		}
		System.out.println("Image of fractal will appear shortly. Thank you.");

		// Newton.showSequential(new ComplexRootedPolynomial(list
		// .toArray(new Complex[0])));

		Newton.showParallel(new ComplexRootedPolynomial(list
				.toArray(new Complex[0])));
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

	/**
	 * Otvara prozor i prikazuje Newtonov fraktal koji se računa slijedno.
	 * 
	 * @param polynom2
	 *            polinom za koga se racuna newtonov fraktal
	 */
	public static void showSequential(ComplexRootedPolynomial polynom2) {
		polynomial = polynom2;
		derived = polynomial.toComplexPolynom().derive();
		FractalViewer.show(getSequentialFractalproducer());
	}

	/**
	 * Otvara prozor i prikazuje Newtonov fraktal koji se računa paralelno.
	 * 
	 * @param polynom2
	 *            polinom za koga se racuna newtonov fraktal
	 */
	public static void showParallel(ComplexRootedPolynomial polynom2) {
		polynomial = polynom2;
		derived = polynomial.toComplexPolynom().derive();
		FractalViewer.show(getParallelFractalproducer());

	}

	/**
	 * Vraća objekt koji Newtonov fraktal generira paralelno.
	 * 
	 * @return paralelni generator Newtonovog fraktala
	 */
	private static IFractalProducer getParallelFractalproducer() {
		return new FractalProducer();
	}

	/**
	 * Klasa implementira {@link IFractalProducer}
	 * 
	 * @author Borna Feldšar
	 * @version 1.0
	 *
	 */
	private static class FractalProducer implements IFractalProducer {
		ExecutorService pool;

		/**
		 * Construira {@link FractalProducer} i {@link Executors} sa demonskim
		 * dretama
		 */
		public FractalProducer() {
			int numberOfProcessors = Runtime.getRuntime().availableProcessors();
			pool = Executors.newFixedThreadPool(numberOfProcessors, (r) -> {
				Thread thread = new Thread(r);
				thread.setDaemon(true);
				return thread;
			});
		}

		@Override
		public void produce(double reMin, double reMax, double imMin,
				double imMax, int width, int height, long requestNo,
				IFractalResultObserver observer) {
			System.out.println("Započinjem izračune...");
			short[] data = new short[width * height];
			calculateParallel(reMin, reMax, imMin, imMax, width, height, 0,
					height - 1, data, pool);
			System.out.println("Izračuni gotovi...");
			observer.acceptResult(data, (short) (polynomial.toComplexPolynom()
					.order() + 1), requestNo);
			System.out.println("Dojava gotova...");
		}

	}

	/**
	 * Paralelna implementacija koja računa Newtonov fraktal.
	 * 
	 * @param reMin
	 *            minimalna vrijednost po realnoj osi
	 * @param reMax
	 *            maksimalna vrijednost po realnoj osi
	 * @param imMin
	 *            minimalna vrijednost po imaginarnoj osi
	 * @param imMax
	 *            maksimalna vrijednost po imaginarnoj osi
	 * @param width
	 *            širina zaslona na kojem se prikazuje fraktal
	 * @param height
	 *            visina zaslona na kojem se prikazuje fraktal
	 * @param ymin
	 *            y-linija od koje se popunjava polje (uključiva)
	 * @param ymax
	 *            y-linija do koje se popunjava polje (uključiva)
	 * @param data
	 *            polje u koje treba pohraniti rezultat
	 * @param pool
	 *            {@link ExecutorService} zaduzen za paralelizaciju
	 */
	private static void calculateParallel(double reMin, double reMax,
			double imMin, double imMax, int width, int height, int ymin,
			int ymax, short[] data, ExecutorService pool) {

		class Task implements Runnable {

			int ymin;
			int ymax;

			public Task(int ymin, int ymax) {
				super();
				this.ymax = ymax;
				this.ymin = ymin;
			}

			@Override
			public void run() {
				calculate(reMin, reMax, imMin, imMax, width, height, ymin,
						ymax, data);
			}

		}

		int numberOfProcessors = Runtime.getRuntime().availableProcessors();
		int trackLength = 8 * numberOfProcessors;
		int numberOfTracks = (ymax - ymin) / trackLength;
		int restOfTrack = (ymax - ymin) - trackLength * numberOfTracks;
		List<Future<?>> results = new ArrayList<>();

		int tmpYmin = ymin;
		int tmpYmax;
		if (numberOfTracks == 0) {
			tmpYmax = ymin + restOfTrack;
		} else {
			tmpYmax = tmpYmin + trackLength;
		}

		for (int i = 0; i < numberOfTracks; i++) {
			results.add(pool.submit(new Task(tmpYmin, tmpYmax)));
			tmpYmin = tmpYmax;
			tmpYmax += trackLength;
		}

		if (restOfTrack > 0) {
			tmpYmax = tmpYmin + restOfTrack;
			results.add(pool.submit(new Task(tmpYmin, tmpYmax)));

		}

		for (Future<?> f : results) {
			while (true) {
				try {
					f.get();
					break;
				} catch (InterruptedException | ExecutionException e) {
				}
			}
		}

	}

	/**
	 * Slijedna implementacija koja računa Newtonov fraktal. Omogućava da se u
	 * predano polje pohrane rezultati samo za zadani raspon y-koordinata
	 * (ostatak polja se ne dira).
	 * 
	 * @param reMin
	 *            minimalna vrijednost po realnoj osi
	 * @param reMax
	 *            maksimalna vrijednost po realnoj osi
	 * @param imMin
	 *            minimalna vrijednost po imaginarnoj osi
	 * @param imMax
	 *            maksimalna vrijednost po imaginarnoj osi
	 * @param width
	 *            širina zaslona na kojem se prikazuje fraktal
	 * @param height
	 *            visina zaslona na kojem se prikazuje fraktal
	 * @param m
	 *            broj pokušaja otkrivanja divergencije
	 * @param ymin
	 *            y-linija od koje se popunjava polje (uključiva)
	 * @param ymax
	 *            y-linija do koje se popunjava polje (uključiva)
	 * @param data
	 *            polje u koje treba pohraniti rezultat
	 */

	private static void calculate(double reMin, double reMax, double imMin,
			double imMax, int width, int height, int ymin, int ymax,
			short[] data) {
		int offset = ymin * width;
		for (int y = ymin; y <= ymax; y++) {
			for (int x = 0; x < width; x++) {
				double cre = x * (reMax - reMin) / (width - 1) + reMin;
				double cim = (height - 1 - y) * (imMax - imMin) / (height - 1)
						+ imMin;
				Complex zn = new Complex(cre, cim);
				Complex zn1;
				int maxIter = 64;
				double convergenceTreshold = 1e-3;
				double rootTreshold = 1e-3;
				int iter = 0;
				double module;
				do {
					Complex numerator = polynomial.apply(zn);
					Complex denominator = derived.apply(zn);
					Complex fraction = numerator.divide(denominator);
					zn1 = zn.sub(fraction);
					module = zn1.sub(zn).module();
					zn = zn1;

					iter++;
				} while (module > convergenceTreshold && iter < maxIter);
				short index = polynomial.indexOfClosestRootFor(zn1,
						rootTreshold);
				if (index == -1) {
					data[offset++] = 0;
				} else {
					data[offset++] = (short) (index + 1);
				}
			}

		}
	}

	/**
	 * Vraća objekt koji Newtonov fraktal generira slijedno.
	 * 
	 * @return slijedni generator Newtonovog fraktala
	 */
	private static IFractalProducer getSequentialFractalproducer() {
		return new IFractalProducer() {

			@Override
			public void produce(double reMin, double reMax, double imMin,
					double imMax, int width, int height, long requestNo,
					IFractalResultObserver observer) {
				System.out.println("Započinjem izračune...");
				short[] data = new short[width * height];
				calculate(reMin, reMax, imMin, imMax, width, height, 0,
						height - 1, data);
				System.out.println("Izračuni gotovi...");
				observer.acceptResult(data, (short) (polynomial
						.toComplexPolynom().order() + 1), requestNo);
				System.out.println("Dojava gotova...");
			}

		};
	}
}

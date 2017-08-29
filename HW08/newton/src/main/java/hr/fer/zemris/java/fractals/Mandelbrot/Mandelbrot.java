package hr.fer.zemris.java.fractals.Mandelbrot;

import hr.fer.zemris.java.fractals.Mandelbrot.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Osnovna potpora za prikaz Mandelbrotovog fraktala.
 * 
 * @author marcupic
 *
 */
public class Mandelbrot {

	/**
	 * Otvara prozor i prikazuje Mandelbrotov fraktal koji se računa slijedno.
	 */
	public static void showSequential() {
		FractalViewer.show(getSequentialFractalproducer());
	}

	/**
	 * Otvara prozor i prikazuje Mandelbrotov fraktal koji se računa paralelno.
	 */
	public static void showParallel() {
		FractalViewer.show(getParallelFractalproducer());

	}

	/**
	 * Vraća objekt koji Mandelbrotov fraktal generira paralelno.
	 * 
	 * @return paralelni generator Mandelbrotovog fraktala
	 */
	private static IFractalProducer getParallelFractalproducer() {
		return new FractalProducer();
	}

	/**
	 * Klasa koja implemenitra {@link IFractalProducer}
	 * 
	 * @author Borna Feldšar
	 * @version 1.0
	 *
	 */
	private static class FractalProducer implements IFractalProducer {
		ExecutorService pool;

		/**
		 * Konstruktor stvara {@link ExecutorService} s demonskim dretvama
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
			int m = 16 * 16 * 16;
			short[] data = new short[width * height];
			calculateParallel(reMin, reMax, imMin, imMax, width, height, m, 0,
					height - 1, data, pool);
			System.out.println("Izračuni gotovi...");
			observer.acceptResult(data, (short) m, requestNo);
			System.out.println("Dojava gotova...");
		}

	}

	/**
	 * Paralelna implementacija koja računa Mandelbrotov fraktal. Omogućava da
	 * se u predano polje pohrane rezultati samo za zadani raspon y-koordinata
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
	 * @param pool
	 *            {@link ExecutorService} zaduzen za paralelizaciju
	 */
	public static void calculateParallel(double reMin, double reMax,
			double imMin, double imMax, int width, int height, int m, int ymin,
			int ymax, short[] data, ExecutorService pool) {

		class Posao implements Runnable {

			int ymin;
			int ymax;

			public Posao(int ymin, int ymax) {
				super();
				this.ymax = ymax;
				this.ymin = ymin;
			}

			@Override
			public void run() {
				calculate(reMin, reMax, imMin, imMax, width, height, m, ymin,
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
			results.add(pool.submit(new Posao(tmpYmin, tmpYmax)));
			tmpYmin = tmpYmax;
			tmpYmax += trackLength;
		}

		if (restOfTrack > 0) {
			tmpYmax = tmpYmin + restOfTrack;
			results.add(pool.submit(new Posao(tmpYmin, tmpYmax)));

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
	 * Slijedna implementacija koja računa Mandelbrotov fraktal. Omogućava da se
	 * u predano polje pohrane rezultati samo za zadani raspon y-koordinata
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
	public static void calculate(double reMin, double reMax, double imMin,
			double imMax, int width, int height, int m, int ymin, int ymax,
			short[] data) {
		int offset = ymin * width;
		for (int y = ymin; y <= ymax; y++) {
			for (int x = 0; x < width; x++) {
				double cre = x / (width - 1.0) * (reMax - reMin) + reMin;
				double cim = ((height - 1) - y) / (height - 1.0)
						* (imMax - imMin) + imMin;
				double znre = 0;
				double znim = 0;
				double module = 0;
				int iters = 0;
				do {
					double zn1re = znre * znre - znim * znim + cre;
					double zn1im = 2 * znre * znim + cim;
					module = zn1re * zn1re + zn1im * zn1im;
					znre = zn1re;
					znim = zn1im;
					iters++;
				} while (iters < m && module < 4.0);
				data[offset] = (short) iters;
				offset++;
			}
		}
	}

	/**
	 * Vraća objekt koji Mandelbrotov fraktal generira slijedno.
	 * 
	 * @return slijedni generator Mandelbrotovog fraktala
	 */
	private static IFractalProducer getSequentialFractalproducer() {
		return new IFractalProducer() {

			@Override
			public void produce(double reMin, double reMax, double imMin,
					double imMax, int width, int height, long requestNo,
					IFractalResultObserver observer) {
				System.out.println("Započinjem izračune...");
				int m = 16 * 16 * 16;
				short[] data = new short[width * height];
				calculate(reMin, reMax, imMin, imMax, width, height, m, 0,
						height - 1, data);
				System.out.println("Izračuni gotovi...");
				observer.acceptResult(data, (short) m, requestNo);
				System.out.println("Dojava gotova...");
			}

		};
	}
}

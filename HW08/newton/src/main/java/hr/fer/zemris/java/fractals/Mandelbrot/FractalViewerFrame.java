package hr.fer.zemris.java.fractals.Mandelbrot;

import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.Stack;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Implementacija prozora koji se koristi za prikaz fraktala.
 * 
 * @author marcupic
 *
 */
public class FractalViewerFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private double reMin = -2.5;
	private double reMax = 1.0;
	private double imMin = -1.2;
	private double imMax = 1.2;
	private JFractalCanvas fCanvas;
	private long imageVersion = 0;
	private IFractalProducer fProducer;
	private Stack<double[]> oldParameters = new Stack<>();

	public FractalViewerFrame(IFractalProducer fProducer) {
		setTitle("Fractal Viewer V1.0");
		if (fProducer == null) {
			throw new NullPointerException("fractalProducer can not be null!");
		}
		this.fProducer = fProducer;
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.getContentPane().setLayout(new BorderLayout());
		fCanvas = new JFractalCanvas();
		this.add(fCanvas, BorderLayout.CENTER);
		this.pack();

		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == 'r') {
					if (oldParameters.isEmpty())
						return;
					double[] old = oldParameters.pop();
					reMin = old[0];
					reMax = old[1];
					imMin = old[2];
					imMax = old[3];
					System.out.println("Novo područje: (" + reMin + "," + reMax
							+ "):(" + imMin + "," + imMax + ")");
					fCanvas.requireNewFractalData();
					return;
				}
				if (e.getKeyChar() == 27) {
					oldParameters.clear();
					reMin = -2.5;
					reMax = 1.0;
					imMin = -1.2;
					imMax = 1.2;
					System.out.println("Novo područje: (" + reMin + "," + reMax
							+ "):(" + imMin + "," + imMax + ")");
					fCanvas.requireNewFractalData();
					return;
				}
				if (e.getKeyChar() == '-') {
					oldParameters.push(new double[] { reMin, reMax, imMin,
							imMax });
					double xw = (reMax - reMin);
					double xc = reMin + xw / 2.0;
					reMin = xc - xw;
					reMax = xc + xw;
					double yw = (imMax - imMin);
					double yc = imMin + yw / 2.0;
					imMin = yc - yw;
					imMax = yc + yw;
					System.out.println("Novo područje: (" + reMin + "," + reMax
							+ "):(" + imMin + "," + imMax + ")");
					fCanvas.requireNewFractalData();
					return;
				}
				if (e.getKeyChar() == '+') {
					oldParameters.push(new double[] { reMin, reMax, imMin,
							imMax });
					double xw = (reMax - reMin);
					double xc = reMin + xw / 2.0;
					reMin = xc - xw / 4.0;
					reMax = xc + xw / 4.0;
					double yw = (imMax - imMin);
					double yc = imMin + yw / 2.0;
					imMin = yc - yw / 4.0;
					imMax = yc + yw / 4.0;
					System.out.println("Novo područje: (" + reMin + "," + reMax
							+ "):(" + imMin + "," + imMax + ")");
					fCanvas.requireNewFractalData();
					return;
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					double deltaY = (imMax - imMin) / 2.0;
					oldParameters.push(new double[] { reMin, reMax, imMin,
							imMax });
					imMin += deltaY;
					imMax += deltaY;
					System.out.println("Novo područje: (" + reMin + "," + reMax
							+ "):(" + imMin + "," + imMax + ")");
					fCanvas.requireNewFractalData();
					return;
				}
				if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					double deltaY = (imMax - imMin) / 2.0;
					oldParameters.push(new double[] { reMin, reMax, imMin,
							imMax });
					imMin -= deltaY;
					imMax -= deltaY;
					System.out.println("Novo područje: (" + reMin + "," + reMax
							+ "):(" + imMin + "," + imMax + ")");
					fCanvas.requireNewFractalData();
					return;
				}
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					double deltaX = (reMax - reMin) / 2.0;
					oldParameters.push(new double[] { reMin, reMax, imMin,
							imMax });
					reMin -= deltaX;
					reMax -= deltaX;
					System.out.println("Novo područje: (" + reMin + "," + reMax
							+ "):(" + imMin + "," + imMax + ")");
					fCanvas.requireNewFractalData();
					return;
				}
				if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					double deltaX = (reMax - reMin) / 2.0;
					oldParameters.push(new double[] { reMin, reMax, imMin,
							imMax });
					reMin += deltaX;
					reMax += deltaX;
					System.out.println("Novo područje: (" + reMin + "," + reMax
							+ "):(" + imMin + "," + imMax + ")");
					fCanvas.requireNewFractalData();
					return;
				}
				if (e.getKeyCode() == KeyEvent.VK_F1) {
					String msg = "Podržane tipke:\r\n"
							+ "F1 - pomoć\r\n"
							+ "ESC - reset parametara\r\n"
							+ "'r' - povratak na prethodni skup parametara\r\n"
							+ "LEFT, RIGHT, UP, DOWN - pomicanje po kompleksnoj ravnini za pola raspona\r\n"
							+ "'+' - uvećanje skale za faktor 2\r\n"
							+ "'-' - smanjenje skale za faktor 2\r\n";
					JOptionPane.showMessageDialog(FractalViewerFrame.this, msg,
							"Pomoć", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
			}
		});
	}

	private class JFractalCanvas extends JComponent implements
			IFractalResultObserver {

		private static final long serialVersionUID = 1L;
		private BufferedImage bim;
		private int bbx0;
		private int bby0;
		private int bbx1;
		private int bby1;
		private boolean bbvalid = false;

		public JFractalCanvas() {
			System.out.println("Novo područje: (" + reMin + "," + reMax + "):("
					+ imMin + "," + imMax + ")");
			setPreferredSize(new Dimension(500, 500));
			this.addMouseMotionListener(new MouseAdapter() {
				@Override
				public void mouseDragged(MouseEvent e) {
					bbx1 = e.getX();
					bby1 = e.getY();
					JFractalCanvas.this.repaint();
				}
			});
			this.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					JFractalCanvas.this.repaint();
				}

				@Override
				public void mousePressed(MouseEvent e) {
					bbx0 = e.getX();
					bby0 = e.getY();
					bbx1 = bbx0;
					bby1 = bby0;
					bbvalid = true;
					JFractalCanvas.this.repaint();
				}

				@Override
				public void mouseReleased(MouseEvent e) {
					bbx1 = e.getX();
					bby1 = e.getY();
					bbvalid = false;
					if (Math.abs(bbx1 - bbx0) > 5 && Math.abs(bby1 - bby0) > 5) {
						if (bbx1 < bbx0) {
							int t = bbx1;
							bbx1 = bbx0;
							bbx0 = t;
						}
						if (bby1 > bby0) {
							int t = bby1;
							bby1 = bby0;
							bby0 = t;
						}
						double u0 = bbx0 / (fCanvas.getWidth() - 1.0)
								* (reMax - reMin) + reMin;
						double u1 = bbx1 / (fCanvas.getWidth() - 1.0)
								* (reMax - reMin) + reMin;
						double v0 = (fCanvas.getHeight() - 1.0 - bby0)
								/ (fCanvas.getHeight() - 1.0) * (imMax - imMin)
								+ imMin;
						double v1 = (fCanvas.getHeight() - 1.0 - bby1)
								/ (fCanvas.getHeight() - 1.0) * (imMax - imMin)
								+ imMin;
						oldParameters.push(new double[] { reMin, reMax, imMin,
								imMax });
						reMin = u0;
						reMax = u1;
						imMin = v0;
						imMax = v1;
						System.out.println("Novo područje: (" + reMin + ","
								+ reMax + "):(" + imMin + "," + imMax + ")");
						fCanvas.requireNewFractalData();
					}

				}
			});
		}

		@Override
		protected void paintComponent(Graphics g) {
			System.out.println("Pozvano crtanje...");
			Dimension dim = getSize();
			if (bim == null) {
				bim = new BufferedImage(dim.width, dim.height,
						BufferedImage.TYPE_3BYTE_BGR);
				g.setColor(Color.WHITE);
				g.fillRect(0, 0, dim.width, dim.height);
				g.setColor(Color.BLACK);
				g.drawString("Slika još nije dostupna. Započeto je crtanje...",
						10, 30);
				requireNewFractalData();
			} else if (bim.getWidth() != dim.width
					|| bim.getHeight() != dim.height) {
				bim = new BufferedImage(dim.width, dim.height,
						BufferedImage.TYPE_3BYTE_BGR);
				g.setColor(Color.WHITE);
				g.fillRect(0, 0, dim.width, dim.height);
				g.setColor(Color.BLACK);
				g.drawString(
						"Čekam podatke o fraktalu zbog promjene dimenzija slike. Započeto je crtanje...",
						10, 30);
				requireNewFractalData();
			} else {
				g.drawImage(bim, 0, 0, null);
				if (bbvalid) {
					g.setColor(Color.YELLOW);
					g.drawRect(bbx0, bby0, bbx1 - bbx0 + 1, bby1 - bby0 + 1);
				}
			}
		}

		private void requireNewFractalData() {
			imageVersion++;
			new Thread(new Runnable() {
				@Override
				public void run() {
					fProducer.produce(reMin, reMax, imMin, imMax,
							JFractalCanvas.this.getWidth(),
							JFractalCanvas.this.getHeight(), imageVersion,
							JFractalCanvas.this);
				}
			}).start();
		}

		@Override
		public void acceptResult(final short[] data, final short limit,
				final long requestVersion) {
			if (SwingUtilities.isEventDispatchThread()) {
				acceptResultEDT(data, limit, requestVersion);
			} else {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						acceptResultEDT(data, limit, requestVersion);
					}
				});
			}
		}

		public void acceptResultEDT(short[] data, short limit,
				long requestVersion) {
			if (requestVersion != imageVersion) {
				System.out
						.println("Dobio sam rezultat koji zanemarujem jer je prestar...");
				return;
			} else {
				System.out
						.println("Dobio sam rezultat izračuna; preuzimam ga...");
			}
			WritableRaster raster = bim.getRaster();
			int[] color = new int[3];
			int h = bim.getHeight();
			int w = bim.getWidth();
			int offset = 0;
			for (int y = 0; y < h; y++) {
				for (int x = 0; x < w; x++) {
					if (limit < 16) {
						int v = data[offset];
						int r = (int) (v / (double) (limit - 1) * 255 + 0.5);
						int g = 255 - r;
						int b = (v % (limit / 2)) * 255 / (limit / 2);
						color[0] = r;
						color[1] = g;
						color[2] = b;
						raster.setPixel(x, y, color);
					} else {
						if (data[offset] == limit) {
							color[0] = 0;
							color[1] = 0;
							color[2] = 0;
							raster.setPixel(x, y, color);
							// bim.setRGB(x, y, 0);
						} else {
							int lim = limit < 32 ? limit : 32;
							int v = data[offset];
							int r = v * 255 / lim;
							int g = (v % (lim / 4)) * 255 / (lim / 4);
							int b = (v % (lim / 8)) * 255 / (lim / 8);
							color[0] = r;
							color[1] = g;
							color[2] = b;
							raster.setPixel(x, y, color);
							// bim.setRGB(x, y, (r<<16)|(g<<8)|b);
							// bim.setRGB(x, y, 0);
						}
					}
					offset++;
				}
			}
			System.out
					.println("Dojavljujem GUI-ju da ima nove rezultate koje bi trebalo nacrtati...");
			this.repaint();
		}

	}

}

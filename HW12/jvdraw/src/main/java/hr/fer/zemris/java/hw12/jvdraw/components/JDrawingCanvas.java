package hr.fer.zemris.java.hw12.jvdraw.components;

import hr.fer.zemris.java.hw12.jvdraw.geometricalobjects.GeometricalObject;
import hr.fer.zemris.java.hw12.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw12.jvdraw.model.DrawingModelListener;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;

/**
 * Class extends {@link JComponent} and implements {@link DrawingModelListener}.
 * Represents canvas on which components from {@link DrawingModel} are painted
 * 
 * @author Borna
 *
 */
public class JDrawingCanvas extends JComponent implements DrawingModelListener {
	/**
	 * default serial
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Current object that is painting on screen
	 */
	private GeometricalObject currentObject;
	/**
	 * GeometricalObject provider
	 */
	private GeometricalObjectProvider provider;
	/**
	 * Drawing model
	 */
	private DrawingModel model;
	/**
	 * Is first time clicked. If first time currentObject is setted
	 */
	boolean firstTime = true;

	/**
	 * Constructs JDrawingCanvas with given parameters. Adds mouse listeners on
	 * this component
	 * 
	 * @param model
	 *            given {@link DrawingModel}
	 */
	public JDrawingCanvas(DrawingModel model) {
		this.model = model;
		setSize(400, 500);
		setForeground(Color.WHITE);
		setBackground(Color.WHITE);
		setOpaque(false);
		this.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
				if (firstTime) {
					return;
				}
				currentObject.mouseMoved(e.getX(), e.getY());
				JDrawingCanvas.this.repaint();
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				mouseMoved(e);
			}
		});

		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (firstTime) {
					currentObject = provider.getGeometricalObject(e.getX(),
							e.getY());
					firstTime = false;
				} else {
					model.add(currentObject);
					currentObject = null;
					firstTime = true;
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setBackground(Color.WHITE);
		for (int i = 0, length = model.getSize(); i < length; i++) {
			model.getObject(i).paint(g2d);
		}
		if (currentObject != null) {
			currentObject.paint(g);
		}
		g2d.dispose();
	}

	/**
	 * Sets {@link GeometricalObjectProvider} for getting objects
	 * 
	 * @param provider given provider
	 */
	public void setGeomObjectColorProvider(GeometricalObjectProvider provider) {
		this.provider = provider;
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		repaint();
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		repaint();

	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		repaint();

	}
}

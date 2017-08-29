package hr.fer.zemris.java.hw12.jvdraw.model;

import hr.fer.zemris.java.hw12.jvdraw.geometricalobjects.GeometricalObject;

import javax.swing.AbstractListModel;

/**
 * Class extends {@link AbstractListModel} and implements
 * {@link DrawingModelListener}. It uses as adapter between {@link DrawingModel}
 * and {@link javax.swing.JList}
 * 
 * @author Borna
 *
 */
public class DrawingObjectListModel extends
		AbstractListModel<GeometricalObject> implements DrawingModelListener {

	/**
	 * default serial
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Reference to DrawingModel
	 */
	private DrawingModel model;

	/**
	 * Stores reference in intern variable
	 * 
	 * @param model
	 *            reference that is stored
	 */
	public DrawingObjectListModel(DrawingModel model) {
		this.model = model;
	}

	@Override
	public GeometricalObject getElementAt(int index) {
		return model.getObject(index);
	}

	@Override
	public int getSize() {
		return model.getSize();
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		fireIntervalAdded(source, index0, index1);
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		fireIntervalRemoved(source, index0, index1);
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		fireContentsChanged(source, index0, index1);
	}

}

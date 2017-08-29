package hr.fer.zemris.java.hw12.jvdraw.model;

import hr.fer.zemris.java.hw12.jvdraw.geometricalobjects.GeometricalObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Class implements {@link DrawingModel} and has inter list of listeners and
 * stored objects.
 * 
 * @author Borna
 *
 */
public class DrawingModelImpl implements DrawingModel {
	/**
	 * Stored objects.
	 */
	private List<GeometricalObject> objects = new ArrayList<>();
	/**
	 * List of listeners
	 */
	private List<DrawingModelListener> listeners = new LinkedList<>();

	@Override
	public int getSize() {
		return objects.size();
	}

	@Override
	public GeometricalObject getObject(int index) {
		if (index < 0 || index > objects.size() - 1) {
			throw new IllegalArgumentException("Invalid index: " + index);
		}
		return objects.get(index);
	}

	@Override
	public void add(GeometricalObject object) {
		objects.add(object);
		for (DrawingModelListener listener : listeners) {
			listener.objectsAdded(this, objects.size() - 1, objects.size() - 1);
		}
	}

	@Override
	public void addDrawingModelListener(DrawingModelListener l) {
		listeners.add(l);
	}

	@Override
	public void removeDrawingModelListener(DrawingModelListener l) {
		listeners.remove(l);
	}

	@Override
	public void clear() {
		int size = objects.size() == 0 ? 0 : objects.size() - 1;
		objects.clear();
		for (DrawingModelListener listener : listeners) {
			listener.objectsRemoved(this, 0, size);
		}
	}

	@Override
	public void addAll(List<GeometricalObject> list) {
		int size = objects.size();
		objects.addAll(list);
		for (DrawingModelListener listener : listeners) {
			listener.objectsAdded(this, size, objects.size() - 1);
		}
	}

}

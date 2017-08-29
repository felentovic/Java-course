package hr.fer.zemris.java.hw12.jvdraw.model;

import hr.fer.zemris.java.hw12.jvdraw.geometricalobjects.GeometricalObject;

import java.util.List;

/**
 * Interface that presents model for {@link GeometricalObject}s
 * 
 * @author Borna
 *
 */
public interface DrawingModel {

	/**
	 * Retrun drawing model size.
	 * 
	 * @return model size
	 */
	public int getSize();

	/**
	 * Return object on given index.
	 * 
	 * @param index
	 *            index of object in list
	 * @return {@link GeometricalObject} from given index
	 */
	public GeometricalObject getObject(int index);

	/**
	 * Add GeometricalObject to intern list
	 * 
	 * @param object
	 *            object that is added
	 */
	public void add(GeometricalObject object);

	/**
	 * Adds {@link DrawingModelListener} to intern list of listeners.
	 * 
	 * @param l
	 *            listener that is added in intern list
	 */
	public void addDrawingModelListener(DrawingModelListener l);

	/**
	 * Removes {@link DrawingModelListener} from intern list of listeners.
	 * 
	 * @param l
	 *            listener that is removed from intern list
	 */
	public void removeDrawingModelListener(DrawingModelListener l);

	/**
	 * Removes all objects from list.
	 */
	public void clear();

	/**
	 * Adds all objects to intern list of {@link GeometricalObject}s
	 * 
	 * @param list
	 *            list that is added to intern list
	 */
	public void addAll(List<GeometricalObject> list);

}

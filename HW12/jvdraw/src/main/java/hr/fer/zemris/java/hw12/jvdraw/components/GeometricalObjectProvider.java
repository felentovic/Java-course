package hr.fer.zemris.java.hw12.jvdraw.components;

import hr.fer.zemris.java.hw12.jvdraw.geometricalobjects.GeometricalObject;

/**
 * Interface represents class that creates {@link GeometricalObject}s
 * 
 * @author Borna
 *
 */
public interface GeometricalObjectProvider {

	/**
	 * Returns {@link GeometricalObject} with initial x and y coordinates
	 * 
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * @return {@link GeometricalObject} with initial x and y coordinates
	 */
	public GeometricalObject getGeometricalObject(int x, int y);
}

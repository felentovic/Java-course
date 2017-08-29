package hr.fer.zemris.java.hw12.jvdraw.model;

/**
 * Interface represents listener on {@link DrawingModel}.
 * 
 * @author Borna
 *
 */
public interface DrawingModelListener {

	/**
	 * Method is called if objects are added into {@link DrawingModel}
	 * 
	 * @param source
	 *            {@link DrawingModel} source
	 * @param index0
	 *            index of last object before adding new ones
	 * @param index1
	 *            index of last object that is added
	 */
	public void objectsAdded(DrawingModel source, int index0, int index1);

	/**
	 * Method is called if objects are removed from {@link DrawingModel}
	 * 
	 * @param source
	 *            {@link DrawingModel} source
	 * @param index0
	 *            index of last object before removing
	 * @param index1
	 *            index of last object that is removed
	 */
	public void objectsRemoved(DrawingModel source, int index0, int index1);

	/**
	 * Method is called if something is changed in {@link DrawingModel}
	 * 
	 * @param source
	 *            {@link DrawingModel} source
	 * @param index0
	 *            index of last object before changing
	 * @param index1
	 *            index of last object that is changed
	 */
	public void objectsChanged(DrawingModel source, int index0, int index1);
}

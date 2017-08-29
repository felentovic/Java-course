package hr.fer.zemris.java.hw12.jvdraw.components;

/**
 * Interface represents subject that has listeners to color change
 * 
 * @author Borna
 *
 */
public interface ColorChangeSubject {

	/**
	 * Adds given listener to inter list
	 * 
	 * @param l
	 *            given listener
	 */
	public void addColorChangeListener(ColorChangeListener l);

	/**
	 * Removes given listener from list
	 * 
	 * @param l
	 *            given listener
	 */
	public void removeColorChangeListener(ColorChangeListener l);

}

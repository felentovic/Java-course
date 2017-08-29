package hr.fer.zemris.java.hw12.jvdraw.components.actions;

import hr.fer.zemris.java.hw12.jvdraw.components.JVDraw;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

/**
 * Class extends {@link AbstractAction} and empty existing model. Ask user to
 * save image before creting new one.
 * 
 * @author Borna
 *
 */
public class NewAction extends AbstractAction {

	/**
	 * default serial
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * reference to {@link JVDraw}
	 */
	private JVDraw jvdraw;

	/**
	 * Constructs action with name {@code "New"}
	 * 
	 * @param jvdraw
	 *            reference
	 */
	public NewAction(JVDraw jvdraw) {
		putValue(Action.NAME, "New");
		this.jvdraw = jvdraw;

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (jvdraw.isModified && !jvdraw.askToSave()) {
			return;
		}

		jvdraw.model.clear();
	}

}

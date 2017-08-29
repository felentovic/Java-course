package hr.fer.zemris.java.hw12.jvdraw.components.actions;

import hr.fer.zemris.java.hw12.jvdraw.components.JVDraw;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

/**
 * Class extends {@link AbstractAction} and represents exit action. Asks user
 * before exiting
 * 
 * @author Borna
 *
 */
public class ExitAction extends AbstractAction {
	
	/**
	 * default serial
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * reference to {@link JVDraw}
	 */
	private JVDraw jvdraw;
	
	/**
	 * Constructs action with given parameter and name {@code "Exit"}
	 * @param jvdraw reference
	 */
	public ExitAction(JVDraw jvdraw) {
		putValue(Action.NAME, "Exit");
		this.jvdraw = jvdraw;

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (jvdraw.isModified && !jvdraw.askToSave()) {
			return;
		}
		jvdraw.dispose();

	}

}

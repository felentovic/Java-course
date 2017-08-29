package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Class extends {@link JFrame} and used as container for {@link JScrollPane}
 * from {@link JList}
 * 
 * @author Borna Feldšar
 * @version 1.0
 *
 */
public class PrimDemo extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Main method
	 * 
	 * @param args
	 *            main method arguments
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new PrimDemo();
			frame.setVisible(true);

		});
	}

	/**
	 * Constructs PrimDemo and initialize gui
	 */
	public PrimDemo() {
		setLocation(20, 50);
		setSize(300, 400);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGui();
	}

	/**
	 * Initialize gui that have two {@link ScrollPane} with {@link JList} that
	 * have view on {@link PrimListModel}
	 */
	private void initGui() {
		setTitle("Prim");
		PrimListModel model = new PrimListModel();
		JList<Integer> list1 = new JList<Integer>(model);
		JScrollPane scroll1 = new JScrollPane(list1);

		JList<Integer> list2 = new JList<Integer>(model);
		JScrollPane scroll2 = new JScrollPane(list2);

		JPanel panel = new JPanel(new GridLayout(1, 2));
		panel.add(scroll1);
		panel.add(scroll2);
		JButton next = new JButton("next");
		setLayout(new BorderLayout());
		getContentPane().add(next, BorderLayout.PAGE_END);
		getContentPane().add(panel, BorderLayout.CENTER);

		ActionListener pressedNext = e -> {
			model.next();
			int lastIndex = list2.getModel().getSize() - 1;
			list1.ensureIndexIsVisible(lastIndex);
			list2.ensureIndexIsVisible(lastIndex);

		};

		next.addActionListener(pressedNext);

	}
}

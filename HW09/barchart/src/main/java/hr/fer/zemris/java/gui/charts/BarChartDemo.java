package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Class extends {@link JFrame} and used as container for
 * {@link BarChartComponent}, also have main method that reads information from
 * file. Path to file is entered as command arguments
 * 
 * @author Borna Feldšar
 * @version 1.0
 */
public class BarChartDemo extends JFrame {

	private static final long serialVersionUID = 1L;
	private BarChart model;
	private Path source;

	/**
	 * Main method
	 * 
	 * @param args
	 *            main method arguments
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("You need to enter file path.");
			return;
		}
		final Path source;
		try {
			source = Paths.get(args[0]).toAbsolutePath().normalize();
		} catch (Exception e) {
			System.out.println("Cannot convert to path: " + args[0]);
			return;
		}

		List<String> lines = null;
		try {
			lines = Files.readAllLines(source);
		} catch (IOException e) {
			System.out.println("File:" + args[0] + "does not exist");
			return;
		}

		if (lines.size() < 6) {
			System.out.println("File must contain at least 5 lines.");
			return;
		}

		List<XYValue> values;
		try {
			values = parseXYValues(lines.get(2));
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			return;
		}

		int yMin = Integer.parseInt(lines.get(3));
		int yMax = Integer.parseInt(lines.get(4));
		int space = Integer.parseInt(lines.get(5));

		BarChart model = new BarChart(values, lines.get(0), lines.get(1), yMin,
				yMax, space);

		SwingUtilities.invokeLater(() -> {
			JFrame frame = new BarChartDemo(model, source);
			frame.pack();
			frame.setVisible(true);
		});

	}

	/**
	 * Constructs barchart from barchart model and file source.
	 * 
	 * @param model
	 *            {@link BarChart} model from file source
	 * @param source
	 *            file with barchart informations
	 */
	public BarChartDemo(BarChart model, Path source) {
		setLocation(20, 50);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.model = model;
		this.source = source;
		initGUI();
	}

	/**
	 * Parses given string into list of {@link XYValue}
	 * 
	 * @param str
	 *            string that is parsed
	 * @return list of XYValue from parsed string
	 */
	private static List<XYValue> parseXYValues(String str) {
		String[] components = str.split("\\s+");

		List<XYValue> values = new LinkedList<XYValue>();
		for (String comp : components) {
			values.add(XYValue.parseString(comp));

		}
		return values;

	}
	
	/**
	 * Initialize gui components.
	 */
	private void initGUI() {
		setTitle("Barchart");
		JLabel labela = new JLabel(source.toString());
		labela.setHorizontalAlignment(SwingConstants.CENTER);
		labela.setOpaque(true);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(new BarChartComponent(model), BorderLayout.CENTER);
		getContentPane().add(labela, BorderLayout.PAGE_START);
		setMinimumSize(getMinimumSize());

	}
}

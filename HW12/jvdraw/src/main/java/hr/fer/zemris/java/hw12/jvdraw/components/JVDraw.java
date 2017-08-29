package hr.fer.zemris.java.hw12.jvdraw.components;

import hr.fer.zemris.java.hw12.jvdraw.components.actions.ExitAction;
import hr.fer.zemris.java.hw12.jvdraw.components.actions.ExportAction;
import hr.fer.zemris.java.hw12.jvdraw.components.actions.NewAction;
import hr.fer.zemris.java.hw12.jvdraw.components.actions.OpenAction;
import hr.fer.zemris.java.hw12.jvdraw.components.actions.SaveAction;
import hr.fer.zemris.java.hw12.jvdraw.components.actions.SaveAsAction;
import hr.fer.zemris.java.hw12.jvdraw.geometricalobjects.Circle;
import hr.fer.zemris.java.hw12.jvdraw.geometricalobjects.FilledCircle;
import hr.fer.zemris.java.hw12.jvdraw.geometricalobjects.GeometricalObject;
import hr.fer.zemris.java.hw12.jvdraw.geometricalobjects.Line;
import hr.fer.zemris.java.hw12.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw12.jvdraw.model.DrawingModelImpl;
import hr.fer.zemris.java.hw12.jvdraw.model.DrawingModelListener;
import hr.fer.zemris.java.hw12.jvdraw.model.DrawingObjectListModel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

/**
 * Class extends {@link JFrame} and implements {@link GeometricalObjectProvider}
 * Represents main frame and has main method. JColorArea for background color is visible
 * only if filled circle is selected.
 * 
 * @author Borna
 *
 */
public class JVDraw extends JFrame implements GeometricalObjectProvider,
		DrawingModelListener {
	/**
	 * default serial
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Buttons group. Used for GeometricalObjects
	 */
	private ButtonGroup buttons;
	/**
	 * Area represents foreground color
	 */
	private JColorArea foreground;
	/**
	 * Area represents background color
	 */
	private JColorArea background;
	/**
	 * Counts number of circles
	 */
	private int circleCounter;
	/**
	 * Counts number of filled circles
	 */
	private int filledCcounter;
	/**
	 * Counts number of lines
	 */
	private int lineCounter;
	/**
	 * Source path of picture
	 */
	public Path source;
	/**
	 * Is file modified
	 */
	public boolean isModified;
	/**
	 * Drawing model
	 */
	public DrawingModel model;
	/**
	 * Canvas
	 */
	private JDrawingCanvas canvas;

	/**
	 * Constructs and initilazite GUI
	 */
	public JVDraw() {
		setLocation(300, 300);
		setSize(600, 400);
		initGUI();
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setForeground(Color.WHITE);
		setBackground(Color.WHITE);
	}

	/**
	 * Initialize all GUI
	 */
	private void initGUI() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ignorable) {
		}
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (JVDraw.this.isModified && !askToSave()) {
					return;
				}
				dispose();

			}
		});
		setLayout(new BorderLayout());
		getContentPane().add(createModel(), BorderLayout.LINE_END);
		getContentPane().add(createToolbar(), BorderLayout.PAGE_START);
		getContentPane().add(createLabelsPanel(), BorderLayout.PAGE_END);
		createMenu();
	}

	/**
	 * Creates menus
	 */
	private void createMenu() {
		JMenuBar menu = new JMenuBar();
		JMenu file = new JMenu("File");
		menu.add(file);
		setJMenuBar(menu);
		file.add(new JMenuItem(new NewAction(this)));
		file.add(new JMenuItem(new OpenAction(this)));
		file.add(new JMenuItem(save));
		file.add(new JMenuItem(saveAs));
		file.add(new JMenuItem(new ExportAction(this)));
		file.addSeparator();
		file.add(new JMenuItem(new ExitAction(this)));

	}

	/**
	 * save as action
	 */
	public Action saveAs = new SaveAsAction(this);
	/**
	 * save action
	 */
	public Action save = new SaveAction(this);

	/**
	 * Creates Toolbar and returns it
	 * 
	 * @return created {@link JToolBar}
	 */
	private JToolBar createToolbar() {
		foreground = new JColorArea(new Color(229, 156, 156));
		background = new JColorArea(new Color(72, 144, 206));
		JToolBar toolbar = new JToolBar("Tools");
		toolbar.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 0));
		addButtons(toolbar);
		toolbar.addSeparator();
		toolbar.addSeparator();
		toolbar.addSeparator();
		toolbar.add(foreground);
		toolbar.add(background);
		background.setVisible(false);
		return toolbar;

	}

	/**
	 * Adds toogle buttons to given toolbar
	 * 
	 * @param toolbar
	 *            given toolbar
	 */
	private void addButtons(JToolBar toolbar) {
		buttons = new ButtonGroup();
		JToggleButton line = new JToggleButton("Line");
		line.setActionCommand("line");
		buttons.add(line);
		line.setFocusPainted(false);
		line.addActionListener((e) -> {
			background.setVisible(false);
		});
		line.setIcon(new ImageIcon("./icons/line.png"));
		line.setSelected(true);
		
		JToggleButton circle = new JToggleButton("Circle");
		circle.setActionCommand("circle");
		circle.setFocusPainted(false);
		circle.addActionListener((e) -> {
			background.setVisible(false);
		});
		circle.setIcon(new ImageIcon("./icons/circle.png"));
		buttons.add(circle);
		
		JToggleButton filledCircle = new JToggleButton("Filled circle");
		filledCircle.setActionCommand("filledCircle");
		filledCircle.setFocusPainted(false);
		filledCircle.addActionListener((e) -> {
			background.setVisible(true);
		});
		filledCircle.setIcon(new ImageIcon("./icons/filled_circle.png"));
		buttons.add(filledCircle);
		
		toolbar.add(line);
		toolbar.add(circle);
		toolbar.add(filledCircle);
	}

	/**
	 * Creates labels for background and foreground color
	 * 
	 * @return JPanel that contains labels
	 */
	private JPanel createLabelsPanel() {
		JPanel labels = new JPanel(new GridLayout(1, 2));
		ColorLabel foregroundLabel = new ColorLabel("Foreground color: ",
				foreground.getCurrentColor());
		foreground.addColorChangeListener(foregroundLabel);
		labels.add(foregroundLabel);
		ColorLabel backgroundLabel = new ColorLabel("Background color: ",
				foreground.getCurrentColor());
		background.addColorChangeListener(backgroundLabel);
		labels.add(backgroundLabel);
		return labels;
	}

	/**
	 * Creates {@link DrawingModel} and {@link JList}. JList is adde into
	 * {@link JScrollPane}
	 * 
	 * @return {@link JList} in {@link JScrollPane}
	 */
	private JScrollPane createModel() {
		model = new DrawingModelImpl();
		DrawingObjectListModel listModel = new DrawingObjectListModel(model);
		model.addDrawingModelListener(listModel);

		canvas = new JDrawingCanvas(model);
		canvas.setGeomObjectColorProvider(this);
		getContentPane().add(canvas, BorderLayout.CENTER);

		JList<GeometricalObject> list = new JList<GeometricalObject>(listModel);
		list.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				JPanel property = model.getObject(list.getSelectedIndex())
						.getProperty();
				JOptionPane.showConfirmDialog(null, property, "Properties",
						JOptionPane.PLAIN_MESSAGE,
						JOptionPane.INFORMATION_MESSAGE);
				canvas.repaint();
			}
		});
		Dimension size = new Dimension(getWidth() / 6, 0);
		JScrollPane scroll = new JScrollPane(list);
		scroll.setPreferredSize(size);
		model.addDrawingModelListener(canvas);
		model.addDrawingModelListener(this);
		return scroll;

	}

	/**
	 * Asks user to save file. Returns false if action is canceled, otherwise
	 * true.
	 * 
	 * @return false if action is canceled, otherwise true.
	 * 
	 */
	public boolean askToSave() {
		int r = JOptionPane.showConfirmDialog(JVDraw.this,
				"File is modified. Do you want to save it?", "Warning",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (r == JOptionPane.CANCEL_OPTION || r == JOptionPane.CLOSED_OPTION) {
			return false;

		} else if (r == JOptionPane.YES_OPTION) {
			save.actionPerformed(null);
		}
		return true;
	}

	/**
	 * Returns model as text. Every object in new line
	 * 
	 * @return model as text
	 */
	public String getModelAsText() {
		StringBuilder sb = new StringBuilder();

		for (int i = 0, length = model.getSize(); i < length; i++) {
			sb.append(model.getObject(i).export()).append("\n");
		}
		return sb.toString();
	}

	/**
	 * Returns minimal image dimensions. Starting and ending positions.
	 * 
	 * @return minimal image dimensions
	 */
	public ImageDimension getImageDimension() {
		int length = model.getSize();
		if (length == 0) {
			return new ImageDimension();
		}
		ImageDimension dimension = new ImageDimension(model.getObject(0));

		for (int i = 1; i < length; i++) {
			GeometricalObject object = model.getObject(i);
			if (dimension.minX > object.getMinX()) {
				dimension.minX = object.getMinX();
			}
			if (dimension.maxX < object.getMaxX()) {
				dimension.maxX = object.getMaxX();
			}
			if (dimension.minY > object.getMinY()) {
				dimension.minY = object.getMinY();
			}

			if (dimension.maxY < object.getMaxY()) {
				dimension.maxY = object.getMaxY();
			}
		}
		return dimension;
	}

	/**
	 * Class represents image dimensions with start and ending positions
	 * 
	 * @author Borna
	 *
	 */
	public static class ImageDimension {
		/**
		 * Minimal x coordinate
		 */
		public int minX;
		/**
		 * Maximal x coordinate
		 */
		public int maxX;
		/**
		 * Minimal y coordinate
		 */
		public int minY;
		/**
		 * Maximal y coordinate
		 */
		public int maxY;

		/**
		 * Initialize variables to zero
		 */
		public ImageDimension() {
		}

		/**
		 * Initialize variables to object size
		 * 
		 * @param object
		 *            given object
		 */
		public ImageDimension(GeometricalObject object) {
			minX = object.getMinX();
			maxX = object.getMaxX();
			minY = object.getMinY();
			maxY = object.getMaxY();
		}
	}

	/**
	 * Adds parsed given file content to model. First clears model.
	 * 
	 * @param file
	 *            given file
	 * @throws IllegalArgumentException
	 *             if content can not be parsed
	 */
	public void addToModel(String file) {
		List<GeometricalObject> objects = new LinkedList<>();
		String[] lines = file.split("\n");
		int circleCounter = 0;
		int filledCcounter = 0;
		int lineCounter = 0;
		for (String line : lines) {
			if (line.startsWith("CIRCLE")) {
				circleCounter++;
				GeometricalObject object = Circle.parseFromString(line,
						"Circle" + circleCounter);
				objects.add(object);
			} else if (line.startsWith("LINE")) {
				lineCounter++;
				GeometricalObject object = Line.parseFromString(line, "Line"
						+ lineCounter);
				objects.add(object);
			} else if (line.startsWith("FCIRCLE")) {
				filledCcounter++;
				GeometricalObject object = FilledCircle.parseFromString(line,
						"Filled circle" + filledCcounter);
				objects.add(object);
			} else {
				throw new IllegalArgumentException("Invalid line " + line);
			}
		}
		model.clear();
		model.addAll(objects);
		JVDraw.this.circleCounter = circleCounter;
		JVDraw.this.filledCcounter = filledCcounter;
		JVDraw.this.lineCounter = lineCounter;
	}

	@Override
	public GeometricalObject getGeometricalObject(int x, int y) {
		String selButt = buttons.getSelection().getActionCommand();
		if (selButt.equals("circle")) {
			circleCounter++;
			return new Circle(x, y, 0, foreground.getCurrentColor(), "Circle"
					+ circleCounter);
		} else if (selButt.equals("line")) {
			lineCounter++;
			return new Line(x, y, x, y, foreground.getCurrentColor(), "Line"
					+ lineCounter);
		} else {
			filledCcounter++;
			return new FilledCircle(x, y, 0, background.getCurrentColor(),
					foreground.getCurrentColor(), "FilledCircle"
							+ filledCcounter);
		}
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		isModified = true;
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		isModified = true;

	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		isModified = true;

	}

	/**
	 * Main method with arguments
	 * 
	 * @param args
	 *            main method arguments
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new JVDraw();
			frame.setVisible(true);
		});
	}
}

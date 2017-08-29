package hr.fer.zemris.java.hw12.jvdraw.components.actions;

import hr.fer.zemris.java.hw12.jvdraw.components.JVDraw;
import hr.fer.zemris.java.hw12.jvdraw.components.JVDraw.ImageDimension;
import hr.fer.zemris.java.hw12.jvdraw.geometricalobjects.GeometricalObject;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Class extends {@link AbstractAction} and represents export action. Exports
 * given model to the image.
 * 
 * @author Borna
 *
 */
public class ExportAction extends AbstractAction {
	/**
	 * default serial
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * reference to {@link JVDraw}
	 */
	private JVDraw jvdraw;

	/**
	 * Constructs action with name {@code "Export"}
	 * 
	 * @param jvdraw
	 *            reference
	 */
	public ExportAction(JVDraw jvdraw) {
		putValue(Action.NAME, "Export");
		this.jvdraw = jvdraw;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		ImageDimension dimension = jvdraw.getImageDimension();
		int box_width = dimension.maxX - dimension.minX;
		int box_height = dimension.maxY - dimension.minY;
		BufferedImage image = new BufferedImage(box_width + 1, box_height + 1,
				BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g = image.createGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, box_width, box_height);
		for (int i = 0, length = jvdraw.model.getSize(); i < length; i++) {
			GeometricalObject object = jvdraw.model.getObject(i);
			object.setOffset(dimension.minX, dimension.minY);
			object.paint(g);
			object.setOffset(0, 0);
		}

		g.dispose();
		// ------------file chooser-------------------------------
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(Paths.get("./").toFile());
		fc.setDialogTitle("Export picture");
		FileNameExtensionFilter jvdfilter = new FileNameExtensionFilter(
				"image files", "png", "jpg", "gif");
		fc.setFileFilter(jvdfilter);
		if (fc.showSaveDialog(null) != JFileChooser.APPROVE_OPTION) {
			return;
		}

		Path selectedPath = fc.getSelectedFile().toPath();
		String path = selectedPath.toString();
		if (!path.endsWith("jpg") && !path.endsWith("png")
				&& !path.endsWith("gif")) {
			JOptionPane.showMessageDialog(null,
					"Invalid file: " + selectedPath.getFileName(), "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (Files.exists(selectedPath)) {
			int r = JOptionPane.showConfirmDialog(null, "File " + selectedPath
					+ "already exist.\n Do you want to replace it?",
					"Confirm SaveAs", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE);
			if (r != JOptionPane.YES_OPTION) {
				return;
			}
		}
		File file = selectedPath.toFile();
		try {
			ImageIO.write(image, "png", file);
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(null, "Something went wrong",
					"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		JOptionPane.showMessageDialog(null, "Picture successfully exported",
				"Success", JOptionPane.INFORMATION_MESSAGE);
	}
}

package hr.fer.zemris.java.gui.charts;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;

import javax.swing.JComponent;

/**
 * Class extendes {@link JComponent} and represents component for
 * {@link BarChart}. Draws barchart with given informations
 * 
 * @author Borna Feldšar
 * @version 1.0
 *
 */
public class BarChartComponent extends JComponent {

	private static final long serialVersionUID = 1L;
	private BarChart barchart;
	private int BOTTOM_TO_DESC = 20;
	private int NUM_TO_AXIS = 20;
	private int DESC_TO_NUM = 20;
	private int DASH = 5;
	private int GRAPH_OFFSET = 20;
	private int DASH_SPACE = 2;

	/**
	 * Constructs {@link BarChartComponent} for given barchart
	 * 
	 * @param barchart
	 *            barchart that is drawen
	 */
	public BarChartComponent(BarChart barchart) {
		this.barchart = barchart;
		setBackground(Color.WHITE);
		setSize(700, 500);
	}

	@Override
	protected void paintComponent(Graphics g) {
		Insets ins = getInsets();
		Dimension size = getSize();

		FontMetrics fm = g.getFontMetrics();
		int maxYwidth = fm.stringWidth(Integer.valueOf(barchart.getyMax())
				.toString());

		int fontHeight = fm.getHeight();
		int graphStartX = BOTTOM_TO_DESC + fontHeight + DESC_TO_NUM + maxYwidth
				+ NUM_TO_AXIS;
		int graphStartY = BOTTOM_TO_DESC + fontHeight + DESC_TO_NUM
				+ NUM_TO_AXIS;

		int width = size.width - (ins.left + ins.right);
		int height = size.height - (ins.top + ins.bottom);

		int graphWidth = size.width - (ins.left + ins.right) - graphStartX
				- GRAPH_OFFSET;
		int graphHeight = size.height - (ins.top + ins.bottom) - graphStartY
				- GRAPH_OFFSET;

		int numberOfCellsYaxis = (barchart.getyMax() - barchart.getyMin())
				/ barchart.getSpace();
		int cellWidth = graphWidth / barchart.getnumberOfValues();
		int cellHeight = graphHeight / numberOfCellsYaxis;

		Graphics2D g2d = (Graphics2D) g.create();

		drawDescriptions(width, height, fm, g2d);
		drawNumbers(graphStartX, graphStartY, cellWidth, height, cellHeight,
				numberOfCellsYaxis, fm, g2d);

		drawGrid(graphStartX, graphStartY, graphWidth, graphHeight, height,
				cellHeight, cellWidth, numberOfCellsYaxis, g2d);
		drawShades(graphStartX, graphStartY, graphHeight, numberOfCellsYaxis,
				cellWidth, cellHeight, height, g2d);
		drawColumns(graphStartX, graphStartY, graphHeight, numberOfCellsYaxis,
				cellWidth, cellHeight, height, g2d);
		drawAxis(graphStartX, graphStartY, graphWidth, graphHeight,
				numberOfCellsYaxis, cellWidth, cellHeight, height, g2d);

		g2d.dispose();
	}

	/**
	 * Draws shades behind columns.
	 * 
	 * @param graphStartX
	 *            x coordinate for graph start
	 * @param graphStartY
	 *            y coordinate for graph start from bottom
	 * @param graphHeight
	 *            graph height
	 * @param numberOfCellsYaxis
	 *            number of cells on Y Axis
	 * @param cellWidth
	 *            cell width
	 * @param cellHeight
	 *            cell height
	 * @param height
	 *            component height
	 * @param g2d
	 *            graphics object
	 */
	private void drawShades(int graphStartX, int graphStartY, int graphHeight,
			int numberOfCellsYaxis, int cellWidth, int cellHeight, int height,
			Graphics2D g2d) {
		g2d.setColor(new Color(210, 199, 194));
		g2d.setComposite(AlphaComposite.SrcOver.derive(0.6f));

		int shadowSpace = 5;
		int shadowLength = cellWidth / 18;
		for (XYValue coordinates : barchart.getValues()) {
			int rectHeight = coordinates.getY() / barchart.getSpace()
					* cellHeight - shadowSpace;

			g2d.fillRect(graphStartX + (coordinates.getX()) * cellWidth, height
					- (graphStartY + rectHeight), shadowLength, rectHeight);
		}
		g2d.setComposite(AlphaComposite.SrcOver);

	}

	/**
	 * Draws columns
	 * 
	 * @param graphStartX
	 *            x coordinate for graph start
	 * @param graphStartY
	 *            y coordinate for graph start from bottom
	 * @param graphHeight
	 *            graph height
	 * @param numberOfCellsYaxis
	 *            number of cells on Y Axis
	 * @param cellWidth
	 *            cell width
	 * @param cellHeight
	 *            cell height
	 * @param height
	 *            component height
	 * @param g2d
	 *            graphics object
	 */
	private void drawColumns(int graphStartX, int graphStartY, int graphHeight,
			int numberOfCellsYaxis, int cellWidth, int cellHeight, int height,
			Graphics2D g2d) {
		g2d.setColor(new Color(255, 139, 88));
		for (XYValue coordinates : barchart.getValues()) {
			int rectHeight = coordinates.getY() / barchart.getSpace()
					* cellHeight;
			g2d.fillRect(graphStartX + (coordinates.getX() - 1) * cellWidth,
					height - (graphStartY + rectHeight), cellWidth, rectHeight);
		}
		// draw lines between columns
		g2d.setColor(new Color(252, 184, 112));
		for (int i = 1; i <= barchart.getnumberOfValues(); i++) {
			int x = graphStartX + i * cellWidth;
			g2d.drawLine(x, height - graphStartY, x, height
					- (graphStartY + graphHeight + DASH_SPACE));
		}
	}

	/**
	 * Draws grid behind the columns
	 * 
	 * @param graphStartX
	 *            x coordinate for graph start
	 * @param graphStartY
	 *            y coordinate for graph start from bottom
	 * @param graphWidth
	 *            graph width
	 * @param graphHeight
	 *            graph height
	 * @param numberOfCellsYaxis
	 *            number of cells on Y Axis
	 * @param cellWidth
	 *            cell width
	 * @param cellHeight
	 *            cell height
	 * @param height
	 *            component height
	 * @param g2d
	 *            graphics object
	 */
	private void drawGrid(int graphStartX, int graphStartY, int graphWidth,
			int graphHeight, int height, int cellHeight, int cellWidth,
			int numberOfCellsYaxis, Graphics2D g2d) {
		g2d.setColor(new Color(252, 184, 112));

		// horizontal lines
		for (int i = 1; i <= numberOfCellsYaxis; i++) {
			int y = height - (graphStartY + i * cellHeight);
			g2d.drawLine(graphStartX, y, graphStartX + graphWidth + DASH_SPACE,
					y);
		}

		// vertical lines
		for (int i = 1; i <= barchart.getnumberOfValues(); i++) {
			int x = graphStartX + i * cellWidth;
			g2d.drawLine(x, height - graphStartY, x, height
					- (graphStartY + graphHeight + DASH_SPACE));
		}
	}

	/**
	 * Draws X and Y axis with dashes for every x and y
	 * 
	 * @param graphStartX
	 *            x coordinate for graph start
	 * @param graphStartY
	 *            y coordinate for graph start from bottom
	 * @param graphWidth
	 *            graph width
	 * @param graphHeight
	 *            graph height
	 * @param numberOfCellsYaxis
	 *            number of cells on Y Axis
	 * @param cellWidth
	 *            cell width
	 * @param cellHeight
	 *            cell height
	 * @param height
	 *            component height
	 * @param g2d
	 *            graphics object
	 */
	private void drawAxis(int graphStartX, int graphStartY, int graphWidth,
			int graphHeight, int numberOfCellsYaxis, int cellWidth,
			int cellHeight, int height, Graphics2D g2d) {
		Stroke defaultStroke = g2d.getStroke();
		g2d.setStroke(new BasicStroke(2));
		g2d.setColor(Color.GRAY);
		int yAx = height - (graphStartY);

		int dashLength = GRAPH_OFFSET - DASH_SPACE - 8;
		int dashStroke = dashLength / 2;
		// x axis
		g2d.drawLine(graphStartX, yAx, graphStartX + graphWidth + DASH_SPACE,
				yAx);
		for (int i = 0; i <= barchart.getnumberOfValues(); i++) {
			int x = graphStartX + i * cellWidth;
			g2d.drawLine(x, height - graphStartY, x, height
					- (graphStartY - DASH));
		}
		int xDash = graphStartX + graphWidth + DASH_SPACE;
		g2d.fillPolygon(new int[] { xDash, xDash + dashLength, xDash },
				new int[] { height - (graphStartY + dashLength - dashStroke),
						height - graphStartY,
						height - (graphStartY - dashLength + dashStroke) }, 3);

		// y axis
		g2d.drawLine(graphStartX, yAx, graphStartX, height
				- (graphStartY + graphHeight + DASH_SPACE));
		for (int i = 0; i <= numberOfCellsYaxis; i++) {
			int y = height - (graphStartY + i * cellHeight);
			g2d.drawLine(graphStartX, y, graphStartX - DASH, y);
		}

		int yDash = height - (graphStartY + graphHeight + DASH_SPACE);
		g2d.fillPolygon(
				new int[] { graphStartX - dashStroke, graphStartX + dashStroke,
						graphStartX },
				new int[] {
						yDash,
						yDash,
						height
								- (graphStartY + graphHeight + dashLength + DASH_SPACE) },
				3);
		g2d.setStroke(defaultStroke);
	}

	/**
	 * Draws numbers for axis.
	 * 
	 * @param graphStartX
	 *            x coordinate for graph start
	 * @param graphStartY
	 *            y coordinate for graph start from bottom
	 * @param numberOfCellsYaxis
	 *            number of cells on Y Axis
	 * @param cellWidth
	 *            cell width
	 * @param cellHeight
	 *            cell height
	 * @param height
	 *            component height
	 * @param fm
	 *            component {@link FontMetrics}
	 * @param g2d
	 *            graphics object
	 */
	private void drawNumbers(int graphStartX, int graphStartY, int cellWidth,
			int height, int cellHeight, int numberOfCellsYaxis, FontMetrics fm,
			Graphics2D g2d) {
		g2d.setColor(Color.BLACK);
		Font font = this.getFont();
		this.setFont(font.deriveFont(Font.BOLD));
		// draw numbers on Xaxis
		for (int i = 0; i < barchart.getnumberOfValues(); i++) {
			String str = Integer.valueOf(i + 1).toString();
			int x = graphStartX + i * cellWidth + cellWidth / 2
					- fm.stringWidth(str) / 2;
			int y = height - (graphStartY - NUM_TO_AXIS);
			g2d.drawString(str, x, y);
		}

		// draw numbers on Yaxis
		int shiftDown = fm.getAscent() / 2;
		for (int i = 0; i <= numberOfCellsYaxis; i++) {
			String str = Integer.valueOf(
					barchart.getyMin() + i * barchart.getSpace()).toString();
			int x = graphStartX - NUM_TO_AXIS - fm.stringWidth(str) + 1;
			int y = height - (graphStartY + i * cellHeight);
			g2d.drawString(str, x, y + shiftDown);
		}
	}

	/**
	 * Draws description on axis.
	 * 
	 * @param width
	 *            component width
	 * @param height
	 *            component height
	 * @param fm
	 *            component {@link FontMetrics}
	 * @param g2d
	 *            graphics object
	 */
	private void drawDescriptions(int width, int height, FontMetrics fm,
			Graphics2D g2d) {
		g2d.setColor(Color.GRAY);

		// draw Xaxis decription
		int xDescX = width / 2 - fm.stringWidth(barchart.getxDescription()) / 2;
		int xDescY = height - BOTTOM_TO_DESC;
		g2d.drawString(barchart.getxDescription(), xDescX, xDescY);

		// draw Yaxis description
		AffineTransform at = AffineTransform.getQuadrantRotateInstance(3);
		AffineTransform defaultAt = g2d.getTransform();
		g2d.setTransform(at);
		int yDescX = -(height / 2 - fm.stringWidth(barchart.getyDescription()) / 2);
		int yDescY = BOTTOM_TO_DESC;
		g2d.drawString(barchart.getyDescription(), yDescX, yDescY);
		g2d.setTransform(defaultAt);
	}

}

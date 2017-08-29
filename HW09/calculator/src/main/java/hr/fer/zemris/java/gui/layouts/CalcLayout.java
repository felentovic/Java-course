package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CalcLayout implements {@link LayoutManager2} interface intended for creating
 * simple calculator. It has seven columns and five rows.
 * 
 * @author Borna
 *
 */
public class CalcLayout implements LayoutManager2 {
	private int gap;
	private Map<Component, RCPosition> constraintsMap;
	private int NUMBER_OF_COLUMNS = 7;
	private int NUMBER_OF_ROWS = 5;
	private final static List<RCPosition> forbiddenPositions = Arrays.asList(
			new RCPosition(1, 2), new RCPosition(1, 3), new RCPosition(1, 4),
			new RCPosition(1, 5));

	/**
	 * Constructs {@link CalcLayout} with no gaps between components.
	 */
	public CalcLayout() {
		this(0);
	}

	/**
	 * Constructs {@link CalcLayout} with given gap size between components.
	 * 
	 * @param gap
	 *            gap size between components
	 */
	public CalcLayout(int gap) {
		this.gap = gap;
		constraintsMap = new HashMap<>();
	}

	/**
	 * Not used.
	 */
	@Override
	public void addLayoutComponent(String name, Component comp) {
	}

	@Override
	public void layoutContainer(Container parent) {
		synchronized (parent.getTreeLock()) {
			Insets insets = parent.getInsets();
			int ncomponents = parent.getComponentCount();

			if (ncomponents == 0) {
				return;
			}

			// Total parent dimensions
			Dimension size = parent.getSize();
			int totalW = size.width - (insets.left + insets.right);
			int totalH = size.height - (insets.top + insets.bottom);

			// Cell dimensions, including padding
			int totalCellW = totalW / NUMBER_OF_COLUMNS;
			int totalCellH = totalH / NUMBER_OF_ROWS;

			// Cell dimensions, without padding
			int cellW = (totalW - ((NUMBER_OF_COLUMNS + 1) * gap))
					/ NUMBER_OF_COLUMNS;
			int cellH = (totalH - ((NUMBER_OF_ROWS + 1) * gap))
					/ NUMBER_OF_ROWS;

			RCPosition biggestCell = new RCPosition(1, 1);

			for (int i = 0; i < ncomponents; i++) {
				Component c = parent.getComponent(i);
				RCPosition position = constraintsMap.get(c);
				if (position != null) {
					int w;
					if (position.equals(biggestCell)) {
						w = (5 * (cellW - gap) + 4 * gap);
					} else {
						w = ((cellW) - gap);
					}

					int x = (insets.left
							+ (totalCellW * (position.getCol() - 1)) + gap);
					int y = (insets.top
							+ (totalCellH * (position.getRow() - 1)) + gap);
					int h = ((cellH) - gap);
					c.setBounds(x, y, w, h);
				}
			}
		}
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return getLayoutSize(parent, false);
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return getLayoutSize(parent, true);
	}

	/**
	 * Returns largest preffered cell size in this container if isPreffered is
	 * true, otherwise largest minimum cell size.
	 * 
	 * @param parent
	 *            parent component
	 * @param isPreferred
	 *            if true returned size is preffered, otherwise returned size is
	 *            minimal
	 * @return largest preffered cell size in this container if isPreffered is
	 *         true, otherwise largest minimum cell size.
	 */
	private Dimension getLargestCellSize(Container parent, boolean isPreferred) {
		int ncomponents = parent.getComponentCount();
		Dimension maxCellSize = new Dimension(0, 0);
		RCPosition biggestCell = new RCPosition(1, 1);
		for (int i = 0; i < ncomponents; i++) {
			Component c = parent.getComponent(i);
			RCPosition position = constraintsMap.get(c);
			if (c != null && position != null) {
				Dimension componentSize;
				if (isPreferred) {
					componentSize = c.getPreferredSize();
				} else {
					componentSize = c.getMinimumSize();
				}
				if (position.equals(biggestCell)) {
					int componentWidth = (componentSize.width - 4 * gap) / 5;
					maxCellSize.width = Math.max(maxCellSize.width,
							componentWidth);
					maxCellSize.height = Math.max(maxCellSize.height,
							componentSize.height);
				} else {
					maxCellSize.width = Math.max(maxCellSize.width,
							componentSize.width);
					maxCellSize.height = Math.max(maxCellSize.height,
							componentSize.height);
				}
			}
		}
		return maxCellSize;
	}

	/**
	 * If isPreffered is true returns preffered layout size, otherwise returns
	 * minimal layout size.
	 * 
	 * @param parent
	 *            parent component
	 * @param isPreferred
	 *            if true preffered layout size is returned, otherwise minimal
	 *            layout size.
	 * @return If isPreffered is true returns preffered layout size, otherwise
	 *         returns minimal layout size.
	 */
	private Dimension getLayoutSize(Container parent, boolean isPreferred) {
		Dimension largestSize = getLargestCellSize(parent, isPreferred);
		Insets insets = parent.getInsets();
		largestSize.width = (largestSize.width * NUMBER_OF_COLUMNS)
				+ (gap * (NUMBER_OF_COLUMNS + 1)) + insets.left + insets.right;
		largestSize.height = (largestSize.height * NUMBER_OF_ROWS)
				+ (gap * (NUMBER_OF_ROWS + 1)) + insets.top + insets.bottom;
		return largestSize;
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		constraintsMap.remove(comp);
	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		RCPosition position = null;
	
		if (constraints instanceof RCPosition) {
			position = (RCPosition) constraints;

		} else if (constraints instanceof String) {
			position = RCPosition.parseFromString((String) constraints);

		} else {
			throw new IllegalArgumentException(
					"Cannot add to layout: constraint must be a RCPosition");

		}

		// check if position is within the borders
		if (position.getCol() > NUMBER_OF_COLUMNS
				|| position.getRow() > NUMBER_OF_ROWS) {
			throw new IllegalArgumentException("Position " + position
					+ " is beyond the borders");
		}

		// check if component on given position already exists
		if (constraintsMap.containsValue(position)) {
			throw new IllegalArgumentException("Component od given position: "
					+ position + "" + " already exists.");
		}

		// check if position is forbidden (first cell takes five cells)
		if (forbiddenPositions.contains(position)) {
			throw new IllegalArgumentException("Forbidden position: "
					+ position);
		}
		constraintsMap.put(comp, position);

	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0.5f;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0.5f;
	}

	/**
	 * Not used.
	 */
	@Override
	public void invalidateLayout(Container target) {
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);

	}

}

package org.elektronetf.util.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;

public class VerticalGridLayout extends GridLayout {
	public VerticalGridLayout() {
		super();
	}

	public VerticalGridLayout(int rows, int cols) {
		super(rows, cols);
	}

	public VerticalGridLayout(int rows, int cols, int hgap, int vgap) {
		super(rows, cols, hgap, vgap);
	}

	@Override
	public void layoutContainer(Container parent) {
		synchronized (parent.getTreeLock()) {
			Insets insets = parent.getInsets();
			int ncomponents = parent.getComponentCount();
			int nrows = getRows();
			int ncols = getColumns();
			boolean ltr = parent.getComponentOrientation().isLeftToRight();

			if (ncomponents == 0) {
				return;
			}
			if (nrows > 0) {
				ncols = (ncomponents + nrows - 1) / nrows;
			} else {
				nrows = (ncomponents + ncols - 1) / ncols;
			}
			
			int totalGapsWidth = (ncols - 1) * getHgap();
			int widthWOInsets = parent.getWidth() - (insets.left + insets.right);
			int widthOnComponent = (widthWOInsets - totalGapsWidth) / ncols;
			int extraWidthAvailable = (widthWOInsets - (widthOnComponent * ncols + totalGapsWidth)) / 2;

			int totalGapsHeight = (nrows - 1) * getVgap();
			int heightWOInsets = parent.getHeight() - (insets.top + insets.bottom);
			int heightOnComponent = (heightWOInsets - totalGapsHeight) / nrows;
			int extraHeightAvailable = (heightWOInsets - (heightOnComponent * nrows + totalGapsHeight)) / 2;
			if (ltr) {
				for (int c = 0, x = insets.left + extraWidthAvailable; c < ncols ; c++, x += widthOnComponent + getHgap()) {
					for (int r = 0, y = insets.top + extraHeightAvailable; r < nrows ; r++, y += heightOnComponent + getVgap()) {
						layoutComponent(parent, ncomponents, nrows, r, c, x, y, widthOnComponent, heightOnComponent);
					}
				}
			} else {
				for (int c = 0, x = (parent.getWidth() - insets.right - widthOnComponent) - extraWidthAvailable; c < ncols ; c++, x -= widthOnComponent + getHgap()) {
					for (int r = 0, y = insets.top + extraHeightAvailable; r < nrows ; r++, y += heightOnComponent + getVgap()) {
						layoutComponent(parent, ncomponents, nrows, r, c, x, y, widthOnComponent, heightOnComponent);
					}
				}
			}
		}
	}
	
	protected static void layoutComponent(Container parent, int ncomponents,
			int nrows, int r, int c,
			int x, int y, int widthOnComponent, int heightOnComponent) {
		
		int i = c * nrows + r; // THIS IS CHANGED
		if (i < ncomponents) {
			Component comp = parent.getComponent(i);
			Dimension size = comp.getPreferredSize();
			int width  = (size.width  > 0 && size.width  < widthOnComponent)  ? size.width  : widthOnComponent;
			int height = (size.height > 0 && size.height < heightOnComponent) ? size.height : heightOnComponent;
			comp.setBounds(x, y, width, height);
		}
	}
}

package com.elektronetf.util.gui;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;

import javax.swing.JPanel;

public class GridBagPanel extends JPanel {
	private static final GridBagConstraints GBC = new GridBagConstraints();
	
	public GridBagPanel() {
		super(new GridBagLayout());
	}
	
	@Override
	public GridBagLayout getLayout() {
		return (GridBagLayout) super.getLayout();
	}
	
	@Override
	public void setLayout(LayoutManager mgr) {
		if (mgr instanceof GridBagLayout) {
			super.setLayout(mgr);
		} else {
			throw new IllegalArgumentException("Layout must be a GridBagLayout");
		}
	}
	
	public GridBagPanel addToGrid(Component comp) {
		addToGrid(comp,
				GBC.gridx, GBC.gridy,
				GBC.gridwidth, GBC.gridheight,
				GBC.weightx, GBC.weighty,
				GBC.anchor, GBC.fill,
				GBC.insets, GBC.ipadx, GBC.ipady);
		
		return this;
	}
	
	public GridBagPanel addToGrid(Component comp,
			int gridx, int gridy) {
		
		addToGrid(comp,
				gridx, gridy,
				GBC.gridwidth, GBC.gridheight,
				GBC.weightx, GBC.weighty,
				GBC.anchor, GBC.fill,
				GBC.insets, GBC.ipadx, GBC.ipady);
		
		return this;
	}
	
	public GridBagPanel addToGrid(Component comp,
			int gridx, int gridy,
			int gridwidth, int gridheight) {
		
		addToGrid(comp,
				gridx, gridy,
				gridwidth, gridheight,
				GBC.weightx, GBC.weighty,
				GBC.anchor, GBC.fill,
				GBC.insets, GBC.ipadx, GBC.ipady);
		
		return this;
	}
	
	public GridBagPanel addToGrid(Component comp,
			int gridx, int gridy,
			int gridwidth, int gridheight,
			double weightx, double weighty) {
		
		addToGrid(comp,
				gridx, gridy,
				gridwidth, gridheight,
				weightx, weighty,
				GBC.anchor, GBC.fill,
				GBC.insets, GBC.ipadx, GBC.ipady);
		
		return this;
	}
	
	public GridBagPanel addToGrid(Component comp,
			int gridx, int gridy,
			int gridwidth, int gridheight,
			double weightx, double weighty,
			int anchor, int fill) {
		
		addToGrid(comp,
				gridx, gridy,
				gridwidth, gridheight,
				weightx, weighty,
				anchor, fill,
				GBC.insets, GBC.ipadx, GBC.ipady);
		
		return this;
	}
	
	public GridBagPanel addToGrid(Component comp,
			int gridx, int gridy,
			int gridwidth, int gridheight,
			double weightx, double weighty,
			int anchor, int fill,
			Insets insets, int ipadx, int ipady) {
		
		add(comp, new GridBagConstraints(
				gridx, gridy,
				gridwidth, gridheight,
				weightx, weighty,
				anchor, fill,
				insets, ipadx, ipady));
		
		return this;
	}
}

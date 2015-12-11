package org.elektronetf.ttt.gui;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import org.elektronetf.ttt.TourneyData;
import org.elektronetf.util.gui.VerticalGridLayout;

public abstract class TourneyPanel extends JPanel {
	public static final int MAX_ROWS = 3;
//	public static final int MAX_COLS = 0; // Any number of columns in a GridLayout
	public static final int MAX_COLS = -Math.floorDiv(-TourneyData.MAX_GROUP_COUNT, MAX_ROWS); // Essentially ceilDiv()
	
	static final int DIV_SIZE = 2 * TTTFrame.DIV_SIZE;
	
	private static final GridLayout LAYOUT = new VerticalGridLayout(MAX_ROWS, MAX_COLS);
	
	private TourneyData data;
	
	public TourneyPanel() {
		this(null);
	}

	public TourneyPanel(TourneyData data) {
		setData(data);
		
		setLayout(LAYOUT);
		LAYOUT.setHgap(DIV_SIZE);
		LAYOUT.setVgap(DIV_SIZE);
		setBorder(BorderFactory.createEmptyBorder(DIV_SIZE, DIV_SIZE, DIV_SIZE, DIV_SIZE));
		
		initComponents();
	}
	
	TourneyData getData() {
		return data;
	}
	
	void setData(TourneyData data) {
		this.data = data;
	}
	
	protected abstract void initComponents();
}

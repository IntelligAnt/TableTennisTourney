package com.elektronetf.ttt.gui;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import com.elektronetf.ttt.TourneyData;
import com.elektronetf.util.gui.GridBagPanel;

public abstract class TourneyPanel extends GridBagPanel {
	public static final int MAX_ROWS = 3;
	public static final int MAX_COLS = -Math.floorDiv(-TourneyData.MAX_GROUP_COUNT, MAX_ROWS); // Essentially ceilDiv()
	
	static final int DIV_SIZE = TTTFrame.DIV_SIZE;
	static final Insets INSETS = new Insets(DIV_SIZE, DIV_SIZE, DIV_SIZE, DIV_SIZE);
	
	private TourneyData data;
	private int currx = 0;
	private int curry = 0;
	
	public TourneyPanel() {
		this(null);
	}

	public TourneyPanel(TourneyData data) {
//		setBorder(BorderFactory.createEmptyBorder(DIV_SIZE, DIV_SIZE, DIV_SIZE, DIV_SIZE));
		setData(data);
		setUpPanel();
	}
	
	@Override
	public Component add(Component comp) {
		addToGrid(comp, currx, curry++,
				1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
				INSETS, 20, 20);
		if (curry == MAX_ROWS) {
			currx++;
			curry = 0;
		}
		return comp;
	}

	TourneyData getData() {
		return data;
	}
	
	void setData(TourneyData data) {
		this.data = data;
	}
	
	protected abstract void setUpPanel();
}

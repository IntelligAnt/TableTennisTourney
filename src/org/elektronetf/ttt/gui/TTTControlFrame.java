package org.elektronetf.ttt.gui;

import org.elektronetf.ttt.TourneyData;

public class TTTControlFrame extends TTTFrame {
	public TTTControlFrame() {
		setTitle("[Управљање] " + getTitle());
	}

	@Override
	public void bindData(TourneyData data) {
		if (panel != null) {
			panel.setData(data);
		} else {
			panel = new DisplayPanel(data);
		}
	}
	
	@Override
	protected void initPanel() {
		panel = new ControlPanel();
	}
}

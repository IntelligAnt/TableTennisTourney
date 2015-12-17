package org.elektronetf.ttt.gui;

import org.elektronetf.ttt.TourneyData;
import org.elektronetf.ttt.gui.TourneyPanel.TourneyControlPanel;

public class TTTControlFrame extends TTTFrame {
	public TTTControlFrame() {
		setTitle("[Управљање] " + getTitle());
	}

	@Override
	public void bindData(TourneyData data) {
		panel.setData(data);
		panel.repopulate();
	}
	
	@Override
	protected void initPanel() {
		panel = new TourneyControlPanel();
	}
}

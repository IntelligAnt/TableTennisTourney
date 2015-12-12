package org.elektronetf.ttt.gui;

import org.elektronetf.ttt.TourneyData;
import org.elektronetf.ttt.gui.TourneyPanel.TourneyDisplayPanel;

public class TTTDisplayFrame extends TTTFrame {
	public TTTDisplayFrame() {
		setTitle("[„P„‚„y„{„p„x] " + getTitle());
	}

	@Override
	public void bindData(TourneyData data) {
		if (panel != null) {
			panel.setData(data);
		} else {
			panel = new TourneyDisplayPanel(data);
		}
	}
	
	@Override
	protected void initPanel() {
		panel = new TourneyDisplayPanel();
	}
}

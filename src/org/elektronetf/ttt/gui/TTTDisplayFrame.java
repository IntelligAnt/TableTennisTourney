package org.elektronetf.ttt.gui;

import javax.swing.JScrollBar;
import javax.swing.Timer;

import org.elektronetf.ttt.TourneyData;
import org.elektronetf.ttt.gui.TourneyPanel.TourneyDisplayPanel;

public class TTTDisplayFrame extends TTTFrame {
	private static final int DELAY = 5000; // Milliseconds
	
	private Timer delayTimer;
	private Timer scrollTimer;
	private boolean scrollsDown;
	
	
	public TTTDisplayFrame() {
		setTitle("[Приказ] " + getTitle());
	}

	@Override
	public void bindData(TourneyData data) {
		panel.setData(data);
		panel.repopulate();
	}
	
	@Override
	protected void initPanel() {
		panel = new TourneyDisplayPanel();
		
		delayTimer = new Timer(DELAY, (evt) -> scrollTimer.start());
		delayTimer.setRepeats(false);
		delayTimer.start();
		
		JScrollBar scrollBar = scrollPane.getHorizontalScrollBar();
		scrollTimer = new Timer(1, (evt) -> scrollTimerActionPerformed(scrollBar));
	}
	
	private void scrollTimerActionPerformed(JScrollBar scrollBar) {
		int min = scrollBar.getMinimum();
		int max = scrollBar.getMaximum() - scrollBar.getVisibleAmount();
		int limit = scrollsDown ? min : max;
		int value = scrollBar.getValue();
		
		if (Math.abs(limit - value) > 0) {
			int delta = scrollsDown ? -1 : 1;
			scrollBar.setValue(value + delta);
		} else {
			scrollTimer.stop();
			delayTimer.start();
			scrollsDown = !scrollsDown;
		}
	}
}

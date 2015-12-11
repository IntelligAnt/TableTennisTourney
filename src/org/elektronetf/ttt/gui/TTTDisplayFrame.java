package org.elektronetf.ttt.gui;

public class TTTDisplayFrame extends TTTFrame {
	public TTTDisplayFrame() {
		setTitle("[„P„‚„y„{„p„x] " + getTitle());
	}
	
	@Override
	protected void initPanel() {
		panel = new DisplayPanel();
	}
}

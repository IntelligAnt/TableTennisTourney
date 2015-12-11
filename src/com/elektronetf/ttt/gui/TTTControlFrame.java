package com.elektronetf.ttt.gui;

public class TTTControlFrame extends TTTFrame {
	public TTTControlFrame() {
		setTitle("[Управљање] " + getTitle());
	}
	
	@Override
	protected void initPanel() {
		panel = new ControlPanel();
	}
}

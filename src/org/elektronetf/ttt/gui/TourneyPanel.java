package org.elektronetf.ttt.gui;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import org.elektronetf.ttt.Group;
import org.elektronetf.ttt.TourneyData;
import org.elektronetf.ttt.gui.GroupPanel.GroupControlPanel;
import org.elektronetf.util.gui.GridBagPanel;

public abstract class TourneyPanel extends GridBagPanel {
	public static final int MAX_GROUP_COUNT = 16;
	public static List<ActionListener> publishListeners = new ArrayList<>();
	
	protected TourneyData data;
	
	private static final int MAX_ROWS = 3;
//	private static final int MAX_COLS = -Math.floorDiv(-TourneyData.MAX_GROUP_COUNT, MAX_ROWS); // Essentially ceilDiv()
	private int currx = 0;
	private int curry = 0;
	
	public TourneyPanel() {
		this(null);
	}

	public TourneyPanel(TourneyData data) {
		this.data = (data != null) ? data : new TourneyData();
		publishListeners.add((evt) -> publishActionPerformed(evt));
		setUpPanel();
	}

	public TourneyData getData() {
		return data;
	}
	
	public void setData(TourneyData data) {
		this.data = data;
	}
	
	@Override
	public Component add(Component comp) {
		int size = TTTFrame.DIV_SIZE;
		addToGrid(comp, currx, curry++,
				1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
				new Insets(size, size, size, size), 20, 20);
		if (curry == MAX_ROWS) {
			currx++;
			curry = 0;
		}
		return comp;
	}
	
	protected abstract void setUpPanel();
	protected abstract void publishActionPerformed(ActionEvent evt);
	
	public static class TourneyControlPanel extends TourneyPanel {
		public TourneyControlPanel() {
			super();
		}

		public TourneyControlPanel(TourneyData data) {
			super(data);
		}

		@Override
		protected void setUpPanel() {
			for (int i = 0; i < MAX_GROUP_COUNT; i++) {
				Group group = getData().createGroup();
				add(new GroupControlPanel(group));
			}
		}

		@Override
		protected void publishActionPerformed(ActionEvent evt) {
		}
	}
	
	public static class TourneyDisplayPanel extends TourneyPanel {
		public TourneyDisplayPanel() {
			super();
		}

		public TourneyDisplayPanel(TourneyData data) {
			super(data);
		}

		@Override
		protected void setUpPanel() {
//			data.
		}

		@Override
		protected void publishActionPerformed(ActionEvent evt) {
			// TODO Auto-generated method stub
			System.out.println("published");
		}
	}
}

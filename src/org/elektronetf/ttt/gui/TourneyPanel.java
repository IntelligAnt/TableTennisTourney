package org.elektronetf.ttt.gui;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import org.elektronetf.ttt.Contestant;
import org.elektronetf.ttt.Group;
import org.elektronetf.ttt.TourneyData;
import org.elektronetf.ttt.gui.GroupPanel.GroupControlPanel;
import org.elektronetf.util.gui.GridBagPanel;

public abstract class TourneyPanel extends GridBagPanel {
	public static final int MAX_ROWS = 3;
//	public static final int MAX_COLS = -Math.floorDiv(-TourneyData.MAX_GROUP_COUNT, MAX_ROWS); // Essentially ceilDiv()
	
	protected TourneyData data;
	
	private int currx = 0;
	private int curry = 0;
	
	public TourneyPanel() {
		this(null);
	}

	public TourneyPanel(TourneyData data) {
		this.data = (data != null) ? data : new TourneyData();
//		setBorder(BorderFactory.createEmptyBorder(DIV_SIZE, DIV_SIZE, DIV_SIZE, DIV_SIZE));
		setUpPanel();
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

	TourneyData getData() {
		return data;
	}
	
	void setData(TourneyData data) {
		this.data = data;
	}
	
	protected abstract void setUpPanel();
	
	public static class TourneyControlPanel extends TourneyPanel {
		public TourneyControlPanel() {
			super();
		}

		public TourneyControlPanel(TourneyData data) {
			super(data);
		}

		@Override
		protected void setUpPanel() {
			for (int i = 0; i < TourneyData.MAX_GROUP_COUNT; i++) {
				Group group = getData().makeGroup();
				group.addContestant(new Contestant("con", "1"));
				add(new GroupControlPanel(group));
			}
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
			// TODO Auto-generated method stub
		}
	}

}

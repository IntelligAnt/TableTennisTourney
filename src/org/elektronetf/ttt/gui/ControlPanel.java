package org.elektronetf.ttt.gui;

import org.elektronetf.ttt.Contestant;
import org.elektronetf.ttt.Group;
import org.elektronetf.ttt.TourneyData;
import org.elektronetf.ttt.gui.GroupPanel.GroupControlPanel;

public class ControlPanel extends TourneyPanel {
	public ControlPanel() {
		super();
	}

	public ControlPanel(TourneyData data) {
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

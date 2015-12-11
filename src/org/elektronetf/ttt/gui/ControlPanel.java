package org.elektronetf.ttt.gui;

import java.util.ArrayList;
import java.util.List;

import org.elektronetf.ttt.TourneyData;

public class ControlPanel extends TourneyPanel {
	public ControlPanel() {
		super();
	}

	public ControlPanel(TourneyData data) {
		super(data);
	}

	@Override
	protected void setUpPanel() {
		tableList = new ArrayList<>(TourneyData.MAX_GROUP_COUNT);
		char c = 'A';
		for (int i = 0; i < 4; i++) {
			tableList.add(new GroupTable(String.valueOf(c++), getData()));
		}
		
		for (GroupTable t : tableList) {
			add(t);
		}
	}
	
	List<GroupTable> tableList;
}

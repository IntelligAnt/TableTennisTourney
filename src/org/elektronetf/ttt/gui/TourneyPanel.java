package org.elektronetf.ttt.gui;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.swing.event.EventListenerList;

import org.elektronetf.ttt.Group;
import org.elektronetf.ttt.TourneyData;
import org.elektronetf.ttt.gui.GroupPanel.GroupControlPanel;
import org.elektronetf.ttt.gui.GroupPanel.GroupDisplayPanel;
import org.elektronetf.util.gui.GridBagPanel;

public abstract class TourneyPanel extends GridBagPanel {
	public static final int MAX_GROUP_COUNT = 16;
	
	protected TourneyData data;
	
	private static final int MAX_ROWS = 3;
//	private static final int MAX_COLS = -Math.floorDiv(-TourneyData.MAX_GROUP_COUNT, MAX_ROWS); // Essentially ceilDiv()
	private static final EventListenerList PUBLISH_LISTENERS = new EventListenerList();
	
	private int currx;
	private int curry;
	
	public TourneyPanel() {
		this(null);
	}

	public TourneyPanel(TourneyData data) {
		this.data = data;
		currx = curry = 0;
		PUBLISH_LISTENERS.add(PublishListener.class, (evt) -> publishPerformed(evt));
		setUpPanel();
	}

	public TourneyData getData() {
		return data;
	}
	
	public void setData(TourneyData data) {
		this.data = data;
	}
	
	public void repopulate() {
		removeAll();
		setUpPanel();
	}
	
	public static PublishListener[] getPublishListeners() {
		return PUBLISH_LISTENERS.getListeners(PublishListener.class);
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
	protected abstract void publishPerformed(PublishEvent evt);
	
	public static class TourneyControlPanel extends TourneyPanel {
		public TourneyControlPanel() {
			super();
		}

		public TourneyControlPanel(TourneyData data) {
			super(data);
		}

		@Override
		protected void setUpPanel() {
			if (data != null) {
				int i = 0;
				for (Group group : data.getGroups()) {
					add(new GroupControlPanel(group));
					i++;
				}
				for (; i < MAX_GROUP_COUNT; i++) {
					Group group = data.createGroup();
					add(new GroupControlPanel(group));
				}
			}
		}

		@Override
		protected void publishPerformed(PublishEvent evt) {
		}
	}
	
	public static class TourneyDisplayPanel extends TourneyPanel {
		private SortedMap<Group, GroupDisplayPanel> groupMap;
		
		public TourneyDisplayPanel() {
			super();
		}

		public TourneyDisplayPanel(TourneyData data) {
			super(data);
		}

		@Override
		protected void setUpPanel() {
			groupMap = new TreeMap<>();
			if (data != null) {
				for (Group group : data.getGroups()) {
					if (group.hasGames()) {
						GroupDisplayPanel panel = new GroupDisplayPanel(group);
						add(panel);
						groupMap.put(group, panel);
					}
				}
			}
		}

		@Override
		protected void publishPerformed(PublishEvent evt) {
			Group group = evt.getSource();
			
			switch(evt.getType()) {
			case PUBLISH_UPDATE:
				if (!groupMap.containsKey(group)) {
					Collection<GroupDisplayPanel> panels = groupMap.values();
					List<Group> panelGroupList = panels.stream().map((panel) -> panel.getGroup())
							.collect(Collectors.toList());
					int index = Collections.binarySearch(panelGroupList, group);
					assert(index < 0);
					GroupDisplayPanel panel = new GroupDisplayPanel(group);
					add(panel, -index - 1);
					groupMap.put(group, panel);
				}
				break;
			case PUBLISH_REMOVE:
				if (group.getContestantCount() != 0) {
					return;
				}
				remove(groupMap.get(group));
				groupMap.remove(group);
				break;
			default:
				return;
			}
			
			revalidate();
			repaint();
		}
	}
}

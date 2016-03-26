package org.elektronetf.ttt.gui;

import java.util.Collections;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.swing.JPanel;
import javax.swing.event.EventListenerList;

import org.elektronetf.ttt.Group;
import org.elektronetf.ttt.TourneyData;
import org.elektronetf.ttt.gui.GroupPanel.GroupControlPanel;
import org.elektronetf.ttt.gui.GroupPanel.GroupDisplayPanel;
import org.elektronetf.util.gui.VerticalWrapLayout;

public abstract class TourneyPanel extends JPanel {
	static final int BASE_HEIGHT = TTTFrame.SCREEN_SIZE.height - TTTFrame.TOP_BAR_HEIGHT;
	
	protected TourneyData data;
	
	private static final EventListenerList PUBLISH_LISTENERS = new EventListenerList();
	
	public TourneyPanel() {
		this(null);
	}

	public TourneyPanel(TourneyData data) {
		this.data = data;
		PUBLISH_LISTENERS.add(PublishListener.class, (evt) -> publishPerformed(evt));
		setLayout(new VerticalWrapLayout(VerticalWrapLayout.TOP, TTTFrame.DIV_WIDTH, TTTFrame.DIV_WIDTH));
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
				for (; i < TourneyData.MAX_GROUPS; i++) {
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
					if (group.hasMatches()) {
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
				List<Group> panelGroupList = groupMap.values().stream().map(
						(panel) -> panel.getGroup()).collect(Collectors.toList());
				int index = Collections.binarySearch(panelGroupList, group);
				GroupDisplayPanel panel = new GroupDisplayPanel(group);
				if (index >= 0) {
					remove(groupMap.get(group));	// Panel already exists, replace it
				} else {
					index = -index - 1;				// Panel doesn't exist, find insertion index
				}
				add(panel, index);
				groupMap.put(group, panel);
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
//			repaint();
		}
	}
}

package org.elektronetf.ttt.gui;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
		public TourneyDisplayPanel() {
			super();
		}

		public TourneyDisplayPanel(TourneyData data) {
			super(data);
		}

		@Override
		protected void setUpPanel() {
			if (data != null) {
				for (Group group : data.getGroups()) {
					if (group.hasGames()) {
						add(new GroupDisplayPanel(group));
					}
				}
			}
		}

		@Override
		protected void publishPerformed(PublishEvent evt) {
			Group group = evt.getSource();
			int index;
			
			switch (evt.getType()) {
			case PUBLISH_ADD: case PUBLISH_UPDATE:
				index = data.getGroups().indexOf(group);
				assert(index >= 0);
				if (group.hasGames()) {
					if (index < getComponentCount() && ((GroupPanel) getComponent(index)).getGroup() != group) {
						add(new GroupDisplayPanel(group), index);
					} else {
						add(new GroupDisplayPanel(group)); // TODO What happens if the last component is the bracket?
					}
				}
				break;
			case PUBLISH_REMOVE:
				if (group.getContestantCount() == 0) {
					Component[] comps = getComponents();
					List<Group> groups = new ArrayList<>(comps.length - 1);
					for (int i = 0; i < comps.length - 1; i++) {
						groups.add(((GroupPanel) comps[i]).getGroup());
					}
					index = Collections.binarySearch(groups, group);
					if (index >= 0) {
						remove(index);
					}
				}
				break;
			default:
				// Should never happen
			}
		}
	}
}

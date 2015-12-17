package org.elektronetf.ttt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.elektronetf.ttt.gui.TTTFrame;

public final class TourneyData implements Cloneable {
	public static final int MAX_GROUPS = new Integer(TTTFrame.getProperty("int.maxgroups"));
	
	private List<Group> groups;
	
	public TourneyData() {
		groups = new ArrayList<>(MAX_GROUPS);
	}
	
	public Group getGroup(int index) {
		return groups.get(index);
	}
	
	public List<Group> getGroups() {
		return Collections.unmodifiableList(groups);
	}
	
	public void addGroup(Group group) {
		int index = Collections.binarySearch(groups, group);
		if (index < 0) {
			groups.add(-index - 1, group);
		}
	}
	
	public Group createGroup() {
		String designation = Group.FIRST_DESIGNATION;
		if (groups.size() > 0) {
			designation = Group.getNextDesignation(groups.get(groups.size() - 1).getDesignation());
		}
		Group group = new Group(designation);
		groups.add(group);
		return group;
	}

	@Override
	public TourneyData clone() {
		 try {
			TourneyData data = (TourneyData) super.clone();
			data.groups = new ArrayList<>(groups);
			return data;
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e);	// Shouldn't happen
		}
	}
}

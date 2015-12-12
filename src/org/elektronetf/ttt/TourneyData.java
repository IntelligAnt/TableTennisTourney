package org.elektronetf.ttt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class TourneyData implements Cloneable {
	private static final String FIRST_DESIGNATION = "A";
	
	private List<Group> groups;
	
	public TourneyData() {
		groups = new ArrayList<>();
	}
	
	public Group getGroup(int index) {
		return groups.get(index);
	}
	
	public List<Group> getGroups() {
		return Collections.unmodifiableList(groups);
	}
	
	public void addGroup(Group group) {
		int pos = Collections.binarySearch(groups, group);
		if (pos < 0) {
			groups.add(-pos - 1, group);
		}
	}
	
	public Group createGroup() {
		String designation = FIRST_DESIGNATION;
		if (groups.size() > 0) {
			designation = groups.get(groups.size() - 1).getDesignation();
			int i = designation.length() - 1;
			designation = designation.substring(0, i) + (char) (designation.charAt(i) + 1);
		}
		Group group = new Group(designation);
//		groups.add(group);
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

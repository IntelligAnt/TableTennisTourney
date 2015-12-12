package org.elektronetf.ttt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class TourneyData implements Cloneable {
	public static final int MAX_GROUP_COUNT = 16;
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
	
	public Group makeGroup() {
//		if (groups.size() == MAX_GROUP_COUNT) {
//			return null;
//		}
		String designation = FIRST_DESIGNATION;
		if (groups.size() > 0) {
			designation = groups.get(groups.size() - 1).getDesignation();
			int i = designation.length() - 1;
			designation = designation.substring(0, i) + (char) (designation.charAt(i) + 1);
		}
		Group group = new Group(designation);
		groups.add(group);
		return group;
	}

	@Override
	public TourneyData clone() {
		 try {
			TourneyData data = (TourneyData) super.clone();
			// TODO Cloning code
			return data;
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e);	// Shouldn't happen
		}
	}
}

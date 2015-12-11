package com.elektronetf.ttt.gui;

import javax.swing.BorderFactory;
import javax.swing.JTable;

public class GroupTable extends JTable {
	public static final String GROUP_PREFIX = "ÑCÑÇÑÖÑÅÑp ";
	
	private String designation;
	
	public GroupTable(String designation) {
		if (designation == null) {
			throw new NullPointerException("Group designation is null");
		}
		if (designation.isEmpty()) {
			throw new IllegalArgumentException("Group designation must not be empty");
		}
		setDesignation(designation);
		
//		setPreferredSize(new Dimension(0, 100));
		setBorder(BorderFactory.createTitledBorder(getGroupName()));
//		setFont(new Font(TTTFrame.FONT_NAME, Font.PLAIN, 20)); // TODO Display font
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String groupName) {
		this.designation = groupName;
	}
	
	public String getGroupName() {
		return GROUP_PREFIX + getDesignation();
	}
	
	@Override
	public String toString() {
		return getGroupName() + ": " + super.toString();
	}
}

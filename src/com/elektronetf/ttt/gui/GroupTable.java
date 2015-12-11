package com.elektronetf.ttt.gui;

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import com.elektronetf.ttt.TourneyData;

public class GroupTable extends JTable {
	public static final String GROUP_PREFIX = "ÑCÑÇÑÖÑÅÑp ";
	
	private String designation;
	
	public GroupTable(String designation) {
		super(new GroupTableModel());
		
		if (designation == null) {
			throw new NullPointerException("Group designation is null");
		}
		if (designation.isEmpty()) {
			throw new IllegalArgumentException("Group designation must not be empty");
		}
		setDesignation(designation);
		
//		setPreferredSize(new Dimension(560, 190)); // TODO Temporary size fix
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
	
	public static class GroupTableModel extends AbstractTableModel {
		private int rows = TourneyData.PLAYERS_PER_GROUP;
		private int cols = 3;
		
		@Override
		public int getRowCount() {
			return rows;
		}
		
		@Override
		public int getColumnCount() {
			return cols;
		}
		
		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			return 3;
		}
	}
}

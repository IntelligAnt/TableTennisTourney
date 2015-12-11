package org.elektronetf.ttt.gui;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import org.elektronetf.ttt.TourneyData;

public class GroupTable extends JTable {
	public static final String GROUP_PREFIX = "ÑCÑÇÑÖÑÅÑp ";
	
	private String designation;
	
	public GroupTable(String designation, TourneyData data) {
		super(new GroupTableModel(data));
		
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
		private final TourneyData data;
		
		public GroupTableModel(TourneyData data) {
			this.data = data;
		}

		@Override
		public int getRowCount() {
			return TourneyData.PLAYERS_PER_GROUP;
		}
		
		@Override
		public int getColumnCount() {
			return 3;
		}
		
		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			// TODO Auto-generated method stub
		}
		
		@Override
		public Class<?> getColumnClass(int columnIndex) {
			switch (columnIndex) {
			case 0:
				return String.class;
			case 1: case 2:
				return JButton.class;
			default:
				return Object.class;	
			}
		}

		@Override
		public String getColumnName(int columnIndex) {
			return null;
		}
		
		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return columnIndex == 0;
		}
	}
}

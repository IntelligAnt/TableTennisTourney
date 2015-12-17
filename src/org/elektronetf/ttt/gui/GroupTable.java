package org.elektronetf.ttt.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

import org.elektronetf.ttt.Contestant;
import org.elektronetf.ttt.Group;

public class GroupTable extends JTable {
	static final int ROW_HEIGHT	= (int) (TourneyPanel.BASE_HEIGHT * 0.035);
	static final int FONT_SIZE	= (int) (ROW_HEIGHT * 0.9);
	
	public GroupTable(GroupTableModel model) {
		super(model);
		
		setRowHeight(ROW_HEIGHT);
		TableColumnModel columnModel = getColumnModel();
		for (int i = 0; i < columnModel.getColumnCount(); i++) {
			Class<?> columnClass = model.getColumnClass(i);
			if (columnClass == String.class) {
				columnModel.getColumn(i).setPreferredWidth(FONT_SIZE * 6);
			} else if (columnClass == Integer.class) {
				columnModel.getColumn(i).setPreferredWidth(FONT_SIZE * 2);
			}
		}
		
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		setFont(new Font(TTTFrame.FONT_NAME, Font.PLAIN, FONT_SIZE));
		setDefaultRenderer(Object.class, new GroupTableCellRenderer());
	}
	
	public static abstract class GroupTableModel extends AbstractTableModel {
		protected final Group group;
		
		public GroupTableModel(Group group) {
			this.group = group;
		}

		public Group getGroup() {
			return group;
		}

		@Override
		public String getColumnName(int column) {
			switch (column) {
			case 0:
				return "ÑyÑ}Ñu";
			case 1:
				return "ÑÅÑÇÑuÑxÑyÑ}Ñu";
			case 2:
				return "+Ñ}ÑuÑâ";
			case 3:
				return "-ÑÉÑuÑÑ";
			case 4:
				return "-ÑqÑÄÑt";
			default:
				return null;
			}
		}
	}
	
	public static class GroupControlTableModel extends GroupTableModel {
		private String[] newName = { null, null };
		
		public GroupControlTableModel(Group group) {
			super(group);
		}
		
		public String[] getNewName() {
			return newName;
		}
		
		public void setNewName(String[] newName) {
			if (newName.length != 2) {
				throw new IllegalArgumentException("New name must contain two strings");
			}
			this.newName = newName;
		}
		
		public int getNewNameRow() {
			return group.getContestantCount();
		}
		
		@Override
		public int getRowCount() {
			return Group.MAX_CONTESTANTS;
		}
		
		@Override
		public int getColumnCount() {
			return 2;
		}
		
		@Override
		public Object getValueAt(int row, int column) {
			Contestant con = group.getContestant(row);
			if (con != null) {
				return (column == 0) ? con.getFirstName() : con.getLastName();
			} else if (row == getNewNameRow()) {
				return newName[column];
			}
			return null;
		}
		
		@Override
		public void setValueAt(Object value, int row, int column) {
			assert isCellEditable(row, column);
			Contestant con = group.getContestant(row);
			String str = (String) value;
			if (con != null) {
				if (column == 0) {
					con.setFirstName(str);
				} else {
					con.setLastName(str);
				}
				fireTableCellUpdated(row, column);
			} else {
				newName[column] = str;
				fireTableCellUpdated(getNewNameRow(), column);
			}
		}
		
		@Override
		public Class<?> getColumnClass(int column) {
			return String.class;
		}
		
		@Override
		public boolean isCellEditable(int row, int column) {
			return true;
		}
	}
	
	public static class GroupDisplayTableModel extends GroupTableModel {
		public GroupDisplayTableModel(Group group) {
			super(group);
		}
		
		@Override
		public int getRowCount() {
			return group.getContestantCount();
		}
		
		@Override
		public int getColumnCount() {
			return 5;
		}
		
		@Override
		public Object getValueAt(int row, int column) {
			Contestant con = group.getContestant(row);
			switch (column) {
			case 0:
				return con.getFirstName();
			case 1:
				return con.getLastName();
			case 2:
				return con.getMatchesWon();
			case 3:
				return con.getSetsLost();
			case 4:
				return con.getPointsLost();
			default:
				return null;	
			}
		}

		@Override
		public Class<?> getColumnClass(int column) {
			return (column < 2) ? String.class : Integer.class; 
		}
	}
	
	public static class GroupTableCellRenderer extends DefaultTableCellRenderer {
		static final Color EMPTY_COLOR = new Color(0xECF0F1);
		static final Color SELECTION_EMPTY_COLOR = new Color(0x2980B9);
		
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value,
				boolean isSelected, boolean hasFocus, int row, int column) {
			
			if (!(table instanceof GroupTable)) {
				throw new IllegalArgumentException("Table must be a GroupTable");
			}
			Component comp = super.getTableCellRendererComponent(table, value,
					isSelected, hasFocus, row, column);
			Group group = ((GroupTableModel) table.getModel()).getGroup();
			if (row >= group.getContestantCount()) {
				comp.setBackground(isSelected ? SELECTION_EMPTY_COLOR : EMPTY_COLOR);
			} else {
				comp.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
			}
			return comp;
		}
	}
}

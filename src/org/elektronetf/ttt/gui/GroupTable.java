package org.elektronetf.ttt.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import org.elektronetf.ttt.Contestant;
import org.elektronetf.ttt.Group;

public class GroupTable extends JTable {
	public GroupTable(GroupTableModel model) {
		super(model);
//		setPreferredSize(new Dimension(560, 200)); // TODO Temporary size fix
		setFont(new Font(TTTFrame.FONT_NAME, Font.PLAIN, 20)); // TODO Display font
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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
			assert(isCellEditable(row, column));
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
	
	public static class GroupTableCellRenderer extends DefaultTableCellRenderer {
		static final Color EMPTY_COLOR = new Color(0xECF0F1); // TODO Better colors
		static final Color SELECTION_EMPTY_COLOR = new Color(0x39697B);
		
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

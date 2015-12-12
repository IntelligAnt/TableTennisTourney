package org.elektronetf.ttt.gui;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.elektronetf.ttt.Contestant;
import org.elektronetf.ttt.Group;
import org.elektronetf.ttt.gui.GroupTable.ControlGroupTableModel;

public abstract class GroupPanel extends JPanel {
	protected final Group group;
	
	public GroupPanel(Group group) {
		this.group = group;
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		setBorder(BorderFactory.createTitledBorder(group.getName()));
		setUpPanel();
	}
	
	public Group getGroup() {
		return group;
	}
	
	protected abstract void setUpPanel();
	
	GroupTable table;
	
	public static class GroupControlPanel extends GroupPanel {
		public GroupControlPanel(Group group) {
			super(group);
		}

		@Override
		protected void setUpPanel() {
			GroupTable table = new GroupTable(new ControlGroupTableModel(group));
			add(table);
			
			JPanel panelButtons = new JPanel();
			panelButtons.setLayout(new BoxLayout(panelButtons, BoxLayout.PAGE_AXIS));
			
			JButton buttonAdd = new JButton("Додај");
			buttonAdd.addActionListener((evt) -> buttonAddActionPerformed(table, group));
			panelButtons.add(buttonAdd);
			
			JButton buttonRemove = new JButton("Уклони");
			buttonRemove.addActionListener((evt) -> buttonRemoveActionPerformed(table, group));
			panelButtons.add(buttonRemove, BorderLayout.LINE_END);
			
			add(panelButtons);
		}
		
		private void buttonAddActionPerformed(GroupTable table, Group group) {
			ControlGroupTableModel model = (ControlGroupTableModel) table.getModel();
			String[] newName = model.getNewName();
			if (newName[0] != null && newName[0] != "" && newName[1] != null && newName[1] != "") {
				int row = model.getNewNameRow();
				group.addContestant(new Contestant(newName[0], newName[1]));
				model.setNewName(new String[]{ null, null });
				model.fireTableRowsInserted(row, row);
				row = model.getNewNameRow();
				table.setRowSelectionInterval(row, row);
				table.requestFocusInWindow();
			}
		}

		private void buttonRemoveActionPerformed(GroupTable table, Group group) {
			ControlGroupTableModel model = (ControlGroupTableModel) table.getModel();
			int row = table.getSelectedRow();
			Contestant con = group.getContestant(row);
			if (con != null) {
				group.removeContestant(con);
				model.fireTableRowsDeleted(row, row);
				table.requestFocusInWindow();
			}
		}
	}
	
	public static class GroupDisplayPanel extends GroupPanel {
		public GroupDisplayPanel(Group group) {
			super(group);
		}

		@Override
		protected void setUpPanel() {
			// TODO Auto-generated method stub
		}
	}
}

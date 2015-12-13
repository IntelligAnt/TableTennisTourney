package org.elektronetf.ttt.gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;

import org.elektronetf.ttt.Contestant;
import org.elektronetf.ttt.Group;
import org.elektronetf.ttt.gui.GroupTable.GroupControlTableModel;
import org.elektronetf.ttt.gui.GroupTable.GroupDisplayTableModel;
import org.elektronetf.ttt.gui.PublishEvent.PublishType;

public abstract class GroupPanel extends JPanel {
	static final int 	BORDER_FONT_SIZE		= (int) (TTTFrame.BASE_SIZE * 0.02);
	static final Color	BORDER_READY_COLOR		= TTTFrame.ACCENT_COLOR;
	static final Color	BORDER_PUBLISHED_COLOR	= new Color(0x3498DB);
	
	protected final Group group;
	protected final Border borderReady;
	protected final Border borderPublished;
	
	public GroupPanel(Group group) {
		this.group = group;
		
		int size = TTTFrame.BORDER_SIZE;
		Border border = new MatteBorder(size, size, size, size, BORDER_READY_COLOR);
		Font font = new Font(TTTFrame.FONT_NAME, Font.PLAIN, BORDER_FONT_SIZE);
		borderReady = BorderFactory.createTitledBorder(border, group.getName(),
				TitledBorder.LEFT, TitledBorder.TOP, font);
		border = new MatteBorder(size, size, size, size, BORDER_PUBLISHED_COLOR);
		borderPublished = BorderFactory.createTitledBorder(border, group.getName(),
				TitledBorder.LEFT, TitledBorder.TOP, font);
		
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
			setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
			setBorder(borderReady);
			
			table = new GroupTable(new GroupControlTableModel(group));
			add(table);
			
			panelButtons = new JPanel();
			panelButtons.setLayout(new BoxLayout(panelButtons, BoxLayout.LINE_AXIS));
			
			buttonAdd = new JButton("Додај");
			buttonAdd.addActionListener((evt) -> buttonAddActionPerformed(table, group));
			panelButtons.add(buttonAdd);
			
			buttonRemove = new JButton("Уклони");
			buttonRemove.addActionListener((evt) -> buttonRemoveActionPerformed(table, group));
			panelButtons.add(buttonRemove);
			
			buttonGenerate = new JButton("Објави");
			buttonGenerate.addActionListener((evt) -> buttonGenerateActionPerformed(group));
			panelButtons.add(buttonGenerate);
			
			add(panelButtons);
		}

		private void buttonAddActionPerformed(GroupTable table, Group group) {
			GroupControlTableModel model = (GroupControlTableModel) table.getModel();
			String[] newName = model.getNewName();
			if (newName[0] != null && newName[0] != "" && newName[1] != null && newName[1] != "") {
				int row = model.getNewNameRow();
				group.addContestant(new Contestant(newName[0], newName[1]));
				model.setNewName(new String[]{ null, null });
				model.fireTableRowsInserted(row, row);
				row = model.getNewNameRow();
				table.setRowSelectionInterval(row, row);
				table.requestFocusInWindow();
				setBorder(borderReady);
				publish(group, PublishType.PUBLISH_ADD);
			}
		}

		private void buttonRemoveActionPerformed(GroupTable table, Group group) {
			GroupControlTableModel model = (GroupControlTableModel) table.getModel();
			int row = table.getSelectedRow();
			Contestant con = group.getContestant(row);
			if (con != null) {
				group.removeContestant(con);
				model.fireTableRowsDeleted(row, row);
				table.requestFocusInWindow();
				setBorder(borderReady);
				publish(group, PublishType.PUBLISH_REMOVE);
			}
		}
		
		private void buttonGenerateActionPerformed(Group group) {
			if (group.generateGames()) {
				setBorder(borderPublished);
				publish(group, PublishType.PUBLISH_UPDATE);
			}
		}
		
		private void publish(Group group, PublishType type) {
			for (PublishListener l : TourneyPanel.getPublishListeners()) {
				l.publishPerformed(new PublishEvent(group, type));
			}
		}
		
		private JButton buttonAdd;
		private JButton buttonRemove;
		private JButton buttonGenerate;
		private JPanel panelButtons;
	}
	
	public static class GroupDisplayPanel extends GroupPanel {
		public GroupDisplayPanel(Group group) {
			super(group);
		}

		@Override
		protected void setUpPanel() {
			setBorder(borderPublished);
			
			table = new GroupTable(new GroupDisplayTableModel(group));
			add(table);
		}
	}
}

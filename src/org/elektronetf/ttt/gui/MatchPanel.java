package org.elektronetf.ttt.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.InputVerifier;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.elektronetf.ttt.Match;

public class MatchPanel extends JPanel { // TODO Fix & finish matches panel
	public MatchPanel() {
		super(new BorderLayout());
		
		comboBox = new JComboBox<>();
		comboBox.setEditable(false);
		comboBox.addItemListener((evt) -> ((CardLayout) cards.getLayout()).show(cards, evt.getItem().toString()));
		add(comboBox, BorderLayout.PAGE_START);
		
		cards = new JPanel(new CardLayout());
		add(cards, BorderLayout.CENTER);
	}
	
	public void bindMatches(List<Match> matches) {
		comboBox.setModel(new DefaultComboBoxModel<>(new Vector<>(matches)));
		if (matches != null) {
			cards.removeAll();
			for (Match match : matches) {
				cards.add(new PointsPanel(match), match.toString()); 
			}
		}
	}
	
	public void submit() {
		for (Component comp : cards.getComponents()) {
			if (comp instanceof PointsPanel) {
				((PointsPanel) comp).submitPoints();
			}
		}
	}

	private JComboBox<Match> comboBox;
	private JPanel cards;
	
	private static class PointsPanel extends JPanel {
		private final Match match;
		
		public PointsPanel(Match match) {
			super(new GridLayout(2, 4));
			this.match = match;
		}
		
		public void submitPoints() {
			
		}
	}
	
	private static class PointsTextField extends JTextField {
		public PointsTextField() {
			super("0", 2);
			setInputVerifier(new InputVerifier() {
				@Override
				public boolean verify(JComponent input) {
					try {
						((PointsTextField) input).getPoints();
					} catch (NumberFormatException e) {
						return false;
					}
					return true;
				}
			});
		}
		
		public int getPoints() {
			return Integer.valueOf(getText());
		}
	}
}

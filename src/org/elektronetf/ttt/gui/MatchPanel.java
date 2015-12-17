package org.elektronetf.ttt.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.InputVerifier;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.elektronetf.ttt.Match;

public class MatchPanel extends JPanel { // TODO SUCKS ASS
	private List<Match> matches;
	
	public MatchPanel() {
		super(new BorderLayout());
		
		comboBox = new JComboBox<>();
		comboBox.setEditable(false);
		comboBox.addItemListener((evt) -> ((CardLayout) getLayout()).show(this, (String) evt.getItem()));
		add(comboBox, BorderLayout.PAGE_START);
		
		JPanel cards = new JPanel(new CardLayout());
		matchPanels = new ArrayList<>();
//		for (Match match : matches) {
//			JPanel panelMatch = new JPanel(new BorderLayout());
//			
//			GridBagPanel panelInput = new GridBagPanel();
//			inputs = new ArrayList<>(6);
//			for (int i = 0; i < 6; i++) {
//				PointsTextField input = new PointsTextField();
//				panelInput.addToGrid(input, i % 3, i / 3);
//				inputs.add(input);
//			}
//			panelMatch.add(panelInput, BorderLayout.CENTER);
//			
//			cards.add(panelMatch, match.toString());
//		}
		add(cards, BorderLayout.CENTER);
	}

	public List<Match> getMatches() {
		return matches;
	}
		
//	public void submit() {
//		int cardIndex 
//		for (int i = 0; i < 3; i++) {
//			match.setScore(i, points.get(i).get(0), points.get(i).get(1));
//		}
//		match.finish();
//	}
	
	private JComboBox<Match> comboBox;
	private List<JPanel> matchPanels;
	private List<PointsTextField> inputs;
	
	private static class PointsPanel extends JPanel {
		
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
			return new Integer(getText());
		}
	}
}

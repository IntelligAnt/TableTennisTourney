package org.elektronetf.ttt.gui;

import java.awt.CardLayout;
import java.util.List;

import javax.swing.JPanel;

import org.elektronetf.ttt.Match;

public class MatchPanel extends JPanel {
	private final List<Match> matches;
	
	public MatchPanel(List<Match> matches) {
		super(new CardLayout());
		this.matches = matches;
		
		for (Match match : matches) {
			JPanel panelMatch = new JPanel();
			add(panelMatch, match);
		}
	}
	
	public List<Match> getMatches() {
		return matches;
	}
}

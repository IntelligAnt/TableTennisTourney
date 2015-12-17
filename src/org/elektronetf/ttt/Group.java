package org.elektronetf.ttt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.elektronetf.ttt.gui.TTTFrame;

public class Group implements Comparable<Group> {
	public static final int MAX_CONTESTANTS = new Integer(TTTFrame.getProperty("int.maxcontestants"));
	public static final String FIRST_DESIGNATION = TTTFrame.getProperty("str.firstdesignation");
	
	private static final String NAME_FORMAT = "ÑCÑÇÑÖÑÅÑp %s";
	
	private String designation;
	private List<Contestant> contestants;
	private List<Match> matches;
	
	public Group(String designation) {
		this(designation, new ArrayList<>(MAX_CONTESTANTS));
	}
	
	public Group(String designation, List<Contestant> contestants) {
		this(designation, contestants,
				new ArrayList<>((MAX_CONTESTANTS - 1) * MAX_CONTESTANTS / 2));
	}
	
	public Group(String designation, List<Contestant> contestants, List<Match> matches) {
		if (designation == null) {
			throw new NullPointerException("Group designation is null");
		}
		if (designation.isEmpty()) {
			throw new IllegalArgumentException("Group designation must not be empty");
		}
		this.designation = designation;
		this.contestants = contestants;
		this.matches = matches;
	}

	public String getDesignation() {
		return designation;
	}
	
	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getName() {
		return String.format(NAME_FORMAT, getDesignation());
	}
	
	public Contestant getContestant(int index) {
		try {
			return contestants.get(index);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}
	
	public List<Contestant> getContestants() {
		return Collections.unmodifiableList(contestants);
	}
	
	public int getContestantCount() {
		return contestants.size();
	}
	
	public boolean addContestant(Contestant con) {
		if (contestants.size() < MAX_CONTESTANTS) {
			return contestants.add(con);
		}
		return false;
	}
	
	public boolean removeContestant(Contestant con) {
		return contestants.remove(con);
	}

	public List<Match> getMatches() {
		return Collections.unmodifiableList(matches);
	}
	
	public boolean hasMatches() {
		return !matches.isEmpty();
	}

	public boolean generateMatches() {
		boolean generated = false;
		for (int i = 0; i < contestants.size(); i++) {
			for (int j = i + 1; j < contestants.size(); j++) {
				matches.add(new Match(3, contestants.get(i), contestants.get(j)));
				generated = true;
			}
		}
		return generated;
	}
	
	public static String getNextDesignation(String designation) {
		try {
			return Integer.toString(new Integer(designation) + 1);
		} catch (NumberFormatException e) {
			int i = designation.length() - 1;
			return designation.substring(0, i) + (char) (designation.charAt(i) + 1);
		}
	}
	
	@Override
	public int compareTo(Group other) {
		try {
			return new Integer(designation).compareTo(new Integer(other.designation));
		} catch (NumberFormatException e) {
			return designation.compareTo(other.designation);
		}
	}
	
	@Override
	public String toString() {
		return getName() + ": " + contestants + ", " + matches;
	}
}

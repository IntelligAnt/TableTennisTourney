package org.elektronetf.ttt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Group implements Comparable<Group> {
	public static final int MAX_CONTESTANTS = 5;
	
	private static final String NAME_FORMAT = "ÑCÑÇÑÖÑÅÑp %s";
	
	private String designation;
	private List<Contestant> contestants;
	private List<Game> games;
	
	public Group(String designation) {
		this(designation, new ArrayList<>(MAX_CONTESTANTS));
	}
	
	public Group(String designation, List<Contestant> contestants) {
		this(designation, contestants,
				new ArrayList<>((MAX_CONTESTANTS - 1) * MAX_CONTESTANTS / 2));
	}
	
	public Group(String designation, List<Contestant> contestants, List<Game> games) {
		if (designation == null) {
			throw new NullPointerException("Group designation is null");
		}
		if (designation.isEmpty()) {
			throw new IllegalArgumentException("Group designation must not be empty");
		}
		this.designation = designation;
		this.contestants = contestants;
		this.games = games;
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

	public List<Game> getGames() {
		return Collections.unmodifiableList(games);
	}

	public void generateGames() {
		for (int i = 0; i < contestants.size(); i++) {
			for (int j = i + 1; j < contestants.size(); j++) {
				games.add(new Game(2, contestants.get(i), contestants.get(j)));
			}
		}
	}
	
	@Override
	public int compareTo(Group other) {
		return designation.compareTo(other.designation);
	}
	
	@Override
	public String toString() {
		return getName() + ": " + contestants + ", " + games;
	}
}

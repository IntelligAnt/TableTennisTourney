package org.elektronetf.ttt;

public class Contestant {
    private String firstName;
    private String lastName;
    private int gamesWon;
    private int setsLost;
    private int pointsLost;

    public Contestant(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        gamesWon = setsLost = pointsLost = 0;
    }
    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getName() {
    	return firstName + " " + lastName;
    }
    
    public int getGamesWon() {
        return gamesWon;
    }

    public void setGamesWon(int gamesWon) {
        this.gamesWon = gamesWon;
    }

    public int getSetsLost() {
        return setsLost;
    }

    public void setSetsLost(int setsLost) {
        this.setsLost = setsLost;
    }

    public int getPointsLost() {
        return pointsLost;
    }

    public void setPointsLost(int pointsLost) {
        this.pointsLost = pointsLost;
    }
    
    @Override
    public String toString() {
        return getName() + " (" + gamesWon + "," + setsLost + "," + pointsLost + ")";
    }
}


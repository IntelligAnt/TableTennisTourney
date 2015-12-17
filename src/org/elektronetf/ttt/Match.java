package org.elektronetf.ttt;

import java.util.ArrayList;
import java.util.List;

public class Match {
    public int numberOfSets;
    public Contestant contestant1;
    public Contestant contestant2;
    public List<List<Integer>> score;

    public Match(int numberOfSets, Contestant contestant1, Contestant contestant2)
    {
        this.numberOfSets = numberOfSets;
        this.contestant1 = contestant1;
        this.contestant2 = contestant2;
        score = new ArrayList<>(6);
        for (int i = 0; i < 6; i++) {
            score.add(new ArrayList<>(3));
        }
    }

    public void setScore(int set, int contestant1, int contestant2)
    {
        if (set > 3)
            return;
        score.get(set).set(0, contestant1);
        score.get(set).set(1, contestant2);
        int sets1 = 0;
        int sets2 = 0;
        for (int i=0; i < 2 * numberOfSets - 1; i++)
        {
            if (score.get(i).get(0) > 10 && score.get(i).get(0) > score.get(i).get(1) + 1)
            {
                sets1++;
            }
            else if (score.get(i).get(1) > 10 && score.get(i).get(1) > score.get(i).get(0) + 1)
            {
                sets2++;
            }
        }
        score.get(5).set(0, sets1);
        score.get(5).set(1, sets2);
    }

    public int finish()
    {
        int winner;
        if (score.get(5).get(0)==numberOfSets)
        {
            winner=0;
        }
        else if(score.get(5).get(1)==numberOfSets)
        {
            winner=1;
        }
        else return -1;
        for (int i=0; i < 2 * numberOfSets - 1; i++)
        {
            contestant1.setPointsLost(contestant1.getPointsLost()+score.get(i).get(1));
            contestant2.setPointsLost(contestant2.getPointsLost()+score.get(i).get(0));
        }
        contestant1.setSetsLost(contestant1.getSetsLost()+score.get(5).get(1));
        contestant2.setSetsLost(contestant2.getSetsLost()+score.get(5).get(0));
        if (winner==0)
        {
            contestant1.setMatchesWon(contestant1.getMatchesWon()+1);
        }
        else
        {
            contestant2.setMatchesWon(contestant2.getMatchesWon()+1);
        }
        return winner;
    }

    @Override
    public String toString() {
        return contestant1 + " – " + contestant2; 
    }
}



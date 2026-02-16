package org.example.cards;

import java.util.ArrayList;
import java.util.List;

public class HandScore {
    HandRanking handRanking;
    List<Integer> tieBreakers;

    public HandScore(){
        handRanking = null;
        tieBreakers = new ArrayList<>();
    }
    public HandScore(HandRanking hr, ArrayList<Integer> tieBreakers){
        this.handRanking = hr;
        this.tieBreakers = tieBreakers;
    }
    public void addHandRanking(HandRanking hr){
        this.handRanking = hr;
    }
    public void addTieBreakers(Integer val){
        tieBreakers.add(val);
    }
}

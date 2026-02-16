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
    public int compare(HandScore hs1, HandScore hs2){
        if(hs1.handRanking.compareTo(hs2.handRanking) > 0){
            return 1;
        }else if (hs1.handRanking.compareTo(hs2.handRanking) < 0){
            return -1;
        }else {
            int i = 0;
            while(i < hs2.tieBreakers.size() && hs1.tieBreakers.get(i).equals(hs2.tieBreakers.get(i))){
                i++;
            }
            return Integer.compare(hs1.tieBreakers.get(i).compareTo(hs2.tieBreakers.get(i)), 0);
        }
    }
    public int compare(HandScore hs){
        if(this.handRanking.compareTo(hs.handRanking) > 0){
            return 1;
        }else if (this.handRanking.compareTo(hs.handRanking) < 0){
            return -1;
        }else {
            int i = 0;
            while(i < this.tieBreakers.size() && this.tieBreakers.get(i).equals(hs.tieBreakers.get(i))){
                i++;
            }
            if(i == this.tieBreakers.size()){
                return 0;
            }
            return Integer.compare(this.tieBreakers.get(i).compareTo(hs.tieBreakers.get(i)), 0);
        }
    }
}

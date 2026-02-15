package org.example.cards;

import java.util.*;

public class FiveCardHandScorer {
    Card[] cards;
    Integer[] ranks;
    Map<CardRank, Integer> rankCount;
    List<List<CardRank>> rankGroups;
    boolean flush;
    int straightHigh;

    public FiveCardHandScorer(Card[] cards){
        this.cards = cards;
        ranks = new Integer[cards.length];
        rankCount = new HashMap<>();
        rankGroups = new ArrayList<>();
        for(int i = 0; i < cards.length; i++){
            rankGroups.add(new ArrayList<>());
        }
        flush = true;
        straightHigh = 0;
    }

    public void compute() {
        Suite setSuite = cards[0].suite;

        for (int i = 0; i < cards.length; i++){
            ranks[i] = cards[i].rank.value();
            rankCount.put(cards[i].rank, rankCount.getOrDefault(cards[i].rank, 0) + 1);
            if(cards[i].suite != setSuite){
                flush = false;
            }
        }

        Arrays.sort(ranks, Collections.reverseOrder());
        checkStraight();
        for(Map.Entry<CardRank, Integer> entry : rankCount.entrySet()){
            rankGroups.get(entry.getValue()).add(entry.getKey());
        }
        for(List<CardRank> l : rankGroups){
            l.sort(Comparator.comparingInt(CardRank::value));
            l.sort(Comparator.reverseOrder());
        }

    }

    public void checkStraight(){
        if(ranks[0] == CardRank.ACE.value()) {
            if (ranks[1] == CardRank.KING.value()) {
                int prevRank = ranks[0];
                int i = 1;
                while (i < ranks.length ){
                    if(prevRank != ranks[i] + 1){
                        return;
                    }
                    prevRank = ranks[i];
                    i++;
                }
                straightHigh = CardRank.ACE.value();
                return;
            } else if (ranks[1] == CardRank.FIVE.value()){
                int prevRank = ranks[1];
                int i = 2;
                while (i < ranks.length ){
                    if(prevRank != ranks[i] + 1){
                        return;
                    }
                    prevRank = ranks[i];
                    i++;
                }
                straightHigh = CardRank.FIVE.value();
                return;
            } else return;
        }
        int prevRank = ranks[0];
        int i = 1;
        while (i < ranks.length ){
            if(prevRank != ranks[i] + 1){
                return;
            }
            prevRank = ranks[i];
            i++;
        }
        straightHigh = ranks[0];
    }

}

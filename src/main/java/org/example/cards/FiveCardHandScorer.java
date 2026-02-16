package org.example.cards;

import java.util.*;

public class FiveCardHandScorer {
    Card[] cards;
    Integer[] ranks;
    Map<CardRank, Integer> rankCount;
    List<List<CardRank>> rankGroups;
    boolean flush;
    int straightHigh;
    HandScore hs;

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
        hs = new HandScore();
    }

    public void calculateScore(){
        if(straightHigh > 0){
            if(flush){
                hs.addHandRanking(HandRanking.STRAIGHT_FLUSH);
                determineAceStraight(hs);
            }else{
                hs.addHandRanking(HandRanking.STRAIGHT);
                determineAceStraight(hs);
            }
        }else {
            if(!rankGroups.get(4).isEmpty()){
                hs.addHandRanking(HandRanking.QUADS);
                hs.tieBreakers.add(rankGroups.get(4).get(0).value());
                Set<Integer> otherCard = new HashSet<>(List.of(ranks));
                otherCard.remove(rankGroups.get(4).get(0).value());
                hs.tieBreakers.addAll(otherCard);
            } else if (!rankGroups.get(3).isEmpty() && !rankGroups.get(2).isEmpty()) {
                hs.addHandRanking(HandRanking.FULL_HOUSE);
                hs.tieBreakers.add(rankGroups.get(3).get(0).value());
                hs.tieBreakers.add(rankGroups.get(2).get(0).value());
            } else if(flush){
                hs.addHandRanking(HandRanking.FLUSH);
                for(int i = 0; i < cards.length; i++){
                    hs.addTieBreakers(ranks[i]);
                }
            } else if(!rankGroups.get(3).isEmpty() && rankGroups.get(2).isEmpty()){
                hs.addHandRanking(HandRanking.TRIPS);
                hs.tieBreakers.add(rankGroups.get(3).get(0).value());
                Set<Integer> otherCards = new HashSet<>(List.of(ranks));
                otherCards.remove(rankGroups.get(3).get(0).value());
                Integer[] sortedRanks = otherCards.toArray(new Integer[0]);
                Arrays.sort(sortedRanks, Comparator.reverseOrder());
                hs.tieBreakers.addAll(Arrays.asList(sortedRanks));
            } else if( rankGroups.get(2).size() > 1){
                hs.addHandRanking(HandRanking.TWO_PAIR);
                hs.tieBreakers.add(rankGroups.get(2).get(0).value());
                hs.tieBreakers.add(rankGroups.get(2).get(1).value());
                Set<Integer> otherCard = new HashSet<>(List.of(ranks));
                otherCard.remove(rankGroups.get(2).get(0).value());
                otherCard.remove(rankGroups.get(2).get(1).value());
                hs.tieBreakers.addAll(otherCard);
            } else if( rankGroups.get(2).size() == 1){
                hs.addHandRanking(HandRanking.PAIR);
                hs.tieBreakers.add(rankGroups.get(2).get(0).value());
                Set<Integer> otherCards = new HashSet<>(List.of(ranks));
                otherCards.remove(rankGroups.get(2).get(0).value());
                Integer[] sortedRanks = otherCards.toArray(new Integer[0]);
                Arrays.sort(sortedRanks, Comparator.reverseOrder());
                hs.tieBreakers.addAll(Arrays.asList(sortedRanks));
            } else {
                hs.addHandRanking(HandRanking.HIGH_CARD);
                hs.tieBreakers.addAll(Arrays.asList(ranks));
            }
        }
    }

    private void determineAceStraight(HandScore hs) {
        if(straightHigh == CardRank.ACE.value()){
            if(ranks[1] == CardRank.KING.value()){
                hs.tieBreakers.add(CardRank.ACE.value());
            } else if(ranks[1] == CardRank.FIVE.value()){
                hs.tieBreakers.add(CardRank.FIVE.value());
            }
        }else hs.tieBreakers.add(straightHigh);
    }

    public void analyze() {
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
            l.sort(Comparator.comparingInt(CardRank::value).reversed());
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

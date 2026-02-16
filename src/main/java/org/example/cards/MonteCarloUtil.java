package org.example.cards;

import static org.example.cards.HandRankerUtil.bestFiveCardHandOfSeven;

public final class MonteCarloUtil {
    Board board;
    int[] wins;
    double[] res;
    Hand[] hands;

    public MonteCarloUtil(Hand[] hands){
        this.board = new Board(hands, new Deck());
        this.wins = new int[hands.length];
        this.res = new double[hands.length];
        this.hands = hands;
    }

    public double[] simulate(){
        for(int i = 0; i < 10000; i++){
            board.drawBoard();
            determineBestHand();
            board.clearBoard();
        }

        for(int i = 0; i < wins.length; i++){
            System.out.println("Win: " + wins[i]);
            res[i] = (double) wins[i] / 10000;
        }
        return res;
    }
    public void determineBestHand(){
        int bestIdx = -1;
        HandScore bestHand = null;
        for(int j = 0; j < hands.length; j++){
            if(bestIdx < 0){
                bestIdx = j;
                bestHand = bestFiveCardHandOfSeven(hands[j], board);
            }else {
                if(bestHand.compare(bestFiveCardHandOfSeven(hands[j], board)) < 1){
                    bestIdx = j;
                    bestHand = bestFiveCardHandOfSeven(hands[j], board);
                }
            }
        }
        wins[bestIdx]++;
    }
}

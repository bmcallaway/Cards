package org.example.cards;

public class HandRankerUtil {
    static FiveCardHandScorer scorer;
    public static HandScore bestFiveCardHandOfSeven(Hand hand, Board board){

        Card[] cards = new Card[hand.size() + board.size()];

        int i = 0;
        for(Card c : hand.getCards()){
            cards[i] = c;
            i++;
        }
        for(Card c : board.cards){
            cards[i] = c;
            i++;
        }

        //0 1 2 3 4 5 6
        //A K Q J T 9 8
        //x
        //  j
        //    l
        HandScore best = null;
        Card[] five = new Card[5];

        for (int[] c : COMBOS_7_CHOOSE_5) {
            five[0] = cards[c[0]];
            five[1] = cards[c[1]];
            five[2] = cards[c[2]];
            five[3] = cards[c[3]];
            five[4] = cards[c[4]];

            FiveCardHandScorer scorer = new FiveCardHandScorer(five);
            scorer.analyze();
            scorer.calculateScore();

            if (best == null || scorer.hs.compare(best) > 0) best = scorer.hs;
        }
        return best;
    }
    static final int[][] COMBOS_7_CHOOSE_5 = {
            {0,1,2,3,4},{0,1,2,3,5},{0,1,2,3,6},
            {0,1,2,4,5},{0,1,2,4,6},{0,1,2,5,6},
            {0,1,3,4,5},{0,1,3,4,6},{0,1,3,5,6},
            {0,1,4,5,6},
            {0,2,3,4,5},{0,2,3,4,6},{0,2,3,5,6},
            {0,2,4,5,6},
            {0,3,4,5,6},
            {1,2,3,4,5},{1,2,3,4,6},{1,2,3,5,6},
            {1,2,4,5,6},
            {1,3,4,5,6},
            {2,3,4,5,6}
    };

}

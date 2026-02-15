package org.example.cards;

public final class MonteCarloUtil {
    static Deck deck = new Deck();
    static Board board = new Board();
    public static double[] simulate(Hand[] hands){
        double[] res = new double[hands.length];
        removeHands(hands);
        deck.shuffle();

        for(int i = 0; i < 10000; i++){
            drawBoard();
            //evaluate the drawn board compared to the hands

        }


        return null;
    }
    public static void removeHands(Hand[] hands){
        for(Hand h : hands){
            for(Card c : h.getCards()){
                deck.remove(c);
            }
        }
    }
    public static void drawBoard(){
        board = new Board();
        for(int i = 0; i < board.size() ; i++){
            board.add(deck.pop());
        }
    }
}

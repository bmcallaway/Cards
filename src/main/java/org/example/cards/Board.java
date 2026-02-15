package org.example.cards;

public class Board {
    Card[] cards = new Card[5];
    int cardCount = 0;
    public void add(Card c){
        if(cardCount < 5){
            cards[cardCount] = c;
        } else{
            throw new IllegalArgumentException("Board Full - Unable to Add Card");
        }
    }
    public void clearBoard(){
        cardCount = 0;
        cards = new Card[5];
    }
    public int size(){
        return cards.length;
    }
}

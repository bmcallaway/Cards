package org.example.cards;

public class Hand {
    Card[] cards;
    int size;

    public Hand(){
        cards = new Card[2];
        size = 0;
    }
    public Hand(Card c1, Card c2){
        cards = new Card[2];
        cards[0] = c1;
        cards[1] = c2;
        size = 2;
    }
    public void addCard(Card card){
        if(size < 2){
            cards[size] = card;
        }
    }
    public Card[] getCards(){
        return cards;
    }

}

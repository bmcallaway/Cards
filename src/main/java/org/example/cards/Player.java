package org.example.cards;

import java.util.Arrays;

public class Player {
    Card[] cards;
    int cardCount;

    public Player(){
        cards = new Card[2];
        cardCount = 0;
    }

    public void addCard(Card card){
        cards[cardCount] = card;
        cardCount++;
    }

    public void muck(){
        cardCount = 0;
        Arrays.fill(cards, null);
    }

    public void showCard(){
        for(Card c : cards){
            System.out.println(c.suite.getSymbol() + c.rank);
        }
    }
}

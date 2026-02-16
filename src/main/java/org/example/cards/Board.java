package org.example.cards;

import java.util.Arrays;
import java.util.List;

public class Board {

    Hand[] hands;
    Deck deck;

    Card[] cards = new Card[5];
    int cardCount = 0;

    public Board(){

    }

    public Board(Hand[] hands, Deck deck){
        this.hands = hands;
        this.deck = deck;
        for (Hand hand : hands){
            deck.removeAll(Arrays.asList(hand.cards));
        }
        deck.shuffle();
    }

    public void add(Card c){
        if(cardCount < 5){
            cards[cardCount] = c;
            cardCount++;
        } else{
            throw new IllegalArgumentException("Board Full - Unable to Add Card");
        }
    }
    /*
    This function will draw five cards from the deck onto the board.
    Assumptions are the board is already cleared and the deck is full(minus the dealt hands
    already taken from the deck).
     */
    public void drawBoard(){
        if(deck.isEmpty()){
            throw new RuntimeException("Deck is Empty - Nothing available to pop");
        }
        for(int i = 0; i < cards.length ; i++){
            this.add(deck.pop());
        }
    }
    public void clearBoard(){
        if(cards[0] == null){
            throw new IllegalArgumentException("No cards to clear from board");
        }
        int cardsRemoved = hands.length * 2;
        deck.addAll(Arrays.asList(cards));
        deck.partialShuffle(cardsRemoved);
        cards = new Card[5];
        cardCount = 0;
    }
    public int size(){
        return cardCount;
    }
    public Card[] getCards(){
        return cards;
    }
}

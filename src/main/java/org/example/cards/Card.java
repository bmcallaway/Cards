package org.example.cards;

public class Card {
    CardRank rank;
    Suite suite;
    public Card(Suite suite, CardRank rank){
        this.rank = rank;
        this.suite = suite;
    }
}

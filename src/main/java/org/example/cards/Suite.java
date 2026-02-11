package org.example.cards;

public enum Suite {
    SPADES("♠"),
    CLUBS("♣"),
    DIAMONDS("♦"),
    HEARTS("♥");

    private final String symbol;

    Suite(String symbol){
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}

package org.example.cards;

import java.util.Objects;

public class Card {
    CardRank rank;
    Suite suite;
    public Card(Suite suite, CardRank rank){
        this.rank = rank;
        this.suite = suite;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card)) return false;
        Card card = (Card) o;
        return suite == card.suite && rank == card.rank;
    }

    @Override
    public int hashCode() {
        return Objects.hash(suite, rank);
    }

}

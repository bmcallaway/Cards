package org.example.cards;

import java.util.*;

public class Deck extends Stack<Card> {
    public Deck(){
        int i = 0;
        for(Suite s : Suite.values()){
            for(CardRank cR : CardRank.values()){
                this.add(new Card(s, cR));
                i++;
            }
        }
    }
    public void showDeck(){
        for(Card c : this){
            System.out.println(c.rank + c.suite.getSymbol());
        }
    }
    public void shuffle(){
        Random rand = new Random();
        Card[] copy = this.toArray(new Card[0]);
        int i = copy.length;
        while(i > 0){
            int r = rand.nextInt(0, i);
            Card temp = copy[i-1];
            copy[i-1] = copy[r];
            copy[r] = temp;
            i--;
        }
        this.clear();
        this.addAll(Arrays.asList(copy));
    }
}

package org.example.cards;

import java.util.ArrayList;
import java.util.List;

public class Table {
    int seats;
    List<Player> players;
    Deck deck;
    public Table(){
        seats = 8;
        players = new ArrayList<>();
        deck = new Deck();
    }
    public void addPlayer(Player player){
        players.add(player);
    }
    public void removePlayer(Player player){
        players.remove(player);
    }
    public void deal(){
        for (Player player : players) {
            player.addCard(deck.pop());
        }
        for (Player player : players) {
            player.addCard(deck.pop());
        }
    }

}

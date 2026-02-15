package org.example.cards;

import java.util.ArrayList;
import java.util.List;

public class Table {
    boolean inProgress;
    int seats;
    Player dealer;
    Player smallBlind;
    Player bigBlind;
    List<Player> players;
    Deck deck;
    public Table(){
        inProgress = false;
        seats = 8;
        players = new ArrayList<>();
        deck = new Deck();
    }
    public void addPlayer(Player player){
        while(players.size() < seats){
            players.add(player);
        }
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

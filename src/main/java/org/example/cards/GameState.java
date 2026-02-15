package org.example.cards;

import java.util.ArrayList;
import java.util.List;

public class GameState {
    boolean inProgress;
    List<Player> players;
    Player action;
    Player dealer;
    Player sb;
    Player bb;
    public GameState(){
        inProgress = false;
        players = new ArrayList<>();
    }

    public void startGame(){
        action = dealer;
    }

}

package org.example.cards;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CardsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CardsApplication.class, args);

        Deck mainDeck = new Deck();
        mainDeck.shuffle();
        mainDeck.shuffle();


        Table t1 = new Table();
        t1.deck.shuffle();
        t1.deck.shuffle();
        Player p1 = new Player();
        Player p2 = new Player();
        t1.addPlayer(p1);
        t1.addPlayer(p2);
        t1.deal();
        for(Player p : t1.players){
            p.showCard();
        }

    }

}

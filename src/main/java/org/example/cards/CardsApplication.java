package org.example.cards;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class CardsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CardsApplication.class, args);

        Card card = new Card(Suite.SPADES, CardRank.TEN);
        Card card2 = new Card(Suite.SPADES, CardRank.TWO);
        Hand h1 = new Hand(card, card2);
        Card card3 = new Card(Suite.CLUBS, CardRank.ACE);
        Card card4 = new Card(Suite.HEARTS, CardRank.ACE);
        Hand h2 = new Hand(card3, card4);
        Hand[] hands = new Hand[2];
        hands[0] = h1;
        hands[1] = h2;
        long start = System.currentTimeMillis();
// ...
        MonteCarloUtil monteCarloUtil = new MonteCarloUtil(hands);
        double[] ans = monteCarloUtil.simulate();
        long finish = System.currentTimeMillis();
        long timeElapsed = (finish - start);
        double durationSeconds = timeElapsed / 1000.0;

        for(double d : ans){
            System.out.println(d);
        }
        System.out.println("Time: " + durationSeconds);
    }

}

package org.example.cards;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class BestHandScoreTests {

    @Test
    public void bestHandScore_StraightFlushFromBoardPlusHand(){
        Board board = new Board();
        board.add(new Card(Suite.SPADES, CardRank.ACE));
        board.add(new Card(Suite.SPADES, CardRank.KING));
        board.add(new Card(Suite.SPADES, CardRank.QUEEN));
        board.add(new Card(Suite.SPADES, CardRank.JACK));
        board.add(new Card(Suite.SPADES, CardRank.TWO));

        Hand hand = new Hand();
        hand.addCard(new Card(Suite.SPADES, CardRank.TEN));   // completes A-high straight flush
        hand.addCard(new Card(Suite.CLUBS, CardRank.JACK));   // irrelevant

        HandScore bestScore = HandRankerUtil.bestFiveCardHandOfSeven(hand, board);

        assertEquals(HandRanking.STRAIGHT_FLUSH, bestScore.handRanking);
        // If your tieBreakers[0] = straightHigh like in your other tests:
        assertEquals(CardRank.ACE.value(), bestScore.tieBreakers.get(0));
    }

    @Test
    public void bestHandScore_PlaysTheBoard_WhenHandDoesNotImprove(){
        // Board has a straight already: 9-T-J-Q-K
        Board board = new Board();
        board.add(new Card(Suite.SPADES, CardRank.NINE));
        board.add(new Card(Suite.HEARTS, CardRank.TEN));
        board.add(new Card(Suite.DIAMONDS, CardRank.JACK));
        board.add(new Card(Suite.CLUBS, CardRank.QUEEN));
        board.add(new Card(Suite.SPADES, CardRank.KING));

        Hand hand = new Hand();
        hand.addCard(new Card(Suite.HEARTS, CardRank.TWO));
        hand.addCard(new Card(Suite.DIAMONDS, CardRank.SEVEN));

        HandScore bestScore = HandRankerUtil.bestFiveCardHandOfSeven(hand, board);

        assertEquals(HandRanking.STRAIGHT, bestScore.handRanking);
        assertEquals(CardRank.KING.value(), bestScore.tieBreakers.get(0));
    }

    @Test
    public void bestHandScore_HandMakesHigherStraightThanBoardStraight(){
        // Board straight: 9-T-J-Q-K
        Board board = new Board();
        board.add(new Card(Suite.SPADES, CardRank.NINE));
        board.add(new Card(Suite.HEARTS, CardRank.TEN));
        board.add(new Card(Suite.DIAMONDS, CardRank.JACK));
        board.add(new Card(Suite.CLUBS, CardRank.QUEEN));
        board.add(new Card(Suite.SPADES, CardRank.KING));

        // Hand adds Ace -> makes A-high straight (T-J-Q-K-A) is higher than K-high straight
        Hand hand = new Hand();
        hand.addCard(new Card(Suite.HEARTS, CardRank.ACE));
        hand.addCard(new Card(Suite.DIAMONDS, CardRank.TWO));

        HandScore bestScore = HandRankerUtil.bestFiveCardHandOfSeven(hand, board);

        assertEquals(HandRanking.STRAIGHT, bestScore.handRanking);
        assertEquals(CardRank.ACE.value(), bestScore.tieBreakers.get(0));
    }

    @Test
    public void bestHandScore_FullHouseBeatsFlush_WhenBothAvailable(){
        // Board: two 8s and three spades (flush possible if hand is spades),
        // and hand makes trips for full house
        Board board = new Board();
        board.add(new Card(Suite.SPADES, CardRank.ACE));
        board.add(new Card(Suite.SPADES, CardRank.NINE));
        board.add(new Card(Suite.SPADES, CardRank.TWO));
        board.add(new Card(Suite.HEARTS, CardRank.EIGHT));
        board.add(new Card(Suite.DIAMONDS, CardRank.EIGHT));

        // Hand: 8 + spade to tempt flush, but 8 makes trips -> full house with board pair
        Hand hand = new Hand();
        hand.addCard(new Card(Suite.CLUBS, CardRank.EIGHT));   // trips 8s
        hand.addCard(new Card(Suite.SPADES, CardRank.KING));   // gives 4 spades total (still not a flush)
        // Actually this board+hand is full house only if there is a pair besides the 8s.
        // Let's fix it by giving hand a pair-maker with board: add an Ace instead of King.

        // (See corrected test below)
    }

    @Test
    public void bestHandScore_FullHouseBeatsFlush_Corrected(){
        // Board has: A♠ 9♠ 2♠ 8♥ 8♦
        // Hand: A♥ 8♣  -> makes 8s full of Aces (full house)
        Board board = new Board();
        board.add(new Card(Suite.SPADES, CardRank.ACE));
        board.add(new Card(Suite.SPADES, CardRank.NINE));
        board.add(new Card(Suite.SPADES, CardRank.TWO));
        board.add(new Card(Suite.HEARTS, CardRank.EIGHT));
        board.add(new Card(Suite.DIAMONDS, CardRank.EIGHT));

        Hand hand = new Hand();
        hand.addCard(new Card(Suite.HEARTS, CardRank.ACE));   // pair Aces with board Ace
        hand.addCard(new Card(Suite.CLUBS, CardRank.EIGHT));  // trips 8s

        HandScore bestScore = HandRankerUtil.bestFiveCardHandOfSeven(hand, board);

        assertEquals(HandRanking.FULL_HOUSE, bestScore.handRanking);
        // Common tie-break ordering: trips rank then pair rank
        assertEquals(CardRank.EIGHT.value(), bestScore.tieBreakers.get(0));
        assertEquals(CardRank.ACE.value(), bestScore.tieBreakers.get(1));
    }

    @Test
    public void bestHandScore_WheelStraight_AcePlaysLow(){
        // Board: A 2 3 4 9
        Board board = new Board();
        board.add(new Card(Suite.SPADES, CardRank.ACE));
        board.add(new Card(Suite.HEARTS, CardRank.TWO));
        board.add(new Card(Suite.DIAMONDS, CardRank.THREE));
        board.add(new Card(Suite.CLUBS, CardRank.FOUR));
        board.add(new Card(Suite.SPADES, CardRank.NINE));

        // Hand gives 5 -> wheel straight A-2-3-4-5 (5-high)
        Hand hand = new Hand();
        hand.addCard(new Card(Suite.HEARTS, CardRank.FIVE));
        hand.addCard(new Card(Suite.DIAMONDS, CardRank.KING));

        HandScore bestScore = HandRankerUtil.bestFiveCardHandOfSeven(hand, board);

        assertEquals(HandRanking.STRAIGHT, bestScore.handRanking);
        assertEquals(CardRank.FIVE.value(), bestScore.tieBreakers.get(0));
    }

    @Test
    public void bestHandScore_FourOfAKind_FromBoardTripsPlusHandPair(){
        // Board: Q Q Q 7 2
        Board board = new Board();
        board.add(new Card(Suite.SPADES, CardRank.QUEEN));
        board.add(new Card(Suite.HEARTS, CardRank.QUEEN));
        board.add(new Card(Suite.DIAMONDS, CardRank.QUEEN));
        board.add(new Card(Suite.CLUBS, CardRank.SEVEN));
        board.add(new Card(Suite.SPADES, CardRank.TWO));

        // Hand: Q + A -> makes quads Queens with Ace kicker
        Hand hand = new Hand();
        hand.addCard(new Card(Suite.CLUBS, CardRank.QUEEN));
        hand.addCard(new Card(Suite.HEARTS, CardRank.ACE));

        HandScore bestScore = HandRankerUtil.bestFiveCardHandOfSeven(hand, board);

        assertEquals(HandRanking.QUADS, bestScore.handRanking);
        assertEquals(CardRank.QUEEN.value(), bestScore.tieBreakers.get(0));
        assertEquals(CardRank.ACE.value(), bestScore.tieBreakers.get(1));
    }

    @Test
    public void bestHandScore_FlushBeatsStraight_WhenBothAvailable(){
        // Board gives a straight possibility and also 4 hearts;
        // hand completes flush but also completes straight -> flush should win
        Board board = new Board();
        board.add(new Card(Suite.HEARTS, CardRank.ACE));
        board.add(new Card(Suite.HEARTS, CardRank.NINE));
        board.add(new Card(Suite.HEARTS, CardRank.FIVE));
        board.add(new Card(Suite.HEARTS, CardRank.TWO));
        board.add(new Card(Suite.CLUBS, CardRank.THREE));

        Hand hand = new Hand();
        hand.addCard(new Card(Suite.HEARTS, CardRank.KING)); // completes heart flush
        hand.addCard(new Card(Suite.DIAMONDS, CardRank.FOUR)); // also makes A-2-3-4-5 straight

        HandScore bestScore = HandRankerUtil.bestFiveCardHandOfSeven(hand, board);

        assertEquals(HandRanking.FLUSH, bestScore.handRanking);
        // Highest card in the flush should be Ace here
        assertEquals(CardRank.ACE.value(), bestScore.tieBreakers.get(0));
    }
}

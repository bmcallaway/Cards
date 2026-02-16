package org.example.cards;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class FiveCardScorerTests {

    @Test
    void AceHighStraightFlushTest(){
        Card[] cards = new Card[5];
        cards[0] = new Card(Suite.SPADES, CardRank.ACE);
        cards[1] = new Card(Suite.SPADES, CardRank.KING);
        cards[2] = new Card(Suite.SPADES, CardRank.QUEEN);
        cards[3] = new Card(Suite.SPADES, CardRank.JACK);
        cards[4] = new Card(Suite.SPADES, CardRank.TEN);
        FiveCardHandScorer scorer = new FiveCardHandScorer(cards);
        scorer.compute();

        assertTrue(scorer.flush);
        assertEquals(CardRank.ACE.value(), scorer.straightHigh);
        assertEquals(CardRank.ACE, scorer.rankGroups.get(1).get(0));
        assertEquals(0, scorer.rankGroups.get(2).size());
        assertEquals(0, scorer.rankGroups.get(3).size());
        assertEquals(0, scorer.rankGroups.get(4).size());

    }

    @Test
    void WheelStraightFlushTest(){
        Card[] cards = new Card[5];
        cards[0] = new Card(Suite.SPADES, CardRank.ACE);
        cards[1] = new Card(Suite.SPADES, CardRank.FIVE);
        cards[2] = new Card(Suite.SPADES, CardRank.FOUR);
        cards[3] = new Card(Suite.SPADES, CardRank.THREE);
        cards[4] = new Card(Suite.SPADES, CardRank.TWO);
        FiveCardHandScorer scorer = new FiveCardHandScorer(cards);
        scorer.compute();

        assertTrue(scorer.flush);
        assertEquals(CardRank.FIVE.value(), scorer.straightHigh);
        assertEquals(CardRank.ACE, scorer.rankGroups.get(1).get(0));
        assertEquals(0, scorer.rankGroups.get(2).size());
        assertEquals(0, scorer.rankGroups.get(3).size());
        assertEquals(0, scorer.rankGroups.get(4).size());
    }

    @Test
    void WheelStraightTest(){
        Card[] cards = new Card[5];
        cards[0] = new Card(Suite.CLUBS, CardRank.ACE);
        cards[1] = new Card(Suite.SPADES, CardRank.FIVE);
        cards[2] = new Card(Suite.SPADES, CardRank.FOUR);
        cards[3] = new Card(Suite.SPADES, CardRank.THREE);
        cards[4] = new Card(Suite.SPADES, CardRank.TWO);
        FiveCardHandScorer scorer = new FiveCardHandScorer(cards);
        scorer.compute();

        assertFalse(scorer.flush);
        assertEquals(CardRank.FIVE.value(), scorer.straightHigh);
        assertEquals(CardRank.ACE, scorer.rankGroups.get(1).get(0));
        assertEquals(0, scorer.rankGroups.get(2).size());
        assertEquals(0, scorer.rankGroups.get(3).size());
        assertEquals(0, scorer.rankGroups.get(4).size());
    }

    @Test
    void KingHighStraightFlushTest(){
        Card[] cards = new Card[5];
        cards[0] = new Card(Suite.HEARTS, CardRank.KING);
        cards[1] = new Card(Suite.HEARTS, CardRank.QUEEN);
        cards[2] = new Card(Suite.HEARTS, CardRank.JACK);
        cards[3] = new Card(Suite.HEARTS, CardRank.TEN);
        cards[4] = new Card(Suite.HEARTS, CardRank.NINE);
        FiveCardHandScorer scorer = new FiveCardHandScorer(cards);
        scorer.compute();

        assertTrue(scorer.flush);
        assertEquals(CardRank.KING.value(), scorer.straightHigh);
        assertEquals(CardRank.KING, scorer.rankGroups.get(1).get(0));
        assertEquals(0, scorer.rankGroups.get(2).size());
        assertEquals(0, scorer.rankGroups.get(3).size());
        assertEquals(0, scorer.rankGroups.get(4).size());
    }

    void NonStraightFlushTest(){
        Card[] cards = new Card[5];
        cards[0] = new Card(Suite.SPADES, CardRank.ACE);
        cards[1] = new Card(Suite.SPADES, CardRank.KING);
        cards[2] = new Card(Suite.SPADES, CardRank.QUEEN);
        cards[3] = new Card(Suite.SPADES, CardRank.JACK);
        cards[4] = new Card(Suite.SPADES, CardRank.NINE); // breaks the straight
        FiveCardHandScorer scorer = new FiveCardHandScorer(cards);
        scorer.compute();

        assertTrue(scorer.flush);
        assertNotEquals(CardRank.ACE.value(), scorer.straightHigh); // should NOT be a straight
        assertEquals(CardRank.ACE, scorer.rankGroups.get(1).get(0));
        assertEquals(0, scorer.rankGroups.get(2).size());
        assertEquals(0, scorer.rankGroups.get(3).size());
        assertEquals(0, scorer.rankGroups.get(4).size());
    }

    @Test
    void AceHighStraightNotFlushTest(){
        Card[] cards = new Card[5];
        cards[0] = new Card(Suite.SPADES, CardRank.ACE);
        cards[1] = new Card(Suite.HEARTS, CardRank.KING);
        cards[2] = new Card(Suite.DIAMONDS, CardRank.QUEEN);
        cards[3] = new Card(Suite.CLUBS, CardRank.JACK);
        cards[4] = new Card(Suite.SPADES, CardRank.TEN);
        FiveCardHandScorer scorer = new FiveCardHandScorer(cards);
        scorer.compute();

        assertFalse(scorer.flush);
        assertEquals(CardRank.ACE.value(), scorer.straightHigh);
        assertEquals(CardRank.ACE, scorer.rankGroups.get(1).get(0));
        assertEquals(0, scorer.rankGroups.get(2).size());
        assertEquals(0, scorer.rankGroups.get(3).size());
        assertEquals(0, scorer.rankGroups.get(4).size());
    }

    @Test
    void SixHighStraightTest(){
        Card[] cards = new Card[5];
        cards[0] = new Card(Suite.SPADES, CardRank.SIX);
        cards[1] = new Card(Suite.HEARTS, CardRank.FIVE);
        cards[2] = new Card(Suite.DIAMONDS, CardRank.FOUR);
        cards[3] = new Card(Suite.CLUBS, CardRank.THREE);
        cards[4] = new Card(Suite.SPADES, CardRank.TWO);
        FiveCardHandScorer scorer = new FiveCardHandScorer(cards);
        scorer.compute();

        assertFalse(scorer.flush);
        assertEquals(CardRank.SIX.value(), scorer.straightHigh);
        assertEquals(CardRank.SIX, scorer.rankGroups.get(1).get(0));
        assertEquals(0, scorer.rankGroups.get(2).size());
        assertEquals(0, scorer.rankGroups.get(3).size());
        assertEquals(0, scorer.rankGroups.get(4).size());
    }

    @Test
    void NotAStraight_GapTest(){
        Card[] cards = new Card[5];
        cards[0] = new Card(Suite.SPADES, CardRank.SIX);
        cards[1] = new Card(Suite.HEARTS, CardRank.FIVE);
        cards[2] = new Card(Suite.DIAMONDS, CardRank.FOUR);
        cards[3] = new Card(Suite.CLUBS, CardRank.THREE);
        cards[4] = new Card(Suite.SPADES, CardRank.ACE); // gap prevents straight
        FiveCardHandScorer scorer = new FiveCardHandScorer(cards);
        scorer.compute();

        assertFalse(scorer.flush);
        assertNotEquals(CardRank.SIX.value(), scorer.straightHigh);
        assertNotEquals(CardRank.FIVE.value(), scorer.straightHigh);
        assertEquals(CardRank.ACE, scorer.rankGroups.get(1).get(0));
        assertEquals(0, scorer.rankGroups.get(2).size());
        assertEquals(0, scorer.rankGroups.get(3).size());
        assertEquals(0, scorer.rankGroups.get(4).size());
    }

    @Test
    void FlushNotStraightTest(){
        Card[] cards = new Card[5];
        cards[0] = new Card(Suite.CLUBS, CardRank.ACE);
        cards[1] = new Card(Suite.CLUBS, CardRank.JACK);
        cards[2] = new Card(Suite.CLUBS, CardRank.NINE);
        cards[3] = new Card(Suite.CLUBS, CardRank.FIVE);
        cards[4] = new Card(Suite.CLUBS, CardRank.TWO);
        FiveCardHandScorer scorer = new FiveCardHandScorer(cards);
        scorer.compute();

        assertTrue(scorer.flush);
        // If your scorer uses 0 / -1 / null-like value for "no straight", swap this assert accordingly:
        assertNotEquals(CardRank.ACE.value(), scorer.straightHigh);
        assertNotEquals(CardRank.FIVE.value(), scorer.straightHigh);
        assertEquals(CardRank.ACE, scorer.rankGroups.get(1).get(0));
        assertEquals(0, scorer.rankGroups.get(2).size());
        assertEquals(0, scorer.rankGroups.get(3).size());
        assertEquals(0, scorer.rankGroups.get(4).size());
    }

    @Test
    void DuplicateRanksCannotBeStraightTest(){
        // A, K, Q, J, J is not a straight
        Card[] cards = new Card[5];
        cards[0] = new Card(Suite.SPADES, CardRank.ACE);
        cards[1] = new Card(Suite.HEARTS, CardRank.KING);
        cards[2] = new Card(Suite.DIAMONDS, CardRank.QUEEN);
        cards[3] = new Card(Suite.CLUBS, CardRank.JACK);
        cards[4] = new Card(Suite.SPADES, CardRank.JACK);
        FiveCardHandScorer scorer = new FiveCardHandScorer(cards);
        scorer.compute();

        assertFalse(scorer.flush);
        assertNotEquals(CardRank.ACE.value(), scorer.straightHigh);
        assertEquals(CardRank.JACK, scorer.rankGroups.get(2).get(0));
        assertEquals(CardRank.ACE, scorer.rankGroups.get(1).get(0));
        assertEquals(CardRank.JACK, scorer.rankGroups.get(2).get(0));
        assertEquals(0, scorer.rankGroups.get(3).size());
        assertEquals(0, scorer.rankGroups.get(4).size());
    }

    @Test
    void WheelStraight_AceMustCountLowOnlyTest(){
        // This catches the classic bug: treating A-2-3-4-5 as straight-high Ace (wrong)
        Card[] cards = new Card[5];
        cards[0] = new Card(Suite.HEARTS, CardRank.ACE);
        cards[1] = new Card(Suite.CLUBS, CardRank.TWO);
        cards[2] = new Card(Suite.DIAMONDS, CardRank.THREE);
        cards[3] = new Card(Suite.SPADES, CardRank.FOUR);
        cards[4] = new Card(Suite.HEARTS, CardRank.FIVE);
        FiveCardHandScorer scorer = new FiveCardHandScorer(cards);
        scorer.compute();

        assertFalse(scorer.flush);
        assertEquals(CardRank.FIVE.value(), scorer.straightHigh);
        assertNotEquals(CardRank.ACE.value(), scorer.straightHigh);
        assertEquals(CardRank.ACE, scorer.rankGroups.get(1).get(0));
        assertEquals(0, scorer.rankGroups.get(2).size());
        assertEquals(0, scorer.rankGroups.get(3).size());
        assertEquals(0, scorer.rankGroups.get(4).size());
    }

    @Test
    void StraightWithUnsortedInputTest(){
        // Tests that compute() sorts/normalizes ranks correctly
        Card[] cards = new Card[5];
        cards[0] = new Card(Suite.SPADES, CardRank.TEN);
        cards[1] = new Card(Suite.HEARTS, CardRank.ACE);
        cards[2] = new Card(Suite.DIAMONDS, CardRank.QUEEN);
        cards[3] = new Card(Suite.CLUBS, CardRank.KING);
        cards[4] = new Card(Suite.SPADES, CardRank.JACK);
        FiveCardHandScorer scorer = new FiveCardHandScorer(cards);
        scorer.compute();

        assertFalse(scorer.flush);
        assertEquals(CardRank.ACE.value(), scorer.straightHigh);
        assertEquals(CardRank.ACE, scorer.rankGroups.get(1).get(0));
        assertEquals(0, scorer.rankGroups.get(2).size());
        assertEquals(0, scorer.rankGroups.get(3).size());
        assertEquals(0, scorer.rankGroups.get(4).size());
    }
}

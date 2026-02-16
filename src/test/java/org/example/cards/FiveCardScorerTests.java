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
        scorer.analyze();

        assertTrue(scorer.flush);
        assertEquals(CardRank.ACE.value(), scorer.straightHigh);

        scorer.calculateScore();
        assertEquals(HandRanking.STRAIGHT_FLUSH, scorer.hs.handRanking);
        assertEquals(CardRank.ACE.value(), scorer.hs.tieBreakers.get(0));
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
        scorer.analyze();

        assertTrue(scorer.flush);
        assertEquals(CardRank.FIVE.value(), scorer.straightHigh);

        scorer.calculateScore();
        assertEquals(HandRanking.STRAIGHT_FLUSH, scorer.hs.handRanking);
        assertEquals(CardRank.FIVE.value(), scorer.hs.tieBreakers.get(0));
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
        scorer.analyze();

        assertFalse(scorer.flush);
        assertEquals(CardRank.FIVE.value(), scorer.straightHigh);

        scorer.calculateScore();
        assertEquals(HandRanking.STRAIGHT, scorer.hs.handRanking);
        assertEquals(CardRank.FIVE.value(), scorer.hs.tieBreakers.get(0));
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
        scorer.analyze();

        assertTrue(scorer.flush);
        assertEquals(CardRank.KING.value(), scorer.straightHigh);

        scorer.calculateScore();
        assertEquals(HandRanking.STRAIGHT_FLUSH, scorer.hs.handRanking);
        assertEquals(CardRank.KING.value(), scorer.hs.tieBreakers.get(0));
    }

    @Test
    void NonStraightFlushTest(){
        Card[] cards = new Card[5];
        cards[0] = new Card(Suite.SPADES, CardRank.ACE);
        cards[1] = new Card(Suite.SPADES, CardRank.KING);
        cards[2] = new Card(Suite.SPADES, CardRank.QUEEN);
        cards[3] = new Card(Suite.SPADES, CardRank.JACK);
        cards[4] = new Card(Suite.SPADES, CardRank.NINE); // breaks the straight

        FiveCardHandScorer scorer = new FiveCardHandScorer(cards);
        scorer.analyze();

        assertTrue(scorer.flush);
        assertEquals(0, scorer.straightHigh); // your other tests imply "no straight" => 0

        scorer.calculateScore();
        assertEquals(HandRanking.FLUSH, scorer.hs.handRanking);
        assertEquals(CardRank.ACE.value(), scorer.hs.tieBreakers.get(0));
        assertEquals(CardRank.KING.value(), scorer.hs.tieBreakers.get(1));
        assertEquals(CardRank.QUEEN.value(), scorer.hs.tieBreakers.get(2));
        assertEquals(CardRank.JACK.value(), scorer.hs.tieBreakers.get(3));
        assertEquals(CardRank.NINE.value(), scorer.hs.tieBreakers.get(4));

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
        scorer.analyze();

        assertFalse(scorer.flush);
        assertEquals(CardRank.ACE.value(), scorer.straightHigh);

        scorer.calculateScore();
        assertEquals(HandRanking.STRAIGHT, scorer.hs.handRanking);
        assertEquals(CardRank.ACE.value(), scorer.hs.tieBreakers.get(0));

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
        scorer.analyze();

        assertFalse(scorer.flush);
        assertEquals(CardRank.SIX.value(), scorer.straightHigh);

        scorer.calculateScore();
        assertEquals(HandRanking.STRAIGHT, scorer.hs.handRanking);
        assertEquals(CardRank.SIX.value(), scorer.hs.tieBreakers.get(0));

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
        scorer.analyze();

        assertFalse(scorer.flush);
        assertEquals(0, scorer.straightHigh);

        scorer.calculateScore();
        assertNotEquals(HandRanking.STRAIGHT, scorer.hs.handRanking);
        assertNotEquals(HandRanking.STRAIGHT_FLUSH, scorer.hs.handRanking);
        assertEquals(CardRank.ACE.value(), scorer.hs.tieBreakers.get(0));
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
        scorer.analyze();

        assertTrue(scorer.flush);
        assertEquals(0, scorer.straightHigh);

        scorer.calculateScore();
        assertEquals(HandRanking.FLUSH, scorer.hs.handRanking);


        assertEquals(CardRank.ACE.value(), scorer.hs.tieBreakers.get(0));
        assertEquals(CardRank.JACK.value(), scorer.hs.tieBreakers.get(1));
        assertEquals(CardRank.NINE.value(), scorer.hs.tieBreakers.get(2));
        assertEquals(CardRank.FIVE.value(), scorer.hs.tieBreakers.get(3));
        assertEquals(CardRank.TWO.value(), scorer.hs.tieBreakers.get(4));
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
        scorer.analyze();

        assertFalse(scorer.flush);
        assertEquals(0, scorer.straightHigh);

        scorer.calculateScore();
        assertNotEquals(HandRanking.STRAIGHT, scorer.hs.handRanking);
        assertNotEquals(HandRanking.STRAIGHT_FLUSH, scorer.hs.handRanking);
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
        scorer.analyze();

        assertFalse(scorer.flush);
        assertEquals(CardRank.FIVE.value(), scorer.straightHigh);

        scorer.calculateScore();
        assertEquals(HandRanking.STRAIGHT, scorer.hs.handRanking);
    }

    @Test
    void StraightWithUnsortedInputTest(){
        // Tests that analyze() sorts/normalizes ranks correctly
        Card[] cards = new Card[5];
        cards[0] = new Card(Suite.SPADES, CardRank.TEN);
        cards[1] = new Card(Suite.HEARTS, CardRank.ACE);
        cards[2] = new Card(Suite.DIAMONDS, CardRank.QUEEN);
        cards[3] = new Card(Suite.CLUBS, CardRank.KING);
        cards[4] = new Card(Suite.SPADES, CardRank.JACK);

        FiveCardHandScorer scorer = new FiveCardHandScorer(cards);
        scorer.analyze();

        assertFalse(scorer.flush);
        assertEquals(CardRank.ACE.value(), scorer.straightHigh);

        scorer.calculateScore();
        assertEquals(HandRanking.STRAIGHT, scorer.hs.handRanking);
    }

    @Test
    void HighCardTest(){
        // A K 9 6 2 (not flush, not straight)
        Card[] cards = new Card[5];
        cards[0] = new Card(Suite.SPADES, CardRank.ACE);
        cards[1] = new Card(Suite.HEARTS, CardRank.KING);
        cards[2] = new Card(Suite.DIAMONDS, CardRank.NINE);
        cards[3] = new Card(Suite.CLUBS, CardRank.SIX);
        cards[4] = new Card(Suite.SPADES, CardRank.TWO);

        FiveCardHandScorer scorer = new FiveCardHandScorer(cards);
        scorer.analyze();
        scorer.calculateScore();

        assertEquals(HandRanking.HIGH_CARD, scorer.hs.handRanking);
        assertEquals(CardRank.ACE.value(), scorer.hs.tieBreakers.get(0));
        assertEquals(CardRank.KING.value(), scorer.hs.tieBreakers.get(1));
        assertEquals(CardRank.NINE.value(), scorer.hs.tieBreakers.get(2));
        assertEquals(CardRank.SIX.value(), scorer.hs.tieBreakers.get(3));
        assertEquals(CardRank.TWO.value(), scorer.hs.tieBreakers.get(4));
    }

    @Test
    void OnePairTest(){
        // Pair of Jacks with A K 9 kickers
        Card[] cards = new Card[5];
        cards[0] = new Card(Suite.SPADES, CardRank.JACK);
        cards[1] = new Card(Suite.HEARTS, CardRank.JACK);
        cards[2] = new Card(Suite.DIAMONDS, CardRank.ACE);
        cards[3] = new Card(Suite.CLUBS, CardRank.KING);
        cards[4] = new Card(Suite.SPADES, CardRank.NINE);

        FiveCardHandScorer scorer = new FiveCardHandScorer(cards);
        scorer.analyze();
        scorer.calculateScore();

        assertEquals(HandRanking.PAIR, scorer.hs.handRanking);
        assertEquals(CardRank.JACK.value(), scorer.hs.tieBreakers.get(0));
        assertEquals(CardRank.ACE.value(), scorer.hs.tieBreakers.get(1));
        assertEquals(CardRank.KING.value(), scorer.hs.tieBreakers.get(2));
        assertEquals(CardRank.NINE.value(), scorer.hs.tieBreakers.get(3));
    }

    @Test
    void TwoPairTest(){
        // Kings and Tens, kicker Ace
        Card[] cards = new Card[5];
        cards[0] = new Card(Suite.SPADES, CardRank.KING);
        cards[1] = new Card(Suite.HEARTS, CardRank.KING);
        cards[2] = new Card(Suite.DIAMONDS, CardRank.TEN);
        cards[3] = new Card(Suite.CLUBS, CardRank.TEN);
        cards[4] = new Card(Suite.SPADES, CardRank.ACE);

        FiveCardHandScorer scorer = new FiveCardHandScorer(cards);
        scorer.analyze();
        scorer.calculateScore();

        assertEquals(HandRanking.TWO_PAIR, scorer.hs.handRanking);
        // expected ordering: higher pair, lower pair, kicker
        assertEquals(CardRank.KING.value(), scorer.hs.tieBreakers.get(0));
        assertEquals(CardRank.TEN.value(), scorer.hs.tieBreakers.get(1));
        assertEquals(CardRank.ACE.value(), scorer.hs.tieBreakers.get(2));
    }

    @Test
    void ThreeOfAKindTest(){
        // Trip 7s with A and K kickers
        Card[] cards = new Card[5];
        cards[0] = new Card(Suite.SPADES, CardRank.SEVEN);
        cards[1] = new Card(Suite.HEARTS, CardRank.SEVEN);
        cards[2] = new Card(Suite.DIAMONDS, CardRank.SEVEN);
        cards[3] = new Card(Suite.CLUBS, CardRank.ACE);
        cards[4] = new Card(Suite.SPADES, CardRank.KING);

        FiveCardHandScorer scorer = new FiveCardHandScorer(cards);
        scorer.analyze();
        scorer.calculateScore();

        assertEquals(HandRanking.TRIPS, scorer.hs.handRanking);
        assertEquals(CardRank.SEVEN.value(), scorer.hs.tieBreakers.get(0));
        assertEquals(CardRank.ACE.value(), scorer.hs.tieBreakers.get(1));
        assertEquals(CardRank.KING.value(), scorer.hs.tieBreakers.get(2));
    }

    @Test
    void FullHouseTest(){
        // 9s full of 4s
        Card[] cards = new Card[5];
        cards[0] = new Card(Suite.SPADES, CardRank.NINE);
        cards[1] = new Card(Suite.HEARTS, CardRank.NINE);
        cards[2] = new Card(Suite.DIAMONDS, CardRank.NINE);
        cards[3] = new Card(Suite.CLUBS, CardRank.FOUR);
        cards[4] = new Card(Suite.SPADES, CardRank.FOUR);

        FiveCardHandScorer scorer = new FiveCardHandScorer(cards);
        scorer.analyze();
        scorer.calculateScore();

        assertEquals(HandRanking.FULL_HOUSE, scorer.hs.handRanking);
        // expected ordering: trips rank, then pair rank
        assertEquals(CardRank.NINE.value(), scorer.hs.tieBreakers.get(0));
        assertEquals(CardRank.FOUR.value(), scorer.hs.tieBreakers.get(1));
    }

    @Test
    void FourOfAKindTest(){
        // Quad Queens with Ace kicker
        Card[] cards = new Card[5];
        cards[0] = new Card(Suite.SPADES, CardRank.QUEEN);
        cards[1] = new Card(Suite.HEARTS, CardRank.QUEEN);
        cards[2] = new Card(Suite.DIAMONDS, CardRank.QUEEN);
        cards[3] = new Card(Suite.CLUBS, CardRank.QUEEN);
        cards[4] = new Card(Suite.SPADES, CardRank.ACE);

        FiveCardHandScorer scorer = new FiveCardHandScorer(cards);
        scorer.analyze();
        scorer.calculateScore();

        assertEquals(HandRanking.QUADS, scorer.hs.handRanking);
        // expected ordering: quad rank, kicker
        assertEquals(CardRank.QUEEN.value(), scorer.hs.tieBreakers.get(0));
        assertEquals(CardRank.ACE.value(), scorer.hs.tieBreakers.get(1));
    }

    @Test
    void RoyalFlushIsStraightFlushAceHighTest(){
        // Some codebases have ROYAL_FLUSH; if yours doesn't, this should still be STRAIGHT_FLUSH with A-high.
        Card[] cards = new Card[5];
        cards[0] = new Card(Suite.HEARTS, CardRank.ACE);
        cards[1] = new Card(Suite.HEARTS, CardRank.KING);
        cards[2] = new Card(Suite.HEARTS, CardRank.QUEEN);
        cards[3] = new Card(Suite.HEARTS, CardRank.JACK);
        cards[4] = new Card(Suite.HEARTS, CardRank.TEN);

        FiveCardHandScorer scorer = new FiveCardHandScorer(cards);
        scorer.analyze();
        scorer.calculateScore();

        assertEquals(HandRanking.STRAIGHT_FLUSH, scorer.hs.handRanking);
        assertEquals(CardRank.ACE.value(), scorer.hs.tieBreakers.get(0));
    }
}

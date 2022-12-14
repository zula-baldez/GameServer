package com.server.game.process;

import com.server.game.process.util.Card;
import com.server.game.process.data.Suit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
//class that creates shaffled deck for game
public class CardManager {
    private static final int CARD_NUMBER = 14;

    public static List<Card> getShuffledDeck() {
        List<Card> deck = new ArrayList<>();

        for(int i = 6; i <= CARD_NUMBER; i++) {
            Card card = new Card(i*10+1, i, Suit.KRESTI);
            deck.add(card);
        }
        for(int i = 6; i <= CARD_NUMBER; i++) {
            Card card = new Card(i*10+2, i, Suit.BYBI);
            deck.add(card);
        }
        for(int i = 6; i <=CARD_NUMBER; i++) {
            Card card = new Card(i*10+3, i, Suit.CHERVI);
            deck.add(card);
        }
        for(int i = 6; i <=CARD_NUMBER; i++) {
            Card card = new Card(i*10+4, i, Suit.PICK);
            deck.add(card);
        }
        Collections.shuffle(deck);
        return deck;
    }
}

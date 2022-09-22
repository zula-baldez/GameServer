package com.server.game_process;

import com.server.game_process_util.Card;
import com.server.game_process_util.Suit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
//class that creates shaffled deck for game
public class CardManager {
    private static final int CARD_NUMBER = 14;

    public static List<Card> getDeck() {
        List<Card> deck = new ArrayList<>();

        for(int i = 2; i <=CARD_NUMBER; i++) {
            Card card = new Card("", i, Suit.KRESTI);
            deck.add(card);
        }
        for(int i = 2; i <= CARD_NUMBER; i++) {
            Card card = new Card("", i, Suit.BYBI);
            deck.add(card);
        }
        for(int i = 2; i <=CARD_NUMBER; i++) {
            Card card = new Card("", i, Suit.CHERVI);
            deck.add(card);
        }
        for(int i = 2; i <=CARD_NUMBER; i++) {
            Card card = new Card("", i, Suit.PICK);
            deck.add(card);
        }
        Collections.shuffle(deck);
        return deck;
    }
}

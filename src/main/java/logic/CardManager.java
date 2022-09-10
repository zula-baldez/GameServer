package logic;

import com.server.Card;
import com.server.Suit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CardManager {


    public static List<Card> getDeck() {
        List<Card> deck = new ArrayList<>();

        for(int i = 2; i <=14; i++) {
            Card card = new Card("", i, Suit.KRESTI);
            deck.add(card);
        }
        for(int i = 2; i <=14; i++) {
            Card card = new Card("", i, Suit.BYBI);
            deck.add(card);
        }
        for(int i = 2; i <=14; i++) {
            Card card = new Card("", i, Suit.CHERVI);
            deck.add(card);
        }
        for(int i = 2; i <=14; i++) {
            Card card = new Card("", i, Suit.PICK);
            deck.add(card);
        }
        Collections.shuffle(deck);
        return deck;
    }
}

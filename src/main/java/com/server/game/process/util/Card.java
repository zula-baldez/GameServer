package com.server.game.process.util;

import com.server.game.process.data.Suit;

public class Card {
    public int id;
    public int attack;
    public boolean isPenek = false;
    public Suit suit;
    public Card(int id, int attack, Suit suit) {
        this.id = id;
        this.attack = attack;

        this.suit = suit;

    }

}



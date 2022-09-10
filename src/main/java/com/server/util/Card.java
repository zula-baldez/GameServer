package com.server.util;

public class Card {
    public String Name;
    public int Attack;
    public boolean isPenek = false;
    public Suit suit;
    public Card(String name, int attack, Suit suit) {
        Name = name;
        Attack = attack;

        this.suit = suit;

    }

}



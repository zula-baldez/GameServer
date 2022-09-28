package com.server.game.process;

import com.server.game.process.util.Card;
import com.server.game.process.data.Suit;
import com.server.game.process.util.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//class that contains info about game (cards in the field etc.)
public class Game {
    private List<Card> field = new ArrayList<>();
    private Suit kozir = Suit.PICK;
    private List<Card> deck = CardManager.getShuffledDeck(); //default card is not penek;
    private Map<Integer, List<Card>> playersHands = new HashMap<>(); //to serialization
    private List<Player> players = new ArrayList<>();
    public Game(List<Player> players) {
        this.players = players;
    }
    public List<Card> getField() {
        return field;
    }

    public Suit getKozir() {
        return kozir;
    }

    public List<Card> getDeck() {
        return deck;
    }

    public void setField(List<Card> field) {
        this.field = field;
    }

    public void setKozir(Suit kozir) {
        this.kozir = kozir;
    }

    public void setDeck(List<Card> deck) {
        this.deck = deck;
    }

    public Map<Integer, List<Card>> getPlayersHands() {
        return playersHands;
    }

    public List<Player> getPlayers() {
        return players;
    }
    public Map<Integer, List<Card>> giveCards() {
        for (Player player: players) {
            List<Card> cards = new ArrayList<>();
            deck.get(0).isPenek = true;
            deck.get(1).isPenek = true;
            cards.add( deck.get(0));
            cards.add( deck.get(1));
            cards.add( deck.get(2));
            deck.remove(0);
            deck.remove(0);

            deck.remove(0);
            playersHands.put(player.getId(), cards);
        }
        return playersHands;
    }
}

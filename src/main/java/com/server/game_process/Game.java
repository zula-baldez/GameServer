package com.server.game_process;

import com.server.game_process_util.Card;
import com.server.game_process_util.Player;
import com.server.game_process_util.Suit;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private final List<Card> field = new ArrayList<>();

    private final Suit kozir = Suit.PICK;
    private final List<Player> players = new ArrayList<>();
    private final List<Card> deck = CardManager.getDeck();
    private final int playersAmount = 0;

    public List<Player> getPlayers() {
        return players;
    }
}




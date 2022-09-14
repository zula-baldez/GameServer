package com.server.rooms;

import com.server.game_process.CardManager;
import com.server.game_process.GameManager;
import com.server.game_process_util.Card;
import com.server.game_process_util.Player;
import com.server.game_process_util.Suit;
import com.server.util.ResponseCode;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private GameManager gameManager;
    private String name;
    private int maxPlayers;
    private List<Player> players = new ArrayList<>();
    int id;
    private final List<Card> field = new ArrayList<>();

    private final Suit kozir = Suit.PICK;
    private final List<Card> deck = CardManager.getDeck();


    public int getPlayersNumber() {
        return players.size();
    }
    public Room(int amountOfPlayers, int maxPlayers, String name, int id) {
        this.name = name;
        this.maxPlayers = maxPlayers;
        this.name = name;
        this.id = id;
    }

    public int getAmountOfPlayers() {
        return players.size();
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public ResponseCode addPlayer(Player player) {
        if (getMaxPlayers() == getAmountOfPlayers()) {
            return ResponseCode.ERROR;
        } else {
            players.add(player);
            if(getMaxPlayers() == getAmountOfPlayers()) {
                startGame();
            }
            return ResponseCode.OK;
        }


    }
    public void startGame() {
        gameManager = new GameManager(this);
        gameManager.startGame();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

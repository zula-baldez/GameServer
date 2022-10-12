package com.server.rooms;

import com.server.exception.NoSuchPlayerException;
import com.server.game.process.GameManager;
import com.server.game.process.util.Player;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private final String name;
    private final int maxPlayers;
    private List<Player> players = new ArrayList<>();
    private int id;
    private final GameManager gameManager = new GameManager(this);

    public Room(int maxPlayers, String name, int id) {
        this.maxPlayers = maxPlayers;
        this.name = name;
        this.id = id;
    }
    public GameManager getGameManager() {
        return gameManager;
    }
    public int getAmountOfPlayers() {
        return players.size();
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void addPlayer(Player player) {
        if (getMaxPlayers() != getAmountOfPlayers()) {
            players.add(player);
            if(getMaxPlayers() == getAmountOfPlayers()) {
                gameManager.startGame();
            }
        }


    }


    public Player getPlayerById(int id) throws NoSuchPlayerException {
        for(Player player : players) {
            if(player.getId() == id) return player;
        }
        throw new NoSuchPlayerException();
    }
    public String getName() {
        return name;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public int getId() {
        return id;
    }

}

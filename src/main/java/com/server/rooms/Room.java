package com.server.rooms;

import com.server.util.Player;
import com.server.util.ResponseCode;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private String name;
    private int maxPlayers;
    private List<Player> players = new ArrayList<>();
    int id;
    public Room(int amountOfPlayers, int maxPlayers, String name, int id) {
        this.name = name;
        this.maxPlayers =maxPlayers;
        this.name  = name;
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
        if(getMaxPlayers() == getAmountOfPlayers()) {
            return ResponseCode.ERROR;
        } else {
            players.add(player);
            return ResponseCode.OK;
        }
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

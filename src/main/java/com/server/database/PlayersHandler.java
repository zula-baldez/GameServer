package com.server.database;

import com.server.exception.NoSuchPlayerException;
import com.server.game_process_util.Player;

import java.util.ArrayList;
import java.util.List;
//todo нормальную бд
//handles all the info about players(without their rooms)
//when peplix connect, instance of class Player creates and saves in this class

public class PlayersHandler {
    private final List<Player> players = new ArrayList<>();
    public Player getPlayerById(int id) throws NoSuchPlayerException {
        for(Player player : players) {
            if(player.getId() == id) return player;
        }
        throw new NoSuchPlayerException();
    }
    public void addPlayer(Player player) {
        players.add(player);
    }
}

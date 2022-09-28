package com.server.database;

import com.server.exception.NoSuchPlayerException;
import com.server.game.process.util.Player;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//todo нормальную бд
//handles all the info about players(without their rooms)
//when peplix connect, instance of class Player creates and saves in this class
@Component
public class PlayersHandler {
    private final Set<Player> players = new HashSet<>();
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

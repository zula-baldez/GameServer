package com.server.game.process;

import com.server.database.DBController;
import com.server.game.process.util.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerHandler {

    public static void addFine(Player player) {
        player.addFine();

    }
}

package com.server.game.process;

import com.server.Validators.MoveValidator;
import com.server.controllers.GameProcessNotifierImpl;
import com.server.game.process.util.Card;
import com.server.game.process.util.Player;
import com.server.game.process.data.Stage;
import com.server.game.process.Timer.TimerImpl;
import com.server.rooms.Room;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 play handler for one room
 handle info like turn faze, not info about cards on the field etc., there is Game class for that
 so there is a GameManager and Game classes for every Room class
 */

public class GameManager {
    private final Room room;
    private final MoveValidator moveValidator = new MoveValidator();
    private int playerTurn = 1;
    private final Game game = new Game();
    private final Stage stage = Stage.Razdacha;
    private final TimerImpl timerImpl = new TimerImpl(this);
    private final Map<Player, List<Card>> playerHands = new HashMap();



    public GameManager(Room room) {
        this.room = room;
    }
    //enter point
    //after that Room class do nothing
    public void startGame() {
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        GameProcessNotifierImpl.sendGameStartMessage(room, game.getDeck());
        timerImpl.startCountTurns();
    }

    public void changeTurn() {
        if (playerTurn + 1 <= room.getPlayersNumber()) {
            playerTurn++;
        } else {
            playerTurn = 1;
        }
    }




    public Room getGame() {
        return room;
    }
}


package com.server.game.process;

import com.server.Validators.MoveValidator;
import com.server.Validators.ValidationResponse;
import com.server.controllers.GameProcessNotifierImpl;
import com.server.game.process.data.FieldType;
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

public class   GameManager {
    private final Room room;
    private final MoveValidator moveValidator = new MoveValidator();
    private int playerTurn = 1;
    private Game game;
    private final Stage stage = Stage.Razdacha;
    private final TimerImpl timerImpl = new TimerImpl(this);
    private final Map<Player, List<Card>> playerHands = new HashMap();


    public GameManager(Room room) {
        this.room = room;
        game = new Game(room.getPlayers());
    }

    //enter point
    //after that Room class do nothing
    public void startGame() {
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        HashMap<Integer, List<Card>> playersToHands = (HashMap<Integer, List<Card>>) game.giveCards();
        GameProcessNotifierImpl.sendGameStartMessage(room, playersToHands, game.getField(), game.getDeck());
        timerImpl.startCountTurns();
    }

    public ValidationResponse validateCardMove(Room room, Card card, Player mainPlayer, Player playerTakenCard) {
        if (playerTakenCard.getId() == -1) {

            return moveValidator.ValidateDistribution(room, mainPlayer, game.getField().get(game.getField().size() - 1), card, FieldType.FIELD);
        }
        if (mainPlayer == playerTakenCard) {

            return moveValidator.ValidateDistribution(room, mainPlayer, playerTakenCard.getPlayerHand().get(playerTakenCard.getPlayerHand().size() - 1), card, FieldType.SELF_HAND);
        }


            return moveValidator.ValidateDistribution(room, mainPlayer, playerTakenCard.getPlayerHand().get(playerTakenCard.getPlayerHand().size() - 1), card, FieldType.ENEMY_HAND);


    }

    public void changeTurn() {
        if (playerTurn + 1 <= room.getPlayersNumber()) {
            playerTurn++;
        } else {
            playerTurn = 1;
        }
    }

    public Room getRoom() {
        return room;
    }

    public MoveValidator getMoveValidator() {
        return moveValidator;
    }

    public int getPlayerTurn() {
        return playerTurn;
    }

    public Game getGame() {
        return game;
    }

    public Stage getStage() {
        return stage;
    }

    public TimerImpl getTimerImpl() {
        return timerImpl;
    }

    public Map<Player, List<Card>> getPlayerHands() {
        return playerHands;
    }
}


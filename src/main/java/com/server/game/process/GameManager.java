package com.server.game.process;

import com.server.Validators.MoveValidator;
import com.server.Validators.ValidationResponse;
import com.server.controllers.GameProcessNotifierImpl;
import com.server.game.process.data.Action;
import com.server.game.process.data.ActionTypes;
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

public class GameManager {
    private final Room room;
    private final MoveValidator moveValidator = new MoveValidator();
    private Game game;
    private final Stage stage = Stage.Razdacha;
    private final TimerImpl timerImpl = new TimerImpl(this);
    private final Map<Player, List<Card>> playerHands = new HashMap();
    private int playerIdTurn = 0;
    private int iterPlayerTurnId = 0;
    private boolean hasPlayerDroppedHisCards = false;
    private boolean hasTakenCardFromDeck = false;
    public GameManager(Room room) {
        this.room = room;
        game = new Game(room.getPlayers());
    }

    public boolean isHasPlayerDroppedHisCards() {
        return hasPlayerDroppedHisCards;
    }

    public void setHasPlayerDroppedHisCards(boolean hasPlayerDroppedHisCards) {
        this.hasPlayerDroppedHisCards = hasPlayerDroppedHisCards;
    }

    //enter point
    //after that Room class do nothing
    public void startGame() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        timerImpl.changeTurn();
        HashMap<Integer, List<Card>> playersToHands = (HashMap<Integer, List<Card>>) game.giveCards();
        GameProcessNotifierImpl.sendGameStartMessage(room, playersToHands, game.getField(), game.getDeck());
    }

    public ValidationResponse validateCardMove(Room room, Player mainPlayer, Player playerFrom, Player playerTo) {
        ValidationResponse val = null;
        if (room.getGameManager().getPlayerTurn() != mainPlayer.getId()) {
            val = new ValidationResponse(false, false);
        } else if (playerFrom.getId() == playerTo.getId()) {
            val = new ValidationResponse(true, false);
        } else if (playerTo.getId() == -1) {
            Card card = playerFrom.getPlayerHand().get(playerFrom.getPlayerHand().size() - 1);
            if (game.getField().size() != 0) {
                val = moveValidator.ValidateDistribution(room, mainPlayer, game.getField().get(game.getField().size() - 1), card, FieldType.FIELD, FieldType.SELF_HAND);
                if (val.isTurnRight()) {
                    playerFrom.getPlayerHand().remove(playerFrom.getPlayerHand().size() - 1);
                    game.getField().add(card);
                }
            } else {
                val = new ValidationResponse(false, true);
            }
        } else if (playerFrom.getId() == -1) {
            Card postcard = getGame().getField().get(room.getGameManager().getGame().getField().size() - 1);
            if (mainPlayer.getId() == playerTo.getId())
                val = moveValidator.ValidateDistribution(room, mainPlayer, playerTo.getPlayerHand().get(playerTo.getPlayerHand().size() - 1), postcard, FieldType.SELF_HAND, FieldType.FIELD);
            else
                val = moveValidator.ValidateDistribution(room, mainPlayer, playerTo.getPlayerHand().get(playerTo.getPlayerHand().size() - 1), postcard, FieldType.ENEMY_HAND, FieldType.FIELD);
            if (val.isTurnRight()) {
                game.getField().remove(postcard);
                playerTo.getPlayerHand().add(postcard);
                hasTakenCardFromDeck = false;
            }

        } else {
            Card card = playerFrom.getPlayerHand().get(playerFrom.getPlayerHand().size() - 1);
            if (mainPlayer.getId() == playerFrom.getId())
                val = moveValidator.ValidateDistribution(room, mainPlayer,  playerTo.getPlayerHand().get(playerTo.getPlayerHand().size() - 1), card, FieldType.ENEMY_HAND, FieldType.SELF_HAND);
            else val = new ValidationResponse(false, false);

            if (val.isTurnRight()) {
                playerFrom.getPlayerHand().remove(card);
                playerTo.getPlayerHand().add(card);
            }
        }


        System.out.println("Will change turn? " + val.isNeedToChangeTurn());
        if (val.isNeedToChangeTurn()) timerImpl.changeTurn();
        return val;

    }

    public Action getCard(int playerId) {
        if(hasTakenCardFromDeck) {
            return new Action(ActionTypes.BAD_MOVE, game.getPlayersHands(), game.getField(), game.getDeck(), playerIdTurn);

        }
        if (game.getDeck().size() != 0 && playerId == playerIdTurn) {
            Card card = game.getDeck().get(game.getDeck().size() - 1);
            game.getDeck().remove(card);
            game.getField().add(card);
            hasTakenCardFromDeck = true;
            return new Action(ActionTypes.OK_MOVE, game.getPlayersHands(), game.getField(), game.getDeck(), playerIdTurn);
        } else return null;
    }


    public Room getRoom() {
        return room;
    }

    public MoveValidator getMoveValidator() {
        return moveValidator;
    }

    public int getPlayerTurn() {
        return playerIdTurn;
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

    public void setPlayerTurn(int playerTurn) {
        this.playerIdTurn = playerTurn;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void changeTurnId() {
        playerIdTurn = game.getPlayers().get(iterPlayerTurnId).getId();
        iterPlayerTurnId++;
        if (iterPlayerTurnId >= game.getPlayers().size()) iterPlayerTurnId = 0;
    }
}


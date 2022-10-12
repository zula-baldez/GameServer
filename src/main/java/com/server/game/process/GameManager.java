package com.server.game.process;

import com.server.Validators.MoveValidator;
import com.server.Validators.ValidationResponse;
import com.server.controllers.GameProcessNotifierImpl;
import com.server.exception.StartGameException;
import com.server.game.process.data.*;
import com.server.game.process.util.Card;
import com.server.game.process.util.Player;
import com.server.rooms.Room;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
 play handler for one room
 handle info like turn faze, not info about cards on the field etc., there is Game class for that
 so there is a GameManager and Game classes for every Room class
 */

public class GameManager {
    private final Room room;
    private final MoveValidator moveValidator = new MoveValidator();
    private Game game;
    private Stage stage = Stage.RAZDACHA;
    private int playerIdTurn = 0;
    private boolean hasTakenCardFromDeck = false;
    private List<Player> activePlayers = null;

    public GameManager(Room room) {
        this.room = room;
        game = new Game(room.getPlayers());
    }

    //enter point
    //after that Room class do nothing
    public void startGame() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        HashMap<Integer, List<Card>> playersToHands = (HashMap<Integer, List<Card>>) game.giveCards();
        changeTurnId();
        GameProcessNotifierImpl.sendGameStartMessage(room, playersToHands, game.getField(), game.getDeck());
    }

    public ValidationResponse validateCardMoveRazd(Room room, Player mainPlayer, Player playerFrom, Player playerTo) throws StartGameException {
        ValidationResponse val = null;

        val = checkIsPlayerTurn(room, mainPlayer);
        if (val != null) return val;


        val = checkIfMovingCardToHimself(playerFrom, playerTo);
        if (val != null) return val;

        if (playerTo.getId() == -1) {
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
            if (game.getDeck().size() == 1) {
                if (playerTo.getId() == mainPlayer.getId()) {

                    val = new ValidationResponse(true, true);
                } else {
                    val = new ValidationResponse(false, false);
                }
            } else {
                Card postcard = getGame().getField().get(room.getGameManager().getGame().getField().size() - 1);
                if (mainPlayer.getId() == playerTo.getId()) {
                    val = moveValidator.ValidateDistribution(room, mainPlayer, playerTo.getPlayerHand().get(playerTo.getPlayerHand().size() - 1), postcard, FieldType.SELF_HAND, FieldType.FIELD);

                } else {
                    val = moveValidator.ValidateDistribution(room, mainPlayer, playerTo.getPlayerHand().get(playerTo.getPlayerHand().size() - 1), postcard, FieldType.ENEMY_HAND, FieldType.FIELD);

                }
            }
            if (val.isTurnRight()) {
                Card postcard = getGame().getField().get(room.getGameManager().getGame().getField().size() - 1);

                if (postcard.suit != Suit.PICK) {
                    game.setKozir(postcard.suit);
                }
                game.getField().remove(postcard);
                playerTo.getPlayerHand().add(postcard);
                hasTakenCardFromDeck = false;
            }

        } else {
            Card card = playerFrom.getPlayerHand().get(playerFrom.getPlayerHand().size() - 1);
            if (mainPlayer.getId() == playerFrom.getId()) {
                val = moveValidator.ValidateDistribution(room, mainPlayer, playerTo.getPlayerHand().get(playerTo.getPlayerHand().size() - 1), card, FieldType.ENEMY_HAND, FieldType.SELF_HAND);

            } else {
                val = new ValidationResponse(false, false);

            }
            if (val.isTurnRight()) {
                playerFrom.getPlayerHand().remove(card);
                playerTo.getPlayerHand().add(card);
            }
        }


        if (game.getDeck().size() == 0) {
            stage = Stage.BAD_MOVES;
            room.getGameManager().changeTurnId();
            GameProcessNotifierImpl.startBadMovesStage(room, game.getPlayersHands(), game.getField(), game.getDeck());
            throw new StartGameException();
        } else
            return val;
    }





    public ValidationResponse validateCardMovePlay(Room room, Player mainPlayer, Player playerFrom, Player playerTo, Card card) {
        ValidationResponse val = null;

        val = checkIsPlayerTurn(room, mainPlayer);
        if (val != null) return val;


        val = checkIfMovingCardToHimself(playerFrom, playerTo);
        if (val != null) return val;

        val = checkMovingCardToField(room, mainPlayer, playerFrom, playerTo, card);
        if (val != null) return val;
        /*
        if (playerTo.getId() == -1) {
            if (game.getField().size() != 0) {
                val = moveValidator.ValidatePlayMove(room, mainPlayer, game.getField().get(game.getField().size() - 1), card);
                if (val.isTurnRight()) {
                    playerFrom.getPlayerHand().remove(card);
                    game.getField().add(card);
                    if (playerFrom.getPlayerHand().size() == playerFrom.getAmountOfPenki()) {
                        for (Card c : playerFrom.getPlayerHand()) {
                            c.isPenek = false;
                        }
                    }
                    if (game.getField().size() == activePlayers.size()) {
                        game.getField().clear();
                        val = new ValidationResponse(true, false);

                        System.out.println("Cleared field");
                        GameProcessNotifierImpl.showCardsBeforeDrop(room, game.getPlayersHands(), game.getField(), game.getDeck());
                        if (checkIfPlayerWon()) {
                            changeTurnId();
                        }
                    }
                }
            } else {
                val = new ValidationResponse(true, true);
                playerFrom.getPlayerHand().remove(card);
                game.getField().add(card);
                if (playerFrom.getPlayerHand().size() == playerFrom.getAmountOfPenki()) {
                    for (Card c : playerFrom.getPlayerHand()) {
                        c.isPenek = false;
                    }
                }
                if (game.getField().size() == activePlayers.size()) {
                    game.getField().clear();
                    System.out.println("Cleared field");
                    GameProcessNotifierImpl.showCardsBeforeDrop(room, game.getPlayersHands(), game.getField(), game.getDeck());

                    if (checkIfPlayerWon()) {
                        changeTurnId();
                    }
                }
            }
        } else*/
        val = new ValidationResponse(false, false);
        mainPlayer.addFine();


        return val;

    }

    private ValidationResponse checkIsPlayerTurn(Room room, Player mainPlayer) {
        if (room.getGameManager().getPlayerTurn() != mainPlayer.getId()) {
            return new ValidationResponse(false, false);
        }
        return null;
    }

    private ValidationResponse checkIfMovingCardToHimself(Player playerFrom, Player playerTo) {
        if (playerFrom.getId() == playerTo.getId()) {
            return new ValidationResponse(true, false);
        }
        return null;
    }

    private ValidationResponse checkMovingCardToField(Room room, Player mainPlayer, Player playerFrom, Player playerTo, Card card) {
        ValidationResponse val = null;
        if (playerTo.getId() == -1) {
            if (game.getField().size() != 0) {
                val = moveValidator.ValidatePlayMove(room, mainPlayer, game.getField().get(game.getField().size() - 1), card);
                if (val.isTurnRight()) {
                    playerFrom.getPlayerHand().remove(card);
                    game.getField().add(card);
                    checkForPenki(playerFrom);
                    if (game.getField().size() == activePlayers.size()) {
                        game.getField().clear();
                        val = new ValidationResponse(true, false);

                        System.out.println("Cleared field");
/*
                        GameProcessNotifierImpl.showCardsBeforeDrop(room, game.getPlayersHands(), game.getField(), game.getDeck());
*/
                        if (checkIfPlayerWon()) {
                            changeTurnId();
                        }
                    }
                }
            } else {
                val = new ValidationResponse(true, true);
                playerFrom.getPlayerHand().remove(card);
                game.getField().add(card);
                checkForPenki(playerFrom);
            }
        }
        return val;
    }

    private void checkForPenki(Player playerFrom) {
        if (playerFrom.getPlayerHand().size() == playerFrom.getAmountOfPenki()) {
            for (Card c : playerFrom.getPlayerHand()) {
                c.isPenek = false;
            }
        }
    }

    public Action getCard(int playerId) {
        if (hasTakenCardFromDeck) {
            return new Action(ActionTypes.BAD_MOVE, game.getPlayersHands(), game.getField(), game.getDeck(), playerIdTurn, game.getKozir());
        } else {
            hasTakenCardFromDeck = true;
        }
        if (game.getDeck().size() != 0 && playerId == playerIdTurn) {
            Card card = game.getDeck().get(game.getDeck().size() - 1);
            game.getDeck().remove(card);
            game.getField().add(card);
            hasTakenCardFromDeck = true;
            return new Action(ActionTypes.OK_MOVE, game.getPlayersHands(), game.getField(), game.getDeck(), playerIdTurn, game.getKozir());
        } else return null;
    }


    public Action getCardFromField(int playerId, Player player) {

        if (game.getField().size() != 0 && playerId == playerIdTurn) {
            Card card = game.getField().get(0);
            game.getField().remove(card);
            player.getPlayerHand().add(card);
            changeTurnId();
            return new Action(ActionTypes.OK_MOVE, game.getPlayersHands(), game.getField(), game.getDeck(), playerIdTurn, game.getKozir());
        } else return null;
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


    public void setGame(Game game) {
        this.game = game;
    }

    public void changeTurnId() {
        hasTakenCardFromDeck = false;
        if (activePlayers == null) {
            activePlayers = new ArrayList<>();
            activePlayers.addAll(game.getPlayers());
        }
        Player player = null;
        while (activePlayers.size() != 0) {
            if (activePlayers.get(0).getPlayerHand().size() == 0) {
                activePlayers.remove(0);

            } else {
                player = activePlayers.get(0);
                activePlayers.remove(0);
                activePlayers.add(player);
                break;
            }
        }
        if (player == null) return;

        playerIdTurn = player.getId();


    }

    private boolean checkIfPlayerWon() {
        try {
            return activePlayers.get(0).getPlayerHand().size() == 0;
        } catch (Exception e) {
            return false;
        }
    }
}
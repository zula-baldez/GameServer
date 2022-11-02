package com.server.game.process;

import com.server.validators.MoveValidator;
import com.server.validators.ValidationResponse;
import com.server.controllers.GameProcessNotifierImpl;
import com.server.exception.StartGameException;
import com.server.game.process.data.*;
import com.server.game.process.util.Card;
import com.server.game.process.util.Player;
import com.server.rooms.Room;
import com.server.util.dto.Action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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

        HashMap<Integer, List<Card>> playersToHands = (HashMap<Integer, List<Card>>) game.giveCards();
        changeTurnId();
        GameProcessNotifierImpl.sendGameStartMessage(room, playersToHands, game.getField(), game.getDeck());
    }

    public ValidationResponse validateCardMoveRazd(Player mainPlayer, Player playerFrom, Player playerTo) throws StartGameException {

        System.out.println("MainPlayer:" + mainPlayer.getId());
        System.out.println("playerFrom: " + playerFrom.getId());
        System.out.println("playerTo: "+playerTo.getId());

        ValidationResponse val = null;

        val = checkIsPlayerTurn(room, mainPlayer);
        if (val != null) return checkForEndOfRazd(val);
        System.out.println("mainPlayer is ok");

        val = checkIfMovingCardToHimself(playerFrom, playerTo);
        if (val != null) return checkForEndOfRazd(val);
        System.out.println("not to himself");


        val = checkIfPlayerToIsFieldRazd(mainPlayer, playerFrom, playerTo);
        if (val != null) return checkForEndOfRazd(val);
        System.out.println("PlayerTo is not field");


        val = checkIfPlayerFromIsFieldRazd(mainPlayer, playerFrom, playerTo);
        if (val != null) return checkForEndOfRazd(val);
        System.out.println("playerFrom is not field");


        val = checkIfMovingFromToPlayerRazd(mainPlayer, playerFrom, playerTo);
        return checkForEndOfRazd(val);


    }

    private ValidationResponse checkForEndOfRazd(ValidationResponse val) throws StartGameException {
        if (game.getDeck().size() == 0 && val.isTurnRight()) {
            if(checkForFines()) {
                stage = Stage.BAD_MOVES;
                GameProcessNotifierImpl.startBadMovesStage(room, game.getPlayersHands(), game.getField(), game.getDeck(), getGame().getPlayers().get(currentPlayerGettingFine).getId());
            } else {
                stage = Stage.PLAY;

                GameProcessNotifierImpl.startPlayStage(room, game.getPlayersHands(), game.getField(), game.getDeck());

            }

            throw new StartGameException();
        } else
            return val;
    }

    private ValidationResponse checkIfPlayerToIsFieldRazd(Player mainPlayer, Player playerFrom, Player playerTo) {
        ValidationResponse val = null;
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
        }
        return val;
    }

    private ValidationResponse checkIfPlayerFromIsFieldRazd(Player mainPlayer, Player playerFrom, Player playerTo) {
        ValidationResponse val = null;
        if (playerFrom.getId() == -1) {
            if (game.getDeck().size() == 1) {
                if (playerTo.getId() == mainPlayer.getId()) {

                    val = new ValidationResponse(true, true);
                } else {
                    val = new ValidationResponse(false, false);
                }
            } else {
                Card postcard = getGame().getField().get(getGame().getField().size() - 1);
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

        }
        return val;
    }

    private ValidationResponse checkIfMovingFromToPlayerRazd(Player mainPlayer, Player playerFrom, Player playerTo) {
        ValidationResponse val = null;
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
        PlayerHandler.addFine(mainPlayer);

        val = new ValidationResponse(false, false);


        return val;

    }

    private ValidationResponse checkIsPlayerTurn(Room room, Player mainPlayer) {
        if (room.getGameManager().getPlayerTurn() != mainPlayer.getId()) {
            PlayerHandler.addFine(mainPlayer);
            System.out.println("It is not right turn!");
            return new ValidationResponse(false, false);
        }
        return null;
    }

    private ValidationResponse checkIfMovingCardToHimself(Player playerFrom, Player playerTo) {
        if (playerFrom.getId() == playerTo.getId()) {
            System.out.println("From and to are equal");
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
                        GameProcessNotifierImpl.showCardsBeforeDrop(room, game.getPlayersHands(), game.getField(), game.getDeck());
                        game.getField().clear();
                        val = new ValidationResponse(true, false);

                        System.out.println("Cleared field");
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


    private final HashSet<Integer> gaveFine = new HashSet<>();
    private int currentPlayerGettingFine = 0;
    private HashSet<Player> playersInGame = new HashSet<>();
    public void giveFine(Room room, Player playerFrom, Card card) {
        if(checkForEndOfFines()) return;
        System.out.println("Fines: " + playerFrom.getFines());
        System.out.println("contains: " + gaveFine.contains(playerFrom.getId()));
        System.out.println("getting fine : " + game.getPlayers().get(currentPlayerGettingFine).getId());
        System.out.println("giving fine " + playerFrom.getId());
        System.out.println(card.isPenek);
        System.out.println("is active : " +  playersInGame.contains(playerFrom));
        playersInGame.remove(game.getPlayers().get(currentPlayerGettingFine));
        if(!gaveFine.contains(playerFrom.getId()) && (game.getPlayers().get(currentPlayerGettingFine).getId() != playerFrom.getId()) && !card.isPenek && playersInGame.contains(playerFrom)) {
            gaveFine.add(playerFrom.getId());
            game.getPlayers().get(currentPlayerGettingFine).getPlayerHand().add(card);
            playerFrom.getPlayerHand().remove(card);

        } else {
            return;
        }
        if(gaveFine.size() == playersInGame.size()) {
            System.out.println("DEC!");
            game.getPlayers().get(currentPlayerGettingFine).decFine();
            gaveFine.clear();
            playersInGame.clear();
            renewPlayersInGame();
        }
        if(!checkForEndOfFines())
        GameProcessNotifierImpl.sendCurrentTable(room, game.getPlayersHands(), game.getField(), game.getDeck(), getGame().getPlayers().get(currentPlayerGettingFine).getId());
    }

    private boolean checkForEndOfFines() {
        if(stage != Stage.BAD_MOVES) return true;


        while(currentPlayerGettingFine < game.getPlayers().size()) {
            if(game.getPlayers().get(currentPlayerGettingFine).getFines() != 0) {
                renewPlayersInGame();
                playersInGame.remove(game.getPlayers().get(currentPlayerGettingFine));
                if(playersInGame.size()==0) {
                    getGame().getPlayers().get(currentPlayerGettingFine).setFines(0);

                } else {
                    break;
                }
            }
            currentPlayerGettingFine++;
        }
        System.out.println(currentPlayerGettingFine);
        renewPlayersInGame();
        if(currentPlayerGettingFine >= game.getPlayers().size()) {
            changeTurnId();
            stage = Stage.PLAY;
            GameProcessNotifierImpl.startPlayStage(room, game.getPlayersHands(), game.getField(), game.getDeck());
            for(Player player : activePlayers) {
                checkForPenki(player);
            }
            return true;
        }
        return false;
    }


    private boolean checkForFines() {
        boolean flg = false;
        for(int i = 0; i < game.getPlayers().size(); i++) {
            if(getGame().getPlayers().get(i).getPlayerHand().size() > getGame().getPlayers().get(i).getAmountOfPenki()) {
                playersInGame.add(getGame().getPlayers().get(i));
            }
            if(getGame().getPlayers().get(i).getFines() != 0 && !flg) {
                currentPlayerGettingFine = i;
                flg = true;

            }
        }

        return flg;
    }

    private void renewPlayersInGame() {
        for(int i = 0; i < game.getPlayers().size(); i++) {
            if(getGame().getPlayers().get(i).getPlayerHand().size() > getGame().getPlayers().get(i).getAmountOfPenki()) {
                playersInGame.add(getGame().getPlayers().get(i));
            }
        }

    }


}
package com.server.controllers;

import com.server.game.process.data.Action;
import com.server.game.process.data.ActionTypes;
import com.server.game.process.util.Card;
import com.server.game.process.util.Player;
import com.server.rooms.Room;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;
import java.util.Map;

public class GameProcessNotifierImpl extends GameProcessController {
    //send actions to all players in room
    //to notify all room
    private static void sendAction(Action action, Room room) {
            for (DeferredResult<Action> output : deferredResults.get(room)) {
                output.setResult(action);

            }

    }
/*    private static void sendAction(Action action, Player player) {
        playersAndTheirDeferredResults.get(player).setResult(action);

    }*/



    public static void sendGameStartMessage(Room room, Map<Integer, List<Card>> playerHands, List<Card> field, List<Card> anotherCards) {

        Action action = new Action(ActionTypes.START_GAME, playerHands, field, anotherCards, room.getGameManager().getPlayerTurn(),
                room.getGameManager().getGame().getKozir());
        sendAction(action, room);

    }
    public static void startPlayStage(Room room, Map<Integer, List<Card>> playerHands, List<Card> field, List<Card> anotherCards) {
        Action action = new Action(ActionTypes.START_PLAY, playerHands, field, anotherCards, room.getGameManager().getPlayerTurn(),
                room.getGameManager().getGame().getKozir());
        sendAction(action, room);
    }
    public static void startBadMovesStage(Room room, Map<Integer, List<Card>> playerHands, List<Card> field, List<Card> anotherCards) {
        Action action = new Action(ActionTypes.BAD_MOVE, playerHands, field, anotherCards, room.getGameManager().getPlayerTurn(),
                room.getGameManager().getGame().getKozir());
        sendAction(action, room);
    }












    public static void showCardsBeforeDrop(Room room, Map<Integer, List<Card>> playerHands, List<Card> field, List<Card> anotherCards) {
        System.out.println("Sending lagg!");
        Action action = new Action(ActionTypes.OK_MOVE, playerHands, field, anotherCards, -1,
                room.getGameManager().getGame().getKozir());
        sendAction(action, room);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Ended!!");

    }
}

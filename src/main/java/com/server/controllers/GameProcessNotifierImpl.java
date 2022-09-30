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

        Action action = new Action(ActionTypes.START_GAME, playerHands, field, anotherCards, room.getGameManager().getPlayerTurn());
        sendAction(action, room);

    }
}

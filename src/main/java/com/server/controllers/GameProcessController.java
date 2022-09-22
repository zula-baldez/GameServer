package com.server.controllers;

import com.server.exception.NoSuchPlayerException;
import com.server.game_process_util.Action;
import com.server.game_process_util.ActionTypes;
import com.server.game_process_util.Card;
import com.server.game_process_util.Player;
import com.server.rooms.Room;
import com.server.rooms.RoomHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
* controller that handles all things connected to game process and notify
* players via long polling http requests
*
*/

@RestController
@Component
@ComponentScan
public class GameProcessController {




    @Autowired
    private static RoomHandler roomHandler;
    private static final HashMap<Room, List<DeferredResult<Action>>> deferredResults = new HashMap<>();

    @ResponseBody
    @RequestMapping(value = "/game/check_game_status", method = RequestMethod.GET)
    public static DeferredResult<Action> gameCheck(@RequestParam int id, @RequestParam int roomId) {

        DeferredResult<Action> output  = new DeferredResult<>();
        Room room = roomHandler.getRoomById(roomId);
        try {
            Player player = room.getPlayerById(id);
            if(!deferredResults.containsKey(room)) {
                System.out.println(roomId);
                List<DeferredResult<Action>> list = new ArrayList<>();
                list.add(output);
                deferredResults.put(room, list);
            } else {
                List<DeferredResult<Action>> list = deferredResults.get(room);
                list.add(output);
                deferredResults.put(room, list);
            }
        } catch (NoSuchPlayerException e) {
            output.setResult(null);
        }
        return output;

    }

    //send actions to all players in room
    private static void sendAction(Action action, Room room) {
        for(DeferredResult<Action> output : deferredResults.get(room)) {
            output.setResult(action);
        }
    }




    public static void sendGameStartMessage(Room room, List<Card> deck) {

        Action action = new Action(ActionTypes.START_GAME, deck, null);
        sendAction(action, room);
    }

}

package com.server.controllers;

import com.server.exception.NoSuchPlayerException;
import com.server.game.process.data.Action;
import com.server.game.process.util.Player;
import com.server.rooms.Room;
import com.server.rooms.RoomHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
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
@Configuration
@ComponentScan
public class GameProcessController {


    @Autowired
    private RoomHandler roomHandler;
    protected static final HashMap<Room, List<DeferredResult<Action>>> deferredResults = new HashMap<>();



    @ResponseBody
    @RequestMapping(value = "/game/check_game_status", method = RequestMethod.GET)
    public DeferredResult<Action> gameCheck(@RequestParam int id, @RequestParam int roomId) {

        DeferredResult<Action> output = new DeferredResult<>();
        Room room = roomHandler.getRoomById(roomId);
        try {
            Player player = room.getPlayerById(id);
            if (!deferredResults.containsKey(room)) {
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



}

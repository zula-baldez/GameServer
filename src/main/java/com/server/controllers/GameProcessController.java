package com.server.controllers;

import com.server.Validators.ValidationResponse;
import com.server.database.PlayersHandler;
import com.server.exception.NoSuchPlayerException;
import com.server.game.process.data.Action;
import com.server.game.process.data.MovingCardData;
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


    //todo нормальная бд
    @Autowired
    private RoomHandler roomHandler;
    @Autowired
    private PlayersHandler playersHandler;
/*    @Autowired
    DBController dbController*/;
    protected static final HashMap<Room, List<DeferredResult<Action>>> deferredResults = new HashMap<>();
    protected static final HashMap<Player, DeferredResult<Action>> playersAndTheirDeferredResults = new HashMap<>();

    @ResponseBody
    @RequestMapping(value = "/game/move_card", method = RequestMethod.POST)
    public int validateMovingCard(@RequestParam MovingCardData movingCardData) {

        try {
            Player mainPlayer = playersHandler.getPlayerById(movingCardData.playerId());
            Player droppedPlayer;
            if (movingCardData.dropPlayerId() == -1) {
                droppedPlayer = new Player(-1);
            } else {
                droppedPlayer = playersHandler.getPlayerById(movingCardData.dropPlayerId());
            }
            Room room = roomHandler.getRoomByPlayer(mainPlayer);
             ValidationResponse valRes = room.getGameManager().validateCardMove(room, movingCardData.card(), mainPlayer, droppedPlayer);
            return  1;
        } catch (NoSuchPlayerException e) {
            e.printStackTrace();
            return -1;
        }
    }

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

            playersAndTheirDeferredResults.put(player, output);
        } catch (NoSuchPlayerException e) {
            output.setResult(null);
        }

        return output;

    }


}

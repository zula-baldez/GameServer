package com.server.controllers;

import com.server.Validators.ValidationResponse;
import com.server.database.PlayersHandler;
import com.server.exception.NoSuchPlayerException;
import com.server.game.process.data.Action;
import com.server.game.process.data.ActionTypes;
import com.server.game.process.data.GetCardData;
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
    public void validateMovingCard(@RequestBody MovingCardData movingCardData) {

        try {
            Player mainPlayer;
            if (movingCardData.mainPlayerId() == -1) {
                mainPlayer = new Player(-1);
            } else {
                mainPlayer = playersHandler.getPlayerById(movingCardData.mainPlayerId());
            }

            Player playerTo;
            if (movingCardData.playerTo() == -1) {
                playerTo = new Player(-1);
            } else {
                playerTo = playersHandler.getPlayerById(movingCardData.playerTo());
            }



            Player playerFrom;
            if (movingCardData.playerFrom() == -1) {
                playerFrom = new Player(-1);
            } else {
                playerFrom = playersHandler.getPlayerById(movingCardData.playerFrom());
            }



            Room room = roomHandler.getRoomById(movingCardData.roomId());
            ValidationResponse valRes = room.getGameManager().validateCardMove(room, mainPlayer, playerFrom, playerTo);
            System.out.println(valRes);

            Action act =  createAction(valRes, room);


            for (var def: deferredResults.get(room)
            ) {
                def.setResult(act);

            }
        } catch (NoSuchPlayerException e) {
            e.printStackTrace();
        }

    }

    @ResponseBody
    @RequestMapping(value = "/game/get_card", method = RequestMethod.POST)
    public void get_card_req(@RequestBody GetCardData getCardData) {

        Room room = roomHandler.getRoomById(getCardData.roomId());
        Action act =  room.getGameManager().getCard(getCardData.playerId());
        for (var def: deferredResults.get(room)
             ) {
            def.setResult(act);
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

    private Action createAction(ValidationResponse validationResponse, Room room) {
        Action action = null;
        if (validationResponse.isNeedToChangeTurn() && validationResponse.isTurnRight()) {
            action = new Action(ActionTypes.OK_MOVE, room.getGameManager().getGame().getPlayersHands(),
                    room.getGameManager().getGame().getField(),
                    room.getGameManager().getGame().getDeck(),
                    room.getGameManager().getPlayerTurn()
            );

        }
        if (!validationResponse.isNeedToChangeTurn() && validationResponse.isTurnRight()) {
            action = new Action(ActionTypes.OK_MOVE, room.getGameManager().getGame().getPlayersHands(),
                    room.getGameManager().getGame().getField(),
                    room.getGameManager().getGame().getDeck(),
                    room.getGameManager().getPlayerTurn()
            );
        }
        if (!validationResponse.isTurnRight()) {
            action = new Action(ActionTypes.BAD_MOVE, room.getGameManager().getGame().getPlayersHands(),
                    room.getGameManager().getGame().getField(),
                    room.getGameManager().getGame().getDeck(),
                    room.getGameManager().getPlayerTurn()
            );
        }
        return action;
    }
}

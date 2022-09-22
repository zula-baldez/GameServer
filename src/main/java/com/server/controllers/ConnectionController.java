package com.server.controllers;

import com.server.database.PlayersHandler;
import com.server.exception.NoSuchPlayerException;
import com.server.game_process_util.Player;
import com.server.game_process_util.RegisterAnswer;
import com.server.rooms.RoomHandler;
import com.server.rooms.RoomListConverter;
import com.server.rooms.RoomInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


/*
    connection handler
    handle room connecting and other similar staff
    all
*/
@RestController
@Configuration
@ComponentScan
public class ConnectionController {
    private final Set<Integer> registeredId = new HashSet<>();
    private final PlayersHandler playersHandler = new PlayersHandler();

    private static int ID = 0;
    @Autowired
    private RoomHandler roomHandler;

    @ResponseBody
    @RequestMapping(value = "/connection/room_list", method = RequestMethod.GET)
    public List<RoomInfo> roomList() {
        RoomListConverter roomListConverter = new RoomListConverter(roomHandler.getRooms());
        return roomListConverter.getRoomsInfo();
    }


    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/connection/register", method = RequestMethod.GET)
    public RegisterAnswer register() {
        ID++;
        Player player = new Player(ID);
        playersHandler.addPlayer(player);
        return new RegisterAnswer(ID);
    }

    @ResponseBody
    @RequestMapping(value = "/connection/enter_room", method = RequestMethod.GET)
    public ResponseEntity enterRoom(@RequestParam int id, @RequestParam int roomId) {
        if (!registeredId.contains(id) && roomHandler.getRoomById(roomId) != null) {
            registeredId.add(id);
            try {
                Player player = playersHandler.getPlayerById(id);
                player.setHost(true);
                roomHandler.getRoomById(roomId).addPlayer(playersHandler.getPlayerById(id));
                return ResponseEntity.status(200).body("Ok");
            } catch (NoSuchPlayerException e) {
                return ResponseEntity.status(400).body("Check your id!");
            }

        } else return ResponseEntity.status(400).body("Wrong query");
    }


    @ResponseBody
    @RequestMapping(value = "/connection/create_room", method = RequestMethod.GET)
    public ResponseEntity createRoom(@RequestParam int maxPlayerNumber, @RequestParam String name, @RequestParam int playerId) {
        try {
            roomHandler.createRoom(maxPlayerNumber, name);

            return ResponseEntity.status(200).body("Ok");
        } catch (Throwable e) {
            return ResponseEntity.status(400).body(e.getMessage()); //todo система ошибок при обработке запросов
        }
}


}

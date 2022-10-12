package com.server.controllers;

import com.server.database.PlayersHandler;
import com.server.exception.NoSuchPlayerException;
import com.server.game.process.data.RegisterAnswer;
import com.server.game.process.util.Player;
import com.server.rooms.Room;
import com.server.rooms.RoomHandler;
import com.server.rooms.RoomInfo;
import com.server.rooms.RoomListConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/*
    connection handler
    handle room connecting and other similar staff
    all
*/
@RestController
@Configuration
@ComponentScan
public class ConnectionControllerImpl {
    private final Set<Integer> registeredId = new HashSet<>();
    @Autowired
    private PlayersHandler playersHandler;
    private final ExecutorService threader = Executors.newCachedThreadPool();
    private static int ID = 0;
    @Autowired
    private RoomHandler roomHandler;
/*    @Autowired
    DBController dbController;*/
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
                threader.submit(() -> {
                    Room room = roomHandler.getRoomById(roomId);
                    room.addPlayer(player);

                });
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

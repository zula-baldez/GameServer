package com.server.connection_controller;

import com.server.Player;
import com.server.rooms.RoomHandler;
import com.server.util.RoomListAnswer;
import com.server.util.RegisterAnswer;
import com.server.asnwers.RoomInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@Configuration
@ComponentScan
public class ConnectionController {
    private Set<Integer> registeredId = new HashSet<>();
    private static int ID = 0;
    @Autowired
    private RoomHandler roomHandler;
    @ResponseBody
    @RequestMapping(value = "/connection/roomlist", method = RequestMethod.GET)
    public List<RoomInfo> roomList() {
        roomHandler.createRoom(10, "Turnir");

        RoomListAnswer roomListAnswer = new RoomListAnswer(roomHandler.getRooms());
        return roomListAnswer.getRoomsInfo();
    }


    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/connection/register", method = RequestMethod.GET)
    public RegisterAnswer register() {
        ID++;
        return new RegisterAnswer(ID);
    }

    @ResponseBody
    @RequestMapping(value = "/connection/enterroom", method = RequestMethod.GET)
    public ResponseEntity enterRoom(@RequestParam int id, @RequestParam int roomId) {
        if(!registeredId.contains(id) && roomHandler.getRoomById(roomId) != null) {
            registeredId.add(id);
            roomHandler.getRoomById(roomId).addPlayer(new Player(id));

            return ResponseEntity.status(200).body("Ok");

        } else return ResponseEntity.status(400).body("Wrong query");
    }



}

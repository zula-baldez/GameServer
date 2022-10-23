package com.server.controllers;

import com.server.database.DBController;
import com.server.database.PlayerAccount;
import com.server.exception.RegisteredLoginException;
import com.server.game.process.util.Player;
import com.server.rooms.Room;
import com.server.rooms.RoomHandler;
import com.server.rooms.RoomInfo;
import com.server.rooms.RoomListConverter;
import com.server.util.dto.RegisterAnswer;
import com.server.util.dto.RegisterNewAccountData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @Autowired
    private RoomHandler roomHandler;
    @Autowired
    DBController dbController;

    @ResponseBody
    @RequestMapping(value = "/connection/room_list", method = RequestMethod.GET)
    public List<RoomInfo> roomList() {
        RoomListConverter roomListConverter = new RoomListConverter(roomHandler.getRooms());
        return roomListConverter.getRoomsInfo();
    }


    @ResponseBody
    @RequestMapping(value = "/connection/enter_room", method = RequestMethod.GET)
    public ResponseEntity enterRoom(@RequestParam int id, @RequestParam int roomId) {
        if (roomHandler.getRoomById(roomId) != null) {
            String name = dbController.getLoginById(id);
            int fines = dbController.getByLogin(name).getFineNumber();
            System.out.println(name);
            Player player = new Player(id, name, dbController);
            player.setFines(Math.max(fines, 0));
            Room room = roomHandler.getRoomById(roomId);
            room.addPlayer(player);

            return ResponseEntity.status(200).body("Ok");
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

    @ResponseBody
    @RequestMapping(value = "/connection/register_new_account", method = RequestMethod.POST)
    public ResponseEntity<RegisterAnswer> register(@RequestBody RegisterNewAccountData registerNewAccountData) {
        PlayerAccount playerAccount = new PlayerAccount(registerNewAccountData.login(), registerNewAccountData.password(), 0);
        try {
            dbController.addPlayerAccount(playerAccount);
            return ResponseEntity.status(200).body(new RegisterAnswer((int) playerAccount.getId().longValue()));
        } catch (RegisteredLoginException e) {
            return ResponseEntity.status(400).body(new RegisterAnswer(-1));
        }
    }

    @ResponseBody
    @RequestMapping(value = "/connection/login", method = RequestMethod.POST)
    public ResponseEntity<RegisterAnswer> login(@RequestBody RegisterNewAccountData registerNewAccountData) {
        PlayerAccount playerAccount = dbController.getByLogin(registerNewAccountData.login());
        if (playerAccount == null) {
            return ResponseEntity.status(400).body(new RegisterAnswer(-1));
        } else {
            if (registerNewAccountData.password().equals(playerAccount.getPassword())) {
                return ResponseEntity.status(200).body(new RegisterAnswer((int) (playerAccount.getId()).longValue()));
            } else {

                return ResponseEntity.status(401).body(new RegisterAnswer(-1));

            }
        }

    }

}

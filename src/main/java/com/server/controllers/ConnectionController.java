package com.server.controllers;

import com.server.game.process.data.RegisterAnswer;
import com.server.rooms.RoomInfo;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ConnectionController {


    public List<RoomInfo> roomList();

    public RegisterAnswer register();


    public ResponseEntity enterRoom(int id, int roomId);


    public ResponseEntity createRoom(int maxPlayerNumber, String name, int playerId);


}



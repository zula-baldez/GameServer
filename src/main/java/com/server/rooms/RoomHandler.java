package com.server.rooms;

import com.server.game_process_util.Player;
import com.server.util.ResponseCode;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class RoomHandler {
    private int id = 0;
    private List<Room> rooms = new ArrayList<>();
    public void createRoom(int maxPlayers, String name) {
        Room room = new Room(0, maxPlayers, name, id);
        id++;
        rooms.add(room);
    }
    public ResponseCode addPlayer(Room room, Player player) {
        return room.addPlayer(player);
    }

    public Room getRoomById(int id) {
        for (Room room:
             rooms) {
           if(room.id == id) return room;
        }
        return null;
    }
    public List<Room> getRooms() {
        return rooms;
    }

}

package com.server.rooms;

import com.server.exception.EnterRoomException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class RoomHandler {
    private int id = 1;
    private final Set<Room> rooms = new HashSet<>();
    public void createRoom(int maxPlayers, String name) throws EnterRoomException {
        if(maxPlayers <= 0 || name == null) throw new EnterRoomException();
        Room room = new Room(maxPlayers, name, id);
        id++;
        rooms.add(room);
    }

    public Room getRoomById(int id) {
        for (Room room:
             rooms) {
           if(room.getId() == id) return room;
        }
        return null;
    }

    public Set<Room> getRooms() {
        return rooms;
    }

}

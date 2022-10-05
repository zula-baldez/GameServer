package com.server.rooms;

import com.server.exception.EnterRoomException;
import com.server.exception.NoSuchPlayerException;
import com.server.game.process.util.Player;
import com.server.util.ResponseCode;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class RoomHandler {
    private int id = 0;
    private Set<Room> rooms = new HashSet<>();
    public void createRoom(int maxPlayers, String name) throws EnterRoomException {
        if(maxPlayers <= 0 || name == null) throw new EnterRoomException();
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
           if(room.getId() == id) return room;
        }
        return null;
    }
    public Room getRoomByPlayer() {
        throw new RuntimeException();
    }

    public Set<Room> getRooms() {
        return rooms;
    }

    public Room getRoomByPlayer(Player mainPlayer) throws NoSuchPlayerException {
        for(Room room : rooms) {
            if(room.getPlayers().contains(mainPlayer)) {
                return room;
            }
        }
        throw new NoSuchPlayerException();
    }
}

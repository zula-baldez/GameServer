package com.server.rooms;

import com.server.rooms.Room;
import com.server.rooms.RoomInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RoomListConverter {
    private List<RoomInfo> roomsInfo = new ArrayList<>();

    public RoomListConverter(Set<Room> rooms) {
        for (Room room : rooms) {
            RoomInfo roomInfo=  new RoomInfo(room.getMaxPlayers(), room.getAmountOfPlayers(), room.getName(), room.getId());
            roomsInfo.add(roomInfo);
        }
    }

    public List<RoomInfo> getRoomsInfo() {
        return roomsInfo;
    }


    public void setRoomsInfo(List<RoomInfo> roomsInfo) {
        this.roomsInfo = roomsInfo;
    }

}

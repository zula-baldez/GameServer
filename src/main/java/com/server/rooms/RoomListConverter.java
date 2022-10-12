package com.server.rooms;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RoomListConverter {
    private final List<RoomInfo> roomsInfo = new ArrayList<>();

    public RoomListConverter(Set<Room> rooms) {
        for (Room room : rooms) {
            RoomInfo roomInfo=  new RoomInfo(room.getMaxPlayers(), room.getAmountOfPlayers(), room.getName(), room.getId());
            roomsInfo.add(roomInfo);
        }
    }

    public List<RoomInfo> getRoomsInfo() {
        return roomsInfo;
    }


}

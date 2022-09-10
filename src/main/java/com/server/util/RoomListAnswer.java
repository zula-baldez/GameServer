package com.server.util;

import com.server.rooms.Room;
import com.server.asnwers.RoomInfo;

import java.util.ArrayList;
import java.util.List;

public class RoomListAnswer {
    private List<RoomInfo> roomsInfo = new ArrayList<>();

    public RoomListAnswer(List<Room> rooms) {
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

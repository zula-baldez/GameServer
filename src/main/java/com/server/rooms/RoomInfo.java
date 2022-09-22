package com.server.rooms;

import java.util.List;

public record RoomInfo(int maxPeople,
        int currentPeople,
        String name,
        int id) {}

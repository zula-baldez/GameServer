package com.server.rooms;

public record RoomInfo(int maxPeople,
        int currentPeople,
        String name,
        int id) {}

package com.server.rooms;

import java.util.List;

public class RoomInfo {
    private int maxPeople;
    private int currentPeople;
    private String name;
    private int id;

    public RoomInfo(int maxPeople, int currentPeople, String name, int id) {
        this.maxPeople = maxPeople;
        this.currentPeople = currentPeople;
        this.name = name;
        this.id = id;
    }

    public int getMaxPeople() {
        return maxPeople;
    }

    public int getCurrentPeople() {
        return currentPeople;
    }

    public void setMaxPeople(int maxPeople) {
        this.maxPeople = maxPeople;
    }

    public void setCurrentPeople(int currentPeople) {
        this.currentPeople = currentPeople;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }




}

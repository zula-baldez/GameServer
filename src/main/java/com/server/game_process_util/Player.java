package com.server.game_process_util;


import java.util.List;

public class Player {
    private int id;
    private String Name;
    private List<Card> PlayerHand;
    private int fines = 0;
    private boolean isEnemy = true;
    private int AmountOfPenki = 2;
    private boolean HasCardFromDeck = false;
    private boolean isHost = false;
    public Player(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public List<Card> getPlayerHand() {
        return PlayerHand;
    }

    public void setPlayerHand(List<Card> playerHand) {
        PlayerHand = playerHand;
    }

    public int getFines() {
        return fines;
    }

    public void setFines(int fines) {
        this.fines = fines;
    }


    public void addFine() {
        fines++;
    }

    public boolean isEnemy() {
        return isEnemy;
    }

    public void setEnemy(boolean enemy) {
        isEnemy = enemy;
    }

    public int getAmountOfPenki() {
        return AmountOfPenki;
    }

    public void setAmountOfPenki(int amountOfPenki) {
        AmountOfPenki = amountOfPenki;
    }

    public boolean isHasCardFromDeck() {
        return HasCardFromDeck;
    }

    public void setHasCardFromDeck(boolean hasCardFromDeck) {
        HasCardFromDeck = hasCardFromDeck;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isHost() {
        return isHost;
    }

    public void setHost(boolean host) {
        isHost = host;
    }
}

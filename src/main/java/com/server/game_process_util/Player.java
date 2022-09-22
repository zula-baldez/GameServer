package com.server.game_process_util;


import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;

public class Player {
    private int id;
    private String name;
    private List<Card> playerHand;
    private int fines = 0;
    private boolean isEnemy = true;
    private int amountOfPenki = 2;
    private boolean hasCardFromDeck = false;
    private boolean isHost = false;
    private DeferredResult<Action> deferredResult;
    public Player(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public DeferredResult<Action> getDeferredResult() {
        return deferredResult;
    }

    public void setDeferredResult(DeferredResult<Action> deferredResult) {
        this.deferredResult = deferredResult;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Card> getPlayerHand() {
        return playerHand;
    }

    public void setPlayerHand(List<Card> playerHand) {
        this.playerHand = playerHand;
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
        return amountOfPenki;
    }

    public void setAmountOfPenki(int amountOfPenki) {
        this.amountOfPenki = amountOfPenki;
    }

    public boolean isHasCardFromDeck() {
        return hasCardFromDeck;
    }

    public void setHasCardFromDeck(boolean hasCardFromDeck) {
        this.hasCardFromDeck = hasCardFromDeck;
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

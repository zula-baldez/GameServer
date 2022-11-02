package com.server.game.process.util;


import com.server.database.DBController;
import com.server.exception.NoSuchCardException;

import java.util.List;
public class Player {
    private int id;
    private String name;
    private List<Card> playerHand;
    private int fines = 0;
    private boolean isEnemy = true;
    private int amountOfPenki = 2;
    private boolean hasCardFromDeck = false;
    private DBController dbController;
    public Player(int id, String name, DBController dbController) {
        this.id = id;
        this.name = name;
        this.dbController = dbController;
    }

    public Card getCardById(int id) throws NoSuchCardException {
        for (Card card:
             playerHand) {
            if(card.id == id) return card;
        }
        throw new NoSuchCardException();
    }
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
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
        dbController.addFine(name);
        System.out.println("Huevaya for: " + name );
        fines++;
    }
    public void decFine() {
        dbController.decFine(name);
        fines--;
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


}

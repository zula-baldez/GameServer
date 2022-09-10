package com.server.logic;

import org.springframework.stereotype.Component;

@Component
public class GameManager {
    private Game game;
    private MoveValidator moveValidator;
    private int turn   = 0;
    public GameManager() {
        game = new Game();
        moveValidator = new MoveValidator();

    }
    public void changeTurn() {
        //mnogopotok

    }
}

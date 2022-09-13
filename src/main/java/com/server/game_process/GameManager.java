package com.server.game_process;

import org.springframework.stereotype.Component;

@Component
public class GameManager {
    private Game game;
    private MoveValidator moveValidator;
    private final int turn = 0;

    public GameManager() {
        game = new Game();
        moveValidator = new MoveValidator();

    }

    public void changeTurn() {
        //mnogopotok

    }

    public Game getGame() {
        return game;
    }
}

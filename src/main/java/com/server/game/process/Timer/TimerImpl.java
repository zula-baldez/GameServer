package com.server.game.process.Timer;

import com.server.game.process.GameManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TimerImpl {
    private ExecutorService timerHandler = Executors.newSingleThreadExecutor();
    private GameManager gameManager;
    private static final int turnTime = 30;
    private int timer = turnTime;
    public TimerImpl(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    //todo ограничение хода, мб и не to do


    public void changeTurn() {
        gameManager.changeTurnId();

    }
}

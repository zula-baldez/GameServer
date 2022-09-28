package com.server.game.process.Timer;

import com.server.game.process.GameManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TimerImpl implements Timer {
    private final ExecutorService timerHandler = Executors.newSingleThreadExecutor();
    private GameManager gameManager;
    private static final int turnTime = 30;
    private int timer = turnTime;
    public TimerImpl(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void startCountTurns() {
        timerHandler.submit(new Runnable() {
            @Override
            public void run() {
                while (timer > 0) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        return;
                    }
                    timer--;
                }
                changeTurn();
            }
        });
    }

    public void changeTurn() {
        gameManager.changeTurn();
        timerHandler.shutdownNow();
        timer = turnTime;
        startCountTurns();
    }
}

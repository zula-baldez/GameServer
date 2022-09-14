package com.server.game_process;

import com.server.rooms.Room;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/*
 play handler for one room
 */


public class GameManager {
    private final Room room;
    private final MoveValidator moveValidator;
    private int playerTurn = 1;
    private static final int turnTime = 30;
    private int timer = turnTime;
    private final ExecutorService timerHandler = Executors.newSingleThreadExecutor();
    public GameManager(Room room) {
        this.room = room;
        moveValidator = new MoveValidator();

    }
    public void startGame() {
        startCountTurns();
    }


    public void startCountTurns() {
        timerHandler.submit(new Runnable() {
            @Override
            public void run() {
                while(timer > 0) {
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
        if(playerTurn + 1 <= room.getPlayersNumber()) {
            playerTurn++;
        } else {
            playerTurn = 1;
        }
        timerHandler.shutdownNow();
        timer = turnTime;
        startCountTurns();
    }

    public Room getGame() {
        return room;
    }
}


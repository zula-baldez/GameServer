package com.server.game_process;

import com.server.controllers.GameProcessController;
import com.server.game_process_util.Stage;
import com.server.rooms.Room;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 play handler for one room
 handle info like turn faze, not info about cards on the field etc., there is Game class for that
 so there is a GameManager and Game classes for every Room class
 */
public class GameManager {




    private final Room room;
    private final MoveValidator moveValidator = new MoveValidator();
    private int playerTurn = 1;
    private static final int turnTime = 30;
    private int timer = turnTime;
    private final ExecutorService timerHandler = Executors.newSingleThreadExecutor();
    private Game game = new Game();
    private Stage stage = Stage.Razdacha;

    public GameManager(Room room) {
        this.room = room;
    }

    //enter point
    //after that Room class do nothing
    public void startGame() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        GameProcessController.sendGameStartMessage(room, game.getDeck());
        startCountTurns();
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
        if (playerTurn + 1 <= room.getPlayersNumber()) {
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


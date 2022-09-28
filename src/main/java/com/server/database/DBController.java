/*
package com.server.database;

import com.server.controllers.ConnectionController;
import com.server.exception.NoSuchPlayerException;
import com.server.game.process.util.Player;
import com.server.rooms.Room;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class DBController {
    //  Database credentials
    static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/";
    static final String USER = "postgres";
    static final String PASS = "KfvYuJQ8";

    public DBController() throws SQLException {
        Connection connection = null;
        try {
            connection = DriverManager
                    .getConnection(DB_URL, USER, PASS);

        } catch (SQLException e) {
            System.out.println("Connection Failed");
            e.printStackTrace();
            return;
        }
        String s = "CREATE TABLE Players\n" +
                "(\n" +
                "    id INT,\n" +
                "    roomId INT\n)";
        connection.prepareStatement(s).execute();
        connection.close();
    }

    public void putPlayer(Player player, Room room) {
        try (Connection connection = DriverManager
                .getConnection(DB_URL, USER, PASS);
        ) {

            String s = "INSERT INTO Players(id, roomId) VALUES(?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(s);
            preparedStatement.setInt(1, player.getId());
            preparedStatement.setInt(2, room.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("Connection Failed");
            e.printStackTrace();
            return;
        }
    }

    public Integer getRoomIdByPlayer(Player player) throws NoSuchPlayerException {
        try (Connection connection = DriverManager
                .getConnection(DB_URL, USER, PASS);
        ) {

            String s = "SELECT roomId FROM Players WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(s);
            preparedStatement.setInt(1, player.getId());
            return preparedStatement.executeQuery().getInt("roomId");

        } catch (SQLException e) {
            System.out.println("Connection Failed");
            e.printStackTrace();
            throw new NoSuchPlayerException();
        }

    }
}
*/

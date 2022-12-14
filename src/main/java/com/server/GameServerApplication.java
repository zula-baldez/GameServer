package com.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

@SpringBootApplication
@Configuration
public class GameServerApplication {

    public static void main(String[] args) throws SQLException {

        SpringApplication.run(GameServerApplication.class, args);
    }

}

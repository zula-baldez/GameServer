package com.server.controllers;

import com.server.database.DBController;
import com.server.database.PlayerAccount;
import com.server.exception.RegisteredLoginException;
/*
import com.server.util.dto.RegisterNewAccountData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Configuration
@ComponentScan
public class RegistrationAndLoggingController  {
    @Autowired
    DBController dbController;
    @RequestMapping(value = "/connection/register_new_account", method = RequestMethod.POST)
    public ResponseEntity<String> register(@RequestBody RegisterNewAccountData registerNewAccountData) {
        PlayerAccount playerAccount = new PlayerAccount(registerNewAccountData.login(), registerNewAccountData.password(), 0);
        try {
            dbController.addPlayerAccount(playerAccount);
            return ResponseEntity.status(200).body("ok");
        } catch(RegisteredLoginException e) {
            return ResponseEntity.status(500).body("This login already registered");
        }
    }
}
*/

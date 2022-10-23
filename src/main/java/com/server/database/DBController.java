package com.server.database;

import com.server.exception.RegisteredLoginException;
import com.server.game.process.util.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DBController {

    @Autowired
    private Repo repo;
    public void addPlayerAccount(PlayerAccount playerAccount) throws RegisteredLoginException {
        if(repo.findPlayerAccountByLogin(playerAccount.getLogin()).size() == 0) {
            repo.save(playerAccount);
        } else {
            throw new RegisteredLoginException();
        }
    }
    public PlayerAccount getByLogin(String login) {
        List<PlayerAccount> playerAccount =  repo.findPlayerAccountByLogin(login);
        if(playerAccount.size()!=0) {
            return playerAccount.get(0);
        } else return null;
    }
    // NULLABLE!!!!!!!
    public String getLoginById(int id) {
        Optional<PlayerAccount> playerAccount =  repo.findById((long) id);
        return playerAccount.get().getLogin();
    }
    public void addFine(String login) {

        repo.updatePlayer(login);
    }
    public void decFine(String login) {
        repo.decFineAmount(login);
    }

}
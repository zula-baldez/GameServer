package com.server.database;

import javax.persistence.*;

@Entity(name="PLAYERS")
public class PlayerAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column
    private String login;
    @Column
    private String password;
    @Column
    private int fineNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public PlayerAccount() {

    }

    public PlayerAccount(String login, String pass, int fineNumber) {
        this.login = login;
        this.password = pass;
        this.fineNumber = fineNumber;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getFineNumber() {
        return fineNumber;
    }

    public void setFineNumber(int fineNumber) {
        this.fineNumber = fineNumber;
    }
}

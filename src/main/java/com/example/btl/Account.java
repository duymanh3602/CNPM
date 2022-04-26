package com.example.btl;

public class Account {
    private String acc;
    private String pass;
    private String type;

    Account(String acc, String pass, String type) {
        this.acc = acc;
        this.pass = pass;
        this.type = type;
    }

    public Account() {
    }

    public String getAcc() {
        return acc;
    }

    public void setAcc(String acc) {
        this.acc = acc;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

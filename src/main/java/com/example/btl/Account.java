package com.example.btl;

public class Account {
    private String acc;
    private String pass;
    private String type;
    private String name;

    Account(String acc, String pass, String type, String name) {
        this.acc = acc;
        this.pass = pass;
        this.type = type;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

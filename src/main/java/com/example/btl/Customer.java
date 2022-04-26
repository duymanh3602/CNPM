package com.example.btl;

public class Customer {
    private String name;
    private String phone;
    private String age;
    private boolean isVIP;
    private Account account = new Account();

    public Customer(String name, String phone, String ID, String age, String pass) {
        this.name = name;
        this.phone = phone;
        this.age = age;
        this.isVIP = false;
        account.setAcc(ID);
        account.setPass(pass);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getID() {
        return account.getAcc();
    }

    public void setID(String ID) {
        account.setAcc(ID);
    }

    public String  getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public boolean isVIP() {
        return isVIP;
    }

    public void setVIP(boolean VIP) {
        isVIP = VIP;
    }

    public String getPass() {
        return account.getPass();
    }

    public void setPass(String pass) {
        account.setPass(pass);
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}

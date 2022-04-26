package com.example.btl;

public class Staff {
    private String name;
    private String phone;
    private String age;
    private String salary;
    private String workedDays;
    private Account account = new Account();

    public Staff(String name, String phone, String ID, String age, String salary, String workedDays, String pass) {
        this.name = name;
        this.phone = phone;
        this.age = age;
        this.salary = salary;
        this.workedDays = workedDays;
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String  getWorkedDays() {
        return workedDays;
    }

    public void setWorkedDays(String workedDays) {
        this.workedDays = workedDays;
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

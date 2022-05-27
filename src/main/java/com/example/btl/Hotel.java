package com.example.btl;

import java.io.FileOutputStream;
import java.io.IOException;

public class Hotel {
    String roomID;
    String price;
    String customerID;

    public Hotel(String roomID, String price, String customerID) {
        this.roomID = roomID;
        this.price = price;
        this.customerID = customerID;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String dataInfo() {
        String line = roomID + "% %" + price + "% %" + customerID;
        return line;
    }
    
    public void booking(String customer) {
        this.customerID = customer;
        //System.out.println(this.customerID);
    }

    public void checkOut() {
        this.customerID = "null";
    }
}

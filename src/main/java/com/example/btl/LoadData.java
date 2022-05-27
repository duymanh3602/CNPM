package com.example.btl;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoadData {

    List<Account> inputData = new ArrayList<>();
    List<Hotel> inputHotel = new ArrayList<>();
    public String accLog;

    public String getAccLog() {
        return accLog;
    }

    public void setAccLog(String accLog) {
        this.accLog = accLog;
    }




    public void insertFromFile() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src\\main\\resources\\com\\example\\data\\data.txt"));// Infile
            String line = reader.readLine();
            while (line != null) {
                //int indexOfTab = line.indexOf("% %");
                String[] process = line.split("% %");
                Account account = new Account(process[0], process[1], process[2]);
                inputData.add(account);
                line = reader.readLine();
            }
        } catch (IOException e) {
            System.out.println("An error occurred."+ e);
        }
    }

    public void exportToFile(String type) {
        try {
            FileOutputStream fileOut = new FileOutputStream("src\\main\\resources\\com\\example\\data\\data.txt"); //Outfile
            for (int i = 0;i< inputData.size();i++) {
                String line = inputData.get(i).getAcc() + "% %" + inputData.get(i).getPass() + "% %" + type + "\n";
                byte out[] = line.getBytes();
                fileOut.write(out);
            }
            fileOut.close();
        } catch (IOException e) {

            System.out.println("An error occurred."+ e);
        }
    }

    public void readMoneyData() {
        try {
            inputHotel.clear();
            BufferedReader reader = new BufferedReader(new FileReader("src\\main\\resources\\com\\example\\data\\money.txt"));// Infile
            String line = reader.readLine();
            while (line != null) {
                //int indexOfTab = line.indexOf("% %");
                String[] process = line.split("% %");
                Hotel newHotel = new Hotel(process[0],process[1],process[2]);
                inputHotel.add(newHotel);
                line = reader.readLine();
            }
        } catch (IOException e) {
            System.out.println("An error occurred."+ e);
        }
    }

    public void saveMoneyData() {
        try {
            FileOutputStream fileOut = new FileOutputStream("src\\main\\resources\\com\\example\\data\\money.txt"); //Outfile
            for (int i = 0;i< inputHotel.size();i++) {
                String line = inputHotel.get(i).dataInfo() + "\n";
                byte out[] = line.getBytes();
                fileOut.write(out);
            }
            fileOut.close();
            inputHotel.clear();
            readMoneyData();
        } catch (IOException e) {

            System.out.println("An error occurred."+ e);
        }
    }
}

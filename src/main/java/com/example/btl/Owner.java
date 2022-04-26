package com.example.btl;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Owner {

    List<Customer> customerList = new ArrayList<>();

    public void insertFromFile() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src\\main\\resources\\com\\example\\btl\\data\\data.txt"));// Infile
            String line = reader.readLine();
            while (line != null) {
                int indexOfTab = line.indexOf("/");
                if (indexOfTab >1) {

                }
                line = reader.readLine();
            }
        } catch (IOException e) {
            System.out.println("An error occurred."+ e);
        }
    }


    public void dictionaryExportToFile() {
        try {
            FileOutputStream fileOut = new FileOutputStream("src\\main\\resources\\com\\example\\btl\\data\\data.txt"); //Outfile
            for (int i = 0;i< customerList.size();i++) {
                //byte out[] = line.getBytes();
                //fileOut.write(out);
            }
            fileOut.close();
        } catch (IOException e) {

            System.out.println("An error occurred."+ e);
        }
    }
}

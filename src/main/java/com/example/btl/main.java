package com.example.btl;

import com.gembox.spreadsheet.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;

public class main extends Application {

    static {
        SpreadsheetInfo.setLicense("FREE-LIMITED-KEY");
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("manager.fxml"));
        primaryStage.setTitle("Hotel Manager Ultra Application");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 1440, 720));
        primaryStage.show();
    }

    public static void main(String[] args) throws IOException {
        launch(args);
        saveDataCus();
        saveDataStaff();
    }

    private static void saveDataCus() throws IOException {
        File file = new File("src/main/resources/com/example/data/Customer.xlsx");
        ExcelFile workbook = ExcelFile.load(file.getAbsolutePath());
        //ExcelFile workbook = ExcelFile.load("Customer.xlsx");
        //System.out.println(file.getAbsolutePath());
        ExcelWorksheet worksheet = workbook.getWorksheet(0);
        String[][] sourceData = new String[100][8];
        for (int row = 0; row < sourceData.length; row++) {
            for (int column = 0; column < sourceData[row].length; column++) {
                ExcelCell cell = worksheet.getCell(row, column);
                if (cell.getValueType() != CellValueType.NULL)
                    sourceData[row][column] = cell.getValue().toString();
            }
        }
        try {
            FileOutputStream fileOut = new FileOutputStream("src\\main\\resources\\com\\example\\data\\data.txt"); //Outfile
            for (int i = 0;i< sourceData.length;i++) {
                if (sourceData[i][0] != null && sourceData[i][7] != null) {
                    String line = sourceData[i][0] + "% %" + sourceData[i][7] + "% %" + "customer" + "\n";
                    byte out[] = line.getBytes();
                    fileOut.write(out);
                }
            }
            fileOut.close();
        } catch (IOException e) {

            System.out.println("An error occurred."+ e);
        }
    }

    private static void saveDataStaff() throws IOException {
        File file = new File("src/main/resources/com/example/data/Staff.xlsx");
        ExcelFile workbook = ExcelFile.load(file.getAbsolutePath());
        //ExcelFile workbook = ExcelFile.load("Customer.xlsx");
        //System.out.println(file.getAbsolutePath());
        ExcelWorksheet worksheet = workbook.getWorksheet(0);
        String[][] sourceData = new String[100][8];
        for (int row = 0; row < sourceData.length; row++) {
            for (int column = 0; column < sourceData[row].length; column++) {
                ExcelCell cell = worksheet.getCell(row, column);
                if (cell.getValueType() != CellValueType.NULL)
                    sourceData[row][column] = cell.getValue().toString();
            }
        }
        try {
            //FileOutputStream fileOut = new FileOutputStream("src\\main\\resources\\com\\example\\data\\data.txt"); //Outfile
            File file1 = new File("src\\main\\resources\\com\\example\\data\\data.txt");
            FileWriter fw = new FileWriter(file1,true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            String manager = "manager" + "% %" + "manager" + "% %" + "manager" + "\n";
            //byte out[] = line.getBytes();
            //fileOut.write(out);
            pw.print(manager);
            for (int i = 0;i< sourceData.length;i++) {
                if (sourceData[i][0] != null && sourceData[i][7] != null) {
                    String line = sourceData[i][0] + "% %" + sourceData[i][7] + "% %" + "staff" + "\n";
                    //byte out[] = line.getBytes();
                    //fileOut.write(out);
                    pw.print(line);
                }
            }
            //fileOut.close();
            pw.close();
        } catch (IOException e) {

            System.out.println("An error occurred."+ e);
        }
    }
}
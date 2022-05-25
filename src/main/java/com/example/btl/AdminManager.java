package com.example.btl;

import com.gembox.spreadsheet.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import java.io.File;
import java.io.IOException;

public class AdminManager {

    static {
        SpreadsheetInfo.setLicense("FREE-LIMITED-KEY");
    }

    public void load(ActionEvent event) throws IOException {
        //FileChooser fileChooser = new FileChooser();
        //fileChooser.setTitle("Open file");
        //File file = fileChooser.showOpenDialog(table.getScene().getWindow());
        File file = new File("src/main/resources/com/example/data/Manager.xlsx");
        ExcelFile workbook = ExcelFile.load(file.getAbsolutePath());
        //ExcelFile workbook = ExcelFile.load("Customer.xlsx");
        //System.out.println(file.getAbsolutePath());
        ExcelWorksheet worksheet = workbook.getWorksheet(0);
        String[][] sourceData = new String[100][7];
        for (int row = 0; row < sourceData.length; row++) {
            for (int column = 0; column < sourceData[row].length; column++) {
                ExcelCell cell = worksheet.getCell(row, column);
                if (cell.getValueType() != CellValueType.NULL)
                    sourceData[row][column] = cell.getValue().toString();
            }
        }
    }

    /*
    public void save(ActionEvent event) throws IOException {
        File file = new File("src/main/resources/com/example/data/Customer.xlsx");
        ExcelFile workbook = ExcelFile.load(file.getAbsolutePath());
        //ExcelFile file = new ExcelFile();
        // worksheet = file.addWorksheet("sheet");
        ExcelWorksheet worksheet = workbook.getWorksheet(0);
        for (int row = 0; row < table.getItems().size(); row++) {
            ObservableList cells = (ObservableList) table.getItems().get(row);
            for (int column = 0; column < cells.size(); column++) {
                if (cells.get(column) != null)
                    worksheet.getCell(row, column).setValue(cells.get(column).toString());
            }
        }
        workbook.save(file.getAbsolutePath());
    }

     */
}

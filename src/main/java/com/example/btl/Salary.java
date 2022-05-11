package com.example.btl;

import com.gembox.spreadsheet.*;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;

import java.io.File;
import java.io.IOException;

public class Salary {

    static {
        SpreadsheetInfo.setLicense("FREE-LIMITED-KEY");
    }

    @FXML
    public TableView table;

    public void load(ActionEvent event) throws IOException {
        //FileChooser fileChooser = new FileChooser();
        //fileChooser.setTitle("Open file");
        //File file = fileChooser.showOpenDialog(table.getScene().getWindow());
        File file = new File("src/main/resources/com/example/data/Staff.xlsx");
        ExcelFile workbook = ExcelFile.load(file.getAbsolutePath());
        //ExcelFile workbook = ExcelFile.load("Customer.xlsx");
        //System.out.println(file.getAbsolutePath());
        ExcelWorksheet worksheet = workbook.getWorksheet(0);
        String[][] sourceData = new String[100][7];
        for (int row = 0; row < sourceData.length; row++) {
            for (int column = 0; column < sourceData[row].length; column++) {
                ExcelCell cell = worksheet.getCell(row, column);
                if (cell.getValueType() != CellValueType.NULL) {
                    if (column == sourceData[row].length - 1) {
                        sourceData[row][column] = Integer.toString((Integer.parseInt(sourceData[row][4]) * Integer.parseInt(sourceData[row][5])));
                    } else {
                        sourceData[row][column] = cell.getValue().toString();
                    }
                }

            }
        }
        fillTable(sourceData);
    }

    private void fillTable(String[][] dataSource) {
        table.getColumns().clear();

        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
        for (String[] row : dataSource)
            data.add(FXCollections.observableArrayList(row));
        table.setItems(data);

        for (int i = 0; i < dataSource[0].length; i++) {
            if (i == 2 || i == 3) {
                continue;
            } else {
                final int currentColumn = i + 1;
                TableColumn<ObservableList<String>, String> column = new TableColumn<>(nameOfColumn(currentColumn));
                column.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(currentColumn - 1)));
                column.setEditable(true);
                column.setCellFactory(TextFieldTableCell.forTableColumn());
                column.setOnEditCommit(
                        (TableColumn.CellEditEvent<ObservableList<String>, String> t) -> {
                            t.getTableView().getItems().get(t.getTablePosition().getRow()).set(t.getTablePosition().getColumn(), t.getNewValue());
                        });
                column.setMinWidth(sizeOfCol(currentColumn));

                table.getColumns().add(column);
            }
        }
    }

    public void save(ActionEvent event) throws IOException {
        File file = new File("src/main/resources/com/example/data/Staff.xlsx");
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

        /*
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XLSX files (*.xlsx)", "*.xlsx"),
                new FileChooser.ExtensionFilter("XLS files (*.xls)", "*.xls"),
                new FileChooser.ExtensionFilter("ODS files (*.ods)", "*.ods"),
                new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv"),
                new FileChooser.ExtensionFilter("HTML files (*.html)", "*.html")
        );
        File saveFile = fileChooser.showSaveDialog(table.getScene().getWindow());
        */


        workbook.save(file.getAbsolutePath());
    }

    private String nameOfColumn(int n) {
        if (n == 1) {
            return "ID Number:";
        } else if (n == 2) {
            return "Name:";
        } else if (n == 5) {
            return "Salary:";
        } else if (n == 6) {
            return "Work-day:";
        } else {
            return "Pay:";
        }
    }

    private int sizeOfCol(int n) {
        if (n == 1) {
            return 150;
        } else if (n == 2) {
            return 250;
        } else if (n == 3) {
            return 120;
        } else if (n == 4) {
            return 50;
        } else if (n == 5) {
            return 50;
        } else if (n == 6) {
            return 100;
        } else {
            return 400;
        }
    }
}

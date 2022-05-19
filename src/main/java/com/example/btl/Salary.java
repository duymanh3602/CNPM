package com.example.btl;

import com.gembox.spreadsheet.*;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

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
                column.setEditable(false);
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

    public void getInfo(Event event) {
        TablePosition pos = (TablePosition) table.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();
        ObservableList cells = (ObservableList) table.getItems().get(row);

        try {
            if (cells.get(0).toString() != null) {

                Alert menu = new Alert(Alert.AlertType.NONE);
                menu.setTitle("About");
                menu.setHeaderText("Salary Information!");
                //menu.initStyle(StageStyle.UNDECORATED);
                //menu.getButtonTypes().clear();
                GridPane grid = new GridPane();
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(20, 150, 10, 10));

                TextField acc = new TextField();
                acc.setText(cells.get(0).toString());
                acc.setEditable(false);
                TextField name = new TextField();
                name.setText(cells.get(1).toString());
                name.setEditable(false);
                TextField phone = new TextField();
                phone.setText(cells.get(4).toString());
                phone.setEditable(false);
                TextField age = new TextField();
                age.setText(cells.get(5).toString());
                age.setEditable(false);
                TextField vip = new TextField();
                vip.setText(cells.get(6).toString());
                vip.setEditable(false);

                grid.add(new Label("ID:"), 0, 0);
                grid.add(acc, 1, 0);
                grid.add(new Label("Name:"), 0, 1);
                grid.add(name, 1, 1);
                grid.add(new Label("Salary:"), 0, 2);
                grid.add(phone, 1, 2);
                grid.add(new Label("Worked-day:"), 0, 3);
                grid.add(age, 1, 3);
                grid.add(new Label("Pay:"), 0, 4);
                grid.add(vip, 1, 4);
                menu.getDialogPane().setContent(grid);

                ButtonType pay = new ButtonType("Paid");
                ButtonType cancel = new ButtonType("Cancel");

                menu.getButtonTypes().addAll(pay, cancel);

                Optional<ButtonType> option = menu.showAndWait();
                if (option.isPresent()) {
                    if (option.get() == pay) {
                        System.out.println("Paiding ...");
                    } else {

                    }
                } else {
                    //Cancel here!.
                    System.out.println("Cancel!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

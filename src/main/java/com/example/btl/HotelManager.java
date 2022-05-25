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
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class HotelManager {
    static {
        SpreadsheetInfo.setLicense("FREE-LIMITED-KEY");
    }

    @FXML
    public TableView table;

    @FXML
    public ToggleButton edit;

    public void load(ActionEvent event) throws IOException {
        //FileChooser fileChooser = new FileChooser();
        //fileChooser.setTitle("Open file");
        //File file = fileChooser.showOpenDialog(table.getScene().getWindow());
        File file = new File("src/main/resources/com/example/data/Room.xlsx");
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
        fillTable(sourceData);
    }

    private void fillTable(String[][] dataSource) {
        table.getColumns().clear();

        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
        for (String[] row : dataSource)
            data.add(FXCollections.observableArrayList(row));
        table.setItems(data);

        for (int i = 0; i < dataSource[0].length; i++) {
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

    public void save(ActionEvent event) throws IOException {
        File file = new File("src/main/resources/com/example/data/Room.xlsx");
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
            return "Hotel's Name:";
        } else if (n == 2) {
            return "Address:";
        } else if (n == 3) {
            return "Rating:";
        } else if (n == 4) {
            return "Room ID:";
        } else if (n == 5) {
            return "Bed:";
        } else if (n == 6) {
            return "Price:";
        } else {
            return "Available:";
        }
    }

    private int sizeOfCol(int n) {
        if (n == 1) {
            return 300;
        } else if (n == 2) {
            return 250;
        } else if (n == 3) {
            return 100;
        } else if (n == 4) {
            return 100;
        } else if (n == 5) {
            return 100;
        } else if (n == 6) {
            return 150;
        } else {
            return 160;
        }
    }

    public void edit(Event event) {
        if (edit.isSelected()) {

        } else {
            getInfo(event);
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
                menu.setHeaderText("Room Information!");
                //menu.initStyle(StageStyle.UNDECORATED);
                //menu.getButtonTypes().clear();
                GridPane grid = new GridPane();
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(20, 150, 10, 10));

                TextField name = new TextField();
                name.setText(cells.get(0).toString());
                name.setEditable(false);
                TextField add = new TextField();
                add.setText(cells.get(1).toString());
                add.setEditable(false);
                TextField rate = new TextField();
                rate.setText(cells.get(2).toString());
                rate.setEditable(false);
                TextField bed = new TextField();
                bed.setText(cells.get(4).toString());
                bed.setEditable(false);
                TextField price = new TextField();
                price.setText(cells.get(5).toString());
                price.setEditable(false);
                TextField status = new TextField();
                status.setText(cells.get(6).toString());
                status.setEditable(false);

                grid.add(new Label("Name:"), 0, 0);
                grid.add(name, 1, 0);
                grid.add(new Label("Address:"), 0, 1);
                grid.add(add, 1, 1);
                grid.add(new Label("Rate:"), 0, 2);
                grid.add(rate, 1, 2);
                grid.add(new Label("Bed:"), 0, 3);
                grid.add(bed, 1, 3);
                grid.add(new Label("Price:"), 0, 4);
                grid.add(price, 1, 4);
                grid.add(new Label("Available: "),0,5);
                grid.add(status, 1,5);
                menu.getDialogPane().setContent(grid);

                ButtonType contact = new ButtonType("Contact");
                ButtonType cancel = new ButtonType("Cancel");
                ButtonType book = new ButtonType("Book!");


                menu.getButtonTypes().addAll(book, contact, cancel);

                Optional<ButtonType> option = menu.showAndWait();
                if (option.isPresent()) {
                    if (option.get() == contact) {
                        System.out.println("Calling to numberphone ...");
                    } else if (option.get() == book) {
                        if (cells.get(6).toString().equals("no")) {
                            Alert failBooking = new Alert(Alert.AlertType.ERROR);
                            failBooking.setHeaderText("This Room is not available now!");
                            failBooking.showAndWait();
                        } else {
                            Alert succesBooking = new Alert(Alert.AlertType.CONFIRMATION);
                            succesBooking.setHeaderText("Booking Success!\nYour Room ID: " + cells.get(3).toString()+"\nPricing: " + cells.get(5).toString());
                            succesBooking.showAndWait();
                        }
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
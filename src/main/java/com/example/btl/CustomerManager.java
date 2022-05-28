package com.example.btl;

import com.gembox.spreadsheet.*;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import org.shaded.apache.poi.ss.formula.functions.Even;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomerManager implements Initializable{

    LoadData inputData = new LoadData();

    static {
        SpreadsheetInfo.setLicense("FREE-LIMITED-KEY");
    }

    @FXML
    public TableView table;

    @FXML
    public ToggleButton editButton;

    @FXML
    public Button addButton;

    @FXML
    public Button saveButton;




    public void load(Event event) throws IOException {
        inputData.insertFromFile();
        inputData.readMoneyData();
        //FileChooser fileChooser = new FileChooser();
        //fileChooser.setTitle("Open file");
        //File file = fileChooser.showOpenDialog(table.getScene().getWindow());
        File file = new File("src/main/resources/com/example/data/Customer.xlsx");
        ExcelFile workbook = ExcelFile.load(file.getAbsolutePath());
        //ExcelFile workbook = ExcelFile.load("Customer.xlsx");
        //System.out.println(file.getAbsolutePath());
        ExcelWorksheet worksheet = workbook.getWorksheet(0);
        String[][] sourceData = new String[100][7];
        for (int row = 0; row < sourceData.length; row++) {
            ExcelCell cellID = worksheet.getCell(row, 0);
            for (int column = 0; column < sourceData[row].length; column++) {
                ExcelCell cell = worksheet.getCell(row, column);
                if (cell.getValueType() != CellValueType.NULL)
                    if (column == 5) {
                        sourceData[row][column] = getRoomList(cellID.getValue().toString());
                    } else {
                        sourceData[row][column] = cell.getValue().toString();
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
        } else if (n == 3) {
            return "Phone:";
        } else if (n == 4) {
            return "Age:";
        } else if (n == 5) {
            return "VIP:";
        } else if (n == 6) {
            return "Room:";
        } else {
            return "Details:";
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


    public void addNewCustomer(Event event) throws IOException {
        Alert menu = new Alert(Alert.AlertType.NONE);
        menu.setTitle("ADD");
        menu.setHeaderText("Enter new Customer Information!");
        //menu.initStyle(StageStyle.UNDECORATED);
        //menu.getButtonTypes().clear();
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField acc = new TextField();
        acc.setPromptText("ID:");
        TextField name = new TextField();
        name.setPromptText("Name:");
        TextField phone = new TextField();
        phone.setPromptText("Phone:");
        TextField age = new TextField();
        age.setPromptText("Age:");
        CheckBox vip = new CheckBox();

        grid.add(new Label("ID:"), 0, 0);
        grid.add(acc, 1, 0);
        grid.add(new Label("Name:"), 0, 1);
        grid.add(name, 1, 1);
        grid.add(new Label("Phone:"), 0, 2);
        grid.add(phone, 1, 2);
        grid.add(new Label("Age:"), 0, 3);
        grid.add(age, 1, 3);
        grid.add(new Label("Vip:"), 0, 4);
        grid.add(vip, 1, 4);
        menu.getDialogPane().setContent(grid);

        ButtonType add = new ButtonType("Add");
        ButtonType cancel = new ButtonType("Cancel");

        menu.getButtonTypes().addAll(add, cancel);

        Optional<ButtonType> option = menu.showAndWait();
        if (option.isPresent()) {
            if (option.get() == add) {
                inputData.insertFromFile();
                for (int i = 0; i < inputData.inputData.size(); i++) {
                    if (inputData.inputData.get(i).getAcc().equals(acc.getText())) {
                        return;
                    }
                }
                //Staff staffNew = new Staff(name.getText(), phone.getText(), acc.getText(), age.getText(), salary.getText(), "0","admin");
                Account newAcc = new Account(acc.getText(), "admin", "customer", name.getText());
                inputData.inputData.add(newAcc);
                //inputData.exportToFile("staff");
                File file = new File("src/main/resources/com/example/data/Customer.xlsx");
                ExcelFile workbook = ExcelFile.load(file.getAbsolutePath());
                //ExcelFile workbook = ExcelFile.load("Customer.xlsx");
                //System.out.println(file.getAbsolutePath());
                ExcelWorksheet worksheet = workbook.getWorksheet(0);
                String[][] sourceData = new String[100][8];
                int temp = 0;
                for (int row = 0; row < sourceData.length; row++) {
                    for (int column = 0; column < sourceData[row].length; column++) {
                        ExcelCell cell = worksheet.getCell(row, column);
                        if (cell.getValueType() != CellValueType.NULL)
                            sourceData[row][column] = cell.getValue().toString();
                        if (worksheet.getCell(row, 0).getValueType() == CellValueType.NULL
                                && worksheet.getCell(row, 6).getValueType() == CellValueType.NULL
                                && temp == 0) {
                            sourceData[row][0] = acc.getText();
                            sourceData[row][1] = name.getText();
                            sourceData[row][2] = phone.getText();
                            sourceData[row][3] = age.getText();
                            if (vip.isSelected()) {
                                sourceData[row][4] = "true";
                            } else {
                                sourceData[row][4] = "false";
                            }
                            sourceData[row][5] = "empty";
                            sourceData[row][6] = "0";
                            sourceData[row][7] = "admin";
                            temp += 1;
                        }
                    }
                }
                //ExcelFile file = new ExcelFile();
                // worksheet = file.addWorksheet("sheet");
                for (int row = 0; row < sourceData.length; row++) {
                    for (int column = 0; column < 8; column++) {
                        if (sourceData[row][column] != null)
                            worksheet.getCell(row, column).setValue(sourceData[row][column]);
                    }
                }
                workbook.save(file.getAbsolutePath());
                load(event);


            } else {
            }
        } else {
            //Cancel here!.
            System.out.println("Cancel!");
        }
    }

    public void getInfo(Event event) {
        if (editButton.isSelected()) {
            return;
        } else {

            TablePosition pos = (TablePosition) table.getSelectionModel().getSelectedCells().get(0);
            int row = pos.getRow();
            ObservableList cells = (ObservableList) table.getItems().get(row);

            try {
                if (cells.get(0).toString() != null) {

                    Alert menu = new Alert(Alert.AlertType.NONE);
                    menu.setTitle("About");
                    menu.setHeaderText("Customer Information!");
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
                    phone.setText(cells.get(2).toString());
                    phone.setEditable(false);
                    TextField age = new TextField();
                    age.setText(cells.get(3).toString());
                    age.setEditable(false);
                    TextField vip = new TextField();
                    vip.setText(cells.get(4).toString().equals("false") ? "no" : "yes");
                    vip.setEditable(false);

                    grid.add(new Label("ID:"), 0, 0);
                    grid.add(acc, 1, 0);
                    grid.add(new Label("Name:"), 0, 1);
                    grid.add(name, 1, 1);
                    grid.add(new Label("Phone:"), 0, 2);
                    grid.add(phone, 1, 2);
                    grid.add(new Label("Age:"), 0, 3);
                    grid.add(age, 1, 3);
                    grid.add(new Label("VIP:"), 0, 4);
                    grid.add(vip, 1, 4);
                    menu.getDialogPane().setContent(grid);

                    ButtonType contact = new ButtonType("Contact");
                    ButtonType cancel = new ButtonType("Cancel");

                    menu.getButtonTypes().addAll(contact, cancel);

                    Optional<ButtonType> option = menu.showAndWait();
                    if (option.isPresent()) {
                        if (option.get() == contact) {
                            System.out.println("Calling to numberphone ...");
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

    private String checkType() {
        for (int i = 0; i < inputData.inputData.size(); i++) {
            if (inputData.inputData.get(i).getAcc().equals(HotelManager.manager)) {
                return inputData.inputData.get(i).getType();
            }
        }
        return "customer";
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inputData.insertFromFile();
        if (checkType().equals("customer")) {
            addButton.setVisible(false);
            saveButton.setVisible(false);
            editButton.setVisible(false);
        }
    }

    private String getRoomList(String id) {
        //System.out.println(HotelManager.manager);
        String res = "";
        for (int i = 0; i < inputData.inputHotel.size(); i++) {
            if (inputData.inputHotel.get(i).getCustomerID().equals(id)) {
                res += inputData.inputHotel.get(i).getRoomID() + ", ";
            }
        }

        if (res.trim().equals("")) {
            return "empty";
        } else {
            return res;
        }
    }
}

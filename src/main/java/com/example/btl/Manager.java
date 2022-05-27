package com.example.btl;

import com.gembox.spreadsheet.CellValueType;
import com.gembox.spreadsheet.ExcelCell;
import com.gembox.spreadsheet.ExcelFile;
import com.gembox.spreadsheet.ExcelWorksheet;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Manager implements Initializable {

    LoadData loadData = new LoadData();
    //HotelManager hotelManager = new HotelManager();
//
    @FXML
    public Pane buttonPane;

    @FXML
    public Button staff;

    @FXML
    public Button cus;

    @FXML
    public Button room;

    @FXML
    public Button salary;

    @FXML
    private Button loginButton;

    @FXML
    private Button myacc;

    @FXML
    private Button registerButton;
    @FXML
    private StackPane contentArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            //Parent fxml = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("logClass.fxml")));
            //contentArea.getChildren().removeAll();
            //contentArea.getChildren().setAll(fxml);
            buttonPane.setVisible(false);
            myacc.setVisible(false);
            //logo.setVisible(false);

        } catch (Exception e) {
            Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, e);
       }
    }
    //IOException e

    @FXML
    public void staff(Event event) throws IOException {
        Parent fxml = FXMLLoader.load((getClass().getResource("staffManager.fxml")));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }

    @FXML
    public void customer(Event event) throws IOException {
        Parent fxml = FXMLLoader.load((getClass().getResource("customerManager.fxml")));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }

    @FXML
    public void salary(Event event) throws IOException {
        Parent fxml = FXMLLoader.load((getClass().getResource("salary.fxml")));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }

    @FXML
    public void myacc(Event event) throws IOException {
        Parent fxml = FXMLLoader.load((getClass().getResource("myacc.fxml")));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }

    @FXML
    public void hotel(Event event) throws IOException {
        Parent fxml = FXMLLoader.load((getClass().getResource("hotelManager.fxml")));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }


    public void Login(Event event) throws IOException {

        Alert menu = new Alert(Alert.AlertType.NONE);
        menu.setTitle("LOGIN");
        menu.setHeaderText("Enter your Account!");
        //menu.initStyle(StageStyle.UNDECORATED);
        //menu.getButtonTypes().clear();
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField username = new TextField();
        username.setPromptText("Username");
        PasswordField password = new PasswordField();
        password.setPromptText("Password");

        grid.add(new Label("Username:"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(password, 1, 1);
        menu.getDialogPane().setContent(grid);

        ButtonType login = new ButtonType("Login");
        ButtonType cancel = new ButtonType("Cancel");

        menu.getButtonTypes().addAll(login, cancel);

        Optional<ButtonType> option = menu.showAndWait();
        if (option.isPresent()) {
            if (option.get() == login) {
                loadData.insertFromFile();
                for (int i = 0; i < loadData.inputData.size(); i++) {
                    if (loadData.inputData.get(i).getAcc().equals(username.getText())) {
                        if (loadData.inputData.get(i).getPass().equals(password.getText())) {
                            //loadData.setAccLog(username.getText());
                            HotelManager.manager = username.getText();
                            buttonPane.setVisible(true);
                            loginButton.setVisible(false);
                            registerButton.setVisible(false);
                            myacc.setVisible(true);
                            if (loadData.inputData.get(i).getType().equals("customer")) {
                                cus.setVisible(false);
                                salary.setVisible(false);
                            } else if (loadData.inputData.get(i).getType().equals("staff")) {
                                salary.setVisible(false);
                            }
                        }
                    }
                }
            } else {
            }
        } else {
            //Cancel here!.
            System.out.println("Cancel!");
        }
    }

    public void Register(Event event) throws IOException {

        Alert menu = new Alert(Alert.AlertType.NONE);
        menu.setTitle("Register");
        menu.setHeaderText("Enter your Information!");
        //menu.initStyle(StageStyle.UNDECORATED);
        //menu.getButtonTypes().clear();
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField acc = new TextField();
        acc.setPromptText("ID:");
        PasswordField pass = new PasswordField();
        pass.setPromptText("Password:");
        TextField name = new TextField();
        name.setPromptText("Name:");
        TextField phone = new TextField();
        phone.setPromptText("Phone:");
        TextField age = new TextField();
        age.setPromptText("Age:");
        CheckBox type = new CheckBox();

        grid.add(new Label("ID:"), 0, 0);
        grid.add(acc, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(pass, 1, 1);
        grid.add(new Label("Name:"), 0, 2);
        grid.add(name, 1, 2);
        grid.add(new Label("Phone:"), 0, 3);
        grid.add(phone, 1, 3);
        grid.add(new Label("Age:"), 0, 4);
        grid.add(age, 1, 4);
        grid.add(new Label("Manager:"), 0, 5);
        grid.add(type, 1, 5);
        menu.getDialogPane().setContent(grid);


        ButtonType register = new ButtonType("Register");
        ButtonType cancel = new ButtonType("Cancel");

        menu.getButtonTypes().addAll(register, cancel);

        Optional<ButtonType> option = menu.showAndWait();
        if (option.isPresent()) {
            if (option.get() == register) {
                loadData.insertFromFile();
                for (int i = 0; i < loadData.inputData.size(); i++) {
                    if (loadData.inputData.get(i).getAcc().equals(acc.getText())) {
                        Alert success = new Alert(Alert.AlertType.WARNING);
                        success.setHeaderText("Account Already Exist!");
                        ButtonType cancel2 = new ButtonType("Cancel");
                        //success.getButtonTypes().addAll();
                        Optional<ButtonType> click = success.showAndWait();
                        //if (click.isPresent()) {
                        //    return;
                        //}
                        return;

                    }
                }
                File file = new File("src/main/resources/com/example/data/Customer.xlsx");
                if (type.isSelected()) {
                    Account managerNew = new Account(acc.getText(), pass.getText(), "manager", name.getText());
                    loadData.inputData.add(managerNew);
                    loadData.exportToFile("manager");
                    //buttonPane.setVisible(true);
                    //logo.setVisible(true);
                    //loginButton.setVisible(false);
                    //registerButton.setVisible(false);
                    Alert success = new Alert(Alert.AlertType.CONFIRMATION);
                    success.setHeaderText("Register Successfully!");
                    success.setContentText("Your Account: " + acc.getText() + "\nYour Password: " + pass.getText());
                    //ButtonType cancel2 = new ButtonType("Cancel");
                    //success.getButtonTypes().addAll();
                    Optional<ButtonType> click = success.showAndWait();
                    file = new File("src/main/resources/com/example/data/Manager.xlsx");
                } else {
                    Customer customerNew = new Customer(name.getText(), phone.getText(), acc.getText(), age.getText(), pass.getText());
                    loadData.inputData.add(customerNew.getAccount());
                    loadData.exportToFile("customer");
                    //buttonPane.setVisible(true);
                    //logo.setVisible(true);
                    //loginButton.setVisible(false);
                    //registerButton.setVisible(false);
                    Alert success = new Alert(Alert.AlertType.CONFIRMATION);
                    success.setHeaderText("Register Successfully!");
                    success.setContentText("Your Account: " + acc.getText() + "\nYour Password: " + pass.getText());
                    //ButtonType cancel2 = new ButtonType("Cancel");
                    //success.getButtonTypes().addAll();
                    Optional<ButtonType> click = success.showAndWait();
                    file = new File("src/main/resources/com/example/data/Customer.xlsx");
                }
                //File file = new File("src/main/resources/com/example/data/Customer.xlsx");
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
                                && worksheet.getCell(row, 7).getValueType() == CellValueType.NULL
                                && temp == 0) {
                            sourceData[row][0] = acc.getText();
                            sourceData[row][1] = name.getText();
                            sourceData[row][2] = phone.getText();
                            sourceData[row][3] = age.getText();
                            sourceData[row][4] = "false";
                            sourceData[row][5] = null;
                            sourceData[row][6] = null;
                            sourceData[row][7] = pass.getText();
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
                /*
                if (pass.getText().equals("111")) {
                    buttonPane.setVisible(true);
                    //logo.setVisible(true);
                    loginButton.setVisible(false);
                    registerButton.setVisible(false);
                }

                 */
            } else {
            }
        } else {
            //Cancel here!.
            System.out.println("Cancel!");
        }
    }

    /*
    private void addAccount() throws IOException {
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
                if (worksheet.getCell(row, 0).getValueType() == null
                        && worksheet.getCell(row, 7).getValueType() == null) {
                    sourceData[row][0] =
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
    }

     */

}

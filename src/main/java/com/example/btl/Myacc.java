package com.example.btl;

import com.gembox.spreadsheet.CellValueType;
import com.gembox.spreadsheet.ExcelCell;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import org.shaded.apache.poi.ss.formula.functions.Even;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Myacc implements Initializable {

    LoadData inputData = new LoadData();

    @FXML
    public TextField myname;

    @FXML
    public TextField username;

    @FXML
    public TextField passShow;

    @FXML
    public TextField type;

    @FXML
    public ToggleButton show;

    @FXML
    public Button change;

    @FXML
    public TableView myRoom;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            inputData.insertFromFile();
            myname.setEditable(false);
            username.setEditable(false);
            passShow.setEditable(false);
            type.setEditable(false);
            inputData.readMoneyData();
            myname.setText(getMyName(HotelManager.manager));
            username.setText(HotelManager.manager);
            passShow.setText(getMyPass(HotelManager.manager));
            passShow.setText("*********");
            type.setText(getType(HotelManager.manager));
        } catch (Exception e) {
            Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void hide(Event event) {
        if (show.isSelected()) {
            passShow.setText(getMyPass(HotelManager.manager));
        } else {
            passShow.setText("*********");

        }
    }

    private String getMyName(String id) {
        for (int i = 0; i < inputData.inputData.size(); i++) {
            if (inputData.inputData.get(i).getAcc().equals(id)) {
                return inputData.inputData.get(i).getName();
            }
        }
        return "N/A";
    }

    private String getMyPass(String id) {
        for (int i = 0; i < inputData.inputData.size(); i++) {
            if (inputData.inputData.get(i).getAcc().equals(id)) {
                return inputData.inputData.get(i).getPass();
            }
        }
        return "N/A";
    }

    private String getType(String id) {
        for (int i = 0; i < inputData.inputData.size(); i++) {
            if (inputData.inputData.get(i).getAcc().equals(id)) {
                return inputData.inputData.get(i).getType();
            }
        }
        return "N/A";
    }

    public void change(Event event) {
        Alert changePass = new Alert(Alert.AlertType.NONE);
        changePass.setTitle("Change PassWord!");
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField oldPass = new TextField();
        oldPass.setPromptText("Enter old Password:");
        TextField newPass = new TextField();
        newPass.setPromptText("Enter new Password:");

        grid.add(new Label("OLD PASSWORD:"), 0, 0);
        grid.add(oldPass, 1, 0);
        grid.add(new Label("NEW PASSWORD:"), 0, 1);
        grid.add(newPass, 1, 1);
        changePass.getDialogPane().setContent(grid);

        ButtonType done = new ButtonType("Done!");
        ButtonType cancel = new ButtonType("Cancel");

        changePass.getButtonTypes().addAll(done, cancel);

        Optional<ButtonType> option = changePass.showAndWait();
        if (option.isPresent()) {
            if (option.get() == done) {
                System.out.println("Done!");
            } else {
                System.out.println("Cancel!");
            }
        }
    }

    private void changePass(String id, String newPass) {
        for (int i = 0; i < inputData.inputData.size(); i++) {
            if (inputData.inputData.get(i).getAcc().equals(id)) {
                inputData.inputData.get(i).setPass(newPass);
                System.out.println("Check code: done " + newPass + " " + id + " " + inputData.inputData.get(i).getPass());
                return;
            }
        }
        saveData();
    }

    public void saveData() {
        try {
            FileOutputStream fileOut = new FileOutputStream("src\\main\\resources\\com\\example\\data\\data.txt"); //Outfile
            for (int i = 0;i< inputData.inputData.size();i++) {
                String line = inputData.inputData.get(i).getAcc() + "% %" + inputData.inputData.get(i).getPass() + "% %" + inputData.inputData.get(i).getType() + "% %" + inputData.inputData.get(i).getName() + "\n";
                byte out[] = line.getBytes();
                fileOut.write(out);
            }
            fileOut.close();
            inputData.inputData.clear();
            inputData.insertFromFile();
        } catch (IOException e) {

            System.out.println("An error occurred."+ e);
        }
    }

    public void showMyRoom(Event event) {
        String id = HotelManager.manager;
        System.out.println(HotelManager.manager);
        String[][] data = new String[10][3];
        int rowIndex = 0;
        for (int i = 0; i < inputData.inputHotel.size(); i++) {
            if (inputData.inputHotel.get(i).getCustomerID().equals(id)) {
                data[rowIndex][0] = inputData.inputHotel.get(i).getRoomID();
                data[rowIndex][1] = inputData.inputHotel.get(i).getPrice();
                rowIndex += 1;
            }
        }

        myRoom.getColumns().clear();
        ObservableList<ObservableList<String>> dataSource = FXCollections.observableArrayList();
        for (String[] row : data)
            dataSource.add(FXCollections.observableArrayList(row));
        myRoom.setItems(dataSource);

        for (int i = 0; i < 2; i++) {
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

            myRoom.getColumns().add(column);

        }
    }

    private String nameOfColumn(int n) {
        if (n == 1) {
            return "Room ID";
        } else {
            return "Price";
        }
    }

    private int sizeOfCol(int n) {
        if (n == 1) {
            return 200;
        } else {
            return 250;
        }
    }

    public void showInfoLog(Event event) {
        TablePosition pos = (TablePosition) myRoom.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();
        ObservableList cells = (ObservableList) myRoom.getItems().get(row);
        Alert info = new Alert(Alert.AlertType.NONE);
        info.setTitle("About");
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField id = new TextField();
        id.setText(cells.get(0).toString());
        id.setEditable(false);
        TextField price = new TextField();
        price.setText(cells.get(1).toString());
        price.setEditable(false);
        TextField cus = new TextField();
        cus.setText(HotelManager.manager);
        cus.setEditable(false);


        grid.add(new Label("Room ID:"), 0, 0);
        grid.add(id, 1, 0);
        grid.add(new Label("Price:"), 0, 1);
        grid.add(price, 1, 1);
        grid.add(new Label("CustomerID:"), 0, 2);
        grid.add(cus, 1, 2);

        info.getDialogPane().setContent(grid);

        ButtonType cancel = new ButtonType("Cancel");
        ButtonType checkout = new ButtonType("Check-out");


        info.getButtonTypes().addAll(checkout, cancel);

        Optional<ButtonType> option = info.showAndWait();
        if (option.isPresent()) {
            if (option.get() == checkout) {
                checkOut(cells.get(0).toString());
                showMyRoom(event);
            }
        }
    }

    private void checkOut(String id) {
        for (int i = 0; i < inputData.inputHotel.size(); i++) {
            if (inputData.inputHotel.get(i).getRoomID().equals(id)) {
                inputData.inputHotel.get(i).checkOut();
                inputData.saveMoneyData();
                return;
            }
        }
    }

}

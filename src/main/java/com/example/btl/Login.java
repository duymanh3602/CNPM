package com.example.btl;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;


import java.io.IOException;
import java.util.Optional;

public class Login {

    /*
    public String userName; // acc
    public String passWord; // pass
    String a = "111";
    public int code = 0;

    @FXML
    private TextField acc;
    //@FXML
    //private PasswordField pass;

    @FXML
    private TextField pass;

    @FXML
    protected void showPass(Event event) {
        pass.setVisible(true);
        System.out.println(pass.getText());
    }

    @FXML
    private void checkLogin(Event event) {
        userName = acc.getText();
        passWord = pass.getText();
        if (pass.getText().equals(a)) {
            System.out.println("SC");
        }
    }

     */
    public void Login(MouseEvent event) throws IOException {

            Alert menu = new Alert(Alert.AlertType.NONE);
            menu.setTitle("Select");
            menu.setHeaderText("Bạn muốn XÓA hay CHỈNH SỬA?");
            //menu.initStyle(StageStyle.UNDECORATED);
            //menu.getButtonTypes().clear();

            ButtonType delete = new ButtonType("Delete");
            ButtonType edit = new ButtonType("Edit");
            ButtonType cancel = new ButtonType("Cancel");

            menu.getButtonTypes().addAll(delete, edit, cancel);

            Optional<ButtonType> option = menu.showAndWait();
            if (option.isPresent()) {
                if (option.get() == delete) {

                } else if (option.get() == edit) {
                }
            } else {
                //Cancel here!.
                System.out.println("Cancel!");
            }

    }
}
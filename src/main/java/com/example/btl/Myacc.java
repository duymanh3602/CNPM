package com.example.btl;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Myacc implements Initializable {

    @FXML
    public TextField myname;

    @FXML
    public TextField username;

    @FXML
    public TextField passShow;

    @FXML
    public TextField passHide;

    @FXML
    public TextField type;

    @FXML
    public ToggleButton show;

    @FXML
    public Button change;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            myname.setEditable(false);
            username.setEditable(false);
            passShow.setEditable(false);
            passHide.setEditable(false);
            type.setEditable(false);
            passShow.setVisible(false);
            show.setSelected(true);
            myname.setText("name");
            username.setText("id");
            passShow.setText("pass");
            type.setText("type");
        } catch (Exception e) {
            Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void hide(Event event) {
        if (show.isSelected()) {
            passShow.setVisible(false);
            passHide.setVisible(true);
        } else {
            passShow.setVisible(true);
            passHide.setVisible(false);
        }
    }


}

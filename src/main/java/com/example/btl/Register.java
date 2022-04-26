package com.example.btl;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Register {

    Owner owner = new Owner();

    @FXML
    private TextField id;

    @FXML
    private PasswordField pass;

    @FXML
    private TextField name;

    @FXML
    private TextField phone;

    @FXML
    private TextField age;

    private boolean checkAccout(String ID) {
        for (int i = 0; i < owner.customerList.size(); i++) {
            if (owner.customerList.get(i).getID().equals(ID)) {
                return false;
            }
        }
        return true;
    }

    /*
    protected void createNew() {
        if (checkAccout(id.getText())) {
            Customer customer = new Customer(name.getText(), phone.getText(), id.getText(), age.getText(), pass.getText());
            String newLine = customer.setInfo();
            //import to file
        } else {
            // Alert false;
        }
    }

     */
}

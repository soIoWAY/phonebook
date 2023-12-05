package com.example.phonebook;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class EditModalController {
    @FXML
    private TextField nameField;

    @FXML
    private TextField phoneField;

    @FXML
    private Button cancelBtn;

    @FXML
    private Button okBtn;

    private Stage modalStage;
    private boolean okClicked = false;

    private PhoneBookController phoneBookController;

    public void setPhoneBookController(PhoneBookController phoneBookController) {
        this.phoneBookController = phoneBookController;
    }

    @FXML
    private void okButtonHandler() throws IOException {
        okClicked = true;
        modalStage.close();
    }

    @FXML
    private void cancelButtonHandler() {
        okClicked = false;
        modalStage.close();
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    public void setDialogStage(Stage dialogStage) {
        this.modalStage = dialogStage;
    }

    public String getName() {
        return nameField.getText();
    }

    public String getPhone() {
        return phoneField.getText();
    }
}

package com.example.phonebook;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.scene.Scene;
import javafx.scene.Parent;

import javafx.event.ActionEvent;

import java.io.IOException;

public class PhoneBookController {
    @FXML
    private Button addButton;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    @FXML
    private TextField searchField;

    @FXML
    private Button searchButton;

    @FXML
    private TableView<DataObject> tableView;

    @FXML
    private Text recordCountText;

    @FXML
    private ObservableList<DataObject> originalData;

    public void initialize() {
        TableColumn<DataObject, String> nameColumn = new TableColumn<>("ПІП");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<DataObject, String> phoneColumn = new TableColumn<>("Телефон");
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));

        tableView.getColumns().addAll(nameColumn, phoneColumn);

        updateRecordCound();

        originalData = FXCollections.observableArrayList(tableView.getItems());
    }

    public void updateRecordCound() {
        int count = tableView.getItems().size();
        recordCountText.setText("Кількість записів в таблиці: " + count);
    }


    @FXML
    private void addEditDeleteBtnsClickHandler(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("edit-modal.fxml"));
            Parent root = loader.load();

            Stage modalStage = new Stage();

            Button ckickedButton = (Button) event.getSource();
            String buttonText = ckickedButton.getText();

            modalStage.setTitle(buttonText);
            modalStage.initModality(Modality.WINDOW_MODAL);
            modalStage.initOwner(ckickedButton.getScene().getWindow());
            modalStage.setHeight(200);
            modalStage.setWidth(350);

            Scene scene = new Scene(root);
            modalStage.setScene(scene);

            EditModalController editModalController = loader.getController();
            editModalController.setDialogStage(modalStage);
            editModalController.setPhoneBookController(this);


            modalStage.showAndWait();

            if (editModalController.isOkClicked()) {
                String name = editModalController.getName();
                String phone = editModalController.getPhone();

                if (buttonText.equals("ДОДАТИ")) {

                    DataObject newData = new DataObject(name, phone);
                    tableView.getItems().add(newData);
                    updateRecordCound();

                } else if (buttonText.equals("ВИДАЛИТИ")) {

                    DataObject itemToRemove = null;
                    for (DataObject item : tableView.getItems()) {
                        if (item.getPhone().equals(phone)) {
                            itemToRemove = item;
                            break;
                        }
                    }

                    if (itemToRemove != null) {
                        tableView.getItems().remove(itemToRemove);
                        updateRecordCound();
                    }

                } else if (buttonText.equals("РЕДАГУВАТИ")) {
                    DataObject foundItem = null;
                    for (DataObject item : tableView.getItems()) {
                        if (item.getPhone().equals(phone)) {
                            foundItem = item;
                            break;
                        }
                    }

                    if (foundItem != null) {
                        foundItem.setName(name);
                        tableView.refresh();
                    } else {
                        DataObject newData = new DataObject(name, phone);
                        tableView.getItems().add(newData);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void searchButtonHandler() {
        String searchText = searchField.getText().toLowerCase();
        ObservableList<DataObject> allData = tableView.getItems();

        if (!searchText.isEmpty()) {
            ObservableList<DataObject> filterData = FXCollections.observableArrayList();

            for (DataObject data : allData) {
                if (data.getName().toLowerCase().contains(searchText)) {
                    filterData.add(data);
                }
            }

            tableView.setItems(filterData);
        } else {
            tableView.setItems(originalData);
        }
    }
}

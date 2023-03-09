package ru.vsu.app.teacher.controllers;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import ru.vsu.app.teacher.tempory.TMPData;

public class TestCreatedBase {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button accept;

    @FXML
    private TextField count;

    @FXML
    private TextField time;

    @FXML
    private TextField version;

    @FXML
    void initialize() {
        assert accept != null : "fx:id=\"accept\" was not injected: check your FXML file 'Untitled'.";
        assert count != null : "fx:id=\"count\" was not injected: check your FXML file 'Untitled'.";
        assert time != null : "fx:id=\"time\" was not injected: check your FXML file 'Untitled'.";
        assert version != null : "fx:id=\"version\" was not injected: check your FXML file 'Untitled'.";
        version.setText("");
        count.setOnMouseClicked(event -> count.setText(""));
        time.setOnMouseClicked(event -> time.setText(""));
        accept.setOnAction(actionEvent -> {
            try {
                if (version.getText().equals("")) {
                    TMPData.version = 0;
                } else {
                    TMPData.version = Integer.parseInt(version.getText());
                }
                TMPData.time = Integer.parseInt(time.getText());
                TMPData.count = Integer.parseInt(count.getText());
            }catch (Exception e){
                GlobalMethods.generateAlert("Введено не корректное значение", Alert.AlertType.ERROR);
            }
        });
    }

}

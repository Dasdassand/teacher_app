package ru.vsu.app.teacher.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Test {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button back;

    @FXML
    private Button create;

    @FXML
    private Button edit;

    @FXML
    private Button load;

    @FXML
    private Button remove;

    @FXML
    private Button save;

    @FXML
    private Button send;

    @FXML
    void initialize() {
        assert back != null : "fx:id=\"back\" was not injected: check your FXML file 'Test.fxml'.";
        assert create != null : "fx:id=\"create\" was not injected: check your FXML file 'Test.fxml'.";
        assert edit != null : "fx:id=\"edit\" was not injected: check your FXML file 'Test.fxml'.";
        assert load != null : "fx:id=\"load\" was not injected: check your FXML file 'Test.fxml'.";
        assert remove != null : "fx:id=\"remove\" was not injected: check your FXML file 'Test.fxml'.";
        assert save != null : "fx:id=\"save\" was not injected: check your FXML file 'Test.fxml'.";
        assert send != null : "fx:id=\"send\" was not injected: check your FXML file 'Test.fxml'.";
        create.setOnAction(actionEvent -> GlobalMethods.openWindow(
                "Создание теста",
                "form/TestCreatedBase.fxml",
                "form/title.png",
                create
                )
        );

    }

}

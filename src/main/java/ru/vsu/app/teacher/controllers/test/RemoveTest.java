package ru.vsu.app.teacher.controllers.test;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class RemoveTest {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button RemoveButton;

    @FXML
    private Button backButton;

    @FXML
    private TextField password;

    @FXML
    private ChoiceBox<?> testID;

    @FXML
    void initialize() {
        assert RemoveButton != null : "fx:id=\"RemoveButton\" was not injected: check your FXML file 'RemoveTest.fxml'.";
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'RemoveTest.fxml'.";
        assert password != null : "fx:id=\"password\" was not injected: check your FXML file 'RemoveTest.fxml'.";
        assert testID != null : "fx:id=\"testID\" was not injected: check your FXML file 'RemoveTest.fxml'.";

    }

}

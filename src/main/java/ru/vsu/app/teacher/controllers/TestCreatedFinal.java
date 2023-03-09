package ru.vsu.app.teacher.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class TestCreatedFinal {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button accept;

    @FXML
    private ChoiceBox<?> count;

    @FXML
    private CheckBox four;

    @FXML
    private TextField fourAnswer;

    @FXML
    private CheckBox one;

    @FXML
    private TextField oneAnswer;

    @FXML
    private TextArea quest;

    @FXML
    private CheckBox three;

    @FXML
    private TextField threeAnswer;

    @FXML
    private CheckBox two;

    @FXML
    private TextField twoAnswer;

    @FXML
    void initialize() {
        assert accept != null : "fx:id=\"accept\" was not injected: check your FXML file 'Untitled'.";
        assert count != null : "fx:id=\"count\" was not injected: check your FXML file 'Untitled'.";
        assert four != null : "fx:id=\"four\" was not injected: check your FXML file 'Untitled'.";
        assert fourAnswer != null : "fx:id=\"fourAnswer\" was not injected: check your FXML file 'Untitled'.";
        assert one != null : "fx:id=\"one\" was not injected: check your FXML file 'Untitled'.";
        assert oneAnswer != null : "fx:id=\"oneAnswer\" was not injected: check your FXML file 'Untitled'.";
        assert quest != null : "fx:id=\"quest\" was not injected: check your FXML file 'Untitled'.";
        assert three != null : "fx:id=\"three\" was not injected: check your FXML file 'Untitled'.";
        assert threeAnswer != null : "fx:id=\"threeAnswer\" was not injected: check your FXML file 'Untitled'.";
        assert two != null : "fx:id=\"two\" was not injected: check your FXML file 'Untitled'.";
        assert twoAnswer != null : "fx:id=\"twoAnswer\" was not injected: check your FXML file 'Untitled'.";

    }

}

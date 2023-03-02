package ru.vsu.app.teacher.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class MainWindow {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button platoonButton;

    @FXML
    private ChoiceBox<?> platoons;

    @FXML
    private TableColumn<?, ?> students;

    @FXML
    private Button studentsButton;

    @FXML
    private TableView<?> tableStudent;

    @FXML
    private Button test;

    @FXML
    void initialize() {
        assert platoonButton != null : "fx:id=\"platoonButton\" was not injected: check your FXML file 'Untitled'.";
        assert platoons != null : "fx:id=\"platoons\" was not injected: check your FXML file 'Untitled'.";
        assert students != null : "fx:id=\"students\" was not injected: check your FXML file 'Untitled'.";
        assert studentsButton != null : "fx:id=\"studentsButton\" was not injected: check your FXML file 'Untitled'.";
        assert tableStudent != null : "fx:id=\"tableStudent\" was not injected: check your FXML file 'Untitled'.";
        assert test != null : "fx:id=\"test\" was not injected: check your FXML file 'Untitled'.";

    }

}

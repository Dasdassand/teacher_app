package ru.vsu.app.teacher.controllers.test;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;

public class SendTest {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button back;

    @FXML
    private RadioButton platoon;

    @FXML
    private ComboBox<?> recipient;

    @FXML
    private Button send;

    @FXML
    private RadioButton student;

    @FXML
    private ComboBox<?> testID;

    @FXML
    private ComboBox<?> version;

    @FXML
    void initialize() {
        assert back != null : "fx:id=\"back\" was not injected: check your FXML file 'SendTest.fxml'.";
        assert platoon != null : "fx:id=\"platoon\" was not injected: check your FXML file 'SendTest.fxml'.";
        assert recipient != null : "fx:id=\"recipient\" was not injected: check your FXML file 'SendTest.fxml'.";
        assert send != null : "fx:id=\"send\" was not injected: check your FXML file 'SendTest.fxml'.";
        assert student != null : "fx:id=\"student\" was not injected: check your FXML file 'SendTest.fxml'.";
        assert testID != null : "fx:id=\"testID\" was not injected: check your FXML file 'SendTest.fxml'.";
        assert version != null : "fx:id=\"version\" was not injected: check your FXML file 'SendTest.fxml'.";
    }

    public void setStudents() {
        //Объект Statement + Name
        //ComboBox должен обновляться при поступлении новых подключений
        //Вопрос лишь в том, как реагировать на подключение всего взвода
    }

}

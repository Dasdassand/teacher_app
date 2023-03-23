package ru.vsu.app.teacher.controllers.test;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import ru.vsu.app.teacher.controllers.GlobalMethods;

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
        edit.setOnAction(actionEvent -> GlobalMethods.openWindow(
                        "Редактирование теста",
                        "form/EnterTest.fxml",
                        "form/title.png",
                        edit
                )
        );
        save.setOnAction(actionEvent -> GlobalMethods.openWindow(
                "Сохранение теста",
                "form/SaveTest.fxml",
                "form/title.png",
                save));
        load.setOnAction(actionEvent -> GlobalMethods.openWindow(
                "Загрузка теста",
                "form/LoadTest.fxml",
                "form/title.png",
                save));
        remove.setOnAction(actionEvent -> GlobalMethods.openWindow(
                "Удаление теста",
                "form/RemoveTest.fxml",
                "form/title.png",
                remove));
        back.setOnAction(actionEvent -> GlobalMethods.openWindow(
                "Работа с тестом",
                "form/Test.fxml",
                "form/title.png", back));
    }

}

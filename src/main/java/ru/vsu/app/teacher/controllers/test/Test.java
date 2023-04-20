package ru.vsu.app.teacher.controllers.test;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ru.vsu.app.teacher.HelloApplication;
import ru.vsu.app.teacher.controllers.GlobalMethods;
import ru.vsu.app.teacher.tempory.TMPData;

import static ru.vsu.app.teacher.controllers.GlobalMethods.openWindow;

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
        back.setOnAction(actionEvent -> openWindow(
                "Главное окно",
                "form/MainWindow.fxml",
                "form/title.png", back));
        send.setOnAction(actionEvent -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setController(TMPData.sendTest);
                fxmlLoader.setLocation(Objects.requireNonNull(HelloApplication.class.getResource("form/SendTest.fxml")));
                Scene scene = new Scene(fxmlLoader.load(), -1, -1);
                Stage stage = new Stage();
                stage.getIcons().add(new Image(Objects.requireNonNull(HelloApplication.class.getResourceAsStream("form/title.png"))));
                stage.setTitle("Отправка теста");
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = (Stage) send.getScene().getWindow();
            stage.close();
        });
    }

}

package ru.vsu.app.teacher.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import ru.vsu.app.teacher.repository.TeacherRepository;
import ru.vsu.app.teacher.tempory.TMPData;
import static ru.vsu.app.teacher.controllers.GlobalMethods.*;
import java.sql.SQLException;

public class Authorization {
    private final TeacherRepository repository = new TeacherRepository();

    @FXML
    private Button auth;

    @FXML
    private TextField login;

    @FXML
    private TextField password;

    @FXML
    void initialize(){
        login.setOnMouseClicked(mouseEvent -> login.setText(""));
        password.setOnMouseClicked(mouseEvent -> password.setText(""));
        auth.setOnAction(actionEvent -> {
            try {
                if (repository.auth(login.getText(), password.getText()) != -1){
                    TMPData.teacherID = repository.auth(login.getText(), password.getText());
                    repository.close();
                    openWindow("Главное окно","form/MainWindow.fxml","form/title.png",auth);
                }else {
                generateAlert("Введены неверные данные или заполнены не все поля", Alert.AlertType.ERROR);
                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

}

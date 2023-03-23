package ru.vsu.app.teacher.controllers.test;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import ru.vsu.app.teacher.controllers.GlobalMethods;
import ru.vsu.app.teacher.repository.TeacherRepository;
import ru.vsu.app.teacher.tempory.TMPData;

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
    private ChoiceBox<String> testID;

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        assert RemoveButton != null : "fx:id=\"RemoveButton\" was not injected: check your FXML file 'RemoveTest.fxml'.";
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'RemoveTest.fxml'.";
        assert password != null : "fx:id=\"password\" was not injected: check your FXML file 'RemoveTest.fxml'.";
        assert testID != null : "fx:id=\"testID\" was not injected: check your FXML file 'RemoveTest.fxml'.";
        TeacherRepository repository = new TeacherRepository();
        var res = repository.getResultSet("Select id from test");
        while (res.next()) {
            testID.getItems().add(res.getString(1));
        }
        password.setText("Введите пароль");
        testID.setValue("Выберите тест");
        password.setOnMouseClicked(action -> password.setText(""));
        backButton.setOnAction(actionEvent -> GlobalMethods.openWindow("Работа с тестом",
                "form/Test.fxml", "form/title.png", backButton));
        RemoveButton.setOnAction(actionEvent -> {
            if (!testID.getValue().equals("Выберите тест")) {
                if (!password.getText().equals("Введите пароль")) {
                    try {
                        if (repository.auth(String.valueOf(TMPData.teacherID), password.getText()) != -1) {
                            repository.addValue("Delete from test where id = '" + testID.getValue() + "';");
                            GlobalMethods.openWindow("Работа с тестом", "form/Test.fxml", "form/title.png", RemoveButton);
                        } else GlobalMethods.generateAlert("Неверный пароль", Alert.AlertType.ERROR);
                    } catch (SQLException | ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                } else GlobalMethods.generateAlert("Пароль не введён", Alert.AlertType.WARNING);
            } else {
                GlobalMethods.generateAlert("Тест не выбран", Alert.AlertType.WARNING);
            }
        });
    }


}

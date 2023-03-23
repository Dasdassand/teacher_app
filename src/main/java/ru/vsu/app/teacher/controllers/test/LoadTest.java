package ru.vsu.app.teacher.controllers.test;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import ru.vsu.app.teacher.controllers.GlobalMethods;
import ru.vsu.app.teacher.file.FileReader;
import ru.vsu.app.teacher.repository.TeacherRepository;
import ru.vsu.app.teacher.tempory.TMPData;

public class LoadTest {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button loadButton;

    @FXML
    private Button pathButton;
    private FileReader.Test test = null;

    @FXML
    void initialize() {
        assert loadButton != null : "fx:id=\"loadButton\" was not injected: check your FXML file 'Untitled'.";
        assert pathButton != null : "fx:id=\"pathButton\" was not injected: check your FXML file 'Untitled'.";
        pathButton.setOnAction(actionEvent -> {
            FileReader reader = new FileReader();
            try {
                test = reader.read();
                pathButton.setDisable(true);
            } catch (IOException e) {
                test = new FileReader.Test("1", 1, "", 1);
                GlobalMethods.generateAlert("Ошибка файла", Alert.AlertType.ERROR);
                throw new RuntimeException(e);
            }
        });
        loadButton.setOnAction(actionEvent -> {
            if (!(test == null)) {
                TeacherRepository repository = new TeacherRepository();
                try {
                    var resultSet = repository.getResultSet("Select time from test Where id = '" + test.getId() + "';");
                    if (resultSet.next()) {
                        repository.addValue("UPDATE test SET test = '" + test.getTest() + "' WHERE id = '" + test.getId() + "';");
                    } else {
                        repository.addValue("INSERT INTO test(ID, VERSION, TEST, TIME) value (" + "'" +
                                test.getId() + "'" + "," + test.getVersion() + "," + "'" + test.getTest() + "'" +
                                "," + test.getTime() +
                                ");");
                    }
                    GlobalMethods.openWindow("Работа с тестом", "form/Test.fxml", "form/title.png", loadButton);
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            } else {
                GlobalMethods.generateAlert("Тест не выбран", Alert.AlertType.WARNING);
            }
        });

    }

}

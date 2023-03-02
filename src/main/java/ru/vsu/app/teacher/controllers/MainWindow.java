package ru.vsu.app.teacher.controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import ru.vsu.app.teacher.repository.TeacherRepository;
import ru.vsu.app.teacher.tempory.TMPData;

public class MainWindow {
    private final TeacherRepository repository = new TeacherRepository();
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button platoonButton;

    @FXML
    private ChoiceBox<String> platoons;

    @FXML
    private Button studentsButton;

    @FXML
    private Button test;

    @FXML
    private TextArea textStudent;

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        assert platoonButton != null : "fx:id=\"platoonButton\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert platoons != null : "fx:id=\"platoons\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert studentsButton != null : "fx:id=\"studentsButton\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert test != null : "fx:id=\"test\" was not injected: check your FXML file 'MainWindow.fxml'.";
        var pl = repository.getResultSet("SELECT * FROM platoon_teacher WHERE teacher_id = " + TMPData.teacherID + ";");
        List<Integer> platoonIDList = new ArrayList<>();
        while (pl.next()) {
            platoonIDList.add(pl.getInt(1));
        }
        List<String> platoonName = new ArrayList<>();
        for (Integer id :
                platoonIDList) {
            pl = repository.getResultSet("SELECT platoon FROM platoon WHERE id = " + id + ";");
            pl.next();
            platoonName.add(pl.getString(1));
        }
        pl.close();
        platoons.getItems().addAll(platoonName);
        platoons.setOnAction(actionEvent -> {
            String student = "";
            try {
               var pli = repository.getResultSet("Select id From platoon Where platoon = " + platoons.getValue() + ";");
                pli.next();
                int tmpID = pli.getInt(1);
                pli = repository.getResultSet("Select name from student where platoon_id = " + tmpID +";");
                while (pli.next()){
                    student += pli.getString(1) + '\n';
                }
                textStudent.setText(student);
                pli.close();
                TMPData.platoonID = tmpID;
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        test.setOnAction(actionEvent -> {
            if (checkChoice()){
                GlobalMethods.openWindow("Работа с тестом","form/Test.fxml","form/title.png", test);
            }else {
                GlobalMethods.generateAlert("Не выбран взвод", Alert.AlertType.ERROR);
            }
        });

        studentsButton.setOnAction(actionEvent -> {
            if (checkChoice()){
                GlobalMethods.openWindow("Просмотр успеваемости","form/UniversalForm.fxml","form/title.png", studentsButton);
                TMPData.flagUniversal = true;
            }else {
                GlobalMethods.generateAlert("Не выбран взвод", Alert.AlertType.ERROR);
            }
        });
        platoonButton.setOnAction(actionEvent -> {
            if (checkChoice()){
                GlobalMethods.openWindow("Просмотр успеваемости","form/UniversalForm.fxml","form/title.png", platoonButton);
                TMPData.flagUniversal = false;
            }else {
                GlobalMethods.generateAlert("Не выбран взвод", Alert.AlertType.ERROR);
            }
        });
    }
    private boolean checkChoice(){
        return !(platoons.getValue() == null);
    }

}
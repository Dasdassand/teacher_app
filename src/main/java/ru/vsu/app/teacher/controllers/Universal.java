package ru.vsu.app.teacher.controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import lombok.Data;
import ru.vsu.app.teacher.entity.Student;
import ru.vsu.app.teacher.repository.TeacherRepository;
import ru.vsu.app.teacher.tempory.TMPData;

public class Universal {
    private final TeacherRepository repository = new TeacherRepository();
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea area;

    @FXML
    private Button back;

    @FXML
    private ChoiceBox<String> box;

    @FXML
    private Label label;

    @FXML
    private Button save;

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        assert area != null : "fx:id=\"area\" was not injected: check your FXML file 'UniversalForm.fxml'.";
        assert back != null : "fx:id=\"back\" was not injected: check your FXML file 'UniversalForm.fxml'.";
        assert box != null : "fx:id=\"box\" was not injected: check your FXML file 'UniversalForm.fxml'.";
        assert label != null : "fx:id=\"label\" was not injected: check your FXML file 'UniversalForm.fxml'.";
        assert save != null : "fx:id=\"save\" was not injected: check your FXML file 'UniversalForm.fxml'.";
        if (TMPData.flagUniversal) {
            List<Student> students = new ArrayList<>();
            label.setText("Выберите студента");
            var pl = repository.getResultSet("Select id,name from student where platoon_id = " + TMPData.platoonID + ";");
            while (pl.next()) {
                students.add(new Student(pl.getInt(1), pl.getString(2), TMPData.platoonID));
            }
            pl.close();
            int count = 0;
            for (Student s :
                    students) {
                box.getItems().add(count + " - " + s.getName());
                count++;
            }

            box.setOnAction(actionEvent -> {
                try {
                    var pli = repository.getResultSet("Select test_id from result where student_id = " +
                            students.get(Integer.parseInt(box.getValue().split(" ")[0])).getId() + ";"
                    );
                    @Data
                    class Test {
                        private final String id;
                        private final String name;
                    }
                    List<String> testID = new ArrayList<>();
                    while (pli.next()) {
                        testID.add(pli.getString(1));
                    }
                    List<Test> tests = new ArrayList<>();
                    for (String s :
                            testID) {
                        pli = repository.getResultSet("Select name from test where id = " + s + ";");
                        pli.next();
                        tests.add(new Test(s, pli.getString(1)));
                    }

                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }

            });
        }
    }

}

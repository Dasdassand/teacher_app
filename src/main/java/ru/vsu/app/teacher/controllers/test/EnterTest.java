package ru.vsu.app.teacher.controllers.test;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import ru.vsu.app.teacher.controllers.GlobalMethods;
import ru.vsu.app.teacher.entity.Quest;
import ru.vsu.app.teacher.repository.TeacherRepository;
import ru.vsu.app.teacher.tempory.TMPData;

public class EnterTest {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button accept;

    @FXML
    private ComboBox<String> test;

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        assert accept != null : "fx:id=\"accept\" was not injected: check your FXML file 'EnterTest.fxml'.";
        assert test != null : "fx:id=\"test\" was not injected: check your FXML file 'EnterTest.fxml'.";
        TeacherRepository repository = new TeacherRepository();
        var res = repository.getResultSet("Select id from test");
        while (res.next()) {
            test.getItems().add(res.getString(1));
        }
        test.setValue("Выберите тест");
        accept.setOnAction(actionEvent -> {
            if (Objects.equals(test.getValue(), "Выберите тест")) {
                GlobalMethods.generateAlert("Тест не выбран", Alert.AlertType.ERROR);
            } else {
                try {
                    ObjectMapper mapper = new ObjectMapper();
                    String[] tmp = new String[4];
                    var testF = repository.getResultSet("Select * from test Where id = " + "'" + test.getValue() + "'");
                    while (testF.next()) {
                        tmp[0] = testF.getString(1);
                        tmp[1] = testF.getString(2);
                        tmp[2] = testF.getString(3);
                        tmp[3] = testF.getString(4);
                    }
                    var quests = mapper.readValue(tmp[2], new TypeReference<List<List<Quest>>>() {
                    });
                    TMPData.flagFix = true;
                    TMPData.quests = quests;
                    TMPData.version = Integer.parseInt(tmp[1]);
                    TMPData.time = Integer.parseInt(tmp[3]);
                    TMPData.testID = tmp[0];
                    TMPData.count = quests.get(0).size();
                    System.out.println(TMPData.String());
                    GlobalMethods.openWindow(
                            "Редактирование теста",
                            "form/TestCreatedFinal.fxml",
                            "form/title.png",
                            accept
                    );
                } catch (SQLException | ClassNotFoundException | JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

}



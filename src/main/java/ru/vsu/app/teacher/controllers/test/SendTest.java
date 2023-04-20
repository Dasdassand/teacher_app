package ru.vsu.app.teacher.controllers.test;

import java.lang.reflect.Type;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import ru.vsu.app.teacher.controllers.GlobalMethods;
import ru.vsu.app.teacher.controllers.test.entity.TestSend;
import ru.vsu.app.teacher.entity.Quest;
import ru.vsu.app.teacher.server.MainHandler;
import ru.vsu.app.teacher.server.MainHandler.PersonChanel;
import ru.vsu.app.teacher.repository.TeacherRepository;
import ru.vsu.app.teacher.server.ServerApp;
import ru.vsu.app.teacher.tempory.TMPData;

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
    private ComboBox<String> recipient;

    @FXML
    private Button send;

    @FXML
    private RadioButton student;

    @FXML
    private ComboBox<String> testID;

    @FXML
    private ComboBox<Integer> version;
    private Random random = new Random();

    @FXML
    void initialize() {
        TMPData.app = new ServerApp();
        assert back != null : "fx:id=\"back\" was not injected: check your FXML file 'SendTest.fxml'.";
        assert platoon != null : "fx:id=\"platoon\" was not injected: check your FXML file 'SendTest.fxml'.";
        assert recipient != null : "fx:id=\"recipient\" was not injected: check your FXML file 'SendTest.fxml'.";
        assert send != null : "fx:id=\"send\" was not injected: check your FXML file 'SendTest.fxml'.";
        assert student != null : "fx:id=\"student\" was not injected: check your FXML file 'SendTest.fxml'.";
        assert testID != null : "fx:id=\"testID\" was not injected: check your FXML file 'SendTest.fxml'.";
        assert version != null : "fx:id=\"version\" was not injected: check your FXML file 'SendTest.fxml'.";
        testID.getItems().add("-");
        testID.setValue("-");
        recipient.getItems().add("-");
        recipient.setValue("-");
        version.getItems().add(-1);
        version.setValue(-1);
        platoon.setOnMouseClicked(action -> student.setSelected(false));
        student.setOnMouseClicked(action -> platoon.setSelected(false));
        TeacherRepository repository = new TeacherRepository();
        List<TestSend> test = new ArrayList<>();
        List<String> testBody = new ArrayList<>();
        try {
            var tests = repository.getResultSet("Select * From test");
            while (tests.next()) {
                testID.getItems().add(tests.getString(1));
                version.getItems().add(tests.getInt(2));
                testBody.add(tests.getString(3));
                test.add(new TestSend(tests.getString(1), tests.getString(2), tests.getString(4)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ObjectMapper mapper = new ObjectMapper();
        List<List<List<Quest>>> testsFinal = new ArrayList<>();
        var typeFactory = mapper.getTypeFactory();
        try {
            for (int i = 0; i < testBody.size(); i++) {
                test.get(i).setQuest(mapper.readValue(testBody.get(i), new TypeReference<List<List<Quest>>>() {
                }));
            }
            System.out.println(test.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }


        send.setOnAction(actionEvent -> {
            if (check()) {
                if (student.isSelected()) {
                    int index = 0;
                    for (int i = 0; i < test.size(); i++) {
                        if (test.get(i).getId().equals(testID.getValue()))
                            index = i;
                    }
                    try {
                        String s1 = "Select version From student_test where student_id = "
                                + recipient.getValue().split(" ")[0] + " and test_id = '" + testID.getValue().toString() + "';";
                        System.out.println(s1);
                        var ch = repository.getResultSet(s1);
                        List<Integer> ver = new ArrayList<>();
                        while (ch.next()) {
                            ver.add(ch.getInt(1));
                        }
                        if (ver.size() == 0) {
                            int var = random.nextInt(Integer.parseInt(test.get(index).getVersion()) - 1);
                            MainHandler.sendTest(recipient.getValue(), test.get(index), var);
                            String s = "INSERT INTO student_test(student_id, test_id, result, version)" + " VALUE (" + recipient.getValue().split(" ")[0] + "," + "'" + testID.getValue() + "'," + 0 + "," + var + 1 + ");";
                            repository.addValue(s);
                        }else {
                           if (ver.size() == Integer.parseInt(test.get(index).getVersion())){
                               GlobalMethods.generateAlert("Данный студент прошёл все варианты теста", Alert.AlertType.ERROR);
                           }else {
                               int tmp = -1;
                               for (int i = 0; i < ver.size(); i++) {
                                   if (ver.get(i) == i){
                                       tmp = i;
                                   }
                               }
                               if (tmp == -1){
                                   tmp =  Integer.parseInt(test.get(index).getVersion());
                               }
                               MainHandler.sendTest(recipient.getValue(), test.get(index), tmp);
                               String s = "INSERT INTO student_test(student_id, test_id, result, version)" + " VALUE (" + recipient.getValue().split(" ")[0] + "," + "'" + testID.getValue() + "'," + 0 + "," + tmp  + ");";
                               repository.addValue(s);

                           }
                        }
                    } catch (SQLException | ClassNotFoundException | JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                }







                if (platoon.isSelected()) {







                }


            } else {
               GlobalMethods.generateAlert("Введены не все значения", Alert.AlertType.ERROR);
            }
        });

    }

    public void setStudents(PersonChanel personChanel, boolean flag) {
        if (flag) {
            recipient.getItems().add(personChanel.getId() + " " + personChanel.getName());
        } else {
            recipient.getItems().remove(personChanel.getId() + " " + personChanel.getName());
        }
    }

    private boolean check() {
        return (student.isSelected() || platoon.isSelected())
                && !testID.getValue().equals("-")
                && (!recipient.getValue().equals("-") || platoon.isSelected())
                && !version.getValue().equals(-1);
    }


}

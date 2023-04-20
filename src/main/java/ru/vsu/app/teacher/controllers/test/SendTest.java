package ru.vsu.app.teacher.controllers.test;

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

    private final Random random = new Random();

    @FXML
    void initialize() {
        TMPData.app = new ServerApp();
        assert back != null : "fx:id=\"back\" was not injected: check your FXML file 'SendTest.fxml'.";
        assert platoon != null : "fx:id=\"platoon\" was not injected: check your FXML file 'SendTest.fxml'.";
        assert recipient != null : "fx:id=\"recipient\" was not injected: check your FXML file 'SendTest.fxml'.";
        assert send != null : "fx:id=\"send\" was not injected: check your FXML file 'SendTest.fxml'.";
        assert student != null : "fx:id=\"student\" was not injected: check your FXML file 'SendTest.fxml'.";
        assert testID != null : "fx:id=\"testID\" was not injected: check your FXML file 'SendTest.fxml'.";
        testID.getItems().add("-");
        testID.setValue("-");
        recipient.getItems().add("-");
        recipient.setValue("-");
        platoon.setOnMouseClicked(action -> student.setSelected(false));
        student.setOnMouseClicked(action -> platoon.setSelected(false));
        TeacherRepository repository = new TeacherRepository();
        List<TestSend> test = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            var tests = repository.getResultSet("Select * From test");
            while (tests.next()) {
                test.add(new TestSend(tests.getString(1), mapper.readValue(tests.getString(3), new TypeReference<List<List<Quest>>>() {
                }), tests.getString(2), tests.getString(4)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (TestSend t :
                test) {
            testID.getItems().add(t.getId());
        }


        send.setOnAction(actionEvent -> {
            if (check()) {
                if (student.isSelected()) {
                    TestSend actualTest = null;
                    for (TestSend t :
                            test) {
                        if (t.getId().equals(testID.getValue()))
                            actualTest = t;
                    }
                    assert actualTest != null;
                    try {
                        var lockedVarBD = repository.getResultSet("Select version From student_test where student_id = "
                                + recipient.getValue().split(" ")[0]
                                + " and test id = '" + actualTest.getId() + "';"
                        );
                        var lockedVar = new ArrayList<Integer>();
                        while (lockedVarBD.next()) {
                            lockedVar.add(lockedVarBD.getInt(1));
                        }
                        if (lockedVar.size() == 0) {
                            int var = random.nextInt(Integer.parseInt(actualTest.getVersion()) - 1);
                            MainHandler.sendTest(recipient.getValue(), actualTest, var);
                            String s = "INSERT INTO student_test(student_id, test_id, result, version)" + " VALUE (" + recipient.getValue().split(" ")[0]
                                    + "," + "'" + testID.getValue() + "'," + 0 + "," + var + ");";
                            repository.addValue(s);
                        } else {
                            if (lockedVar.size() == Integer.parseInt(actualTest.getVersion())) {
                                GlobalMethods.generateAlert("Данный студент прошёл все варианты теста - "
                                        + recipient.getValue().split(" ")[1], Alert.AlertType.ERROR);
                            } else {
                                send(repository, actualTest, lockedVar);

                            }
                        }
                    } catch (SQLException | ClassNotFoundException | JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                }

                if (platoon.isSelected()) {
                    try {
                        var student_id = repository.getResultSet("Select id from student where platoon_id = " + TMPData.platoonID);
                        List<String> students = new ArrayList<>();
                        while (student_id.next()) {
                            students.add(student_id.getString(1));
                        }
                        TestSend actualTest = null;

                        for (TestSend t :
                                test) {
                            if (t.getId().equals(testID.getValue()))
                                actualTest = t;
                        }
                        assert actualTest != null;
                        for (String s :
                                students) {
                            var lockedVarBD = repository.getResultSet("Select version From student_test where student_id = "
                                    + s
                                    + " and test id = '" + actualTest.getId() + "';");
                            var lockedVar = new ArrayList<Integer>();
                            while (lockedVarBD.next()) {
                                lockedVar.add(lockedVarBD.getInt(1));
                            }
                            send(repository, actualTest, lockedVar);
                        }
                    } catch (SQLException | ClassNotFoundException | JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }

                }


            } else {
                GlobalMethods.generateAlert("Введены не все значения", Alert.AlertType.ERROR);
            }
        });

    }

    private void send(TeacherRepository repository, TestSend actualTest, ArrayList<Integer> lockedVar) throws JsonProcessingException, SQLException, ClassNotFoundException {
        int var = -1;
        for (int i = 0; i < Integer.parseInt(actualTest.getVersion()); i++) {
            for (Integer j :
                    lockedVar) {
                if (j != i) {
                    var = i;
                    break;
                }
            }
        }
        MainHandler.sendTest(recipient.getValue(), actualTest, var);
        String z = "INSERT INTO student_test(student_id, test_id, result, version)" + " VALUE (" + recipient.getValue().split(" ")[0]
                + "," + "'" + testID.getValue() + "'," + 0 + "," + var + ");";
        repository.addValue(z);
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
                && (!recipient.getValue().equals("-") || platoon.isSelected());
    }
}

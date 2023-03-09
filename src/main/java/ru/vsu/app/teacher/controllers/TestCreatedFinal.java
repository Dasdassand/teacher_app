package ru.vsu.app.teacher.controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ru.vsu.app.teacher.entity.Quest;
import ru.vsu.app.teacher.entity.Test;
import ru.vsu.app.teacher.repository.TeacherRepository;
import ru.vsu.app.teacher.tempory.TMPData;

import static ru.vsu.app.teacher.controllers.GlobalMethods.openWindow;

public class TestCreatedFinal {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button accept;

    @FXML
    private ChoiceBox<Integer> count;

    @FXML
    private CheckBox four;

    @FXML
    private TextField fourAnswer;

    @FXML
    private CheckBox one;

    @FXML
    private TextField oneAnswer;

    @FXML
    private TextArea quest;

    @FXML
    private CheckBox three;

    @FXML
    private TextField threeAnswer;

    @FXML
    private CheckBox two;

    @FXML
    private TextField twoAnswer;
    private Integer countVar = -1;
    private Integer countVers = 0;

    @FXML
    void initialize() {
        assert accept != null : "fx:id=\"accept\" was not injected: check your FXML file 'Untitled'.";
        assert count != null : "fx:id=\"count\" was not injected: check your FXML file 'Untitled'.";
        assert four != null : "fx:id=\"four\" was not injected: check your FXML file 'Untitled'.";
        assert fourAnswer != null : "fx:id=\"fourAnswer\" was not injected: check your FXML file 'Untitled'.";
        assert one != null : "fx:id=\"one\" was not injected: check your FXML file 'Untitled'.";
        assert oneAnswer != null : "fx:id=\"oneAnswer\" was not injected: check your FXML file 'Untitled'.";
        assert quest != null : "fx:id=\"quest\" was not injected: check your FXML file 'Untitled'.";
        assert three != null : "fx:id=\"three\" was not injected: check your FXML file 'Untitled'.";
        assert threeAnswer != null : "fx:id=\"threeAnswer\" was not injected: check your FXML file 'Untitled'.";
        assert two != null : "fx:id=\"two\" was not injected: check your FXML file 'Untitled'.";
        assert twoAnswer != null : "fx:id=\"twoAnswer\" was not injected: check your FXML file 'Untitled'.";


        AtomicInteger tmp = new AtomicInteger(0);
        quest.setOnMouseClicked(event -> {
            if (tmp.get() == 0) {
                quest.setText("");
                tmp.set(1);
            }
        });
        Test test = new Test();
        for (int i = 0; i < TMPData.count; i++) {
            count.getItems().add(i + 1);
        }
        accept.setOnAction(actionEvent -> {
            if (countVers != TMPData.version) {
                List<Quest> quests = new ArrayList<>();
                if (countVar == -1) {
                    inz(quests);
                    countVar++;
                }
                if (check() && checkBox()) {
                    if (quests.get(count.getValue()).getQuest() != null) {
                        editQuest(quests);
                    } else {
                        if (countVar == TMPData.count - 1) {
                            GlobalMethods.generateAlert("После подтверждения этого вопроса вы перейдёте " +
                                    "к созданию следующего варианта", Alert.AlertType.INFORMATION);
                            editQuest(quests);
                            test.getQuests().add(quests);
                            quests = new ArrayList<>();
                            inz(quests);
                            countVar = 0;
                            countVers++;
                        } else {
                            editQuest(quests);
                            countVar++;
                        }
                    }
                } else
                    GlobalMethods.generateAlert("Введены не все значения", Alert.AlertType.ERROR);
            } else {
                TeacherRepository repository = new TeacherRepository();
                try {
                    ObjectMapper mapper = new ObjectMapper();
                    repository.addValue("INSERT INTO test(ID, VERSION, TEST, TIME) value (" + "'" +
                            test.getId() + "'" + "," + TMPData.version + "," + "'" + mapper.writeValueAsString(test) + "'" +
                            "," + TMPData.time +
                            ");");
                } catch (SQLException | ClassNotFoundException | JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
                openWindow("Главное окно", "form/MainWindow.fxml", "form/title.png", accept);
            }
        });
    }

    private void editQuest(List<Quest> quests) {
        var tmpQuest = quests.get(count.getValue() - 1);
        tmpQuest.setQuest(quest.getText());
        List<Quest.Answer> answers = new ArrayList<>();
        answers.add(new Quest.Answer(oneAnswer.getText(), one.isSelected()));
        answers.add(new Quest.Answer(twoAnswer.getText(), two.isSelected()));
        answers.add(new Quest.Answer(threeAnswer.getText(), three.isSelected()));
        answers.add(new Quest.Answer(fourAnswer.getText(), four.isSelected()));
        tmpQuest.setAnswer(answers);
    }

    private boolean check() {
        return !Objects.equals(quest.getText(), "") && oneAnswer.getText() != null && twoAnswer.getText() != null
                && threeAnswer.getText() != null && fourAnswer.getText() != null;
    }

    private boolean checkBox() {
        return one.isSelected() || two.isSelected() || three.isSelected() || four.isSelected();
    }

    private void inz(List<Quest> quests) {
        for (int i = 0; i < TMPData.count; i++) {
            quests.add(new Quest());
            quests.get(i).setId(i);
        }
    }
}

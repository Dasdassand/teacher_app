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
    private Integer countVar = 0;
    private Integer countVers = 0;
    private final List<List<Quest>> quests = new ArrayList<>();


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

/**
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
 List<Quest> quests = new ArrayList<>();
 accept.setOnAction(actionEvent -> {
 if (countVers != TMPData.version) {

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
 */
        clear();
        for (int i = 0; i < TMPData.count; i++) {
            count.getItems().add(i + 1);
        }


        count.setOnAction(actionEvent -> checkQuest());


        quests.add(new ArrayList<>());
        inz(quests.get(countVers));

        count.setValue(1);

        accept.setOnAction(actionEvent -> {
            if (countVers < TMPData.version && countVar < TMPData.count - 1){
                if (check()){
                    editQuest(quests.get(countVers));
                    countVar++;
                    clear();
                }else {
                    GlobalMethods.generateAlert("Заполнены не все поля", Alert.AlertType.ERROR);
                }
            }else {
                if (countVers != TMPData.version) {
                    countVar = 0;
                    countVers++;
                    quests.add(new ArrayList<>());
                    inz(quests.get(countVers));
                    editQuest(quests.get(countVers));
                    clear();
                }

            }
            if (countVers == TMPData.version) {
                saveTest(quests);
                GlobalMethods.openWindow("Работа с тестом", "form/Test.fxml", "form/title.png", accept);
            }
        });

    }

    private void editQuest(List<Quest> quests) {
        var tmpQuest = quests.get(count.getValue().intValue() - 1);
        tmpQuest.setQuest(quest.getText());
        List<Quest.Answer> answers = new ArrayList<>();
        answers.add(new Quest.Answer(oneAnswer.getText(), one.isSelected()));
        answers.add(new Quest.Answer(twoAnswer.getText(), two.isSelected()));
        answers.add(new Quest.Answer(threeAnswer.getText(), three.isSelected()));
        answers.add(new Quest.Answer(fourAnswer.getText(), four.isSelected()));
        tmpQuest.setAnswer(answers);
        clear();
    }

    private void checkQuest(){
        if (count.getValue() <= countVar) {
            quest.setText(quests.get(countVers).get(countVar).getQuest());
            oneAnswer.setText(quests.get(countVers).get(countVar).getAnswer().get(0).answer());
            twoAnswer.setText(quests.get(countVers).get(countVar).getAnswer().get(1).answer());
            threeAnswer.setText(quests.get(countVers).get(countVar).getAnswer().get(2).answer());
            fourAnswer.setText(quests.get(countVers).get(countVar).getAnswer().get(3).answer());
            one.setSelected(quests.get(countVers).get(countVar).getAnswer().get(0).status());
            two.setSelected(quests.get(countVers).get(countVar).getAnswer().get(1).status());
            three.setSelected(quests.get(countVers).get(countVar).getAnswer().get(2).status());
            four.setSelected(quests.get(countVers).get(countVar).getAnswer().get(3).status());
        } else {
            clear();
        }
    }
    private boolean check() {
        return !Objects.equals(quest.getText(), "")
                && oneAnswer.getText() != null
                && twoAnswer.getText() != null
                && threeAnswer.getText() != null
                && fourAnswer.getText() != null && (
                one.isSelected()
                        || two.isSelected()
                        || three.isSelected()
                        || four.isSelected()
        );
    }


    private void inz(List<Quest> quests) {
        for (int i = 0; i < TMPData.count; i++) {
            quests.add(new Quest());
            quests.get(i).setId(i);
        }
    }

    private void clear() {
        quest.setText("");
        oneAnswer.setText("");
        twoAnswer.setText("");
        threeAnswer.setText("");
        fourAnswer.setText("");
        one.setSelected(false);
        two.setSelected(false);
        three.setSelected(false);
        four.setSelected(false);
    }

    private void saveTest(List<List<Quest>> quests){
        var test = new Test(quests);

    }
}

package ru.vsu.app.teacher.controllers.test;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ru.vsu.app.teacher.controllers.GlobalMethods;
import ru.vsu.app.teacher.entity.Quest;
import ru.vsu.app.teacher.repository.TeacherRepository;
import ru.vsu.app.teacher.tempory.TMPData;
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
    private Integer countQuest = 0;
    private Integer countVersion = 0;
    private List<List<Quest>> quests = new ArrayList<>();


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
        clear();

        if (TMPData.flagFix)
            quests = TMPData.quests;

        for (int i = 0; i < TMPData.count; i++) {
            count.getItems().add(i + 1);
        }
        count.setOnAction(actionEvent -> checkQuest());
        quests.add(new ArrayList<>());
        inz(quests.get(countVersion));
        count.setValue(1);
        accept.setOnAction(actionEvent -> {
            if (countVersion < TMPData.version && countQuest < TMPData.count - 1) {
                if (check()) {
                    editQuest(quests.get(countVersion));
                    countQuest++;
                    clear();
                } else {
                    GlobalMethods.generateAlert("Заполнены не все поля", Alert.AlertType.ERROR);
                }
            } else {
                if (countVersion < TMPData.version) {
                    editQuest(quests.get(countVersion));
                    countQuest = 0;
                    countVersion++;
                    quests.add(new ArrayList<>());
                    inz(quests.get(countVersion));
                    clear();
                }

            }
            if (countVersion == TMPData.version) {
                try {
                    saveTest(quests);
                } catch (JsonProcessingException | SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                GlobalMethods.openWindow("Работа с тестом", "form/Test.fxml", "form/title.png", accept);
            }


            if (count.getValue() == TMPData.count)
                count.setValue(1);
            else
                count.setValue(count.getValue() + 1);
            clear();
        });

    }

    private void editQuest(List<Quest> quests) {
        var tmpQuest = quests.get(count.getValue() - 1);
        tmpQuest.setQuest(quest.getText());
        List<Quest.Answer> answers = new ArrayList<>();
        tmpQuest.setId(countQuest);
        tmpQuest.setQuest(quest.getText());
        answers.add(new Quest.Answer(oneAnswer.getText(), one.isSelected()));
        answers.add(new Quest.Answer(twoAnswer.getText(), two.isSelected()));
        answers.add(new Quest.Answer(threeAnswer.getText(), three.isSelected()));
        answers.add(new Quest.Answer(fourAnswer.getText(), four.isSelected()));
        tmpQuest.setAnswer(answers);
        clear();
    }

    private void checkQuest() {
        if (count.getValue() <= countQuest) {
            quest.setText(quests.get(countVersion).get(countQuest).getQuest());
            oneAnswer.setText(quests.get(countVersion).get(countQuest).getAnswer().get(0).answer());
            twoAnswer.setText(quests.get(countVersion).get(countQuest).getAnswer().get(1).answer());
            threeAnswer.setText(quests.get(countVersion).get(countQuest).getAnswer().get(2).answer());
            fourAnswer.setText(quests.get(countVersion).get(countQuest).getAnswer().get(3).answer());
            one.setSelected(quests.get(countVersion).get(countQuest).getAnswer().get(0).status());
            two.setSelected(quests.get(countVersion).get(countQuest).getAnswer().get(1).status());
            three.setSelected(quests.get(countVersion).get(countQuest).getAnswer().get(2).status());
            four.setSelected(quests.get(countVersion).get(countQuest).getAnswer().get(3).status());
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

    private void saveTest(List<List<Quest>> quests) throws
            JsonProcessingException, SQLException, ClassNotFoundException {
        ObjectMapper mapper = new ObjectMapper();
        TeacherRepository repository = new TeacherRepository();
        var id = mapper.writeValueAsString(UUID.randomUUID());
        quests.remove(quests.size() - 1);
        var testQuest = mapper.writeValueAsString(quests);
        var version = TMPData.version;
        repository.addValue("INSERT INTO test(ID, VERSION, TEST, TIME) value (" + "'" +
                id + "'" + "," + version + "," + "'" + testQuest + "'" +
                "," + TMPData.time +
                ");");
    }
}

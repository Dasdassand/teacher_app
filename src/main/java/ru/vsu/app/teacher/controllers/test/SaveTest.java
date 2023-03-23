package ru.vsu.app.teacher.controllers.test;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import ru.vsu.app.teacher.controllers.GlobalMethods;
import ru.vsu.app.teacher.file.FileWriter;
import ru.vsu.app.teacher.repository.TeacherRepository;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class SaveTest {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button pathButton;

    @FXML
    private Button saveButton;

    @FXML
    private ComboBox<String> testID;
    private String path;

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        assert pathButton != null : "fx:id=\"pathButton\" was not injected: check your FXML file 'Untitled'.";
        assert saveButton != null : "fx:id=\"saveButton\" was not injected: check your FXML file 'Untitled'.";
        assert testID != null : "fx:id=\"testID\" was not injected: check your FXML file 'Untitled'.";
        TeacherRepository repository = new TeacherRepository();
        var res = repository.getResultSet("Select id from test");
        while (res.next()) {
            testID.getItems().add(res.getString(1));
        }
        testID.setValue("Выберите тест");
        pathButton.setOnAction(actionEvent -> {
            AtomicReference<File> dir = new AtomicReference<>();
            DirectoryChooser directoryChooser = new DirectoryChooser();
            configuringDirectoryChooser(directoryChooser);
            dir.set(directoryChooser.showDialog(new Stage()));
            path = dir.get().getAbsolutePath();
            System.out.println(dir.get().getAbsolutePath());
            pathButton.setDisable(true);
        });
        saveButton.setOnAction(actionEvent -> {
            if (!testID.getValue().equals("Выберите тест")) {
                try {
                    var result = repository.getResultSet("Select * from test where id = '" + testID.getValue() + "';");
                    FileWriter fileWriter = new FileWriter();
                    result.next();
                    fileWriter.write(result.getString(1), result.getInt(2), result.getString(3),
                            result.getInt(4), path);
                    GlobalMethods.openWindow("Работа с тестом", "form/Test.fxml", "form/title.png", saveButton);
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }

            } else {
                GlobalMethods.generateAlert("Введены не все значения", Alert.AlertType.WARNING);
            }
        });

    }

    private void configuringDirectoryChooser(DirectoryChooser directoryChooser) {
        directoryChooser.setTitle("Select Some Directories");
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
    }

}

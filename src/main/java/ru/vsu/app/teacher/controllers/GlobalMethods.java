package ru.vsu.app.teacher.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ru.vsu.app.teacher.HelloApplication;

import java.io.IOException;
import java.util.Objects;

public class GlobalMethods {
    public static void generateAlert(String alertMessage, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setContentText(alertMessage);
        alert.showAndWait();
    }

    public static void openWindow(String stageName, String formName, String imageName, Button button) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Objects.requireNonNull(HelloApplication.class.getResource(formName)));
            Scene scene = new Scene(fxmlLoader.load(), -1, -1);
            Stage stage = new Stage();
            stage.getIcons().add(new Image(Objects.requireNonNull(HelloApplication.class.getResourceAsStream(imageName))));
            stage.setTitle(stageName);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
    }
}

package ru.vsu.app.teacher.file;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileReader {
    public Test read() throws IOException {
        java.io.FileReader fileReader = new java.io.FileReader(readFile());
        Scanner scanner = new Scanner(fileReader);
        List<String> res = new ArrayList<>();
        while (scanner.hasNext()) {
            res.add(scanner.next());
        }
        fileReader.close();
        scanner.close();
        return new Test(res.get(0), Integer.parseInt(res.get(1)), res.get(2), Integer.parseInt(res.get(3)));
    }

    public static File readFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Enter file");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        return fileChooser.showOpenDialog(new Stage());
    }

    @Data
    @AllArgsConstructor
    public static class Test {
        private String id;
        private Integer version;
        private String test;
        private Integer time;
    }
}

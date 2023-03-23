package ru.vsu.app.teacher.file;

import java.io.File;
import java.io.IOException;

public class FileWriter {
    public void write(String id, int version, String quest, int time, String path) {
        var resultString = id + "\n" + version + "\n" + quest + "\n" + time;
        try {
            File file = new File(path, id + ".txt");
            var flag = file.createNewFile();
            java.io.FileWriter fileWriter = new java.io.FileWriter(file);
            fileWriter.write(resultString);
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}

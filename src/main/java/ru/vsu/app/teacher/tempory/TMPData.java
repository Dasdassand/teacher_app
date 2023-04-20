package ru.vsu.app.teacher.tempory;

import ru.vsu.app.teacher.controllers.test.SendTest;
import ru.vsu.app.teacher.entity.Quest;
import ru.vsu.app.teacher.server.ServerApp;

import java.util.List;

public class TMPData {
    public static Integer teacherID;
    public static Integer platoonID;
    public static boolean flagUniversal = true;
    public static int count;
    public static int version;
    public static int time;
    public static boolean flagFix = false;
    public static List<List<Quest>> quests;
    public static SendTest sendTest = new SendTest();
    public static ServerApp app;
    public static boolean flagSend = false;

    public static String String() {
        return "TMPData{" +
                "teacherID=" + teacherID +
                ", platoonID=" + platoonID +
                ", flagUniversal=" + flagUniversal +
                ", count=" + count +
                ", version=" + version +
                ", time=" + time +
                ", flagFix=" + flagFix +
                ", quests=" + quests +
                ", testID='" + testID + '\'' +
                '}';
    }

    public static String testID = "";
}

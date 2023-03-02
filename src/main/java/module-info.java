module ru.vsu.app.teacher {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires netty.all;
    requires annotations;
    requires mysql.connector.j;
    requires java.sql;

    opens ru.vsu.app.teacher to javafx.fxml, javafx.graphics;
    opens ru.vsu.app.teacher.controllers to javafx.fxml;
    exports ru.vsu.app.teacher.entity;
    exports ru.vsu.app.teacher.repository;
    exports ru.vsu.app.teacher.controllers;
    exports ru.vsu.app.teacher.file;
    opens ru.vsu.app.teacher.repository to java.sql;

}
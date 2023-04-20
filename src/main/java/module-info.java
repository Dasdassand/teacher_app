module ru.vsu.app.teacher {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires netty.all;
    requires org.jetbrains.annotations;
    requires mysql.connector.j;
    requires java.sql;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires spring.core;

    opens ru.vsu.app.teacher to javafx.fxml, javafx.graphics;
    opens ru.vsu.app.teacher.controllers to javafx.fxml;
    exports ru.vsu.app.teacher.entity;
    exports ru.vsu.app.teacher.repository;
    exports ru.vsu.app.teacher.controllers;
    exports ru.vsu.app.teacher.file;
    exports ru.vsu.app.teacher.server;
    opens ru.vsu.app.teacher.repository to java.sql;
    exports ru.vsu.app.teacher.controllers.test;
    opens ru.vsu.app.teacher.controllers.test to javafx.fxml;
    opens ru.vsu.app.teacher.entity to com.fasterxml.jackson.databind;
}
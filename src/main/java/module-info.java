module ru.vsu.app.teacher {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires netty.all;
    requires annotations;

    opens ru.vsu.app.teacher to javafx.fxml;
    exports ru.vsu.app.teacher;
}
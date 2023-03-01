package ru.vsu.app.teacher.network;

import lombok.Data;

import org.jetbrains.annotations.NotNull;
import ru.vsu.app.teacher.entity.Student;

@Data
public class PersonChanel {
    private final Student student;
    private final String channel;

    public PersonChanel(@NotNull Student student, @NotNull String channel) {
        this.student = student;
        this.channel = channel;
    }
}

package ru.vsu.app.teacher.entity;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Data
@NotNull
public class Quest {
    private final int id;
    private final List<Answer> answer = new ArrayList<>();
    private final String quest;

    record Answer(String answer, boolean status) {
    }
}



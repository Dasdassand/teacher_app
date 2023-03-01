package ru.vsu.app.teacher.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Quest {
    private final int id;
    private final List<Answer> answer = new ArrayList<>();
    private final String quest;

    record Answer(String answer, boolean status) {
    }
}



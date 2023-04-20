package ru.vsu.app.teacher.controllers.test.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import ru.vsu.app.teacher.entity.Quest;

import java.util.List;

@Data
@RequiredArgsConstructor
@ToString
public class TestSend {
    private final String id;
    private final List<List<Quest>> quest;
    private final String version;
    private final String time;
}

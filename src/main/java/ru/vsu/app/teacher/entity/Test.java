package ru.vsu.app.teacher.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class Test {
private final List<Quest> quests = new ArrayList<>();
}

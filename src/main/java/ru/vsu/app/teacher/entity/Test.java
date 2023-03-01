package ru.vsu.app.teacher.entity;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
@Data
@NotNull
public class Test {
private final List<Quest> quests = new ArrayList<>();
}

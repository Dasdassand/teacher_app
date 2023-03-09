package ru.vsu.app.teacher.entity;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NotNull
public class Test {
    private final UUID id = UUID.randomUUID();
    private final List<List<Quest>> quests = new ArrayList<>();
}

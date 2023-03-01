package ru.vsu.app.teacher.entity;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Data
@NotNull
public class Teacher {
    private final int id;
    private final String name;
    private final List<Platoon> platoons = new ArrayList<>();
}

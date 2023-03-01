package ru.vsu.app.teacher.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Teacher {
    private final int id;
    private final String name;
    private final List<Platoon> platoons = new ArrayList<>();
}

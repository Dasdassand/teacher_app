package ru.vsu.app.teacher.entity;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.List;
@Data
@NotNull
public class Platoon {
    private final int id;
    private final int teacherID;
    private final int name;
    private final List<Student> students;
}

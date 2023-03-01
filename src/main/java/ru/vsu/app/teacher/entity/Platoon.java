package ru.vsu.app.teacher.entity;

import lombok.Data;

import java.util.List;
@Data
public class Platoon {
    private final int id;
    private final int teacherID;
    private final int name;
    private final List<Student> students;
}

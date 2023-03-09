package ru.vsu.app.teacher.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Quest {
    private int id;
    private List<Answer> answer = new ArrayList<>();
    private String quest;

   public record Answer(String answer, boolean status) {
    }
}



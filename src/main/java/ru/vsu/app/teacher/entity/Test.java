package ru.vsu.app.teacher.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

@Data
@NotNull
@RequiredArgsConstructor
public class Test {
    private UUID id = UUID.randomUUID();
    private final List<List<Quest>> quests;

}

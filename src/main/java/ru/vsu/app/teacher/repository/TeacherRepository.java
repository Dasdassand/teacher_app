package ru.vsu.app.teacher.repository;


import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;


public class TeacherRepository extends BaseRepository {

    public void addValue(@NotNull String... value) throws SQLException {

        super.addValue("INSERT INTO Teacher(name, password) VALUE ('" + value[0] + "', '" + value[1] + "');");
    }

    public boolean auth(@NotNull String... value) throws SQLException {
       @NotNull var res = super.getResultSet(
                "Select * From Teacher Where name = " + "'" + value[0] + "'"
                        + "AND password = " + "'" + value[1] + "'"
        );
       return res.next();
    }
}

package ru.vsu.app.teacher.repository;


import org.jetbrains.annotations.NotNull;
import java.sql.SQLException;


public class TeacherRepository extends BaseRepository {

    public void addValue(@NotNull String[] value) throws SQLException, ClassNotFoundException {

        super.addValue("INSERT INTO Teacher(name, password) VALUE ('" + value[0] + "', '" + value[1] + "');");
    }

    public Integer auth(@NotNull String... value) throws SQLException, ClassNotFoundException {
       @NotNull var res = super.getResultSet(
                "Select id From teacher Where name = " + "'" + value[0] + "'"
                        + "AND password = " + "'" + value[1] + "'"
        );
       return res.next() ? res.getInt(1) : -1 ;
    }
}

package ru.vsu.app.teacher.repository;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

 abstract class BaseRepository {
    {
        properties = new Properties();
        FileInputStream fis;
        try {
            fis = new FileInputStream("src/main/resources/ru/vsu/app/teacher/properties/application.properties");
            this.properties.load(fis);
            fis.close();
        } catch (IOException e) {
            System.err.println("ОШИБКА: Файл свойств отсуствует!");
        }

    }
    private Connection connection;
    private Statement statement;
    private final Properties properties;


    private void openSession() throws SQLException {
        connection = DriverManager.getConnection(properties.getProperty("url_db"), properties.getProperty("name"),
                properties.getProperty("password"));
        statement = connection.createStatement();
    }

    public ResultSet getResultSet(String query) throws SQLException {
        openSession();
        return statement.executeQuery(query);
    }

    public void addValue(String query) throws SQLException {
        openSession();
        statement.execute(query);
    }

    public void close() throws SQLException {
        statement.close();
        connection.close();
    }

}
